package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import javax.vecmath.Vector2f;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SupportUtils {
	public enum SupportType {
		STEEL("steel", Material.iron, SupportSlot.values()),
		WOODEN("wooden", Material.wood, new SupportSlot[] {SupportSlot.NORTH_WEST, SupportSlot.NORTH_EAST, SupportSlot.CENTER, SupportSlot.SOUTH_WEST, SupportSlot.SOUTH_EAST});

		public final String unlocalizedName;
		public final Material material;
		private final EnumMap<SupportSlot, Integer> validSlotMap = Maps.newEnumMap(SupportSlot.class);

		SupportType(String unlocalizedName, Material material, SupportSlot[] validSlots) {
			this.unlocalizedName = unlocalizedName;
			this.material = material;
			for (int i = 0; i < validSlots.length; i++) this.validSlotMap.put(validSlots[i], i);
		}

		public boolean isValidSlot(SupportSlot slot) {
			return this.validSlotMap.containsKey(slot);
		}

		public int getIndexOfSlot(SupportSlot slot) {
			return !this.isValidSlot(slot) ? -1 : this.validSlotMap.get(slot);
		}

		public EnumMap<SupportSlot, Integer> getValidSlotMap() {
			return this.validSlotMap;
		}

		public List<String> getValidSupportSlotNames() {
			List<String> strings = Lists.newArrayList();
			for (SupportSlot slot : this.validSlotMap.keySet()) {
				strings.add(slot.getGroupName());
				strings.add(slot.getGroupBaseName());
				strings.add(slot.getGroupTopName());
			}
			return strings;
		}
	}

	public enum SupportSlot {
		NORTH_WEST("north_west", 0.165f, 0.165f),
		NORTH("north", 0.5f, 0.165f),
		NORTH_EAST("north_east", 0.835f, 0.165f),
		WEST("west", 0.165f, 0.5f),
		CENTER("center", 0.5f, 0.5f),
		EAST("east", 0.835f, 0.5f),
		SOUTH_WEST("south_west", 0.165f, 0.835f),
		SOUTH("south", 0.5f, 0.835f),
		SOUTH_EAST("south_east", 0.835f, 0.835f);

		private final String internalName;
		private final String BASE_SUFFIX = "_base";
		private final String TOP_SUFFIX = "_top";
		public final Vector2f centerXZ;
		public final float widthXZ = 0.33f;
		public final float heightBase = 0.15f;
		public final float height = 1.0f;
		public final float gapSize = 0.005f;

		SupportSlot(String internalName, float centerX, float centerZ) {
			this.internalName = internalName;
			this.centerXZ = new Vector2f(centerX, centerZ);
		}

		public String getGroupName() {
			return this.internalName;
		}

		public String getGroupBaseName() {
			return this.internalName + this.BASE_SUFFIX;
		}

		public String getGroupTopName() {
			return this.internalName + this.TOP_SUFFIX;
		}
	}

	public static class SupportInfo {
		public final SupportType type;
		//<visible, basePlateVisible, topVisible>
		public EnumMap<SupportSlot, MutableTriple<Boolean, Boolean, Boolean>> visibilityMap = Maps.newEnumMap(SupportSlot.class);

		public SupportInfo(SupportType type) {
			this.type = type;
			for (SupportSlot slot : this.type.getValidSlotMap().keySet()) {
				this.visibilityMap.put(slot, MutableTriple.of(false, false, false));
			}
		}

		public SupportInfo(SupportType type, EnumMap<SupportSlot, MutableTriple<Boolean, Boolean, Boolean>> visibilityMap) {
			this.type = type;
			this.visibilityMap = visibilityMap;
		}

		public void setVisibility(SupportSlot slot, boolean visible) {
			if (this.type.isValidSlot(slot)) this.visibilityMap.get(slot).setLeft(visible);
		}

		public boolean getVisibility(SupportSlot slot) {
			return this.type.isValidSlot(slot) && this.visibilityMap.get(slot).getLeft();
		}

		public void setBasePlateVisibility(SupportSlot slot, boolean visible) {
			if (this.type.isValidSlot(slot)) this.visibilityMap.get(slot).setMiddle(visible);
		}

		public boolean getBasePlateVisibility(SupportSlot slot) {
			return this.type.isValidSlot(slot) && this.visibilityMap.get(slot).getMiddle();
		}

		public void setTopVisibility(SupportSlot slot, boolean visible) {
			if (this.type.isValidSlot(slot)) this.visibilityMap.get(slot).setRight(visible);
		}

		public boolean getTopVisibility(SupportSlot slot) {
			return this.type.isValidSlot(slot) && this.visibilityMap.get(slot).getRight();
		}

		public Triple<Boolean, Boolean, Boolean> getVisibilities(SupportSlot slot) {
			return this.visibilityMap.get(slot);
		}

		public boolean isSlotVisible(SupportSlot slot) {
			if (!this.type.isValidSlot(slot)) return false;
			else {
				Triple<Boolean, Boolean, Boolean> triple = this.getVisibilities(slot);
				return triple.getLeft() || triple.getMiddle() || triple.getRight();
			}
		}

		public NBTTagCompound writeToNBT() {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setInteger("type", this.type.ordinal());
			NBTTagList list = new NBTTagList();
			for (SupportSlot slot : this.type.getValidSlotMap().keySet()) {
				NBTTagCompound slotCompound = new NBTTagCompound();
				Triple<Boolean, Boolean, Boolean> visibilities = this.visibilityMap.get(slot);
				slotCompound.setInteger("slot", slot.ordinal());
				slotCompound.setBoolean("vis", visibilities.getLeft());
				slotCompound.setBoolean("baseVis", visibilities.getMiddle());
				slotCompound.setBoolean("topVis", visibilities.getRight());
				list.appendTag(slotCompound);
			}
			compound.setTag("slots", list);
			return compound;
		}

		public static SupportInfo readFromNBT(NBTTagCompound compound) {
			SupportType type = SupportType.values()[compound.getInteger("type")];
			EnumMap<SupportSlot, MutableTriple<Boolean, Boolean, Boolean>> visibilityMap = Maps.newEnumMap(SupportSlot.class);
			NBTTagList list = compound.getTagList("slots", Constants.NBT.TAG_COMPOUND);
			for (int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound slotCompound = list.getCompoundTagAt(i);
				MutableTriple<Boolean, Boolean, Boolean> triple = MutableTriple.of(slotCompound.getBoolean("vis"), slotCompound.getBoolean("baseVis"), slotCompound.getBoolean("topVis"));
				visibilityMap.put(SupportSlot.values()[slotCompound.getInteger("slot")], triple);
			}
			return new SupportInfo(type, visibilityMap);
		}

		public List<String> getInfoStrings() {
			List<String> strings = Lists.newArrayList();
			strings.add(String.format("Type: %s", this.type.unlocalizedName));
			return strings;
		}

		public List<String> getVisibleSlotNames() {
			List<String> visibleSlots = Lists.newArrayList();
			for (SupportSlot slot : this.type.getValidSlotMap().keySet()) {
				if (this.visibilityMap.containsKey(slot) && this.visibilityMap.get(slot).getLeft()) {
					visibleSlots.add(slot.getGroupName());
				}
			}
			return visibleSlots;
		}

		public List<String> getVisibleSlotBaseNames() {
			List<String> visibleSlotBases = Lists.newArrayList();
			for (SupportSlot slot : this.type.getValidSlotMap().keySet()) {
				if (this.visibilityMap.containsKey(slot) && this.visibilityMap.get(slot).getMiddle()) {
					visibleSlotBases.add(slot.getGroupBaseName());
				}
			}
			return visibleSlotBases;
		}

		public List<String> getVisibleSlotTopNames() {
			List<String> visibleSlotTops = Lists.newArrayList();
			for (SupportSlot slot : this.type.getValidSlotMap().keySet()) {
				if (this.visibilityMap.containsKey(slot) && this.visibilityMap.get(slot).getRight()) {
					visibleSlotTops.add(slot.getGroupTopName());
				}
			}
			return visibleSlotTops;
		}
	}

	private static Map<String, SupportSlot> nameToSlotMap = Maps.newHashMapWithExpectedSize(SupportSlot.values().length);

	private static void initMap() {
		nameToSlotMap.clear();
		for (SupportSlot slot : SupportSlot.values()) {
			nameToSlotMap.put(slot.getGroupName(), slot);
		}
	}

	public static SupportSlot getSlotFromName(String name) {
		if (nameToSlotMap.isEmpty()) initMap();
		if (name.endsWith("_base") || name.endsWith("_top")) return nameToSlotMap.get(name.substring(0, name.lastIndexOf('_')));
		else return nameToSlotMap.get(name);
	}
}
