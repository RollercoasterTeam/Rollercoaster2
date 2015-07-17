package testmod;

import rollercoasterteam.rollercoaster2.core.BlockPosition;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.textures.IMultiFaceTexture;
import rollercoasterteam.rollercoaster2.core.api.textures.MultiFaceTexture;
import rollercoasterteam.rollercoaster2.core.api.textures.Texture;
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

    @Override
    public IMultiFaceTexture getTexturesWithFaces() {
        return new MultiFaceTexture(new Texture("minecraft:cobblestone"),new Texture("minecraft:brick"),new Texture("minecraft:gold_block"),new Texture("minecraft:diamond_block"),new Texture("minecraft:gravel"),new Texture("minecraft:end_stone"));
    }
}