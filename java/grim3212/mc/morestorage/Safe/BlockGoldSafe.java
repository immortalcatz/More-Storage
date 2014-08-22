package grim3212.mc.morestorage.Safe;

import grim3212.mc.morestorage.BlockMoreStorage;
import grim3212.mc.morestorage.MoreStorageCore;
import grim3212.mc.morestorage.TileEntityStorage;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGoldSafe extends BlockMoreStorage {
	public BlockGoldSafe(Material material) {
		super(material);

		this.customBreakManager = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityGoldSafe();
	}

	public void breakBlock(World world, int i, int j, int k, Block par5, int par6) {
		TileEntity tileentity = world.getTileEntity(i, j, k);

		if ((tileentity != null) && ((tileentity instanceof TileEntityStorage))) {
			TileEntityStorage storage = (TileEntityStorage) tileentity;
			ItemStack item = new ItemStack(MoreStorageCore.BlockGoldSafe, 1, 0);

			boolean invFlag = false;
			for (int slot = 0; slot < storage.getSizeInventory(); slot++) {
				if (storage.getStackInSlotNoLockBlock(slot) != null)
					invFlag = true;

			}

			if ((storage.hasPassword()) || (invFlag)) {
				NBTTagCompound mcompound = new NBTTagCompound();
				item.setTagCompound(mcompound);

				NBTTagCompound compound = new NBTTagCompound();
				compound.setInteger("StoragePassword", storage.getPassword());

				storage.clearStoragePassword();

				NBTTagList nbttaglist = new NBTTagList();
				for (int slot = 0; slot < storage.getSizeInventory(); slot++) {
					if (storage.getStackInSlotNoLockBlock(slot) != null) {
						NBTTagCompound nbttagcompound1 = new NBTTagCompound();
						nbttagcompound1.setByte("Slot", (byte) slot);
						storage.getStackInSlotNoLockBlock(slot).writeToNBT(nbttagcompound1);
						nbttaglist.appendTag(nbttagcompound1);

						storage.setInventorySlotContents(slot, null);
					}
				}
				compound.setTag("Items", nbttaglist);

				mcompound.setTag("GoldSafe", compound);
			}

			dropBlockAsItem(world, i, j, k, item);
		}

		super.breakBlock(world, i, j, k, par5, par6);
	}

	public int quantityDropped(Random random) {
		return 0;
	}

	public int getLocalGuiID() {
		return 5;
	}
}