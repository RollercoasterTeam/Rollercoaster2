package rollercoasterteam.rollercoaster2.forge;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rollercoasterteam.rollercoaster2.core.api.IApiHandler;
import rollercoasterteam.rollercoaster2.core.api.IXMod;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.item.RCItem;

public class APIHandler implements IApiHandler {
    @Override
    public void registerBlock(RCBlock block) {
        GameRegistry.registerBlock(new BlockConverter(block), block.getName());
    }

    @Override
    public void registerItem(RCItem rcItem) {
        GameRegistry.registerItem(new ItemConverter(rcItem), rcItem.getName());
    }

    @Override
    public void modContrcution(IXMod mod) {

    }
}
