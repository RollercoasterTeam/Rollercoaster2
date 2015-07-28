package rcteam.rc2.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import rcteam.rc2.rollercoaster.ThemePark;

public class TileEntityEntrance extends TileEntity {
	
	public ThemePark themePark;

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		themePark = new ThemePark(compound.getInteger("direction"), compound.getString("name"), compound.getInteger("size"), compound.getInteger("maxBuildHeight"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		themePark = themePark == null ? new ThemePark() : themePark;
		
		compound.setInteger("direction", themePark.direction);
		
		compound.setString("name", themePark.name);
		
		compound.setInteger("size", themePark.size);
		
		compound.setInteger("maxBuildHeight", themePark.maxBuildHeight);
	}
}
