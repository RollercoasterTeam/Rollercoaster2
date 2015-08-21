package rcteam.rc2.block;

import com.google.common.collect.Lists;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityTrack;
import rcteam.rc2.rollercoaster.*;
import rcteam.rc2.util.Reference;
import rcteam.rc2.util.Utils;

import javax.vecmath.Vector3f;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BlockTrack extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", Lists.newArrayList(EnumFacing.HORIZONTALS));
//	public static final trackPieceProperty PIECE_PROPERTY = new trackPieceProperty("piece");
	//THIS ONLY EXISTS SO THAT THE PROPER INSTANCE OF trackPieceProperty CAN BE RETURNED FROM createBlockState(), DO NOT USE OTHERWISE!
	private TrackPieceProperty trackPieceProperty;
	private static int index = 0;

	public BlockTrack(TrackPieceInfo info) {
		super(info.getCategory().getMaterial());
//		this.trackPieceProperty = info.getCategory().PIECE_PROPERTY;
		setCreativeTab(RC2.tab);
		setBlockUnbreakable();
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(info.getCategory().PIECE_PROPERTY, info.getCurrentStyle().getCurrentPiece()));
		setBlockBounds(0f, 0f, 0f, 1f, 0.75f, 1f);
		setUnlocalizedName("track_" + info.getCategory().getName());
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
	public BlockState createBlockState() {
		this.trackPieceProperty = CategoryEnum.values()[index].PIECE_PROPERTY;
		index++;
		return new BlockState(this, FACING, this.trackPieceProperty);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityTrack) {
			TileEntityTrack tileEntityTrack = (TileEntityTrack) world.getTileEntity(pos);
			if (!state.getValue(tileEntityTrack.getInfo().getCategory().PIECE_PROPERTY).equals(tileEntityTrack.getInfo().getCurrentStyle().getCurrentPiece())) {
//				RC2.logger.printf(Level.INFO, "Updating state: X: %d, Y: %d, Z: %d, Input: %s, Current: %s", pos.getX(), pos.getY(), pos.getZ(), ((TrackPiece)state.getValue(PIECE_PROPERTY)).getName(), tileEntityTrack.getInfo().getCurrentPiece().getName());
				state = state.withProperty(tileEntityTrack.getInfo().getCategory().PIECE_PROPERTY, tileEntityTrack.getInfo().getCurrentStyle().getCurrentPiece());
			}
		}
		return state;
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
		TileEntityTrack tileEntityTrack = new TileEntityTrack();
		for (Object object : state.getProperties().keySet()) {
			if (object instanceof TrackPieceProperty) {
				TrackPieceProperty property = (TrackPieceProperty) object;
				tileEntityTrack = new TileEntityTrack(((TrackPiece)state.getValue(property)).getParentStyle().getParentInfo());
			}
		}
		if (tileEntityTrack.info == null) RC2.logger.error("BlockTrack: WARNING!!! createTileEntity() HAS RETURNED AN INSTANCE WITHOUT ITS INFO SET! WARNING!!!");
		return tileEntityTrack;
	}

	public static class TrackPieceProperty extends PropertyHelper {
		private List<TrackPiece> allowedValues;

		public TrackPieceProperty(String name) {
			super(name, TrackPiece.class);
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
		}
	}

	public static class TrackStateMapper extends StateMapperBase {
		public static final TrackStateMapper INSTANCE = new TrackStateMapper();

		@SuppressWarnings("unchecked")
		@Override
		public Map putStateModelLocations(Block block) {
			Iterator iterator = block.getBlockState().getValidStates().iterator();
			while (iterator.hasNext()) {
				IBlockState state = (IBlockState) iterator.next();
				this.mapStateModelLocations.put(state, this.getModelResourceLocation(state));
			}
			return this.mapStateModelLocations;
		}

		@Override
		public ModelResourceLocation getModelResourceLocation(IBlockState state) {
			ModelResourceLocation ret = null;
			for (Object o : state.getProperties().keySet()) {
				if (o instanceof BlockTrack.TrackPieceProperty) {
					TrackPieceProperty property = (TrackPieceProperty) o;
					CategoryEnum categoryEnum = ((TrackPiece) state.getValue(property)).getParentStyle().getParentInfo().getCategory();
					String location = categoryEnum.BLOCKSTATE_DIR + ((TrackPiece) state.getValue(property)).getParentStyle().getName();
					ret = new ModelResourceLocation(location, this.getPropertyString(state.getProperties()));
				}
			}
			return ret;
		}
	}
}
