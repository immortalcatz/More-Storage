package grim3212.mc.morestorage;

import grim3212.mc.core.packet.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MoreStoragePacket extends AbstractPacket {

	private int xCoord;
	private int yCoord;
	private int zCoord;
	private int password;
	private int ID;

	public MoreStoragePacket() {
	}

	public MoreStoragePacket(int password, int ID) {
		this.password = password;
		this.ID = ID;
	}

	public MoreStoragePacket(int x, int y, int z, int ID) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.ID = ID;
	}

	public MoreStoragePacket(int x, int y, int z, int password, int ID) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.password = password;
		this.ID = ID;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(xCoord);
		buffer.writeInt(yCoord);
		buffer.writeInt(zCoord);
		buffer.writeInt(password);
		buffer.writeInt(ID);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.xCoord = buffer.readInt();
		this.yCoord = buffer.readInt();
		this.zCoord = buffer.readInt();
		this.password = buffer.readInt();
		this.ID = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		try {
			EntityPlayer entityplayer = (EntityPlayer) player;
			if (ID == 2) {
				World world = entityplayer.worldObj;
				TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
				if ((tileentity != null) && ((tileentity instanceof TileEntityStorage))) {
					TileEntityStorage tileentitystorage = (TileEntityStorage) tileentity;
					tileentitystorage.setStoragePassword(password);
					tileentitystorage.hasInitialData = true;
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		try {
			EntityPlayerMP entityplayer = (EntityPlayerMP) player;
			if (ID == 0) {
				ItemStack oldstack = entityplayer.openContainer.getSlot(0).getStack();
				if (entityplayer.openContainer.getSlot(1).getStack() == null) {
					MoreStorageUtility.setCombination(oldstack, password);
					entityplayer.openContainer.getSlot(1).putStack(oldstack);
					entityplayer.openContainer.getSlot(0).putStack(null);
				}
			}
			if (ID == 1) {
				World world = entityplayer.worldObj;
				TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
				if ((tileentity != null) && ((tileentity instanceof TileEntityStorage))) {
					TileEntityStorage tileentitystorage = (TileEntityStorage) tileentity;
					tileentitystorage.sendStoragePassword();
				}
			}
		} catch (Exception e) {
			System.out.println("Error while recieving locksmith crafting packet. SERVER.");
		}
	}
}