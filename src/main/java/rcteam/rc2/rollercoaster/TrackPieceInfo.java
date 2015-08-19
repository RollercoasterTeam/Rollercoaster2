package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import rcteam.rc2.RC2;

import java.util.List;

public class TrackPieceInfo implements Comparable {
	private CategoryEnum category;
	private TrackPiece currentPiece = null;
	private List<TrackPiece> pieces = Lists.newArrayList();

	public TrackPieceInfo(CategoryEnum category) {
		this(category, Lists.newArrayList());
	}

	public TrackPieceInfo(CategoryEnum category, List<TrackPiece> pieces) {
		this(category, pieces, null);
	}

	public TrackPieceInfo(CategoryEnum category, List<TrackPiece> pieces, TrackPiece currentPiece) {
		this.category = category;
		this.pieces = pieces;
		this.currentPiece = currentPiece;
	}

	//TODO: make pieces a map?
	public void setCurrentPiece(int index) {
		this.currentPiece = this.pieces.get(index);
	}

	public void setCurrentPiece(TrackPiece currentPiece) {
		if (!this.pieces.contains(currentPiece)) {
			this.pieces.add(currentPiece);
		}
		this.currentPiece = currentPiece;
	}

	public void setCurrentPiece(String name) {
		for (TrackPiece piece : this.pieces) {
			if (piece.getName().equals(name)) {
				this.currentPiece = piece;
			}
		}
	}

	public TrackPiece getCurrentPiece() {
		if (this.currentPiece == null) {
			this.currentPiece = this.pieces.get(0);
		}
		return this.currentPiece;
	}

	public TrackPiece getNextPiece() {
		if (this.currentPiece == null || !this.pieces.contains(this.currentPiece)) return this.pieces.get(0);
		int index = this.pieces.indexOf(this.currentPiece);
		if (index + 1 == this.pieces.size()) return this.pieces.get(0);
		return this.pieces.get(index + 1);
	}

	public TrackPiece getPiece(String name) {
		for (TrackPiece piece : this.pieces) {
			if (piece.getName().equals(name)) {
				return piece;
			}
		}
		RC2.logger.info("returning index 0!");
		return this.pieces.get(0);
	}

	public TrackPiece cycleCurrentPiece() {
		if (this.currentPiece == null || this.pieces.indexOf(this.currentPiece) + 1 == this.pieces.size()) this.currentPiece = pieces.get(0);
		else this.currentPiece = this.pieces.get(this.pieces.indexOf(this.currentPiece) + 1);
		return this.currentPiece;
	}

	public void setCategory(CategoryEnum category) {
		this.category = category;
	}

	public CategoryEnum getCategory() {
		return this.category;
	}

	public void addPiece(TrackPiece piece) {
		this.pieces.add(piece);
	}

	public void addPieces(List<TrackPiece> pieces) {
		this.pieces.addAll(pieces);
	}

	public List<TrackPiece> getPieces() {
		return this.pieces;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(String.format("TrackPieceInfo: Category: %s, Pieces: [", this.category.getName()));
		for (TrackPiece piece : this.pieces) {
			builder.append(String.format("%s, ", piece.getName()));
		}
		builder.delete(builder.length() - 2, builder.length());
		builder.append("], ");
		builder.append(String.format("Current Piece: %s", this.currentPiece.getName()));
		return builder.toString();
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("category", this.category.ordinal());

		NBTTagList list = new NBTTagList();
		this.pieces.forEach(piece -> list.appendTag(piece.writeToNBT()));
		compound.setTag("pieces", list);

		compound.setTag("current", this.currentPiece.writeToNBT());
		return compound;
	}

	public static TrackPieceInfo readFromNBT(NBTTagCompound compound) {
		CategoryEnum category = CategoryEnum.values()[compound.getInteger("category")];
		List<TrackPiece> pieces = Lists.newArrayList();

		NBTTagList list = compound.getTagList("pieces", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound pieceCompound = list.getCompoundTagAt(i);
			pieces.add(TrackPiece.readFromNBT(pieceCompound));
		}

		TrackPiece current = TrackPiece.readFromNBT(compound.getCompoundTag("current"));

		return new TrackPieceInfo(category, pieces, current);
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof TrackPieceInfo)) {
			return 1;
		}
		TrackPieceInfo info = (TrackPieceInfo) o;
		int categoryComp = info.getCategory().compareTo(this.getCategory());
		if (categoryComp == 0) {
			int pieces = info.getPieces().size();
			if (pieces == this.pieces.size()) return this.currentPiece.compareTo(info.getCurrentPiece());
			else if (pieces > this.pieces.size()) return -1;
			else return 1;
		} else return categoryComp;
	}
}
