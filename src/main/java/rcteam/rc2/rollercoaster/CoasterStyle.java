package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.util.ResourceLocation;
import rcteam.rc2.RC2;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.item.ItemTrack;
import rcteam.rc2.util.OBJModel;
import rcteam.rc2.util.Reference;

import java.util.List;

public class CoasterStyle {
	private final String name;
	private final List<TrackPiece> pieces = Lists.newArrayList();
	private final List<String> trainCars = Lists.newArrayList();
	private TrackPieceInfo parentInfo;
	private TrackPiece currentPiece = null;

	public CoasterStyle(String name, List<TrackPiece> pieces, List<String> trainCars) {
		this(name, pieces, trainCars, null);
	}

	public CoasterStyle(String name, List<TrackPiece> pieces, List<String> trainCars, TrackPiece piece) {
		this.name = name;
		this.pieces.addAll(pieces);
		this.trainCars.addAll(trainCars);
		this.currentPiece = piece;
	}

	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
		StringBuilder builder = new StringBuilder();
		Lists.newArrayList(this.name.split("_")).forEach(s -> builder.append(s.toUpperCase() + " "));
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

	public void setCurrentPiece(TrackPiece piece) {
		this.currentPiece = piece;
	}

	public TrackPiece getCurrentPiece() {
		return this.currentPiece;
	}

	public TrackPiece cycleCurrentPiece() {
		if (this.currentPiece == null || this.pieces.indexOf(this.currentPiece) + 1 == this.pieces.size()) this.currentPiece = pieces.get(0);
		else this.currentPiece = this.pieces.get(this.pieces.indexOf(this.currentPiece) + 1);
		return this.currentPiece;
	}

	public List<TrackPiece> getPieces() {
		return this.pieces;
	}

	public List<String> getTrainCars() {
		return this.trainCars;
	}

	public void setParentInfo(TrackPieceInfo info) {
		this.parentInfo = info;
	}

	public TrackPieceInfo getParentInfo() {
		return this.parentInfo;
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setString("name", this.name);

		NBTTagList list = new NBTTagList();
		for (TrackPiece piece : this.pieces) {
			list.appendTag(piece.writeToNBT());
		}
		compound.setTag("pieces", list);
		compound.setTag("current_piece", this.currentPiece.writeToNBT());
		NBTTagList list1 = new NBTTagList();
		for (String s : this.trainCars) {
			list1.appendTag(new NBTTagString(s));
		}
		compound.setTag("trains", list1);
		return compound;
	}

	public static CoasterStyle readFromNBT(NBTTagCompound compound) {
		String name = compound.getString("name");
		List<TrackPiece> pieces = Lists.newArrayList();
		NBTTagList list = compound.getTagList("pieces", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound pieceCompound = list.getCompoundTagAt(i);
			pieces.add(TrackPiece.readFromNBT(pieceCompound));
		}
		TrackPiece current = TrackPiece.readFromNBT(compound.getCompoundTag("current_piece"));
		List<String> trains = Lists.newArrayList();
		NBTTagList list1 = compound.getTagList("trains", Constants.NBT.TAG_STRING);
		for (int i = 0; i < list1.tagCount(); i++) {
			trains.add(list1.getStringTagAt(i));
		}
		return new CoasterStyle(name, pieces, trains, current);
	}
}
