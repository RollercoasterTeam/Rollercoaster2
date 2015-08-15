package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import rcteam.rc2.RC2;

public enum CategoryEnum {
	STEEL("steel", Material.iron),
	WOODEN("wooden", Material.wood),
	INVERTED("inverted", Material.iron),
	WATER("water", Material.iron);

	private final String name;
	private final Material material;

	CategoryEnum(String name, Material material) {
		this.name = name;
		this.material = material;
	}

	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
		return this.name.toUpperCase();
	}

	public Material getMaterial() {
		return this.material;
	}

	public ResourceLocation getJsonLocation() {
		return new ResourceLocation(RC2.MODID.toLowerCase() + ":" + "coasters/" + this.name + "/");
	}

	public ResourceLocation getModelLocation() {
		return new ResourceLocation(RC2.MODID.toLowerCase() + ":" + "models/" + this.name + "/");
	}

	public static CategoryEnum getByName(String name) {
		return Lists.newArrayList(CategoryEnum.values()).stream().filter(categoryEnum -> categoryEnum.getName().equals(name)).findAny().get();
	}
}
