package rcteam.rc2.block;

import jdk.nashorn.internal.ir.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityTrackSteel;
import rcteam.rc2.rollercoaster.TrackPieceInfo;

public class BlockTrackSteel extends BlockTrack {
	public BlockTrackSteel(TrackPieceInfo info) {
		super(info.getCategory().getMaterial());
		this.info = info;
		setUnlocalizedName("track_" + info.getCategory().getName());
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityTrackSteel(this.info);
	}
}
