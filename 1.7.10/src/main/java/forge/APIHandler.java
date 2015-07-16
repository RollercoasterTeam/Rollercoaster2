package forge;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraftforge.common.DimensionManager;
import rollercoasterteam.rollercoaster2.core.api.IApiHandler;
import rollercoasterteam.rollercoaster2.core.api.IXMod;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.item.RCItem;
import rollercoasterteam.rollercoaster2.core.api.world.RCWorld;

import java.util.HashMap;

public class APIHandler implements IApiHandler {

    HashMap<RCBlock, Block> blockBlockHashMap = new HashMap<RCBlock, Block>();

    @Override
    public void registerBlock(RCBlock block) {
        Block mcBlock = new BlockConverter(block);
        GameRegistry.registerBlock(mcBlock, block.getName());
        if(!blockBlockHashMap.containsKey(block)){
            blockBlockHashMap.put(block, mcBlock);
        }
    }

    @Override
    public void registerItem(RCItem rcItem) {
        GameRegistry.registerItem(new ItemConverter(rcItem), rcItem.getName());
    }

    @Override
    public void modContrcution(IXMod mod) {

    }

    @Override
    public RCWorld getWorld(int dimID) {
        return new WorldConverter(DimensionManager.getWorld(dimID));
    }
}
