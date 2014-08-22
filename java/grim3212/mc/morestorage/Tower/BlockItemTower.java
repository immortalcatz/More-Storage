package grim3212.mc.morestorage.Tower;

import grim3212.mc.morestorage.BlockMoreStorage;
import grim3212.mc.morestorage.MoreStorageCore;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class BlockItemTower extends BlockMoreStorage {
	public BlockItemTower(Material material) {
		super(material);
	}

	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityItemTower();
	}

	public boolean hasRotatedDoor() {
		return true;
	}

	public boolean isDoorBlocked(World world, int i, int j, int k) {
		return false;
	}

	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		TileEntity tileentity = world.getTileEntity(i, j, k);

		if ((tileentity == null) || (entityplayer.isSneaking()))
			return false;

		entityplayer.openGui(MoreStorageCore.instance, 4, world, i, j, k);

		return true;
	}
}