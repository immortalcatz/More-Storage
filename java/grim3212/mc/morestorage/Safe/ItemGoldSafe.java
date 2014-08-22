package grim3212.mc.morestorage.Safe;

import grim3212.mc.morestorage.MoreStorageCore;
import grim3212.mc.morestorage.TileEntityStorage;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class ItemGoldSafe extends ItemBlock {
	private char ColorChar = '\u00a7';

	public ItemGoldSafe(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean par4) {
		super.addInformation(item, player, list, par4);

		if ((item.hasTagCompound()) && (item.getTagCompound().hasKey("GoldSafe"))) {
			NBTTagCompound compound = item.getTagCompound().getCompoundTag("GoldSafe");

			if (compound.getInteger("StoragePassword") > 0)
				list.add(this.ColorChar + "aLocked");

			NBTTagList taglist = compound.getTagList("Items", 10);
			if (taglist.tagCount() > 0) {
				list.add(this.ColorChar + "b" + taglist.tagCount() + this.ColorChar + "9/" + this.ColorChar + "b9 " + this.ColorChar + "7 Slots");
			} else
				list.add("Empty");
		} else {
			list.add("Empty");
		}
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if (!world.setBlock(x, y, z, MoreStorageCore.BlockGoldSafe, 0, 3)) {
			return false;
		}

		if (world.getBlock(x, y, z) == MoreStorageCore.BlockGoldSafe) {
			MoreStorageCore.BlockGoldSafe.onBlockPlacedBy(world, x, y, z, player, stack);
			MoreStorageCore.BlockGoldSafe.onPostBlockPlaced(world, x, y, z, metadata);
		}

		TileEntityStorage tileentity = (TileEntityStorage) world.getTileEntity(x, y, z);

		if ((FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT) && (stack.hasTagCompound()) && (stack.getTagCompound().hasKey("GoldSafe"))) {
			NBTTagCompound compound = stack.getTagCompound().getCompoundTag("GoldSafe");
			tileentity.setStoragePassword(compound.getInteger("StoragePassword"));

			NBTTagList nbttaglist = compound.getTagList("Items", 10);

			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound1.getByte("Slot");

				if ((j >= 0) && (j < tileentity.getSizeInventory())) {
					System.out.println("One Stack");
					tileentity.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
				}
			}
		}

		return true;
	}
}