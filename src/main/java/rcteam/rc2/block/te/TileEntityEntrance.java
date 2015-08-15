package rcteam.rc2.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import rcteam.rc2.rollercoaster.ThemePark;

public class TileEntityEntrance extends TileEntity {
	
	public ThemePark themePark;

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(pos, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		themePark = new ThemePark(EnumFacing.getFront(compound.getInteger("direction")), compound.getString("name"), compound.getInteger("size"), compound.getInteger("maxBuildHeight"));
		
		themePark.logo.bg = compound.getInteger("bg");
		themePark.logo.bgColour = compound.getInteger("bgColour");
		
		themePark.logo.fg = compound.getInteger("fg");
		themePark.logo.fgColour = compound.getInteger("fgColour");
		
		themePark.logo.text = compound.getInteger("text");
		themePark.logo.textColour = compound.getInteger("textColour");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		themePark = themePark == null ? new ThemePark() : themePark;
		
		compound.setInteger("direction", themePark.direction.getIndex());
		
		compound.setString("name", themePark.name);
		
		compound.setInteger("size", themePark.size);
		
		compound.setInteger("maxBuildHeight", themePark.maxBuildHeight);
		
		compound.setInteger("bg", themePark.logo.bg);
		compound.setInteger("bgColour", themePark.logo.bgColour);
		
		compound.setInteger("fg", themePark.logo.fg);
		compound.setInteger("fgColour", themePark.logo.fgColour);
		
		compound.setInteger("text", themePark.logo.text);
		compound.setInteger("textColour", themePark.logo.textColour);
	}
}
