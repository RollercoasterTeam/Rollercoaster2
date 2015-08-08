package testmod;

import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.tile.RCTileMeta;

import java.util.ArrayList;
import java.util.List;

public class ModeledTile extends RCTileMeta {
	public ModeledTile(RCBlock block) {
		super(block);
	}

	@Override
	public List<Integer> types() {
		List<Integer> integers = new ArrayList<Integer>();
		integers.add(0);
		integers.add(1);
		return integers;
	}
}
