package rcteam.rc2.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.rollercoaster.TrackPiece;
import rcteam.rc2.rollercoaster.TrackPieceInfo;

import java.util.Iterator;
import java.util.Map;

public class TrackStateMapper extends StateMapperBase {
	public static final TrackStateMapper INSTANCE = new TrackStateMapper();
//	Map<IBlockState, ModelResourceLocation> stateMap = Maps.newHashMap();

	@SuppressWarnings("unchecked")
	@Override
	public Map putStateModelLocations(Block block) {
		Iterator iterator = block.getBlockState().getValidStates().iterator();

		while (iterator.hasNext()) {
			IBlockState state = (IBlockState) iterator.next();
			BlockTrack track = (BlockTrack) state.getBlock();
			for (TrackPiece piece : track.getInfo().getPieces()) {
				track.getInfo().setCurrentPiece(piece);
				state = state.withProperty(BlockTrack.PIECE_PROPERTY, track.getInfo().getCurrentPiece());
				this.mapStateModelLocations.put(state, this.getModelResourceLocation(state));
			}
		}

		return this.mapStateModelLocations;
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		if (state.getBlock() instanceof BlockTrack) {
			TrackPieceInfo info = ((BlockTrack) state.getBlock()).getInfo();
			if (info.getCurrentPiece() != null) {
				return new ModelResourceLocation(Reference.RESOURCE_PREFIX + "tracks/hyper_twister", this.getPropertyString(state.getProperties()));
			}
		}
		return null;
	}
}
