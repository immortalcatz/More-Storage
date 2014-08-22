package grim3212.mc.morestorage.Cabinet;

import grim3212.mc.morestorage.BlockMoreStorage;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockWoodCabinet extends BlockMoreStorage {
	public BlockWoodCabinet(Material material) {
		super(material);
	}

	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityWoodCabinet();
	}
}