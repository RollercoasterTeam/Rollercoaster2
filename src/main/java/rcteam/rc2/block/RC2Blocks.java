package rcteam.rc2.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.Pair;
import rcteam.rc2.block.te.*;
import rcteam.rc2.item.ItemTrack;
import rcteam.rc2.rollercoaster.CategoryEnum;
import rcteam.rc2.rollercoaster.CoasterStyle;
import rcteam.rc2.rollercoaster.TrackPiece;
import rcteam.rc2.rollercoaster.TrackPieceRegistry;
import rcteam.rc2.util.Reference;

import java.util.List;
import java.util.Map;

public class RC2Blocks {
	public static final Map<Block, Pair<String, ModelResourceLocation>> modelMap = Maps.newHashMap();
	public static Map<CategoryEnum, Block> trackMap = Maps.newEnumMap(CategoryEnum.class);
	public static Block entrance;
//	public static Block track_steel;
//	public static Block track_wooden;
//	public static Block track_inverted;
//	public static Block track_water;
	
	public static void preInit(Side side) {
		entrance = new BlockEntrance();
		registerBlock(entrance, "entrance");
		registerTE(TileEntityEntrance.class, entrance);

		registerTracks(side);
//		for (CategoryEnum categoryEnum : CategoryEnum.values()) {
//			categoryEnum.setValidPieces();
//		}

//		track_steel = new BlockTrack(CategoryEnum.STEEL.getInfo());
//		track_wooden = new BlockTrack(CategoryEnum.WOODEN.getInfo());
//		track_inverted = new BlockTrack(CategoryEnum.INVERTED.getInfo());
//		track_water = new BlockTrack(CategoryEnum.WATER.getInfo());

//		registerBlock(track_steel, ItemTrack.class, "track_steel", CategoryEnum.STEEL.getInfo());
//		registerBlock(track_wooden, ItemTrack.class, "track_wooden", CategoryEnum.WOODEN.getInfo());
//		registerBlock(track_inverted, ItemTrack.class, "track_inverted", CategoryEnum.INVERTED.getInfo());
//		registerBlock(track_water, ItemTrack.class, "track_water", CategoryEnum.WATER.getInfo());

//		if (side == Side.CLIENT) {
//			ModelLoader.setCustomStateMapper(track_steel, BlockTrack.TrackStateMapper.INSTANCE);
//			ModelLoader.setCustomStateMapper(track_wooden, BlockTrack.TrackStateMapper.INSTANCE);
//			ModelLoader.setCustomStateMapper(track_inverted, BlockTrack.TrackStateMapper.INSTANCE);
//			ModelLoader.setCustomStateMapper(track_water, BlockTrack.TrackStateMapper.INSTANCE);
//			ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track_steel), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
//			ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track_wooden), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
//			ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track_inverted), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
//			ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track_water), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
//			ModelBakery.addVariantName(Item.getItemFromBlock(track_steel), Reference.RESOURCE_PREFIX + "tracks/steel/hyper_twister");

//			ModelBakery.addVariantName(Item.getItemFromBlock(track_wooden), Reference.RESOURCE_PREFIX + "tracks/hyper_twister");
//			ModelBakery.addVariantName(Item.getItemFromBlock(track_inverted), Reference.RESOURCE_PREFIX + "tracks/hyper_twister");
//			ModelBakery.addVariantName(Item.getItemFromBlock(track_water), Reference.RESOURCE_PREFIX + "tracks/hyper_twister");
//		}

//		registerTE(TileEntityTrack.class, track_steel);
//		registerTE(TileEntityTrack.class, track_wooden);
//		registerTE(TileEntityTrack.class, track_inverted);
//		registerTE(TileEntityTrack.class, track_water);
	}
	
	private static void registerBlock(Block block, String name) {
		GameRegistry.registerBlock(block, name);
		modelMap.put(block, Pair.of(name, null));
	}

	private static void registerBlock(Block block, Class<? extends ItemBlock> itemClass, String name, Object ... args) {
		GameRegistry.registerBlock(block, itemClass, name, args);
		modelMap.put(block, Pair.of(name, null));
	}
	
	private static void registerTE(Class<? extends TileEntity> te, Block block) {
		GameRegistry.registerTileEntity(te, modelMap.get(block).getLeft());
	}

	public static void init(Side side) {
		List<Block> blocks = Lists.newArrayList(modelMap.keySet());
		for (Block block : blocks) {
			ModelResourceLocation location = new ModelResourceLocation(Reference.RESOURCE_PREFIX + modelMap.get(block).getLeft(), "inventory");
			modelMap.put(block, Pair.of(modelMap.get(block).getLeft(), location));
			if (side == Side.CLIENT && !(block instanceof BlockTrack)) {
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, location);
			}
		}
	}

	public static List<Item> getItemList() {
		List<Item> items = Lists.newArrayList();
		modelMap.keySet().forEach(block -> items.add(Item.getItemFromBlock(block)));
		trackMap.values().forEach(block -> items.add(Item.getItemFromBlock(block)));
		return items;
	}

	private static void registerTracks(Side side) {
		for (CategoryEnum categoryEnum : CategoryEnum.values()) {
			if (categoryEnum.PIECE_PROPERTY.getAllowedValues() != null && !categoryEnum.PIECE_PROPERTY.getAllowedValues().isEmpty()) {
				Block track = new BlockTrack(categoryEnum.getInfo());
				trackMap.put(categoryEnum, track);
				GameRegistry.registerBlock(track, ItemTrack.class, "track_" + categoryEnum.getName(), categoryEnum.getInfo());
				GameRegistry.registerTileEntity(TileEntityTrack.class, "track_" + categoryEnum.getName());
				if (side == Side.CLIENT) {
					ModelLoader.setCustomStateMapper(track, BlockTrack.TrackStateMapper.INSTANCE);
					ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
					if (trackMap.size() <= 1) { //temporary until the fix gets pulled into forge
						for (CoasterStyle style : categoryEnum.getInfo().getStyles()) {
							ModelLoader.addVariantName(Item.getItemFromBlock(track), categoryEnum.BLOCKSTATE_DIR + style.getName());
						}
					}
				}
			}
		}
	}
}
