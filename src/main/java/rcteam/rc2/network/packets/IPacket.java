package rcteam.rc2.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public interface IPacket {
	void encodeInto(ChannelHandlerContext ctx, ByteBuf buf);
	void decodeFrom(ChannelHandlerContext ctx, ByteBuf buf);
	void handleClientSide(EntityPlayer player);
	void handleServerSide(EntityPlayer player);
}
