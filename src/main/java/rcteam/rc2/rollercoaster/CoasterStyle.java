package rcteam.rc2.rollercoaster;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.util.ResourceLocation;
import rcteam.rc2.RC2;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.item.ItemTrack;
import rcteam.rc2.util.OBJModel;
import rcteam.rc2.util.Reference;

import java.util.List;

public class CoasterStyle {
	private final String name;
	private final CategoryEnum category;
	private final List<String> pieces = Lists.newArrayList();
	private final List<String> trainCars = Lists.newArrayList();
	private ResourceLocation jsonLocation;
	private ResourceLocation modelLocation;
	private OBJModel model;

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

	public void setJsonLocation(ResourceLocation location) {
		this.jsonLocation = jsonLocation;
	}

	public ResourceLocation getJsonLocation() {
		return this.jsonLocation;
//		return new ResourceLocation(Reference.RELATIVE_PACKS_DIR + "coasters/" + this.name + "/style.json");
//		return new ResourceLocation(RC2.MODID.toLowerCase() + ":" + "coasters/" + this.name + "/style.json");
	}

	public void setModelLocation(ResourceLocation location) {
		this.modelLocation = location;
	}

	public ResourceLocation getModelLocation() {
		return this.modelLocation;
//		return new ResourceLocation(Reference.RELATIVE_PACKS_DIR + "coasters/" + this.name + "/model.obj");
//		return new ResourceLocation(RC2.MODID.toLowerCase() + ":" + "coasters/" + this.name + "/model.obj");
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

//	public Material getBlockMaterial() {
//		switch (this.category) {
//			case STEEL:
//			case WATER: //TODO: custom material?
//			case INVERTED: return Material.iron;
//			case WOODEN: return Material.wood;
//			case MISC:
//			default: return Material.rock;
//		}
//	}

	public void registerBlock() {
//		RC2Blocks.trackMap.put(this.category, new BlockTrack(this));
//		GameRegistry.registerBlock(RC2Blocks.trackMap.get(this.category), ItemTrack.class, RC2Blocks.trackMap.get(this.category).getUnlocalizedName());
//		GameRegistry.registerTileEntity(TileEntityTrack.class, RC2Blocks.trackMap.get(this.category).getUnlocalizedName());

//		switch (this.category) {
//			case STEEL:
//				RC2Blocks.track_steel = new BlockTrack(Material.iron, "steel");
//				GameRegistry.registerBlock(RC2Blocks.track_steel, ItemTrack.class, RC2Blocks.track_steel.getUnlocalizedName());
//				GameRegistry.registerTileEntity(TileEntityTrack.class, RC2Blocks.track_steel.getUnlocalizedName());
//				break;
//			case WOODEN:
//				RC2Blocks.track_wood = new BlockTrack(Material.wood, "wooden");
//				GameRegistry.registerBlock()
//		}
	}

//	public void registerBlocks() {
//		for (String s : this.pieces) {
//			BlockTrack track = new BlockTrack(this, s);
////			track.setBlockTextureName(this.model.getMaterialLibrary().getGroups().get(s).get)
//			GameRegistry.registerBlock(track, ItemTrack.class, track.getUnlocalizedName());
//			GameRegistry.registerTileEntity(TileEntityTrack.class, track.getUnlocalizedName());
//		}
//	}

//	public void registerItemTrackRenderer() {
//		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
//			for (String s : this.pieces) {
//				BlockTrack track = GameRegistry.findBlock()
//			}
//		}
//	}

	public void setModel(OBJModel model) {
		this.model = model;
	}

	public OBJModel getModel() {
		return this.model;
	}
}
