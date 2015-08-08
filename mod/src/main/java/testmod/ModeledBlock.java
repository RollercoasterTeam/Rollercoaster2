package testmod;

import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.block.RCMeta;
import rollercoasterteam.rollercoaster2.core.api.textures.model.IModeledBlock;
import rollercoasterteam.rollercoaster2.core.api.textures.model.RCModel;
import rollercoasterteam.rollercoaster2.core.api.tile.RCTile;

public class ModeledBlock extends RCBlock implements IModeledBlock {
    public ModeledBlock(String name) {
        super(name);
        setTexture("minecraft:cobblestone");
    }

    @Override
    public RCModel getModel(RCMeta meta) {
        return new TestModel(meta);
    }

	@Override
	public RCTile getTile() {
		return new ModeledTile(this);
	}
}
