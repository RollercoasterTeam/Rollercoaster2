package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;
import rcteam.rc2.RC2;

import java.util.List;
import java.util.Map;

public class TrackPieceInfo {
	private final CategoryEnum category;
	private final Map<String, CoasterStyle> styleMap = Maps.newHashMap();
	private CoasterStyle currentStyle;

	public TrackPieceInfo(CategoryEnum category) {
		this(category, null, null);
	}

	public TrackPieceInfo(CategoryEnum category, Map<String, CoasterStyle> styleMap, CoasterStyle currentStyle) {
		this.category = category;
		if (styleMap != null) {
			this.styleMap.putAll(styleMap);
		}
		this.currentStyle = currentStyle;
		for (CoasterStyle style : this.styleMap.values()) {
			style.setParentInfo(this);
		}
		if (this.currentStyle != null) this.currentStyle.setParentInfo(this);
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

	public boolean setCurrentStyle(CoasterStyle style, boolean force) {
		if (force || this.styleMap.containsValue(style)) {
			this.currentStyle = style;
			return true;
		}
		return false;
	}

	public CoasterStyle getCurrentStyle() {
		return this.currentStyle;
	}

	public void addStyleToMap(CoasterStyle style) {
		if (style != null) this.styleMap.put(style.getName(), style);
	}

	public void addStylesToMap(List<CoasterStyle> styles) {
		if (styles != null && !styles.isEmpty()) styles.forEach(style -> this.styleMap.put(style.getName(), style));
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

	public List<TrackPiece> getAllPieces() {
		List<TrackPiece> pieces = Lists.newArrayList();
		for (CoasterStyle style : this.getStyles()) {
			pieces.addAll(style.getPieces());
		}
		return pieces;
	}

//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder(String.format("TrackPieceInfo: Category: %s, Total Styles: %d, Styles: [", this.category.getName(), this.styleMap.size()));
//		this.styleMap.forEach((s, style) -> );
//		builder.delete(builder.length() - 2, builder.length());
//		builder.append("], ");
////		builder.append(String.format("Current Piece: %s", this.currentPiece.getName()));
//		return builder.toString();
//	}

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TrackPieceInfo info = (TrackPieceInfo) o;

		if (category != info.category) return false;
		if (styleMap != null ? !styleMap.equals(info.styleMap) : info.styleMap != null) return false;
		return !(currentStyle != null ? !currentStyle.equals(info.currentStyle) : info.currentStyle != null);

	}

	@Override
	public int hashCode() {
		int result = category != null ? category.hashCode() : 0;
		result = 31 * result + (styleMap != null ? styleMap.hashCode() : 0);
		result = 31 * result + (currentStyle != null ? currentStyle.hashCode() : 0);
		return result;
	}
}
