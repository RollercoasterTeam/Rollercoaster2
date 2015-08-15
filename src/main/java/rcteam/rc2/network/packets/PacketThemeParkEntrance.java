package rcteam.rc2.network.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import rcteam.rc2.block.te.TileEntityEntrance;
import rcteam.rc2.network.packets.PacketThemeParkEntrance.Packet;

public class PacketThemeParkEntrance extends AbstractPacket {
	public static enum Packet {
		LOGO_BG {
			@Override
			public void execute(EntityPlayer player, BlockPos pos, int numericData, String data) {
				((TileEntityEntrance) player.worldObj.getTileEntity(pos)).themePark.logo.bg = numericData;
			}
		},
		LOGO_BG_COLOUR {
			@Override
			public void execute(EntityPlayer player, BlockPos pos, int numericData, String data) {
				((TileEntityEntrance) player.worldObj.getTileEntity(pos)).themePark.logo.bgColour = numericData;
			}
		},
		LOGO_FG {
			@Override
			public void execute(EntityPlayer player, BlockPos pos, int numericData, String data) {
				((TileEntityEntrance) player.worldObj.getTileEntity(pos)).themePark.logo.fg = numericData;
			}
		},
		LOGO_FG_COLOUR {
			@Override
			public void execute(EntityPlayer player, BlockPos pos, int numericData, String data) {
				((TileEntityEntrance) player.worldObj.getTileEntity(pos)).themePark.logo.fgColour = numericData;
			}
		},
		LOGO_TEXT {
			@Override
			public void execute(EntityPlayer player, BlockPos pos, int numericData, String data) {
				((TileEntityEntrance) player.worldObj.getTileEntity(pos)).themePark.logo.text = numericData;
			}
		},
		LOGO_TEXT_COLOUR {
			@Override
			public void execute(EntityPlayer player, BlockPos pos, int numericData, String data) {
				((TileEntityEntrance) player.worldObj.getTileEntity(pos)).themePark.logo.textColour = numericData;
			}
		};
		
		public void execute(EntityPlayer player, BlockPos pos, int numericData, String data) {
			
		}
	}
	
	private int packet;
	private BlockPos pos;
	private int numericData;
	private String data;
	
	public PacketThemeParkEntrance(int packet, BlockPos pos, int numericData, String data) {
		this.packet = packet;
		this.pos = pos;
		this.numericData = numericData;
		this.data = data;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf out) {
		out.writeInt(packet);
		out.writeInt(numericData);
		PacketBuffer buffer = new PacketBuffer(out);
		buffer.writeString(data);
		buffer.writeBlockPos(pos);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf in) {
		this.packet = in.readInt();
		this.numericData = in.readInt();
		PacketBuffer buffer = new PacketBuffer(in);
		this.data = buffer.readStringFromBuffer(255);
		this.pos = buffer.readBlockPos();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		for(Packet packet : Packet.values()) {
			if(this.packet == packet.ordinal()) {
				packet.execute(player, pos, numericData, data);
				break;
			}
		}
	}

}
