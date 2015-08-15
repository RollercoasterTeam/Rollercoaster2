package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class TrackPieceInfo {
	private CategoryEnum category;
//	private TrackPiece piece;
	private List<TrackPiece> pieces = Lists.newArrayList();

	public TrackPieceInfo(CategoryEnum category) {
		this.category = category;
	}

	public TrackPieceInfo(CategoryEnum category, List<TrackPiece> pieces) {
		this.category = category;
		this.pieces = pieces;
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
		builder.delete(builder.length() - 3, builder.length());
		builder.append("]");
		return builder.toString();
//		return String.format("TrackPieceInfo: Category: %s, ", this.category.getName(), this.piece.getName());
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("category", this.category.ordinal());

		NBTTagList list = new NBTTagList();
		this.pieces.forEach(piece -> list.appendTag(piece.writeToNBT()));

		compound.setTag("pieces", list);
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

		return new TrackPieceInfo(category, pieces);
	}
}
