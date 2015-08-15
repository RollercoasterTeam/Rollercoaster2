package rcteam.rc2.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityTrackWood;
import rcteam.rc2.rollercoaster.TrackPieceInfo;

public class BlockTrackWood extends BlockTrack {
	public BlockTrackWood(TrackPieceInfo info) {
		super(info.getCategory().getMaterial());
		this.info = info;
		setUnlocalizedName("track_" + info.getCategory().getName());
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityTrackWood(this.info);
	}
}
