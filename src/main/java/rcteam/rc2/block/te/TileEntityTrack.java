package rcteam.rc2.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
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

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.info = TrackPieceInfo.readFromNBT(compound.getCompoundTag("info"));
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		compound.setTag("info", this.info.writeToNBT());
	}
}
