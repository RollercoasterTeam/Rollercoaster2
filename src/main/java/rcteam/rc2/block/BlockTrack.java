package rcteam.rc2.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import rcteam.rc2.rollercoaster.TrackPieceEnum;

public class BlockTrack extends Block {
	public BlockTrack(TrackPieceEnum piece) {
		super(Material.iron);
	}
}
