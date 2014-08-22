package grim3212.mc.morestorage.Safe;

import grim3212.mc.morestorage.MoreStorageCore;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityObsidianSafeRenderer extends TileEntitySpecialRenderer {
	private ModelGenericSafe model;
	private static ResourceLocation ModelTex = new ResourceLocation(MoreStorageCore.modID, "textures/modelgfx/safe.png");

	public TileEntityObsidianSafeRenderer() {
		this.model = new ModelGenericSafe();
	}

	public void renderTileEntityWoodCabinet(TileEntityObsidianSafe tileentity, double x, double y, double z, float par8) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);

		int i = 0;

		if (tileentity.getWorldObj() != null) {
			i = tileentity.getBlockMetadata();
		}

		if (i == 1) {
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-1.0F, 0.0F, 0.0F);
		} else if (i == 2) {
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-1.0F, 0.0F, -1.0F);
		} else if (i == 3) {
			GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, 0.0F, -1.0F);
		}

		bindTexture(ModelTex);
		GL11.glPushMatrix();
		this.model.doorAngle = tileentity.rotation;
		this.model.renderHandle = (!tileentity.hasPassword());
		this.model.renderModel(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
		renderTileEntityWoodCabinet((TileEntityObsidianSafe) par1TileEntity, par2, par4, par6, par8);
	}
}