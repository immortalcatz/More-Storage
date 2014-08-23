package grim3212.mc.morestorage;

import grim3212.mc.core.Grim3212Core;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMoreStorage extends BlockContainer {
	protected boolean customBreakManager = false;

	public BlockMoreStorage(Material material) {
		super(material);
		setCreativeTab(Grim3212Core.tabGrimStuff);
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack itemstack) {
		if (hasRotatedDoor()) {
			int l = MathHelper.floor_double(entityliving.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;

			if (l == 0) {
				world.setBlockMetadataWithNotify(i, j, k, 2, 3);
			}

			if (l == 1) {
				world.setBlockMetadataWithNotify(i, j, k, 1, 3);
			}

			if (l == 3) {
				world.setBlockMetadataWithNotify(i, j, k, 3, 3);
			}

			if (l == 2)
				world.setBlockMetadataWithNotify(i, j, k, 0, 3);
		}
	}

	public void onBlockRemoval(World world, int i, int j, int k) {
	}

	public boolean canPlaceTorchOnTop(World world, int i, int j, int k) {
		return true;
	}

	public float getPlayerRelativeBlockHardness(EntityPlayer entityplayer, World world, int i, int j, int k) {
		TileEntityStorage tileentity = (TileEntityStorage) world.getTileEntity(i, j, k);

		if ((tileentity.hasPassword()) && (!MoreStorageUtility.canAccess(tileentity, entityplayer)))
			return -1.0F;

		return super.getPlayerRelativeBlockHardness(entityplayer, world, i, j, k);
	}

	public void breakBlock(World world, int i, int j, int k, Block par5, int par6) {
		TileEntity tileentity = world.getTileEntity(i, j, k);

		if ((FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT) && (!this.customBreakManager) && (tileentity != null) && ((tileentity instanceof TileEntityStorage))) {
			TileEntityStorage storage = (TileEntityStorage) tileentity;
			if (storage.hasPassword()) {
				ItemStack newlock = new ItemStack(MoreStorageCore.ItemLocksmithLock, 1, 0);
				MoreStorageUtility.setCombination(newlock, storage.getPassword());
				dropBlockAsItem(world, i, j, k, newlock);
				storage.clearStoragePassword();
			}

			for (int slot = 0; slot < storage.getSizeInventory(); slot++) {
				if (storage.getStackInSlot(slot) != null) {
					dropBlockAsItem(world, i, j, k, storage.getStackInSlot(slot));
					storage.setInventorySlotContents(slot, null);
				}
			}
		}

		super.breakBlock(world, i, j, k, par5, par6);
	}

	protected boolean isInvalidBlock(World world, int i, int j, int k) {
		return world.getBlock(i, j, k) != Blocks.air ? world.getBlock(i, j, k).isAir(world, i, j, k) : false;
	}

	public boolean hasRotatedDoor() {
		return true;
	}

	public boolean isDoorBlocked(World world, int i, int j, int k) {
		int metadata = world.getBlockMetadata(i, j, k);

		switch (metadata) {
		case 0:
			return isInvalidBlock(world, i, j, k + 1);
		case 1:
			return isInvalidBlock(world, i + 1, j, k);
		case 2:
			return isInvalidBlock(world, i, j, k - 1);
		case 3:
			return isInvalidBlock(world, i - 1, j, k);
		}

		return false;
	}

	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
		ItemStack key = entityplayer.inventory.getCurrentItem();

		if ((key != null) && (key == new ItemStack(MoreStorageCore.ItemLocksmithKey))) {
			TileEntityStorage tileentity = (TileEntityStorage) world.getTileEntity(i, j, k);

			if ((tileentity.hasPassword()) && (MoreStorageUtility.canAccess(tileentity, entityplayer))) {
				if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT) {
					ItemStack itemstack = new ItemStack(MoreStorageCore.ItemLocksmithLock, 1, 0);
					MoreStorageUtility.setCombination(itemstack, tileentity.getPassword());
					dropBlockAsItem(world, i, j, k, itemstack);
				}

				tileentity.clearStoragePassword();
			}
		}
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		TileEntity tileentity = world.getTileEntity(i, j, k);

		if ((tileentity == null) || (entityplayer.isSneaking()))
			return false;

		if (MoreStorageUtility.tryPlaceLock((TileEntityStorage) tileentity, entityplayer))
			return true;

		if ((!isDoorBlocked(world, i, j, k)) && (MoreStorageUtility.canAccess((TileEntityStorage) tileentity, entityplayer)))
			entityplayer.openGui(MoreStorageCore.instance, getLocalGuiID(), world, i, j, k);

		return true;
	}

	protected int getLocalGuiID() {
		return 0;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return MoreStorageCore.CustomRendererID;
	}

	public int damageDropped(int i) {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityStorage();
	}
}