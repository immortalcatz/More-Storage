package grim3212.mc.morestorage.Locksmith;

import grim3212.mc.core.Grim3212Core;
import grim3212.mc.morestorage.MoreStorageCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLocksmithWorkbench extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon textureTop;

	@SideOnly(Side.CLIENT)
	private IIcon textureFront;

	public BlockLocksmithWorkbench() {
		super(Material.wood);
		setCreativeTab(Grim3212Core.tabGrimStuff);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		switch (side) {
		case 0:
			return Blocks.planks.getIcon(side, metadata);
		case 1:
			return this.textureTop;
		case 2:
			return this.textureFront;
		case 3:
			return this.textureFront;
		}
		return this.blockIcon;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon(MoreStorageCore.modID + ":" + "locksmith_side");
		this.textureTop = par1IconRegister.registerIcon(MoreStorageCore.modID + ":" + "locksmith_top");
		this.textureFront = par1IconRegister.registerIcon(MoreStorageCore.modID + ":" + "locksmith_front");
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		entityplayer.openGui(MoreStorageCore.instance, 1, world, i, j, k);

		return true;
	}
}