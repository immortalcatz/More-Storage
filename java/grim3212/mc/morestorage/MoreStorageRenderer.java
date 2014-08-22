package grim3212.mc.morestorage;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoreStorageRenderer implements ISimpleBlockRenderingHandler {
	
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if (MoreStorageCore.DefaultTileEntityMap.containsKey(block)) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, -0.11F, 0.0F);
			TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity) MoreStorageCore.DefaultTileEntityMap.get(block), 0.0D, 0.0D, 0.0D, 0.0F);
			GL11.glPopMatrix();
		}
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return true;
	}

	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	public int getRenderId() {
		return MoreStorageCore.CustomRendererID;
	}
}