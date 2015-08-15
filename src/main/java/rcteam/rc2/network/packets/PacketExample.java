package rcteam.rc2.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.io.IOException;


public class PacketExample extends AbstractPacket {
    String name;
    BlockPos pos;

    public PacketExample() {}

    public PacketExample(String name, BlockPos pos) {
        this.name = name;
	    this.pos = pos;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf out) {
        PacketBuffer buffer = new PacketBuffer(out);
	    buffer.writeString(name);
	    buffer.writeBlockPos(pos);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf in) {
        PacketBuffer buffer = new PacketBuffer(in);
	    name = buffer.readStringFromBuffer(250);//The argument it max size that the string can be
	    pos = buffer.readBlockPos();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        World world = player.worldObj; //Server side world.
    }
}
