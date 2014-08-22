package grim3212.mc.morestorage;

import grim3212.mc.core.packet.PacketPipeline;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityStorage extends TileEntity implements IInventory {
	private String customName;
	private int numUsingPlayers = 0;
	public int rotation = 0;
	private boolean direction = false;
	private ItemStack[] contents;
	private int storagePassword = -1;
	public boolean hasInitialData = false;
	private int hasInitialDataTimeout = 0;

	public TileEntityStorage() {
		this.contents = new ItemStack[getSizeInventory()];
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.storagePassword = nbttagcompound.getInteger("StoragePassword");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		this.contents = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 0xFF;

			if ((j >= 0) && (j < this.contents.length)) {
				this.contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("StoragePassword", this.storagePassword);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.contents.length; i++) {
			if (this.contents[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.contents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	@SideOnly(Side.CLIENT)
	public boolean receiveClientEvent(int eventID, int eventInput) {
		if (eventID == 1) {
			this.numUsingPlayers = eventInput;
			return true;
		}
		if (eventID == 2) {
			this.storagePassword = eventInput;
			return true;
		}

		return super.receiveClientEvent(eventID, eventInput);
	}

	public void sendStoragePassword() {
		PacketPipeline.sendToServer(new MoreStoragePacket(xCoord, yCoord, zCoord, storagePassword, 2));
	}

	public void setStoragePassword(int password) {
		this.storagePassword = password;

		if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT)
			sendStoragePassword();
	}

	public void clearStoragePassword() {
		this.storagePassword = -1;

		if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT)
			sendStoragePassword();
	}

	public boolean hasPassword() {
		return this.storagePassword > 0;
	}

	public int getPassword() {
		return this.storagePassword;
	}

	public void updateEntity() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			if ((!this.hasInitialData) && (this.hasInitialDataTimeout <= 0)) {

				PacketPipeline.sendToServer(new MoreStoragePacket(xCoord, yCoord, zCoord, 1));

				this.hasInitialDataTimeout = 10;
			}

			if (this.hasInitialDataTimeout > 0)
				this.hasInitialDataTimeout -= 1;
		}

		boolean prevdirection = this.direction;
		this.direction = (this.numUsingPlayers > 0);

		if ((!prevdirection) && (this.direction))
			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, getOpenSound(), 1.0F, 1.0F);

		int prevrotation = this.rotation;
		int addspeed = (int) (Math.abs(90 - this.rotation) / 10.0F);

		if (this.direction) {
			if (this.rotation < 90) {
				this.rotation += 6 + addspeed;
			}
		} else if (this.rotation > 0) {
			this.rotation -= 6 + addspeed;
		}

		if (this.rotation < 0)
			this.rotation = 0;
		if (this.rotation > 90)
			this.rotation = 90;

		if ((prevrotation > this.rotation) && (this.rotation == 0)) {
			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, getCloseSound(), 1.0F, 1.0F);
		}
	}

	protected String getOpenSound() {
		return "random.door_open";
	}

	protected String getCloseSound() {
		return "random.door_close";
	}

	public void openInventory() {
		this.numUsingPlayers += 1;
		if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) != Blocks.air)
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord), 1, this.numUsingPlayers);
	}

	public void closeInventory() {
		this.numUsingPlayers -= 1;
		if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) != Blocks.air)
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord), 1, this.numUsingPlayers);
	}

	public void invalidate() {
		super.invalidate();
	}

	public String getInventoryName() {
		return this.customName != null ? this.customName : getSubInvName();
	}

	public String getSubInvName() {
		return "NoName";
	}

	public boolean hasCustomInventoryName() {
		return false;
	}

	public int getSizeInventory() {
		return 27;
	}

	public ItemStack decrStackSize(int i, int j) {
		if ((hasPassword()) && (this.numUsingPlayers == 0))
			return null;

		if (this.contents[i] != null) {
			if (this.contents[i].stackSize <= j) {
				ItemStack itemstack = this.contents[i];
				this.contents[i] = null;
				markDirty();
				return itemstack;
			}

			ItemStack itemstack = this.contents[i].splitStack(j);

			if (this.contents[i].stackSize == 0) {
				this.contents[i] = null;
			}

			markDirty();
			return itemstack;
		}

		return null;
	}

	public ItemStack getStackInSlot(int i) {
		if ((hasPassword()) && (this.numUsingPlayers == 0))
			return null;
		return this.contents[i];
	}

	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	public ItemStack getStackInSlotNoLockBlock(int i) {
		return this.contents[i];
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.contents[i] = itemstack;

		if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit())) {
			itemstack.stackSize = getInventoryStackLimit();
		}

		markDirty();
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if ((FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) && (!this.hasInitialData))
			return false;
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this;
	}

	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return !hasPassword();
	}
}