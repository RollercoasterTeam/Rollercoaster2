package rcteam.rc2.multiblock;

import com.google.common.base.Predicate;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.util.vector.Vector2f;
import rcteam.rc2.util.Matrix;
import rcteam.rc2.util.Utils;
import rcteam.rc2.util.Vector3i;

import javax.vecmath.Matrix4f;
import java.util.*;

public class MultiBlockTemplate {
	private List<List<List>> template = Lists.newArrayList();
	private Vector3i dimensions = new Vector3i(); //x: columns, y: layers, z: rows
	private boolean hasMaster;
	private Vector3i masterLocation = new Vector3i(-1, -1, -1); //x: columns, y: layers, z: rows
	private Block master;
	private String name;
	private EnumFacing front = EnumFacing.NORTH;
	private EnumFacing top = EnumFacing.UP;
//	private Matrix blockMatrix;

	public MultiBlockTemplate(String name) {
		this.name = name;
	}

	public MultiBlockTemplate makeTemplate(MultiBlockTemplate from, List<List<List>> replacementTemplate, Vector3i dimensions, Vector3i masterLocation, EnumFacing front, EnumFacing top) {
		MultiBlockTemplate template = new MultiBlockTemplate(from.name);
		template.name = from.name;
		template.dimensions = dimensions;
		template.hasMaster = from.hasMaster;
		template.masterLocation = masterLocation;
		template.master = from.master;
		template.front = front;
		template.top = top;
		template.template = replacementTemplate;
		return template;
	}

	public String getName() {
		return this.name;
	}

	@SuppressWarnings("unchecked")
	public void addLayer(Block master, Object... objs) {
		//TODO: add support for defining variant of blocks (ie. stone v andesite)
		this.dimensions.y++;
		this.dimensions.z = 0;
		String s = "";
		int i, j, k;
		i = k = j = 0;

		/* Parse Template Strings */
		if (objs[1] instanceof String[]) {
			String[] strings = (String[]) objs[1];
			this.dimensions.z = strings.length;
			for (int l = 0; l < strings.length; l++) {
				String s1 = strings[l];
				this.dimensions.x = s1.length();
				++k;
				j = s1.length();
				s = s + s1;
			}
		} else {
			while (objs[i] instanceof String) {
				this.dimensions.z++;
				String s2 = (String) objs[i++];
				this.dimensions.x = s2.length();
				++k;
				j = s2.length();
				s = s + s2;
			}
		}

		/* Parse and Assign Characters With Block Instances */
		HashMap map;
		Block block = null;
		MultiBlockAlias alias = null;
		Character masterChar = '\0'; //unicode 0

		for (map = new HashMap(); i < objs.length; i += 2) {
			block = null;
			alias = null;
			Character character = (Character) objs[i];
			if (objs[i + 1] instanceof Block) {
				block = (Block) objs[i + 1];
			} else if (objs[i + 1] instanceof Block[]) {
				alias = new MultiBlockAlias((Block[]) objs[i + 1]);
			} else if (objs[i + 1] instanceof MultiBlockAlias) {
				alias = (MultiBlockAlias) objs[i + 1];
			}
			if (master != null) {
				if (this.master == null) {
					this.hasMaster = false;
					if (block != null && Block.isEqualTo(block, master)) {
						this.hasMaster = true;
						this.master = block;
						masterChar = character;
					}
				} else this.hasMaster = false;
			}
			if (StringUtils.contains(s, ' ')) map.put(' ', Blocks.air);
//			if (Character.isSpaceChar(character)) block = Blocks.air;
			map.put(character, block != null ? block : alias);
		}

		/* Create Layer Using Block from HashMap */
		Object[] entries = new Object[j * k];
		char dummyChar = '\0'; //unicode 0

		for (int i1 = 0; i1 < j * k; i1++) {
			char c0 = s.charAt(i1);
			if (c0 == '?') dummyChar = c0;
			if (map.containsKey(Character.valueOf(c0))) {
				if (map.get(Character.valueOf(c0)) instanceof Block) {
					block = (Block) map.get(Character.valueOf(c0));
					if (block != null && Block.isEqualTo(block, this.master) && c0 == masterChar && c0 != dummyChar) {
						this.masterLocation.x = i1 % j;
						this.masterLocation.y = this.dimensions.y - 1;
						this.masterLocation.z = i1 / k;
					}
				}
				entries[i1] = c0 == dummyChar ? null : (map.get(Character.valueOf(c0)) instanceof Block ? (Block) map.get(Character.valueOf(c0)) : (MultiBlockAlias) map.get(Character.valueOf(c0)));
			} else entries[i1] = Blocks.air;
		}

		int index = 0;
		this.template = this.template != null ? this.template : Lists.newArrayList();
		this.template.add(this.dimensions.y - 1, Lists.newArrayList());
		for (int row = 0; row < this.dimensions.z; row++) {
			this.template.get(this.dimensions.y - 1).add(row, Lists.newArrayList());
			for (int col = 0; col < this.dimensions.x; col++) {
				if (this.template.get(this.dimensions.y - 1).get(row).isEmpty()) {
					this.template.get(this.dimensions.y - 1).get(row).add(entries[index]);
				} else {
					((ArrayList) this.template.get(this.dimensions.y - 1).get(row)).ensureCapacity(col);
					this.template.get(this.dimensions.y - 1).get(row).add(col, entries[index]);
				}
				index++;
			}
		}
	}

	public Vec3i getDimensions() {
		return this.dimensions.toVec3i();
	}

	public boolean getHasMaster() {
		return this.hasMaster;
	}

	public Block getMaster() {
		return this.master;
	}

	public Vec3i getMasterLocation() {
		return this.masterLocation.toVec3i();
	}

	public Vec3i getMasterLocationOffset() {
		return Utils.negate(this.masterLocation.toVec3i());
	}

	public List<List<List>> getTemplate() {
		return this.template;
	}

	public boolean isContainedInTemplate(Block block) {
		for (List<List> layer : this.template) {
			for (List row : layer) {
				for (Object column : row) {
					if (column instanceof Block && Block.isEqualTo(block, (Block) column)) return true;
					else if (column instanceof MultiBlockAlias) {
						for (Block block1 : ((MultiBlockAlias) column).getAliases()) {
							if (Block.isEqualTo(block, block1)) return true;
						}
					}
				}
			}
		}
		return false;
	}

	public Iterator getAllInTemplate() {
		return new TemplateIterator(this.template, this.dimensions, new Vector3i(Utils.negate(this.masterLocation.toVec3i())));
	}

	public List getAllInTemplateList() {
		return Lists.newArrayList(getAllInTemplate());
	}

	public MultiBlockTemplate setOrientation(Pair<EnumFacing, EnumFacing> orientation) {
		return this.rotateTo(orientation.getLeft(), orientation.getRight());
	}

	public Pair<EnumFacing, EnumFacing> getOrientation() {
		return Pair.of(this.front, this.top);
	}

	public MultiBlockTemplate rotateTo(EnumFacing front, EnumFacing top) {
		List<Vector2f> matrixVectors = Lists.newArrayList();
//		if (this.blockMatrix == null) {
			for (int l = 0; l < this.dimensions.y; l++) {
				for (int r = 0; r < this.dimensions.z; r++) {
					for (int c = 0; c < this.dimensions.x; c++) {
						matrixVectors.add(new Vector2f(r, c));
					}
				}
			}
//		}
		int times;
		boolean clockwise;
		if (top == EnumFacing.UP) {
			if (top == this.top) {
				if (front == this.front) {
					return this;
				} else if (this.front.getHorizontalIndex() == 0) {
					clockwise = front.getHorizontalIndex() != 3;
					times = 1;
				} else if (this.front.getHorizontalIndex() == 3) {
					clockwise = front.getHorizontalIndex() == 0;
					times = 1;
				} else {
					clockwise = this.front.getHorizontalIndex() < front.getHorizontalIndex();
					times = Math.abs(this.front.getHorizontalIndex() - front.getHorizontalIndex());
				}
				this.front = front;
				this.top = top;
				return rotateLayers(this, times, clockwise);
			}
			//TODO!
			return this;
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	private MultiBlockTemplate rotateLayers(MultiBlockTemplate template, int times, boolean clockWise) {
		List<List<List>> replacement = new ArrayList<>(template.dimensions.y);
		for (int l = 0; l < template.dimensions.y; l++) {
			replacement.add(new ArrayList<>(template.dimensions.z));
			for (int r = 0; r < template.dimensions.z; r++) {
				replacement.get(l).add(new ArrayList<>(template.dimensions.x));
			}
		}
		List<Vector2f> vectors = Lists.newArrayList();
		for (int r = 0; r < template.dimensions.z; r++) {
			for (int c = 0; c < template.dimensions.x; c++) {
				vectors.add(new Vector2f(r, c));
			}
		}
		Matrix startMatrix = Matrix.getMatrixFromVectors(vectors);
		Matrix rotatedMatrix = Matrix.rotate(startMatrix, times, !clockWise, 0, 0);
		Matrix startMaster = Matrix.getMatrixFromVectors(Lists.newArrayList(new Vector2f(template.getMasterLocation().getX(), template.getMasterLocation().getZ())));
		Matrix rotatedMaster = Matrix.rotate(startMaster, times, !clockWise, -2, -2);
		for (int l = 0; l < template.dimensions.y; l++) {
			for (int r = 0; r < template.dimensions.z; r++) {
				for (int c = 0; c < template.dimensions.x; c++) {
					Vector3i end = new Vector3i(rotatedMatrix.matrix[1][c], l, rotatedMatrix.matrix[0][c]);
					replacement.get(l).get(c).add(r, template.getTemplate().get(l).get(end.x).get(end.z));
				}
			}
		}
		Vector3i masterLocation = new Vector3i(rotatedMaster.matrix[1][0], template.getMasterLocation().getY(), rotatedMaster.matrix[0][0]);
		Vector3i dimensions = new Vector3i(template.dimensions.z, template.dimensions.y, template.dimensions.x);
		MultiBlockTemplate ret = template.makeTemplate(this, replacement, dimensions, masterLocation, template.front, template.top);
		return ret;
	}

	public static class MultiBlockAlias implements Predicate {
		private final List<Block> aliases;

		public MultiBlockAlias(List<Block> aliases) {
			this.aliases = aliases;
		}

		public MultiBlockAlias(Block[] aliases) {
			this.aliases = Lists.newArrayList(aliases);
		}

		public List<Block> getAliases() {
			return this.aliases;
		}

		@Override
		public boolean apply(Object input) {
			if (!(input instanceof Block)) return false;
			return this.aliases.contains(input);
		}
	}

	public static class TemplateIterator extends AbstractIterator {
		int index = 0;
		Vector3i current = new Vector3i();
		Vector3i dimensions;
		Vector3i currentMasterLocationOffset;
		Vec3i masterLocationOffset;
		List<List<List>> template;

		public TemplateIterator(List<List<List>> template, Vector3i dimensions, Vector3i masterLocationOffset) {
			this.current.x--;
			this.template = template;
			this.dimensions = dimensions;
			this.masterLocationOffset = new Vec3i(masterLocationOffset.x, masterLocationOffset.y, masterLocationOffset.z);
			this.currentMasterLocationOffset = masterLocationOffset;
			this.currentMasterLocationOffset.x--;
		}

		public Vec3i getCurrentIndices() {
			return new Vec3i(current.x, current.y, current.z);
		}

		public Vec3i getMasterLocationOffset() {
			return this.masterLocationOffset;
		}

		public Vec3i getCurrentMasterLocationOffset() {
			return new Vec3i(currentMasterLocationOffset.x, currentMasterLocationOffset.y, currentMasterLocationOffset.z);
		}

		@Override
		protected Object computeNext() {
			if (index == dimensions.getSize()) return endOfData();
			if (current.x + 1 < dimensions.x) {
				current.x++;
				currentMasterLocationOffset.x++;
			} else if (current.z + 1 < dimensions.z) {
				current.x = 0;
				currentMasterLocationOffset.x = masterLocationOffset.getX();
				current.z++;
				currentMasterLocationOffset.z++;
			} else if (current.y + 1 < dimensions.y) {
				current.x = 0;
				currentMasterLocationOffset.x = masterLocationOffset.getX();
				current.z = 0;
				currentMasterLocationOffset.z = masterLocationOffset.getZ();
				current.y++;
				currentMasterLocationOffset.y++;
			}
			Object object = template.get(current.y).get(current.z).get(current.x);
			index++;
			return object;
		}
	}
}
