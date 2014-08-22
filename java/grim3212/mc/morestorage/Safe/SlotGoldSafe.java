package grim3212.mc.morestorage.Safe;

import grim3212.mc.morestorage.MoreStorageCore;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotGoldSafe extends Slot {
	public SlotGoldSafe(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	public boolean isItemValid(ItemStack itemstack) {
		return itemstack.getItem() != Item.getItemFromBlock(MoreStorageCore.BlockGoldSafe);
	}
}