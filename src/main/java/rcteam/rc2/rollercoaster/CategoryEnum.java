package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import org.apache.commons.lang3.StringUtils;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.multiblock.MultiBlockTemplate;
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
	private MultiBlockTemplate template;
	private final BlockTrack.TrackPieceProperty PIECE_PROPERTY;
	public final String BLOCKSTATE_DIR;

	CategoryEnum(String name, Material material) {
		this.name = name;
		this.material = material;
		this.PIECE_PROPERTY = new BlockTrack.TrackPieceProperty("piece");
		this.PIECE_PROPERTY.setParentCategory(this);
		this.BLOCKSTATE_DIR = Reference.TRACK_STATE_DIR + name + "/";
//		this.info = new TrackPieceInfo(this);
	}

//	@SuppressWarnings("unchecked")
//	public void syncProperty(BlockTrack.TrackPieceProperty from) {
//		this.PIECE_PROPERTY.setAllowedValues(Lists.newArrayList(from.getAllowedValues()));
//	}

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

	public BlockTrack.TrackPieceProperty getProperty() {
		if (this.info != null) {
			this.PIECE_PROPERTY.setParentCategory(this);
			this.PIECE_PROPERTY.setAllowedValues(this.info.getAllowedValues());
		}
		return this.PIECE_PROPERTY;
	}

	public void setTemplate(MultiBlockTemplate template) {
		this.template = template;
	}

	public MultiBlockTemplate getTemplate() {
		return this.template;
	}

//	public TrackPieceInfo setValidPieces(List<TrackPiece> pieces) {
//		//TODO!!!
////		this.info.addPieces(pieces);
//		this.PIECE_PROPERTY.setAllowedValues(pieces);
//		return this.info;
//	}

//	public void setValidPieces() {
//		List<TrackPiece> pieces = Lists.newArrayList();
//		for (CoasterStyle style : this.info.getStyles()) {
//			for (TrackPieceEnum pieceEnum : style.getValidPieces()) {
//				TrackPiece piece = new TrackPiece(pieceEnum);
//				piece.setParentStyle(style);
//				pieces.add(piece);
//			}
//		}
//		this.PIECE_PROPERTY.setAllowedValues(pieces);
//		return this.info;
//	}

	public static CategoryEnum getByName(String name) {
		return Lists.newArrayList(CategoryEnum.values()).stream().filter(categoryEnum -> categoryEnum.getName().equals(name)).findAny().get();
	}
}
