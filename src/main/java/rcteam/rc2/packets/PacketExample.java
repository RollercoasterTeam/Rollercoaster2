package rcteam.rc2.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import java.io.IOException;


public class PacketExample extends AbstractPacket {
    public PacketExample() {
    }
    String name;


    int x, y, z;

    public PacketExample(String name, int x, int y, int z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf out) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        PacketBuffer buffer = new PacketBuffer(out);
        try {
            buffer.writeStringToBuffer(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf in) {
        this.x = in.readInt();
        this.y = in.readInt();
        this.z = in.readInt();
        PacketBuffer buffer = new PacketBuffer(in);
        try {
            name = buffer.readStringFromBuffer(250);//The argument it max size that the string can be
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        World world = player.worldObj; //Server side world.
    }
}
