package rcteam.rc2.network.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import rcteam.rc2.block.te.TileEntityEntrance;
import rcteam.rc2.network.packets.PacketThemeParkEntrance.Packet;

public class PacketThemeParkEntrance implements IPacket {
	public enum Packet {
		LOGO_BG,
		LOGO_BG_COLOR,
		LOGO_FG,
		LOGO_FG_COLOR,
		LOGO_TEXT,
		LOGO_TEXT_COLOR;

		public void execute(EntityPlayer player, BlockPos pos, int numericData, String data) {
			switch (this) {
				case LOGO_BG: ((TileEntityEntrance) player.worldObj.getTileEntity(pos)).themePark.logo.bg = numericData; break;
				case LOGO_BG_COLOR: ((TileEntityEntrance) player.worldObj.getTileEntity(pos)).themePark.logo.bgColour = numericData; break;
				case LOGO_FG: ((TileEntityEntrance) player.worldObj.getTileEntity(pos)).themePark.logo.fg = numericData; break;
				case LOGO_FG_COLOR: ((TileEntityEntrance) player.worldObj.getTileEntity(pos)).themePark.logo.fgColour = numericData; break;
				case LOGO_TEXT: ((TileEntityEntrance) player.worldObj.getTileEntity(pos)).themePark.logo.text = numericData; break;
				case LOGO_TEXT_COLOR: ((TileEntityEntrance) player.worldObj.getTileEntity(pos)).themePark.logo.textColour = numericData; break;
			}
		}
	}
	
	private Packet packet;
	private BlockPos pos;
	private int numericData;
	private String data;
	
	public PacketThemeParkEntrance(int packet, BlockPos pos, int numericData, String data) {
		this.packet = Packet.values()[packet];
		this.pos = pos;
		this.numericData = numericData;
		this.data = data;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf out) {
		out.writeInt(this.packet.ordinal());
		out.writeInt(this.numericData);
		PacketBuffer buffer = new PacketBuffer(out);
		buffer.writeString(this.data);
		buffer.writeBlockPos(this.pos);
	}

	@Override
	public void decodeFrom(ChannelHandlerContext ctx, ByteBuf in) {
		this.packet = Packet.values()[in.readInt()];
		this.numericData = in.readInt();
		PacketBuffer buffer = new PacketBuffer(in);
		this.data = buffer.readStringFromBuffer(255);
		this.pos = buffer.readBlockPos();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {}

	@Override
	public void handleServerSide(EntityPlayer player) {
		this.packet.execute(player, this.pos, this.numericData, this.data);
	}
}
