package forge;

import net.minecraft.world.World;
import rollercoasterteam.rollercoaster2.core.BlockPosition;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.world.RCWorld;

public class WorldConverter extends RCWorld {

    World world;

    public WorldConverter(World world) {
        this.world = world;
    }

    @Override
    public void setBlock(BlockPosition position, RCBlock block) {
        world.setBlock(position.getX(), position.getY(), position.getZ(), Rollercoaster2Forge.handler.blockBlockHashMap.get(block));
    }
}
