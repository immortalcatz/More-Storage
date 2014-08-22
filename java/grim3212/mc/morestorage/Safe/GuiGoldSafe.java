package grim3212.mc.morestorage.Safe;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGoldSafe extends GuiContainer {
	private IInventory upperChestInventory;
	private IInventory lowerChestInventory;
	private static ResourceLocation GUIGoldSafe = new ResourceLocation("textures/gui/container/generic_54.png");
	private int inventoryRows = 0;

	public GuiGoldSafe(IInventory par1IInventory, IInventory par2IInventory) {
		super(new ContainerGoldSafe(par1IInventory, par2IInventory));
		this.upperChestInventory = par1IInventory;
		this.lowerChestInventory = par2IInventory;
		this.allowUserInput = false;
		short short1 = 222;
		int i = short1 - 108;
		this.inventoryRows = (par2IInventory.getSizeInventory() / 9);
		this.ySize = (i + this.inventoryRows * 18);
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(this.lowerChestInventory.hasCustomInventoryName() ? this.lowerChestInventory.getInventoryName() : StatCollector.translateToLocal(this.lowerChestInventory.getInventoryName()), 8, 6, 4210752);
		this.fontRendererObj.drawString(this.upperChestInventory.hasCustomInventoryName() ? this.upperChestInventory.getInventoryName() : StatCollector.translateToLocal(this.upperChestInventory.getInventoryName()), 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUIGoldSafe);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
		drawTexturedModalRect(k, l + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
	}
}