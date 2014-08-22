package grim3212.mc.morestorage.Locksmith;

import grim3212.mc.morestorage.MoreStorageCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerLocksmithWorkbench extends Container {
	public InventoryBasic craftingSlots = new InventoryBasic(null, true, 2);
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;

	public ContainerLocksmithWorkbench(InventoryPlayer inventory, World world, int x, int y, int z) {
		this.worldObj = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;

		addSlotToContainer(new SlotLocksmith(this.craftingSlots, 0, 49, 35, true));

		addSlotToContainer(new SlotLocksmith(this.craftingSlots, 1, 112, 35, false));

		for (int l = 0; l < 3; l++) {
			for (int i1 = 0; i1 < 9; i1++) {
				addSlotToContainer(new Slot(inventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
			}
		}

		for (int l = 0; l < 9; l++) {
			addSlotToContainer(new Slot(inventory, l, 8 + l * 18, 142));
		}
	}

	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);

		if (!this.worldObj.isRemote)
        {
            for (int i = 0; i < 2; ++i)
            {
                ItemStack itemstack = this.craftingSlots.getStackInSlotOnClosing(i);

                if (itemstack != null)
                {
                    par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getBlock(this.posX, this.posY, this.posZ) == MoreStorageCore.BlockLocksmithWorkbench;
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		return null;
	}
}