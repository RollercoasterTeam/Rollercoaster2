package rollercoasterteam.rollercoaster2.forge;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import rollercoasterteam.rollercoaster2.core.BlockPosition;
import rollercoasterteam.rollercoaster2.core.api.BaseAPIProxy;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.textures.model.IModeledBlock;

public class BlockConverter extends Block {

    RCBlock rcBlock;
    
    public BlockConverter(RCBlock rcBlock) {
        super(Material.iron);
        setUnlocalizedName(rcBlock.getName());
        setCreativeTab(CreativeTabs.tabAllSearch);
        this.rcBlock = rcBlock;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        return rcBlock.onActivated(BaseAPIProxy.API.getWorld(worldIn.provider.getDimensionId()), new BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
    }


    public static final IUnlistedProperty<Integer>[] properties = new IUnlistedProperty[6];
    @Override
    protected BlockState createBlockState()
    {
        for(EnumFacing f : EnumFacing.values())
        {
            properties[f.ordinal()] = Properties.toUnlisted(PropertyInteger.create(f.getName(), 0, (1 << (1 * 1)) - 1));
        }
        return new ExtendedBlockState(this, new IProperty[0], properties);
    }

    @Override
    public boolean isFullCube() {
        return !(rcBlock instanceof IModeledBlock);
    }


    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        if(rcBlock instanceof IModeledBlock){
            return EnumWorldBlockLayer.CUTOUT;
        }
        return super.getBlockLayer();
    }
}
