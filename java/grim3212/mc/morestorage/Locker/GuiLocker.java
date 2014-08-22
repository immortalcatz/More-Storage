package grim3212.mc.morestorage.Locker;

import grim3212.mc.morestorage.MoreStorageCore;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class GuiLocker extends GuiContainer {
	private static ResourceLocation GUILocker = new ResourceLocation(MoreStorageCore.modID, "textures/gui/locker.png");
	private IInventory playerInventory;
	private IInventory lockerInventory;
	private ContainerLocker containerLocker;
	private int rowID = 0;

	public GuiLocker(IInventory playerInventory, IInventory lockerInventory) {
		super(new ContainerLocker(playerInventory, lockerInventory));
		this.containerLocker = ((ContainerLocker) this.inventorySlots);
		this.playerInventory = playerInventory;
		this.lockerInventory = lockerInventory;
		this.ySize = 204;
		this.xSize += 17;
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(this.lockerInventory.getInventoryName(), 8, 6, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUILocker);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		GL11.glEnable(3042);
		drawTexturedModalRect(k + (this.xSize - 18), l + 20 + this.rowID, this.xSize, 0, 10, 5);
		GL11.glDisable(3042);
	}

	public void scrollInventory(boolean directionDown) {
		int prevRowID = this.rowID;

		if (directionDown) {
			if (this.rowID < 5)
				this.rowID += 1;
		} else if (this.rowID > 0)
			this.rowID -= 1;

		if (prevRowID != this.rowID) {
			this.containerLocker.setDisplayRow(this.rowID);
			FMLClientHandler.instance().getClient().getMinecraft().thePlayer.playSound("random.click", 1.0F, 1.0F);
		}
	}

	public void handleMouseInput() {
		super.handleMouseInput();

		int i = Mouse.getEventDWheel();
		if (i != 0) {
			if (i > 0)
				scrollInventory(false);

			if (i < 0)
				scrollInventory(true);
		}
	}

	public void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		int modx = x - (this.width - this.xSize) / 2;
		int mody = y - (this.height - this.ySize) / 2;

		if ((modx >= 173) && (modx < 186) && (mody >= 7) && (mody < 20))
			scrollInventory(false);

		if ((modx >= 173) && (modx < 186) && (mody >= 30) && (mody < 43))
			scrollInventory(true);
	}
}