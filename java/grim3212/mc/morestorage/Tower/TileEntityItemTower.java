package grim3212.mc.morestorage.Tower;

import grim3212.mc.morestorage.TileEntityStorage;

public class TileEntityItemTower extends TileEntityStorage {
	ModelItemTower model = new ModelItemTower();
	public int animation = 0;

	public String getSubInvName() {
		return "Tower";
	}

	public void setAnimation(int i) {
		this.model.setAnimation(i);
	}

	@Override
	public int getSizeInventory() {
		return 18;
	}

	public int getLowestMetadata() {
		int ymod = 0;

		while (this.worldObj.getBlock(this.xCoord, this.yCoord - ymod - 1, this.zCoord) == this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord))
			ymod++;

		return this.worldObj.getBlockMetadata(this.xCoord, this.yCoord - ymod, this.zCoord);
	}
}