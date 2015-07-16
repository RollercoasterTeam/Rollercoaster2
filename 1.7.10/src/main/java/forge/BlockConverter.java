package forge;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import rollercoasterteam.rollercoaster2.core.BlockPosition;
import rollercoasterteam.rollercoaster2.core.api.BaseAPIProxy;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;

public class BlockConverter extends Block {

    RCBlock rcBlock;

    public BlockConverter(RCBlock rcBlock) {
        super(Material.iron);
        setBlockName(rcBlock.getName());
        setCreativeTab(CreativeTabs.tabAllSearch);
        this.rcBlock = rcBlock;
    }


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
        return rcBlock.onActivated(BaseAPIProxy.API.getWorld(world.provider.dimensionId), new BlockPosition(x, y, z));
    }
}
