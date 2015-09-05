package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;

import java.util.List;
import java.util.Map;

public class TrackPieceInfo implements Comparable {
	private final CategoryEnum category;
	private final Map<String, CoasterStyle> styleMap = Maps.newHashMap();
	private CoasterStyle currentStyle;

	public TrackPieceInfo(CategoryEnum category) {
		this(category, null, null);
	}

	public TrackPieceInfo(CategoryEnum category, Map<String, CoasterStyle> styleMap) {
		this(category, styleMap, null);
	}

	public TrackPieceInfo(CategoryEnum category, Map<String, CoasterStyle> styleMap, CoasterStyle currentStyle) {
		this.category = category;
		if (styleMap != null) {
			this.styleMap.putAll(styleMap);
		}
		if (!this.styleMap.values().isEmpty()) {
			for (CoasterStyle style : this.styleMap.values()) style.setParentInfo(this);
			List<CoasterStyle> styles = Lists.newArrayList(this.styleMap.values());
			this.currentStyle = currentStyle == null ? styles.get(0) : currentStyle;
			if (this.currentStyle != null) this.currentStyle.setParentInfo(this);
		}
	}

	public CoasterStyle buildStyle(String name, List<TrackPiece> validPieces, List<String> trainCars) {
		CoasterStyle style = new CoasterStyle(name, validPieces, trainCars, this);
		this.styleMap.put(name, style);
		return style;
	}

	public List<TrackPiece> getAllowedValues() {
		List<TrackPiece> allowed = Lists.newArrayList();
		for (CoasterStyle style : this.styleMap.values()) {
			allowed.addAll(style.getValidPieces());
		}
		return allowed;
	}

	public CategoryEnum getCategory() {
		return this.category;
	}

	public boolean setCurrentStyle(String name) {
		if (this.styleMap.containsKey(name)) {
			this.currentStyle = this.styleMap.get(name);
			return true;
		}
		return false;
	}

	public CoasterStyle getCurrentStyle() {
		return this.currentStyle;
	}

	public Map<String, CoasterStyle> getStyleMap() {
		return this.styleMap;
	}

	public List<String> getStyleNames() {
		return Lists.newArrayList(this.styleMap.keySet());
	}

	public List<CoasterStyle> getStyles() {
		return Lists.newArrayList(this.styleMap.values());
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("category", this.category.ordinal());
		NBTTagList keys = new NBTTagList();
		NBTTagList values = new NBTTagList();
		for (Map.Entry<String, CoasterStyle> entry : this.styleMap.entrySet()) {
			keys.appendTag(new NBTTagString(entry.getKey()));
			values.appendTag(entry.getValue().writeToNBT());
		}
		compound.setTag("style_names", keys);
		compound.setTag("styles", values);
		compound.setTag("current_style", this.currentStyle.writeToNBT());
		return compound;
	}

	public static TrackPieceInfo readFromNBT(NBTTagCompound compound) {
		CategoryEnum categoryEnum = CategoryEnum.values()[compound.getInteger("category")];
		Map<String, CoasterStyle> styleMap = Maps.newHashMap();
		NBTTagList keys = compound.getTagList("style_names", Constants.NBT.TAG_STRING);
		NBTTagList values = compound.getTagList("styles", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < keys.tagCount(); i++) {
			String name = keys.getStringTagAt(i);
			CoasterStyle style = CoasterStyle.readFromNBT(values.getCompoundTagAt(i));
			styleMap.put(name, style);
		}
		CoasterStyle currentStyle = CoasterStyle.readFromNBT(compound.getCompoundTag("current_style"));
		return new TrackPieceInfo(categoryEnum, styleMap, currentStyle);
	}

	@Override
	public int compareTo(Object o) {
		if (o == null || !(o instanceof TrackPieceInfo)) return 1;
		TrackPieceInfo input = (TrackPieceInfo) o;
		int catComp = this.category.compareTo(input.getCategory());
		if (catComp == 0) {
			int currComp = this.currentStyle.compareTo(input.currentStyle);
			if (currComp == 0) {
				if (!this.styleMap.equals(input.getStyleMap())) {
					return Integer.compare(this.styleMap.size(), input.getStyleMap().size());
				} else return 0;
			} else return currComp;
		} else return catComp;
	}
}
