package testmod;

import rollercoasterteam.rollercoaster2.core.BlockPosition;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.world.RCWorld;

public class TestBlock extends RCBlock {

    public TestBlock(String name) {
        super(name);
        setTexture("minecraft:cobblestone");
    }

    @Override
    public boolean onActivated(RCWorld world, BlockPosition position) {
        world.setBlock(new BlockPosition(position.getX(), position.getY() + 1, position.getZ()), TestMod.testBlock);
        return super.onActivated(world, position);
    }


}
