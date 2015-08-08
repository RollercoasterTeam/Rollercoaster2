package rollercoasterteam.rollercoaster2.core.api.tile;

import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.block.RCMeta;

public abstract class RCTileMeta extends RCTile implements RCMeta {

	int meta = 0;

	public RCTileMeta(RCBlock block) {
		super(block);
	}

	@Override
	public int getMeta() {
		return meta;
	}

	//TODO save the meta
}
