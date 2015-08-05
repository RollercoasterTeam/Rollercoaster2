package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import rcteam.rc2.RC2;

public enum CategoryEnum {
	STEEL("steel"),
	WOODEN("wooden"),
	INVERTED("inverted"),
	WATER("water"),
	MISC("misc");

	private final String name;

	CategoryEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
		return this.name.toUpperCase();
	}

	public ResourceLocation getJsonLocation() {
		return new ResourceLocation(RC2.MODID.toLowerCase() + ":" + "coasters/" + this.name + "/");
	}

	public ResourceLocation getModelLocation() {
		return new ResourceLocation(RC2.MODID.toLowerCase() + ":" + "models/" + this.name + "/");
	}

	public static CategoryEnum getByName(String name) {
		return Lists.newArrayList(CategoryEnum.values()).stream().filter(categoryEnum -> categoryEnum.getName().equals(name)).findAny().orElse(CategoryEnum.MISC);
	}
}
