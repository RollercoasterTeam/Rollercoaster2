package rcteam.rc2.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityTrackInverted;
import rcteam.rc2.rollercoaster.TrackPieceInfo;

public class BlockTrackInverted extends BlockTrack {
	public BlockTrackInverted(TrackPieceInfo info) {
		super(info.getCategory().getMaterial());
		this.info = info;
		setUnlocalizedName("track_" + info.getCategory().getName());
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityTrackInverted(this.info);
	}
}