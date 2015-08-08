package rollercoasterteam.rollercoaster2.forge;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rollercoasterteam.rollercoaster2.core.BlockPosition;
import rollercoasterteam.rollercoaster2.core.api.BaseAPIProxy;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.block.RCMeta;
import rollercoasterteam.rollercoaster2.core.api.textures.model.IModeledBlock;
import rollercoasterteam.rollercoaster2.core.api.tile.RCTile;

import java.util.List;

public class BlockConverter extends BlockContainer {

    RCBlock rcBlock;
    
    public BlockConverter(RCBlock rcBlock) {
        super(Material.iron);
        setUnlocalizedName(rcBlock.getName());
        setCreativeTab(CreativeTabs.tabAllSearch);
        this.rcBlock = rcBlock;
		setLightLevel(0.3F);
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
        if(rcBlock instanceof IModeledBlock){
            return false;
        }
        return true;
    }


    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        if(rcBlock instanceof IModeledBlock){
            return EnumWorldBlockLayer.CUTOUT;
        }
        return super.getBlockLayer();
    }


    @Override
    public boolean isOpaqueCube() {
        return !(rcBlock instanceof IModeledBlock);
    }

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return new FakeState(world, pos, this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		RCTile rcTile = rcBlock.getTile();
		if(rcTile != null){
			return new TileConverter(rcTile, rcBlock);
		}
		return null;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		if(state instanceof FakeState){
			if(((FakeState) state).meta != -1){
				return ((FakeState) state).meta;
			}
			TileEntity mctile = ((FakeState) state).blockAccess.getTileEntity(((FakeState) state).pos);
			if(mctile instanceof TileConverter){
				RCTile rcTile = ((TileConverter) mctile).rcTile;
				if(rcTile instanceof RCMeta){
					return ((RCMeta) rcTile).getMeta();
				}
			}
		}
		return super.getMetaFromState(state);
	}

//	@Override
//	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
//		RCTile tile = rcBlock.getTile();
//		if(tile instanceof RCMeta){
//			for(Integer i : ((RCMeta) rcBlock.getTile()).types()){
//				list.add(new ItemStack(itemIn, 1, i));
//			}
//		}
//		super.getSubBlocks(itemIn, tab, list);
//	}


	public int getRenderType()
	{
		if(rcBlock instanceof IModeledBlock){
			return 3;
		}
		return super.getRenderType();
	}


	public boolean isBlockNormalCube()
	{
		return !(rcBlock instanceof IModeledBlock);
	}



	public boolean isFullBlock()
	{
		return !(rcBlock instanceof IModeledBlock);
	}


	@SideOnly(Side.CLIENT)
	public boolean isTranslucent()
	{
		return (rcBlock instanceof IModeledBlock);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		FakeState state = new FakeState(null, null, this);
		state.meta = meta;
		return state;
	}
}
