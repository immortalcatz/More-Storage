package grim3212.mc.morestorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MoreStorageUtility {
	public static void setCombination(ItemStack itemstack, int combo) {
		NBTTagCompound c = itemstack.hasTagCompound() ? itemstack.getTagCompound() : new NBTTagCompound();
		c.setInteger("combo", combo);
		itemstack.setTagCompound(c);
	}

	public static int getCombination(ItemStack itemstack) {
		NBTTagCompound c = itemstack.getTagCompound();

		if ((c != null) && (c.hasKey("combo")))
			return c.getInteger("combo");

		return 0;
	}

	public static boolean canAccess(TileEntityStorage tileentity, EntityPlayer entityplayer) {
		if (tileentity.hasPassword()) {
			for (int slot = 0; slot < entityplayer.inventory.getSizeInventory(); slot++) {
				ItemStack itemstack = entityplayer.inventory.getStackInSlot(slot);

				if ((itemstack != null) && (itemstack.getItem() != null) && (itemstack.getItem() == MoreStorageCore.ItemLocksmithKey) && (itemstack.getTagCompound() != null) && (itemstack.getTagCompound().hasKey("combo"))) {
					return itemstack.getTagCompound().getInteger("combo") == tileentity.getPassword();
				}

			}

			return false;
		}

		return true;
	}

	public static boolean tryPlaceLock(TileEntityStorage tileentity, EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem];

		if ((itemstack != null) && (itemstack.getItem() == MoreStorageCore.ItemLocksmithLock) && (itemstack.hasTagCompound()) && (itemstack.getTagCompound().hasKey("combo")) && (itemstack.getTagCompound().getInteger("combo") > 0)) {
			entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
			tileentity.setStoragePassword(itemstack.getTagCompound().getInteger("combo"));
			return true;
		}

		return false;
	}
}