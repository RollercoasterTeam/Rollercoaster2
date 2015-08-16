package rcteam.rc2.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityTrack;
import rcteam.rc2.proxy.ClientProxy;
import rcteam.rc2.rollercoaster.*;
import rcteam.rc2.util.OBJModel;
import rcteam.rc2.util.Utils;

public class BlockTrack extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[] {FACING}, new IUnlistedProperty[] {TrackProperty.instance, OBJModel.OBJProperty.instance});
	protected TrackPieceInfo info;

	public BlockTrack(Material material) {
		super(material);
//		this.info = info;
		setCreativeTab(RC2.tab);
		setBlockUnbreakable();
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
//		setUnlocalizedName(RC2.MODID + ".track_" + info.getCategory().getName());
	}

	public TrackPieceInfo getInfo() {
		return this.info;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isVisuallyOpaque() {
		return false;
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return ((IExtendedBlockState) state).withProperty(TrackProperty.instance, this.info);
	}

	@Override
	public BlockState createBlockState() {
//		return state;
		return new ExtendedBlockState(this, new IProperty[] {FACING}, new IUnlistedProperty[] {TrackProperty.instance, OBJModel.OBJProperty.instance});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumFacing facing = ((EnumFacing) state.getValue(FACING));
		if (facing == EnumFacing.DOWN || facing == EnumFacing.UP) {
			return 0;
		} else {
			return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
		}
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		//TODO: things will be set from the track designer later
		return this.getDefaultState().withProperty(FACING, Utils.getFacingFromEntity(worldIn, pos, placer, false, false));
	}

	@Override
	public int getRenderType() {
		return 3;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTrack(this.info);
	}

	public enum TrackProperty implements IUnlistedProperty<TrackPieceInfo> {
		instance;
		@Override
		public String getName() {
			return "TrackProperty";
		}

		@Override
		public boolean isValid(TrackPieceInfo value) {
			return value != null && value.getCategory() != null && value.getPieces() != null && !value.getPieces().isEmpty();
		}

		@Override
		public Class<TrackPieceInfo> getType() {
			return TrackPieceInfo.class;
		}

		@Override
		public String valueToString(TrackPieceInfo value) {
			return value.toString();
		}
	}
}
