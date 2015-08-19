package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import jdk.internal.util.xml.impl.Pair;
import rcteam.rc2.RC2;

import javax.vecmath.Vector3f;
import java.util.List;
import java.util.Map;

public class TrackPieceRegistry {
	public static final TrackPieceRegistry INSTANCE = new TrackPieceRegistry();
//	private Map<String, TrackPiece> pieceMap = Maps.newHashMap();
	private Map<CategoryEnum, Map<String, TrackPiece>> pieceMap = Maps.newEnumMap(CategoryEnum.class);

	private TrackPieceRegistry() {}

	public void registerPiece(TrackPiece piece) {
		Map<String, TrackPiece> pieceNameMap = pieceMap.get(piece.getCategory());
		if (pieceNameMap == null) {
			pieceNameMap = Maps.newHashMap();
		}
		if (!pieceNameMap.containsKey(piece.getName()) && !pieceNameMap.containsValue(piece)) {
			pieceNameMap.put(piece.getName(), piece);
		}
		this.pieceMap.put(piece.getCategory(), pieceNameMap);
//		if (!this.pieceMap.containsKey(piece.getName()) && !this.pieceMap.containsValue(piece)) {
//			this.pieceMap.put(piece.getName(), piece);
//		} else {
//			RC2.logger.warn("TrackPieceRegistry: Tried to register a duplicate TrackPiece: %s, skipping!", piece.getName());
//		}
	}

//	public void replacePiece(TrackPiece piece) {
//		if (!this.pieceMap.containsKey(piece.getName()) && !this.pieceMap.containsValue(piece)) {
//			this.pieceMap.put(piece.getName(), piece);
//			RC2.logger.warn("TrackPieceRegistry: Tried to replace a piece that hasn't been registered, added piece %s to map instead.", piece.getName());
//		} else {
//			this.pieceMap.replace(piece.getName(), piece);
//		}
//	}
//
//	public void removePiece(String name) {
//		if (!this.pieceMap.containsKey(name)) {
//			RC2.logger.warn("TrackPieceMap: Tried to remove a piece with name %s, but no piece of that name exists in the map.", name);
//		} else {
//			this.pieceMap.remove(name);
//		}
//	}

	public Map<String, TrackPiece> getPieceNameMap(CategoryEnum categoryEnum) {
		return this.pieceMap.get(categoryEnum);
	}

	public Map<CategoryEnum, Map<String, TrackPiece>> getPieceMap() {
		return this.pieceMap;
	}

	public List<TrackPiece> getPieces(CategoryEnum categoryEnum) {
		if (this.pieceMap.get(categoryEnum) == null) return null;
		return Lists.newArrayList(this.pieceMap.get(categoryEnum).values());
	}

//	public List<String> getPieceNames() {
//		return Lists.newArrayList(this.pieceMap.keySet());
//	}

	public TrackPiece getPiece(CategoryEnum categoryEnum, String name) {
		if (this.pieceMap.get(categoryEnum) == null) return null;
		return this.pieceMap.get(categoryEnum).get(name);
	}

	public void registerDefaultPieces() { //TODO: these will be defined by the file structure!
		this.registerPiece(new TrackPiece("straight", new Vector3f(1, 1, 1), CategoryEnum.STEEL));
		this.registerPiece(new TrackPiece("small_corner", new Vector3f(2, 1, 2), CategoryEnum.STEEL));
		this.registerPiece(new TrackPiece("medium_corner", new Vector3f(3, 1, 3), CategoryEnum.STEEL));
		this.registerPiece(new TrackPiece("large_corner_right", new Vector3f(2, 1, 2), CategoryEnum.STEEL));
		this.registerPiece(new TrackPiece("large_corner_left", new Vector3f(2, 1, 2), CategoryEnum.STEEL));

		this.registerPiece(new TrackPiece("straight", new Vector3f(1, 1, 1), CategoryEnum.WOODEN));
		this.registerPiece(new TrackPiece("small_corner", new Vector3f(2, 1, 2), CategoryEnum.WOODEN));

		this.registerPiece(new TrackPiece("straight", new Vector3f(1, 1, 1), CategoryEnum.INVERTED));

		this.registerPiece(new TrackPiece("straight", new Vector3f(1, 1, 1), CategoryEnum.WATER));
		this.registerPiece(new TrackPiece("medium_corner", new Vector3f(3, 1, 3), CategoryEnum.WATER));
		this.registerPiece(new TrackPiece("large_corner_left", new Vector3f(2, 1, 2), CategoryEnum.WATER));
	}
}
