package rcteam.rc2.network;

import rcteam.rc2.RC2;
import rcteam.rc2.network.packets.PacketThemeParkEntrance;

public class NetworkHandler {
	
	public static void updateThemeParkEntrance(PacketThemeParkEntrance.Packet packet, int x, int y, int z, int numericData, String data) {
		RC2.packetPipeline.sendToServer(new PacketThemeParkEntrance(packet.ordinal(), x, y, z, numericData, data));
	}
}
