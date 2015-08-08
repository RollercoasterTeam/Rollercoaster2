package rollercoasterteam.rollercoaster2.core.api.tile;

import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.world.RCWorld;

public class RCTile {

	RCWorld world;

	RCBlock block;


	public RCTile(RCBlock block) {
		this.block = block;
	}

	public void tick(){

	}

	public RCWorld getWorld() {
		return world;
	}

	public void setWorld(RCWorld world) {
		this.world = world;
	}
}
