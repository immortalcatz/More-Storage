package grim3212.mc.morestorage;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCombination extends Item {
	public ItemCombination() {
		super();
		this.maxStackSize = 16;
	}

	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if ((itemstack.hasTagCompound()) && (itemstack.getTagCompound().hasKey("combo")) && (itemstack.getTagCompound().getInteger("combo") > 0)) {
			list.add("Combo: " + itemstack.getTagCompound().getInteger("combo"));
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon) {
		if (this == MoreStorageCore.ItemLocksmithLock) {
			this.itemIcon = icon.registerIcon(MoreStorageCore.modID + ":" + "locksmith_lock");
		}
		if (this == MoreStorageCore.ItemLocksmithKey) {
			this.itemIcon = icon.registerIcon(MoreStorageCore.modID + ":" + "locksmith_key");
		}
	}
}