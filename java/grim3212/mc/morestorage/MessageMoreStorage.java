package grim3212.mc.morestorage;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageMoreStorage implements IMessage, IMessageHandler<MessageMoreStorage, IMessage> {

	private int xCoord;
	private int yCoord;
	private int zCoord;
	private int password;
	private int ID;

	public MessageMoreStorage() {
	}

	public MessageMoreStorage(int password, int ID) {
		this.password = password;
		this.ID = ID;
	}

	public MessageMoreStorage(int x, int y, int z, int ID) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.ID = ID;
	}

	public MessageMoreStorage(int x, int y, int z, int password, int ID) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.password = password;
		this.ID = ID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();
		this.password = buf.readInt();
		this.ID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(password);
		buf.writeInt(ID);
	}

	@Override
	public IMessage onMessage(MessageMoreStorage message, MessageContext ctx) {
		try {
			EntityPlayerMP entityplayer = (EntityPlayerMP) ctx.getServerHandler().playerEntity;
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
			System.out.println("Error while recieving locksmith crafting packet. SERVER.");
		}
		return null;
	}
}