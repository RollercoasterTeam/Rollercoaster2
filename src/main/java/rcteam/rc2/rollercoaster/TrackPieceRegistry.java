package rcteam.rc2.rollercoaster;

import com.google.common.collect.BiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import rcteam.rc2.RC2;

import javax.vecmath.Vector3f;
import java.util.List;
import java.util.Map;

public class TrackPieceRegistry {
	public static final TrackPieceRegistry INSTANCE = new TrackPieceRegistry();
	private Map<String, TrackPiece> pieceMap = Maps.newHashMap();

	private TrackPieceRegistry() {}

	public void registerPiece(TrackPiece piece) {
		if (!this.pieceMap.containsKey(piece.getName()) && !this.pieceMap.containsValue(piece)) {
			this.pieceMap.put(piece.getName(), piece);
		} else {
			RC2.logger.warn("TrackPieceRegistry: Tried to register a duplicate TrackPiece: %s, skipping!", piece.getName());
		}
	}

	public void replacePiece(TrackPiece piece) {
		if (!this.pieceMap.containsKey(piece.getName()) && !this.pieceMap.containsValue(piece)) {
			this.pieceMap.put(piece.getName(), piece);
			RC2.logger.warn("TrackPieceRegistry: Tried to replace a piece that hasn't been registered, added piece %s to map instead.", piece.getName());
		} else {
			this.pieceMap.replace(piece.getName(), piece);
		}
	}

	public void removePiece(String name) {
		if (!this.pieceMap.containsKey(name)) {
			RC2.logger.warn("TrackPieceMap: Tried to remove a piece with name %s, but no piece of that name exists in the map.", name);
		} else {
			this.pieceMap.remove(name);
		}
	}

	public Map<String, TrackPiece> getPieceMap() {
		return this.pieceMap;
	}

	public List<TrackPiece> getPieces() {
		return Lists.newArrayList(this.pieceMap.values());
	}

	public List<String> getPieceNames() {
		return Lists.newArrayList(this.pieceMap.keySet());
	}

	public TrackPiece getPiece(String name) {
		return this.pieceMap.get(name);
	}

	public void registerDefaultPieces() {
		this.registerPiece(new TrackPiece("straight", new Vector3f(1, 1, 1)));
		this.registerPiece(new TrackPiece("small_corner", new Vector3f(2, 1, 2)));
		this.registerPiece(new TrackPiece("medium_corner", new Vector3f(3, 1, 3)));
		this.registerPiece(new TrackPiece("large_corner_right", new Vector3f(2, 1, 2)));
		this.registerPiece(new TrackPiece("large_corner_left", new Vector3f(2, 1, 2)));
	}
}
