package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import org.apache.commons.lang3.StringUtils;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.util.Reference;
import java.util.List;

public enum CategoryEnum {
	STEEL("steel", Material.iron),
	WOODEN("wooden", Material.wood),
	INVERTED("inverted", Material.iron),
	WATER("water", Material.iron);

	private final String name;
	private final Material material;
	private TrackPieceInfo info;
	public final BlockTrack.TrackPieceProperty PIECE_PROPERTY;
	public final String BLOCKSTATE_DIR;

	CategoryEnum(String name, Material material) {
		this.name = name;
		this.material = material;
		this.info = new TrackPieceInfo(this);
		this.PIECE_PROPERTY = new BlockTrack.TrackPieceProperty("piece");
		this.BLOCKSTATE_DIR = Reference.TRACK_STATE_DIR + name + "/";
	}

	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
		return StringUtils.capitalize(this.name);
	}

	public Material getMaterial() {
		return this.material;
	}

	public void setInfo(TrackPieceInfo info) {
		this.info = info;
	}

	public TrackPieceInfo getInfo() {
		return this.info;
	}

//	public TrackPieceInfo setValidPieces(List<TrackPiece> pieces) {
//		//TODO!!!
////		this.info.addPieces(pieces);
//		this.PIECE_PROPERTY.setAllowedValues(pieces);
//		return this.info;
//	}

	public TrackPieceInfo setValidPieces() {
		List<TrackPiece> pieces = Lists.newArrayList();
		for (CoasterStyle style : this.info.getStyles()) {
			pieces.addAll(style.getPieces());
		}
		this.PIECE_PROPERTY.setAllowedValues(pieces);
		return this.info;
	}

	public static CategoryEnum getByName(String name) {
		return Lists.newArrayList(CategoryEnum.values()).stream().filter(categoryEnum -> categoryEnum.getName().equals(name)).findAny().get();
	}
}
