package grim3212.mc.morestorage.Locker;

import grim3212.mc.morestorage.TileEntityStorage;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLocker extends TileEntityStorage {
	public String getSubInvName() {
		return "Locker";
	}

	public int getSizeInventory() {
		return 45;
	}

	public TileEntity getUpperLocker() {
		if ((this.worldObj == null) || (!hasUpperLocker()))
			return null;
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord);
	}

	public boolean hasUpperLocker() {
		if (this.worldObj == null)
			return false;
		return (this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) == this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord)) && (this.worldObj.getBlockMetadata(this.xCoord, this.yCoord + 1, this.zCoord) == this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
	}

	public boolean isUpperLocker() {
		if (this.worldObj == null)
			return false;
		return (this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord)) && (this.worldObj.getBlockMetadata(this.xCoord, this.yCoord - 1, this.zCoord) == this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
	}
}