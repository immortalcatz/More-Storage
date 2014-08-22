package grim3212.mc.morestorage.Tower;

import grim3212.mc.morestorage.MoreStorageCore;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class GuiItemTower extends GuiContainer {
	private IInventory playerInventory;
	private InventoryItemTower itemTowers;
	private ContainerItemTower containerItemTower;
	private int rowID = 0;
	private static ResourceLocation GUITower = new ResourceLocation(MoreStorageCore.modID, "textures/gui/tower.png");

	public GuiItemTower(IInventory playerInventory, IInventory itemTowers) {
		super(new ContainerItemTower(playerInventory, itemTowers));
		this.containerItemTower = ((ContainerItemTower) this.inventorySlots);
		this.playerInventory = playerInventory;
		this.itemTowers = ((InventoryItemTower) itemTowers);
		this.ySize = 132;
		this.xSize += 17;
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(this.itemTowers.getInventoryName() + " - Row " + (this.rowID + 1) + " of " + this.itemTowers.getSizeInventory() / 9, 8, 6, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUITower);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

	public void scrollInventory(boolean directionDown) {
		int prevRowID = this.rowID;

		if (directionDown) {
			if (this.rowID < this.itemTowers.getSizeInventory() / 9 - 1)
				this.rowID += 1;
			else {
				this.rowID = 0;
			}

			this.itemTowers.setAnimation(1);
		} else {
			if (this.rowID > 0)
				this.rowID -= 1;
			else {
				this.rowID = (this.itemTowers.getSizeInventory() / 9 - 1);
			}

			this.itemTowers.setAnimation(-1);
		}

		if (prevRowID != this.rowID) {
			this.containerItemTower.setDisplayRow(this.rowID);
			FMLClientHandler.instance().getClient().getMinecraft().thePlayer.playSound("random.click", 1.0F, 1.0F);
		}
	}

	public void handleMouseInput() {
		super.handleMouseInput();

		int i = Mouse.getEventDWheel();
		if (i != 0) {
			if (i > 0)
				scrollInventory(true);

			if (i < 0)
				scrollInventory(false);
		}
	}

	public void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		int modx = x - (this.width - this.xSize) / 2;
		int mody = y - (this.height - this.ySize) / 2;

		if ((modx >= 173) && (modx < 186) && (mody >= 13) && (mody < 26))
			scrollInventory(true);

		if ((modx >= 173) && (modx < 186) && (mody >= 26) && (mody < 39))
			scrollInventory(false);
	}
}