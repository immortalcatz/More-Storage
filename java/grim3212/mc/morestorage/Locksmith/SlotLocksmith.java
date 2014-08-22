package grim3212.mc.morestorage.Locksmith;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLocksmith extends Slot {
	private boolean allowPlace = true;

	public SlotLocksmith(IInventory par1iInventory, int par2, int par3, int par4, boolean allowPlace) {
		super(par1iInventory, par2, par3, par4);

		this.allowPlace = allowPlace;
	}

	public int getSlotStackLimit() {
		return 1;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		return this.allowPlace;
	}
}