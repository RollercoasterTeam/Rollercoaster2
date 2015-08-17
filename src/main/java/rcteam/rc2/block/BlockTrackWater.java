package rcteam.rc2.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityTrackWater;
import rcteam.rc2.rollercoaster.TrackPieceInfo;

public class BlockTrackWater extends BlockTrack {
	public BlockTrackWater(TrackPieceInfo info) {
		super(null);
		this.info = info;
		setUnlocalizedName("track_" + info.getCategory().getName());
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTrackWater(this.info);
	}
}
