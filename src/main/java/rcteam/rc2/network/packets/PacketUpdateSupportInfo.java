package rcteam.rc2.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntitySupport;
import rcteam.rc2.rollercoaster.SupportUtils;

import java.io.IOException;

public class PacketUpdateSupportInfo implements IPacket {
	private NBTTagCompound compound = new NBTTagCompound();
	private BlockPos pos;

	public PacketUpdateSupportInfo() {}

	public PacketUpdateSupportInfo(NBTTagCompound compound, BlockPos pos) {
		this.compound = compound;
		this.pos = pos;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buf) {
		PacketBuffer packetBuffer = new PacketBuffer(buf);
		packetBuffer.writeNBTTagCompoundToBuffer(this.compound);
		packetBuffer.writeBlockPos(this.pos);
	}

	@Override
	public void decodeFrom(ChannelHandlerContext ctx, ByteBuf buf) {
		PacketBuffer packetBuffer = new PacketBuffer(buf);
		try {
			this.compound = packetBuffer.readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			RC2.logger.error("Failed to read NBTTagCompound from packet buffer.");
		}
		this.pos = packetBuffer.readBlockPos();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if (player.getEntityWorld().getTileEntity(this.pos) != null && player.getEntityWorld().getTileEntity(this.pos) instanceof TileEntitySupport) {
			TileEntitySupport support = (TileEntitySupport) player.getEntityWorld().getTileEntity(this.pos);
			support.info = SupportUtils.SupportInfo.readFromNBT(this.compound);
//			support.readFromNBT(this.compound);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if (player.getEntityWorld().getTileEntity(this.pos) != null && player.getEntityWorld().getTileEntity(this.pos) instanceof TileEntitySupport) {
			TileEntitySupport support = (TileEntitySupport) player.getEntityWorld().getTileEntity(this.pos);
			support.info = SupportUtils.SupportInfo.readFromNBT(this.compound);
//			support.readFromNBT(this.compound);
		}
	}
}
