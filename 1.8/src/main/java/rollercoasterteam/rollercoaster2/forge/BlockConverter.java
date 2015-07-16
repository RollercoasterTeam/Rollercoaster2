package rollercoasterteam.rollercoaster2.forge;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;

public class BlockConverter extends Block {
    public BlockConverter(RCBlock rcBlock) {
        super(Material.iron);
        setUnlocalizedName(rcBlock.getName());
        setCreativeTab(CreativeTabs.tabAllSearch);
    }
}
