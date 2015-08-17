package rcteam.rc2.block;

import com.google.common.collect.Lists;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityTrack;
import rcteam.rc2.rollercoaster.*;
import rcteam.rc2.util.Utils;

import java.util.Collection;
import java.util.List;

public class BlockTrack extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", Lists.newArrayList(EnumFacing.HORIZONTALS));
	public static final TestProperty PIECE_PROPERTY = new TestProperty("piece");
//	public ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[] {FACING, PIECE_PROPERTY}, new IUnlistedProperty[] {TrackProperty.instance});
	protected TrackPieceInfo info;

	public BlockTrack(TrackPieceInfo info) {
		super(info.getCategory().getMaterial());
		this.info = info;
		setCreativeTab(RC2.tab);
		setBlockUnbreakable();
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PIECE_PROPERTY, info.getCurrentPiece()));
		setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
		setUnlocalizedName("track_" + info.getCategory().getName());
	}

	public TrackPieceInfo getInfo() {
		return this.info;
	}

	public void setInfo(TrackPieceInfo info) {
		this.info = info;
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

//	@Override
//	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
//		return ((IExtendedBlockState) state).withProperty(TrackProperty.instance, this.info);
//	}

	@Override
	public BlockState createBlockState() {
		return new BlockState(this, FACING, PIECE_PROPERTY);
//		return new ExtendedBlockState(this, new IProperty[] {FACING}, new IUnlistedProperty[] {TrackProperty.instance});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		if (this.info != null) {
			return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(PIECE_PROPERTY, this.info.getCurrentPiece());
		}
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		if (this.info == null) {
			this.info = ((BlockTrack) state.getBlock()).getInfo();
		}
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

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			RC2.logger.info("someones punching me!");
			this.info.setCurrentPiece(this.info.getNextPiece());
			((BlockTrack) state.getBlock()).setInfo(this.info);
			worldIn.setBlockState(pos, state, 3);
		}
		return true;
	}

	public static class TestProperty extends PropertyHelper {
		private List<TrackPiece> allowedValues;

		public TestProperty(String name) {
			super(name, TrackPieceInfo.class);
		}

		public void setAllowedValues(List<TrackPiece> allowedValues) {
			this.allowedValues = allowedValues;
		}

		@Override
		public Collection getAllowedValues() {
			return this.allowedValues;
		}

		@Override
		public String getName(Comparable value) {
			return ((TrackPiece) value).getName();
//			return this.getName();
		}
	}
}
