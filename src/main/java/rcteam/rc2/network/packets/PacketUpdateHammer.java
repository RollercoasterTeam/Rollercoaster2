package rcteam.rc2.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import rcteam.rc2.item.ItemHammer;

public class PacketUpdateHammer implements IPacket {
	private int damage = 0;

	public PacketUpdateHammer() {} //stupid Class.newInstance()...

	public PacketUpdateHammer(int damage) {
		this.damage = damage;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(this.damage);
	}

	@Override
	public void decodeFrom(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.damage = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {}

	@Override
	public void handleServerSide(EntityPlayer player) {
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemHammer) {
			ItemHammer hammer = (ItemHammer) stack.getItem();
			hammer.setDamage(stack, this.damage);
		}
	}
}
