package grim3212.mc.morestorage.Crate;

import grim3212.mc.morestorage.BlockMoreStorage;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockWarehouseCrate extends BlockMoreStorage {
	public BlockWarehouseCrate(Material material) {
		super(material);
	}

	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityWarehouseCrate();
	}

	public boolean hasRotatedDoor() {
		return true;
	}

	public boolean isDoorBlocked(World world, int i, int j, int k) {
		return isInvalidBlock(world, i, j + 1, k);
	}
}