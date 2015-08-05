package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import rcteam.rc2.RC2;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.item.ItemTrack;

import java.util.List;

public class CoasterStyle {
	private final String name;
	private final CategoryEnum category;
	private final List<String> pieces = Lists.newArrayList();
	private final List<String> trainCars = Lists.newArrayList();

	public CoasterStyle(String name, CategoryEnum category, List<String> pieces, List<String> trainCars) {
		this.name = name;
		this.category = category;
		this.pieces.addAll(pieces);
		this.trainCars.addAll(trainCars);
	}

	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
		StringBuilder builder = new StringBuilder();
		Lists.newArrayList(this.name.split("_")).forEach(s -> builder.append(s.toUpperCase() + " "));
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

	public ResourceLocation getJsonLocation() {
		return new ResourceLocation(RC2.MODID.toLowerCase() + ":" + "coasters/" + this.name + "/style.json");
	}

	public ResourceLocation getModelLocation() {
		return new ResourceLocation(RC2.MODID.toLowerCase() + ":" + "models/" + this.name + "/model.obj");
	}

	public CategoryEnum getCategory() {
		return this.category;
	}

	public List<String> getPieces() {
		return this.pieces;
	}

	public List<String> getTrainCars() {
		return this.trainCars;
	}

	public Material getBlockMaterial() {
		switch (this.category) {
			case STEEL:
			case WATER: //TODO: custom material?
			case INVERTED: return Material.iron;
			case WOODEN: return Material.wood;
			case MISC:
			default: return Material.rock;
		}
	}

	public void registerBlocks() {
		for (String s : this.pieces) {
			BlockTrack track = new BlockTrack(this, s);
			GameRegistry.registerBlock(track, ItemTrack.class, track.getUnlocalizedName());
		}
	}
}
