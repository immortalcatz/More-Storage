package grim3212.mc.morestorage.Tower;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryItemTower implements IInventory {
	public ArrayList<TileEntityItemTower> itemTowers = new ArrayList();
	public TileEntityItemTower mainTower;

	public InventoryItemTower(ArrayList<TileEntityItemTower> itemTowers, TileEntityItemTower mainTower) {
		this.itemTowers = itemTowers;
		this.mainTower = mainTower;
	}

	public int getSizeInventory() {
		return this.mainTower.getSizeInventory() * this.itemTowers.size();
	}

	public ItemStack getStackInSlot(int i) {
		return getInvFromSlot(i).getStackInSlot(getLocalSlot(i));
	}

	public ItemStack decrStackSize(int i, int j) {
		return getInvFromSlot(i).decrStackSize(getLocalSlot(i), j);
	}

	public ItemStack getStackInSlotOnClosing(int i) {
		return getInvFromSlot(i).getStackInSlotOnClosing(getLocalSlot(i));
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		getInvFromSlot(i).setInventorySlotContents(getLocalSlot(i), itemstack);
	}

	public String getInventoryName() {
		return this.mainTower.getInventoryName();
	}

	public boolean hasCustomInventoryName() {
		return this.mainTower.hasCustomInventoryName();
	}

	public int getInventoryStackLimit() {
		return this.mainTower.getInventoryStackLimit();
	}

	public void markDirty() {
		for (IInventory inventory : this.itemTowers)
			inventory.markDirty();
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		boolean flag = true;

		for (IInventory inventory : this.itemTowers) {
			if (!inventory.isUseableByPlayer(entityplayer))
				flag = false;
		}

		return flag;
	}

	public void openInventory() {
		for (IInventory inventory : this.itemTowers)
			inventory.openInventory();
	}

	public void closeInventory() {
		for (IInventory inventory : this.itemTowers)
			inventory.closeInventory();
	}

	public void setAnimation(int animID) {
		for (TileEntityItemTower inventory : this.itemTowers)
			inventory.setAnimation(animID);
	}

	private int getLocalSlot(int slot) {
		return slot % this.mainTower.getSizeInventory();
	}
	
	private IInventory getInvFromSlot(int slot) {
		int inventoryIndex = (int) Math.floor(slot / this.mainTower.getSizeInventory());
		return (IInventory) this.itemTowers.get(inventoryIndex);
	}

	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return getInvFromSlot(i).isItemValidForSlot(getLocalSlot(i), itemstack);
	}
}