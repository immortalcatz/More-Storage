package grim3212.mc.morestorage.Locksmith;

import grim3212.mc.core.packet.PacketPipeline;
import grim3212.mc.morestorage.ItemCombination;
import grim3212.mc.morestorage.MoreStorageCore;
import grim3212.mc.morestorage.MoreStoragePacket;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLocksmithWorkbench extends GuiContainer {
	public String password = "";
	private static ResourceLocation GUILocksmith = new ResourceLocation(MoreStorageCore.modID, "textures/gui/locksmith.png");

	public GuiLocksmithWorkbench(InventoryPlayer inventory, World world, int x, int y, int z) {
		super(new ContainerLocksmithWorkbench(inventory, world, x, y, z));
	}

	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
	}

	public void keyTyped(char c, int i) {
		super.keyTyped(c, i);

		if ((i == 14) && (this.password.length() > 0)) {
			this.password = this.password.substring(0, this.password.length() - 1);
			this.mc.theWorld.playSoundEffect(1.0D, 1.0D, 1.0D, "random.wood_click", 1.0F, 1.0F);
		}

		if ((this.password.length() < 5) && (Character.isDigit(c))) {
			this.password += c;
			this.mc.theWorld.playSoundEffect(1.0D, 1.0D, 1.0D, "random.wood_click", 1.0F, 1.0F);
		}

		if ((i == 28) && (this.password.length() > 0)) {
			ItemStack leftslot = this.inventorySlots.getSlot(0).getStack();
			if ((this.password.length() > 0) && (leftslot != null) && ((leftslot.getItem() instanceof ItemCombination))) {
				this.mc.theWorld.playSoundEffect(1.0D, 1.0D, 1.0D, "random.wood_click", 1.0F, 1.0F);

				PacketPipeline.sendToServer(new MoreStoragePacket(Integer.parseInt(this.password), 0));
			}
		}
	}

	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString("Locksmith Workbench", 38, 6, 4210752);
		this.fontRendererObj.drawString("Type Combo", 59, 17, 7368816);
		this.fontRendererObj.drawString(this.password, 74, 40, 22015);
		ItemStack leftslot = this.inventorySlots.getSlot(0).getStack();
		if ((this.password.length() > 0) && (leftslot != null) && ((leftslot.getItem() instanceof ItemCombination)))
			this.fontRendererObj.drawString("Press Enter to Craft", 37, 60, 28672);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	public void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUILocksmith);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}