package testmod;

import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.textures.model.IModeledBlock;
import rollercoasterteam.rollercoaster2.core.api.textures.model.RCModel;

public class ModeledBlock extends RCBlock implements IModeledBlock {
    public ModeledBlock(String name) {
        super(name);
        setTexture("minecraft:cobblestone");
    }

    @Override
    public RCModel getModel() {
        return new TestModel();
    }
}
