package rcteam.rc2.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import rcteam.rc2.RC2;
import rcteam.rc2.network.packets.PacketThemeParkEntrance;
import rcteam.rc2.network.packets.PacketUpdateHammer;
import rcteam.rc2.network.packets.PacketUpdateSupportInfo;

public class NetworkHandler {
	public static void updateThemeParkEntrance(PacketThemeParkEntrance.Packet packet, BlockPos pos, int numericData, String data) {
		RC2.packetPipeline.sendToServer(new PacketThemeParkEntrance(packet.ordinal(), pos, numericData, data));
	}

	public static void updateHammerDamage(int damage) {
		RC2.packetPipeline.sendToServer(new PacketUpdateHammer(damage));
	}

	public static void updateSupportInfo(NBTTagCompound compound, BlockPos pos) {
		RC2.packetPipeline.sendToServer(new PacketUpdateSupportInfo(compound, pos));
	}
}
