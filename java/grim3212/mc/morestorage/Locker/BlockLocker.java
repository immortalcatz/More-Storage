package grim3212.mc.morestorage.Locker;

import grim3212.mc.morestorage.BlockMoreStorage;
import grim3212.mc.morestorage.MoreStorageCore;
import grim3212.mc.morestorage.MoreStorageUtility;
import grim3212.mc.morestorage.TileEntityStorage;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockLocker extends BlockMoreStorage {
	public BlockLocker(Material material) {
		super(material);
	}

	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityLocker();
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack itemstack) {
		if (((world.getBlock(i, j - 1, k) == MoreStorageCore.BlockLocker) && (world.getBlock(i, j - 2, k) == MoreStorageCore.BlockLocker)) || ((world.getBlock(i, j + 1, k) == MoreStorageCore.BlockLocker) && (world.getBlock(i, j + 2, k) == MoreStorageCore.BlockLocker))) {
			return;
		}

		super.onBlockPlacedBy(world, i, j, k, entityliving, itemstack);

		TileEntity tileentitytop = world.getTileEntity(i, j + 1, k);
		if ((isBottomLocker(world, i, j, k)) && (tileentitytop != null) && ((tileentitytop instanceof TileEntityStorage))) {
			TileEntityStorage tileentitythis = (TileEntityStorage) world.getTileEntity(i, j, k);
			TileEntityStorage tileentitystoragetop = (TileEntityStorage) tileentitytop;

			if (tileentitystoragetop.hasPassword()) {
				tileentitythis.setStoragePassword(tileentitystoragetop.getPassword());
				tileentitystoragetop.clearStoragePassword();
			}
		}
	}

	public float getPlayerRelativeBlockHardness(EntityPlayer entityplayer, World world, int i, int j, int k) {
		if ((isDualLocker(world, i, j, k)) && (isTopLocker(world, i, j, k))) {
			return getPlayerRelativeBlockHardness(entityplayer, world, i, j - 1, k);
		}

		return super.getPlayerRelativeBlockHardness(entityplayer, world, i, j, k);
	}

	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if ((isDualLocker(world, i, j, k)) && (isTopLocker(world, i, j, k))) {
			onBlockClicked(world, i, j - 1, k, entityplayer);
			return;
		}

		super.onBlockClicked(world, i, j, k, entityplayer);
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		TileEntity tileentitytop = null;
		if ((tileentity == null) || (entityplayer.isSneaking()))
			return false;

		if (isDualLocker(world, i, j, k)) {
			if (isTopLocker(world, i, j, k))
				return onBlockActivated(world, i, j - 1, k, entityplayer, par6, par6, par8, par9);

			tileentitytop = world.getTileEntity(i, j + 1, k);

			if (MoreStorageUtility.tryPlaceLock((TileEntityStorage) tileentity, entityplayer))
				return true;

			if ((!isDoorBlocked(world, i, j, k)) && (!isDoorBlocked(world, i, j + 1, k)) && (MoreStorageUtility.canAccess((TileEntityStorage) tileentity, entityplayer))) {
				entityplayer.openGui(MoreStorageCore.instance, 3, world, i, j, k);
			}
		} else {
			if (MoreStorageUtility.tryPlaceLock((TileEntityStorage) tileentity, entityplayer))
				return true;

			if ((!isDoorBlocked(world, i, j, k)) && (MoreStorageUtility.canAccess((TileEntityStorage) tileentity, entityplayer)))
				entityplayer.openGui(MoreStorageCore.instance, 2, world, i, j, k);
		}

		return true;
	}

	public boolean isDualLocker(World world, int i, int j, int k) {
		return (isTopLocker(world, i, j, k)) || (isBottomLocker(world, i, j, k));
	}

	public boolean isTopLocker(World world, int i, int j, int k) {
		return (world.getBlock(i, j - 1, k) == MoreStorageCore.BlockLocker) && (world.getBlockMetadata(i, j - 1, k) == world.getBlockMetadata(i, j, k));
	}

	public boolean isBottomLocker(World world, int i, int j, int k) {
		return (world.getBlock(i, j + 1, k) == MoreStorageCore.BlockLocker) && (world.getBlockMetadata(i, j + 1, k) == world.getBlockMetadata(i, j, k));
	}

	public boolean hasRotatedDoor() {
		return true;
	}
}