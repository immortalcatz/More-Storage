package grim3212.mc.morestorage.Locker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryDualLocker implements IInventory {
	private IInventory topLocker;
	private IInventory bottomLocker;

	public InventoryDualLocker(IInventory bottomLocker, IInventory topLocker) {
		this.bottomLocker = bottomLocker;
		this.topLocker = topLocker;
	}

	public int getSizeInventory() {
		return this.topLocker.getSizeInventory() + this.bottomLocker.getSizeInventory();
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
		return this.bottomLocker.getInventoryName();
	}

	public boolean hasCustomInventoryName() {
		return this.bottomLocker.hasCustomInventoryName();
	}

	public int getInventoryStackLimit() {
		return this.bottomLocker.getInventoryStackLimit();
	}

	public void markDirty() {
		this.bottomLocker.markDirty();
		this.topLocker.markDirty();
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return (this.topLocker.isUseableByPlayer(entityplayer)) && (this.bottomLocker.isUseableByPlayer(entityplayer));
	}

	public void openInventory() {
		this.bottomLocker.openInventory();
	}

	public void closeInventory() {
		this.bottomLocker.closeInventory();
	}

	private int getLocalSlot(int slot) {
		if (getInvFromSlot(slot) == this.bottomLocker)
			return slot;
		return slot - this.bottomLocker.getSizeInventory();
	}

	private IInventory getInvFromSlot(int slot) {
		return slot < this.bottomLocker.getSizeInventory() ? this.bottomLocker : this.topLocker;
	}

	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return getInvFromSlot(i).isItemValidForSlot(getLocalSlot(i), itemstack);
	}

}