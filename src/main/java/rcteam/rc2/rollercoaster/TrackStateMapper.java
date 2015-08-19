package rcteam.rc2.rollercoaster;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

import rcteam.rc2.util.Reference;

import java.util.Iterator;
import java.util.Map;

public class TrackStateMapper extends StateMapperBase {
	public static final TrackStateMapper INSTANCE = new TrackStateMapper();

//	@Override
//	public String getPropertyString(Map map) {
//		StringBuilder builder = new StringBuilder();
//		Iterator iterator = map.entrySet().iterator();
//
//		while (iterator.hasNext()) {
//			Map.Entry entry = (Map.Entry) iterator.next();
//			Iterator pieceIterator = ((TrackPieceInfo) entry.getValue()).getPieces().iterator();
//
//		}
//		return builder.toString();
//	}

	@SuppressWarnings("unchecked")
	@Override
	public Map putStateModelLocations(Block block) {
		Iterator iterator = block.getBlockState().getValidStates().iterator();
		while (iterator.hasNext()) {
			IBlockState state = (IBlockState) iterator.next();
			this.mapStateModelLocations.put(state, this.getModelResourceLocation(state));
		}
		return this.mapStateModelLocations;
	}

	@Override
	public ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return new ModelResourceLocation(Reference.RESOURCE_PREFIX + "tracks/hyper_twister", this.getPropertyString(state.getProperties()));
	}
}
