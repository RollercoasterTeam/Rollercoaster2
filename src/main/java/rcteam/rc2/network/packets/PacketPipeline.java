package rcteam.rc2.network.packets;

import com.google.common.collect.Lists;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import rcteam.rc2.RC2;

import java.util.*;

/**
 * Packet pipeline class. Directs all registered packet data to be handled by
 * the packets themselves.
 *
 * @author sirgingalot some code from: cpw
 */
@ChannelHandler.Sharable
public class PacketPipeline extends MessageToMessageCodec<FMLProxyPacket, IPacket> {
    private void registerPackets() {
        registerPacket(PacketThemeParkEntrance.class);
        registerPacket(PacketUpdateHammer.class);
        registerPacket(PacketUpdateSupportInfo.class);
    }

    private EnumMap<Side, FMLEmbeddedChannel> channels;
    private LinkedList<Class<? extends IPacket>> packets = Lists.newLinkedList();
    private boolean isPostInitialized = false;

    /**
     * Register your packet with the pipeline. Discriminators are automatically
     * set.
     *
     * @param clazz the class to register
     * @return whether registration was successful. Failure may occur if 256
     * packets have been registered or if the registry already contains
     * this packet
     */
    public boolean registerPacket(Class<? extends IPacket> clazz) {
        if (this.packets.size() > 256) {
            RC2.logger.error("Packet registration failed for %s because the number of registered packets exceeds 256!", clazz.toString());
            return false;
        }

        if (this.packets.contains(clazz)) {
            RC2.logger.error("Packet registration failed for %s because it has already been registered!", clazz.toString());
            return false;
        }

        if (this.isPostInitialized) {
            RC2.logger.error("Packet registration failed for %s because it was registered after the pipeline had finished initializing!", clazz.toString());
            return false;
        }

        this.packets.add(clazz);
        return true;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, IPacket msg, List<Object> out) throws Exception {
        ByteBuf buffer = Unpooled.buffer();
        Class<? extends IPacket> clazz = msg.getClass();
        if (!this.packets.contains(msg.getClass())) {
            throw new NullPointerException("No Packet Registered for: " + msg.getClass().getCanonicalName());
        }
        byte discriminator = (byte) this.packets.indexOf(clazz);
        buffer.writeByte(discriminator);
        msg.encodeInto(ctx, buffer);
        FMLProxyPacket proxyPacket = new FMLProxyPacket(new PacketBuffer(buffer.copy()), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
        out.add(proxyPacket);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
        ByteBuf payload = msg.payload();
        byte discriminator = payload.readByte();
        Class<? extends IPacket> clazz = this.packets.get(discriminator);
        if (clazz == null) {
            throw new NullPointerException("No packet registered for discriminator: " + discriminator);
        }
        IPacket pkt = clazz.newInstance();
        pkt.decodeFrom(ctx, payload.slice());
        EntityPlayer player;
        switch (FMLCommonHandler.instance().getEffectiveSide()) {
            case CLIENT:
                player = this.getClientPlayer();
                pkt.handleClientSide(player);
                break;
            case SERVER:
                INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
                player = ((NetHandlerPlayServer) netHandler).playerEntity;
                pkt.handleServerSide(player);
                break;
            default:
        }
        out.add(pkt);
    }

    public void initialize() {
        this.channels = NetworkRegistry.INSTANCE.newChannel("rc2PacketPipeline", this);
        registerPackets();
    }

    public void postInitialize() {
        if (this.isPostInitialized) return;
        this.isPostInitialized = true;
        this.packets.sort((o1, o2) -> o1.getCanonicalName().compareTo(o2.getCanonicalName()));
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    /**
     * Send this message to everyone.
     * <p/>
     * Adapted from CPW's code in
     * net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message The message to send
     */
    public void sendToAll(IPacket message) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to the specified player.
     * <p/>
     * Adapted from CPW's code in
     * net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message The message to send
     * @param player  The player to send it to
     */
    public void sendTo(IPacket message, EntityPlayer player) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to everyone within a certain range of a point.
     * <p/>
     * Adapted from CPW's code in
     * net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message The message to send
     * @param point   The
     *                {@link net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint}
     *                around which to send
     */
    public void sendToAllAround(IPacket message, NetworkRegistry.TargetPoint point) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to everyone within the supplied dimension.
     * <p/>
     * Adapted from CPW's code in
     * net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message     The message to send
     * @param dimensionId The dimension id to target
     */
    public void sendToDimension(IPacket message, int dimensionId) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to the powerSink.
     * <p/>
     * Adapted from CPW's code in
     * net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message The message to send
     */
    public void sendToServer(IPacket message) {
        this.channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        this.channels.get(Side.CLIENT).writeAndFlush(message);
    }
}