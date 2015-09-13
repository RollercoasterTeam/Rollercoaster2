package rcteam.rc2.multiblock;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import scala.tools.nsc.backend.icode.Primitives;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class MultiBlockStructure {
	protected Pair<EnumFacing, EnumFacing> currentOrientation = Pair.of(EnumFacing.NORTH, EnumFacing.UP);
	private Map<EnumFacing, List<EnumFacing>> validFrontToTops = Maps.newEnumMap(EnumFacing.class);
	private Map<EnumFacing, List<EnumFacing>> validTopToFronts = Maps.newEnumMap(EnumFacing.class);
	private MultiBlockTemplate template;

	public MultiBlockStructure(MultiBlockTemplate template) {
		this(template, null);
	}

	public MultiBlockStructure(MultiBlockTemplate template, List<Pair<EnumFacing, EnumFacing>> validOrients) {
		this.template = template;
		if (validOrients != null) initializeOrients(validOrients);
	}

	private void initializeOrients(List<Pair<EnumFacing, EnumFacing>> validOrients) {
		for (Pair<EnumFacing, EnumFacing> pair : validOrients) {
			if (!this.validFrontToTops.containsKey(pair.getLeft())) this.validFrontToTops.put(pair.getLeft(), Lists.newArrayList(pair.getRight()));
			else this.validFrontToTops.get(pair.getLeft()).add(pair.getRight());
			if (!this.validTopToFronts.containsKey(pair.getRight())) this.validTopToFronts.put(pair.getRight(), Lists.newArrayList(pair.getLeft()));
			else this.validTopToFronts.get(pair.getRight()).add(pair.getLeft());
		}
	}

	public EnumFacing getFront() {
		return this.currentOrientation.getLeft();
	}

	public EnumFacing getTop() {
		return this.currentOrientation.getRight();
	}

	public boolean setOrientation(EnumFacing front, EnumFacing top) {
		if (top == front || top == front.getOpposite()) return false;
		if (!this.validFrontToTops.containsKey(front) || !this.validFrontToTops.get(front).contains(top)) return false;
		if (!this.validTopToFronts.containsKey(top) || !this.validTopToFronts.get(top).contains(front)) return false;
		this.template = this.template.setOrientation(Pair.of(front, top));
		this.currentOrientation = Pair.of(front, top);
		return true;
	}

	public Pair<EnumFacing, EnumFacing> getOrientation() {
		return this.currentOrientation;
	}

	protected void setTemplate(MultiBlockTemplate template) {
		this.template = template;
	}

	public MultiBlockTemplate getTemplate() {
		return this.template;
	}

	public boolean checkStructure(World world, BlockPos pos) {
		if (world == null || pos == null || !this.template.isContainedInTemplate(world.getBlockState(pos).getBlock())) return false;
		if (this.template.getHasMaster()) {
			if (Block.isEqualTo(world.getBlockState(pos).getBlock(), this.template.getMaster())) {
				Vec3i masterLocationOffset = this.template.getMasterLocationOffset();
				BlockPos minCorner = pos.add(masterLocationOffset);
				BlockPos maxCorner = pos.add(this.template.getDimensions().getX() + masterLocationOffset.getX(), this.template.getDimensions().getY() + masterLocationOffset.getY(), this.template.getDimensions().getZ() + masterLocationOffset.getZ());
				List<BlockPos> positions = getAllInBoxList(minCorner, maxCorner);
				if (!this.template.getOrientation().equals(this.currentOrientation)) this.template = this.template.rotateTo(this.currentOrientation.getLeft(), this.currentOrientation.getRight());
				List templateContents = this.template.getAllInTemplateList();
				if (positions.size() != templateContents.size()) return false;
				for (int i = 0; i < positions.size(); i++) {
					Block worldBlock = world.getBlockState(positions.get(i)).getBlock();
					if (templateContents.get(i) instanceof MultiBlockTemplate.MultiBlockAlias) {
						MultiBlockTemplate.MultiBlockAlias alias = (MultiBlockTemplate.MultiBlockAlias) templateContents.get(i);
						return alias.apply(worldBlock);
					} else {
						Block templateBlock = (Block) templateContents.get(i);
						if (!Block.isEqualTo(worldBlock, templateBlock)) return false;
					}
				}
			} else return false; //TODO!
		}
		return true;
	}

	public static Iterable<BlockPos> getAllInBox(BlockPos from, BlockPos to) {
		final BlockPos min = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
		final BlockPos max = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
		return new Iterable<BlockPos>() {
			@Override
			public Iterator<BlockPos> iterator() {
				return new AbstractIterator<BlockPos>() {
					private BlockPos lastReturned = null;
					private int lastIndex = -1;
					@Override
					protected BlockPos computeNext() {
						if (this.lastReturned == null) {
							this.lastIndex = 0;
							this.lastReturned = min;
							return this.lastReturned;
						} else if (this.lastReturned.equals(max)) {
							this.lastIndex++;
							return this.endOfData();
						} else {
							this.lastIndex++;
							int x = this.lastReturned.getX();
							int y = this.lastReturned.getY();
							int z = this.lastReturned.getZ();
							if (x < max.getX()) x++;
							else if (z < max.getZ()) {
								x = min.getX();
								z++;
							} else if (y < max.getY()) {
								x = min.getX();
								z = min.getZ();
								y++;
							}
							this.lastReturned = new BlockPos(x, y, z);
							return this.lastReturned;
						}
					}
					protected int getLastIndex() {
						return this.lastIndex;
					}
				};
			}
		};
	}

	public static List<BlockPos> getAllInBoxList(BlockPos from, BlockPos to) {
		return Lists.newArrayList(getAllInBox(from, to));
	}

	private NBTTagCompound writeTemplateToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setString("name", this.template.getName());
		compound.setByte("front", (byte) this.template.getOrientation().getLeft().getIndex());
		compound.setByte("top", (byte) this.template.getOrientation().getRight().getIndex());
		return compound;
	}

	private void readTemplateFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("name")) this.template = MultiBlockManager.templateMap.get(compound.getString("name"));
		if (this.template != null && compound.hasKey("front") && compound.hasKey("top")) this.template.setOrientation(Pair.of(EnumFacing.getFront(compound.getByte("front")), EnumFacing.getFront(compound.getByte("top"))));
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setByte("front", (byte) this.getFront().getIndex());
		compound.setByte("top", (byte) this.getTop().getIndex());
		NBTTagCompound frontToTopCompound = new NBTTagCompound();
		NBTTagCompound topToFrontCompound = new NBTTagCompound();
		for (Map.Entry<EnumFacing, List<EnumFacing>> entry : this.validFrontToTops.entrySet()) {
			byte[] topBytes = new byte[entry.getValue().size()];
			for (int i = 0; i < topBytes.length; i++) topBytes[i] = (byte) entry.getValue().get(i).getIndex();
			frontToTopCompound.setByteArray(entry.getKey().getName(), topBytes);
		}
		for (Map.Entry<EnumFacing, List<EnumFacing>> entry : this.validTopToFronts.entrySet()) {
			byte[] frontBytes = new byte[entry.getValue().size()];
			for (int i = 0; i < frontBytes.length; i++) frontBytes[i] = (byte) entry.getValue().get(i).getIndex();
			topToFrontCompound.setByteArray(entry.getKey().getName(), frontBytes);
		}
		compound.setTag("f2t", frontToTopCompound);
		compound.setTag("t2f", topToFrontCompound);
		compound.setTag("template", this.writeTemplateToNBT());
		return compound;
	}

	@SuppressWarnings("unchecked")
	public void readFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("front") && compound.hasKey("top")) this.setOrientation(EnumFacing.getFront(compound.getByte("front")), EnumFacing.getFront(compound.getByte("top")));
		if (compound.hasKey("f2t")) {
			NBTTagCompound frontToTopCompound = compound.getCompoundTag("f2t");
			Set<String> keys = frontToTopCompound.getKeySet();
			for (String s : keys) {
				EnumFacing front = EnumFacing.byName(s);
				byte[] topBytes = frontToTopCompound.getByteArray(s);
				List<EnumFacing> tops = Lists.newArrayList();
				for (byte b : topBytes) tops.add(EnumFacing.getFront(b));
				this.validFrontToTops.put(front, tops);
			}
		}
		if (compound.hasKey("t2f")) {
			NBTTagCompound topToFrontCompound = compound.getCompoundTag("t2f");
			Set<String> keys = topToFrontCompound.getKeySet();
			for (String s : keys) {
				EnumFacing top = EnumFacing.byName(s);
				byte[] frontBytes = topToFrontCompound.getByteArray(s);
				List<EnumFacing> fronts = Lists.newArrayList();
				for (byte b : frontBytes) fronts.add(EnumFacing.getFront(b));
				this.validTopToFronts.put(top, fronts);
			}
		}
		if (compound.hasKey("template")) this.readTemplateFromNBT(compound.getCompoundTag("template"));
	}

	public abstract boolean buildStructure(World world, BlockPos pos, EnumFacing front, EnumFacing top);

	public abstract boolean destroyStructure(World world, BlockPos pos);
}
