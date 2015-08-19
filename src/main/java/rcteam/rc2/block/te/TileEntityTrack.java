package rcteam.rc2.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import rcteam.rc2.RC2;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.network.packets.PacketThemeParkEntrance;
import rcteam.rc2.rollercoaster.*;

public class TileEntityTrack extends TileEntity {
	//	public CoasterStyle style;
//	public CategoryEnum category;
//	public TrackPiece piece;
	public TrackPieceInfo info;

	public TileEntityTrack() {
//		this.info = new TrackPieceInfo(CategoryEnum.STEEL, TrackPieceRegistry.INSTANCE.getPiece("straight"));
	}

//	public TileEntityTrack(CoasterStyle style) {
//		this.style = style;
//	}

//	public TileEntityTrack(CategoryEnum category, TrackPiece piece) {
//		this.category = category;
//		this.piece = piece;
//	}

	public TileEntityTrack(TrackPieceInfo info) {
		this.info = info;
	}

	public TrackPieceInfo getInfo() {
		return this.info;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("info")) this.info = TrackPieceInfo.readFromNBT(compound.getCompoundTag("info"));
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (this.info != null) compound.setTag("info", this.info.writeToNBT());
	}

	@Override
	public void onChunkUnload() {
		this.writeToNBT(this.getTileData());
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new S35PacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), compound);
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
	}
}
