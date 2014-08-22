package grim3212.mc.morestorage.Tower;

import grim3212.mc.morestorage.SlotMoveable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerItemTower extends Container {
	private IInventory itemTowers;
	private int numRows;

	public ContainerItemTower(IInventory playerInventory, IInventory itemTowers) {
		this.itemTowers = itemTowers;
		this.numRows = (itemTowers.getSizeInventory() / 9);

		itemTowers.openInventory();

		for (int j = 0; j < this.numRows; j++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new SlotMoveable(itemTowers, k + j * 9, 8 + k * 18, 18 + j * 18));
			}

		}

		// Player Inventory
		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 50 + j * 18));
			}

		}

		// Player Hotbar Inventory
		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new Slot(playerInventory, j, 8 + j * 18, 108));
		}

		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			setDisplayRow(0);
	}

	@SideOnly(Side.CLIENT)
	public void setDisplayRow(int row) {
		int minSlot = row * 9;
		int maxSlot = (row + 1) * 9;

		for (int slotIndex = 0; slotIndex < this.numRows * 9; slotIndex++) {
			if ((slotIndex >= minSlot) && (slotIndex < maxSlot)) {
				int modRow = (int) Math.floor((slotIndex - minSlot) / 9.0D);
				int modColumn = slotIndex % 9;
				((SlotMoveable) this.inventorySlots.get(slotIndex)).setSlotPosition(8 + modColumn * 18, 18 + modRow * 18);
			} else {
				((SlotMoveable) this.inventorySlots.get(slotIndex)).setSlotPosition(-9999, -9999);
			}
		}
	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.itemTowers.isUseableByPlayer(par1EntityPlayer);
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotID < this.numRows * 9) {
				if (!mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 0, this.numRows * 9, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
		this.itemTowers.closeInventory();
	}
}