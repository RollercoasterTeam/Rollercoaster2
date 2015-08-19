package rcteam.rc2.network;

import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.BlockPos;
import rcteam.rc2.RC2;
import rcteam.rc2.network.packets.PacketThemeParkEntrance;

public class NetworkHandler {
	public static void updateThemeParkEntrance(PacketThemeParkEntrance.Packet packet, BlockPos pos, int numericData, String data) {
		RC2.packetPipeline.sendToServer(new PacketThemeParkEntrance(packet.ordinal(), pos, numericData, data));
	}
}
