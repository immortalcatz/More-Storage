package grim3212.mc.morestorage.Safe;

import grim3212.mc.morestorage.TileEntityStorage;

public class TileEntityGoldSafe extends TileEntityStorage {
	public String getSubInvName() {
		return "Gold Safe";
	}

	public int getSizeInventory() {
		return 9;
	}
}