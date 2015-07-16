package forge;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;

public class BlockConverter extends Block {
    public BlockConverter(RCBlock rcBlock) {
        super(Material.iron);
        setBlockName(rcBlock.getName());
        setCreativeTab(CreativeTabs.tabAllSearch);
    }
}
