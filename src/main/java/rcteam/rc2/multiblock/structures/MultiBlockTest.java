package rcteam.rc2.multiblock.structures;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Level;
import rcteam.rc2.RC2;
import rcteam.rc2.multiblock.MultiBlockStructure;
import rcteam.rc2.multiblock.MultiBlockTemplate;
import rcteam.rc2.util.Utils;

import java.util.List;

public class MultiBlockTest extends MultiBlockStructure {
	public MultiBlockTest(MultiBlockTemplate template) {
		this(template, null);
	}

	public MultiBlockTest(MultiBlockTemplate template, List<Pair<EnumFacing, EnumFacing>> validOrients) {
		super(template, validOrients);
	}

	@Override
	public boolean buildStructure(World world, BlockPos pos, EnumFacing front, EnumFacing top) {
		if (!world.isRemote) {
			this.getTemplate().rotateTo(front, top);
			this.setTemplate(this.getTemplate().rotateTo(front, top));
			MultiBlockTemplate.TemplateIterator iterator = (MultiBlockTemplate.TemplateIterator) this.getTemplate().getAllInTemplate();
			while (iterator.hasNext()) {
				Block block = (Block) iterator.next();
				if (Block.isEqualTo(block, this.getTemplate().getMaster())) {
					world.setBlockState(pos, block.getDefaultState().withProperty(BlockFurnace.FACING, front), 3);
				} else {
					Vec3i masterLocationOffset = iterator.getCurrentMasterLocationOffset();
					if (front == EnumFacing.WEST || front == EnumFacing.EAST) {
						masterLocationOffset = new Vec3i(masterLocationOffset.getZ(), masterLocationOffset.getY(), masterLocationOffset.getX());
					}
					switch (front.getAxisDirection()) {
						case POSITIVE: masterLocationOffset = Utils.mul(masterLocationOffset, new Vec3i(-1, 1, -1)); break;
						case NEGATIVE: masterLocationOffset = Utils.mul(masterLocationOffset, new Vec3i(1, 1, 1)); break;
					}
					Vec3i location = Utils.add(pos, masterLocationOffset);
					BlockPos dummyPos = new BlockPos(location);
					world.setBlockState(dummyPos, block.getDefaultState(), 3);
				}
			}
		}
		return true;
	}

	@Override
	public boolean destroyStructure(World world, BlockPos pos) {
		return false;
	}
}
