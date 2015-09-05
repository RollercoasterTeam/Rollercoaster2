package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class CoasterStyle implements Comparable {
	private TrackPieceInfo parentInfo;
	private TrackPiece currentPiece;
	private final String name;
	private final List<String> trainCars = Lists.newArrayList();
	private final List<TrackPiece> validPieces = Lists.newArrayList();

	protected CoasterStyle(String name, List<TrackPiece> validPieces, List<String> trainCars, TrackPieceInfo parentInfo) {
		this(name, validPieces, trainCars, parentInfo, validPieces.get(0));
	}

	protected CoasterStyle(String name, List<TrackPiece> validPieces, List<String> trainCars, TrackPieceInfo parentInfo, TrackPiece currentPiece) {
		this.name = name;
		this.validPieces.addAll(validPieces);
		this.trainCars.addAll(trainCars);
		this.parentInfo = parentInfo;
		if (this.validPieces.contains(currentPiece)) this.currentPiece = currentPiece;
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
		if (this.validPieces.contains(piece)) {
			this.currentPiece = piece;
		}
	}

	public void cycleCurrentPiece() {
		int index = this.validPieces.indexOf(this.currentPiece);
		if (index + 1 == this.validPieces.size()) {
			this.currentPiece = this.validPieces.get(0);
		} else {
			this.currentPiece = this.validPieces.get(index + 1);
		}
	}

	public TrackPiece getCurrentPiece() {
		return this.currentPiece;
	}

	public List<TrackPiece> getValidPieces() {
		return Lists.newArrayList(this.validPieces);
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
		int[] pieces = new int[this.validPieces.size()];
		for (int i = 0; i < pieces.length; i++) {
			pieces[i] = this.validPieces.get(i).ordinal();
		}
		compound.setIntArray("pieces", pieces);
		if (this.currentPiece != null) compound.setInteger("current_piece", this.currentPiece.ordinal());
		NBTTagList list = new NBTTagList();
		for (String s : this.trainCars) {
			list.appendTag(new NBTTagString(s));
		}
		compound.setTag("trains", list);
		return compound;
	}

	public static CoasterStyle readFromNBT(NBTTagCompound compound) {
		String name = compound.getString("name");
		List<TrackPiece> validPieces = Lists.newArrayList();
		int[] indices = compound.getIntArray("pieces");
		for (int i : indices) {
			validPieces.add(TrackPiece.values()[i]);
		}
		TrackPiece currentPiece = null;
		if (compound.hasKey("current_piece")) {
			currentPiece = TrackPiece.values()[compound.getInteger("current_piece")];
		}
		List<String> trains = Lists.newArrayList();
		NBTTagList list = compound.getTagList("trains", Constants.NBT.TAG_STRING);
		for (int i = 0; i < list.tagCount(); i++) {
			trains.add(list.getStringTagAt(i));
		}
		return new CoasterStyle(name, validPieces, trains, null, currentPiece);
	}

	@Override
	public int compareTo(Object o) {
		if (o == null || !(o instanceof CoasterStyle)) return 1;
		CoasterStyle input = (CoasterStyle) o;
		int nameComp = this.name.compareTo(input.getName());
		if (nameComp == 0) {
			if (!this.trainCars.equals(input.getTrainCars())) {
				if (!this.validPieces.equals(input.getValidPieces())) {
					int sizeComp = Integer.compare(this.validPieces.size(), input.getValidPieces().size());
					if (sizeComp == 0) {
						return this.currentPiece.compareTo(input.currentPiece);
					} else return sizeComp;
				} else return 0;
			} else return 0;
		} else return nameComp;
	}
}
