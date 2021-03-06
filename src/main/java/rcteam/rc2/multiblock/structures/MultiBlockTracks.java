package rcteam.rc2.multiblock.structures;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Level;
import rcteam.rc2.RC2;
import rcteam.rc2.block.BlockDummy;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.block.te.TileEntityTrack;
import rcteam.rc2.multiblock.MultiBlockManager;
import rcteam.rc2.multiblock.MultiBlockStructure;
import rcteam.rc2.multiblock.MultiBlockTemplate;
import rcteam.rc2.util.Utils;

import java.util.Iterator;
import java.util.List;

public class MultiBlockTracks extends MultiBlockStructure {
	private List<BlockPos> blockPositions = Lists.newArrayList();

	public MultiBlockTracks() {
		this(null, null);
	}

	public MultiBlockTracks(MultiBlockTemplate template) {
		this(template, null);
	}

	public MultiBlockTracks(MultiBlockTemplate template, List<Pair<EnumFacing, EnumFacing>> validOrients) {
		super(template, validOrients);
	}

	@SuppressWarnings("unchecked")
	public boolean buildTrack(World world, BlockPos pos, IBlockState trackState, EnumFacing facing) {
		if (!world.isRemote) {
			//TODO: figure out why rotateTo has to be called twice!
			this.getTemplate().rotateTo(facing, EnumFacing.UP);
			MultiBlockTemplate.TemplateIterator iterator = (MultiBlockTemplate.TemplateIterator) this.getTemplate().getAllInTemplate();
			List<BlockPos> dummyPositions = Lists.newArrayList();
			while (iterator.hasNext()) {
				Block block = (Block) iterator.next();
				if (Block.isEqualTo(block, Blocks.air)) continue;
				if (Block.isEqualTo(block, this.getTemplate().getMaster())) {
					world.setBlockState(pos, trackState, 3);
				} else {
					Vec3i masterLocationOffset = iterator.getCurrentMasterLocationOffset();
					if (facing == EnumFacing.WEST || facing == EnumFacing.EAST) {
						masterLocationOffset = new Vec3i(masterLocationOffset.getZ(), masterLocationOffset.getY(), -masterLocationOffset.getX());
					}
					switch (facing.getAxisDirection()) {
						case POSITIVE: masterLocationOffset = Utils.mul(masterLocationOffset, new Vec3i(-1, 1, -1)); break;
						case NEGATIVE: masterLocationOffset = Utils.mul(masterLocationOffset, new Vec3i(1, 1, 1)); break;
					}
					BlockPos dummyPos = new BlockPos(Utils.add(pos, masterLocationOffset));
					dummyPositions.add(dummyPos);
					if (world.getBlockState(dummyPos).getBlock() instanceof BlockDummy) {
						((TileEntityTrack) world.getTileEntity(dummyPos)).addParentPos(pos);
					} else {
						world.setBlockState(dummyPos, RC2Blocks.dummy.getDefaultState(), 3);
						((TileEntityTrack) world.getTileEntity(dummyPos)).setDummy(true).addParentPos(pos);
					}
				}
			}
			((TileEntityTrack) world.getTileEntity(pos)).setDummy(false).addDummyPositions(dummyPositions);
			this.blockPositions.addAll(dummyPositions);
			this.blockPositions.add(0, pos);
		}
		return true;
	}

	@Override
	public boolean buildStructure(World world, BlockPos pos, EnumFacing front, EnumFacing top) {
		return false;
	}

	@Override
	public boolean destroyStructure(World world, BlockPos pos) {
		if (!world.isRemote) {
			if (this.blockPositions.contains(pos)) {
				for (BlockPos pos1 : this.blockPositions) {
					TileEntityTrack tileEntityTrack = (TileEntityTrack) world.getTileEntity(pos1);
					if (tileEntityTrack != null && tileEntityTrack.getNumberOfParents() > 1) {
						tileEntityTrack.removeParentPos(pos);
					} else {
						world.destroyBlock(pos1, false);
						world.removeTileEntity(pos1);
					}
				}
			}
		}
		return true;
	}

	@Override
	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = super.writeToNBT();
		NBTTagList list = new NBTTagList();
		for (BlockPos blockPos : this.blockPositions) list.appendTag(new NBTTagLong(blockPos.toLong()));
		compound.setTag("blocks", list);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("blocks")) {
			NBTTagList list = compound.getTagList("blocks", Constants.NBT.TAG_LONG);
			for (int i = 0; i < list.tagCount(); i++) this.blockPositions.add(BlockPos.fromLong(((NBTTagLong) list.get(i)).getLong()));
		}
	}
}
