package rcteam.rc2.network.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import rcteam.rc2.network.packets.PacketThemeParkEntrance.Packet;

public class PacketThemeParkEntrance extends AbstractPacket {

	public static enum Packet {
		LOGO_BG {
			@Override
			public void execute() {
				
			}
		},
		LOGO_BG_COLOUR {
			@Override
			public void execute() {
				
			}
		},
		LOGO_FG {
			@Override
			public void execute() {
				
			}
		},
		LOGO_FG_COLOUR {
			@Override
			public void execute() {
				
			}
		},
		LOGO_TEXT {
			@Override
			public void execute() {
				
			}
		},
		LOGO_TEXT_COLOUR {
			@Override
			public void execute() {
				
			}
		};
		
		public void execute() {
			
		}
	}
	
	public int packet;
	public int x, y, z;
	public int numericData;
	public String data;
	
	public PacketThemeParkEntrance(int packet, int x, int y, int z, int numericData, String data) {
		this.packet = packet;
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.numericData = numericData;
		this.data = data;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf out) {
		out.writeInt(packet);
		
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		
		out.writeInt(numericData);
		
		PacketBuffer buffer = new PacketBuffer(out);
		try {
			buffer.writeStringToBuffer(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf in) {
		this.packet = in.readInt();
		
		this.x = in.readInt();
		this.y = in.readInt();
		this.z = in.readInt();
		
		this.numericData = in.readInt();
		
		PacketBuffer buffer = new PacketBuffer(in);
		try {
			this.data = buffer.readStringFromBuffer(255);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		for(Packet packet : Packet.values()) {
			if(this.packet == packet.ordinal()) {
				packet.execute();
				break;
			}
		}
	}

}
