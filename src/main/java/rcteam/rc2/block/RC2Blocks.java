package rcteam.rc2.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.Pair;
import rcteam.rc2.block.te.*;
import rcteam.rc2.item.ItemTrack;
import rcteam.rc2.rollercoaster.CategoryEnum;
import rcteam.rc2.rollercoaster.TrackPieceInfo;
import rcteam.rc2.rollercoaster.TrackPieceRegistry;
import rcteam.rc2.util.Reference;
import rcteam.rc2.util.TrackStateMapper;

import java.util.List;
import java.util.Map;

public class RC2Blocks {
	public static final Map<Block, Pair<String, ModelResourceLocation>> modelMap = Maps.newHashMap();
	public static final Map<CategoryEnum, TrackPieceInfo> infoMap = Maps.newEnumMap(CategoryEnum.class);
	public static Block entrance;
	public static Block track_steel;
	public static Block track_wood;
	public static Block track_inverted;
	public static Block track_water;
	
	public static void preInit(Side side) {
		entrance = new BlockEntrance();

//		TrackPieceInfo steelInfo = new TrackPieceInfo(CategoryEnum.STEEL, TrackPieceRegistry.INSTANCE.getPieces());         //TODO: distinguish between different category pieces and different Style pieces!
//		TrackPieceInfo woodInfo = new TrackPieceInfo(CategoryEnum.WOODEN, TrackPieceRegistry.INSTANCE.getPieces());
//		TrackPieceInfo invertedInfo = new TrackPieceInfo(CategoryEnum.INVERTED, TrackPieceRegistry.INSTANCE.getPieces());
//		TrackPieceInfo waterInfo = new TrackPieceInfo(CategoryEnum.WATER, TrackPieceRegistry.INSTANCE.getPieces());
		infoMap.put(CategoryEnum.STEEL, new TrackPieceInfo(CategoryEnum.STEEL, TrackPieceRegistry.INSTANCE.getPieces()));
		infoMap.put(CategoryEnum.WOODEN, new TrackPieceInfo(CategoryEnum.WOODEN, TrackPieceRegistry.INSTANCE.getPieces()));
		infoMap.put(CategoryEnum.INVERTED, new TrackPieceInfo(CategoryEnum.INVERTED, TrackPieceRegistry.INSTANCE.getPieces()));
		infoMap.put(CategoryEnum.WATER, new TrackPieceInfo(CategoryEnum.WATER, TrackPieceRegistry.INSTANCE.getPieces()));

//		track_steel = new BlockTrackSteel(steelInfo);
//		track_wood = new BlockTrackWood(woodInfo);
//		track_inverted = new BlockTrackInverted(invertedInfo);
//		track_water = new BlockTrackWater(waterInfo);
		BlockTrack.PIECE_PROPERTY.setAllowedValues(infoMap.get(CategoryEnum.STEEL).getPieces());
		track_steel = new BlockTrack(infoMap.get(CategoryEnum.STEEL));
		BlockTrack.PIECE_PROPERTY.setAllowedValues(infoMap.get(CategoryEnum.WOODEN).getPieces());
		track_wood = new BlockTrack(infoMap.get(CategoryEnum.WOODEN));
		BlockTrack.PIECE_PROPERTY.setAllowedValues(infoMap.get(CategoryEnum.INVERTED).getPieces());
		track_inverted = new BlockTrack(infoMap.get(CategoryEnum.INVERTED));
		BlockTrack.PIECE_PROPERTY.setAllowedValues(infoMap.get(CategoryEnum.WATER).getPieces());
		track_water = new BlockTrack(infoMap.get(CategoryEnum.WATER));

		registerBlock(entrance, "entrance");
		registerBlock(track_steel, ItemTrack.class, "track_steel", side);
		registerBlock(track_wood, ItemTrack.class, "track_wooden", side);
		registerBlock(track_inverted, ItemTrack.class, "track_inverted", side);
		registerBlock(track_water, ItemTrack.class, "track_water", side);

		if (side == Side.CLIENT) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(track_steel), 0, new ModelResourceLocation(Reference.RESOURCE_PREFIX + "tracks/hyper_twister", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(track_wood), 0, new ModelResourceLocation(Reference.RESOURCE_PREFIX + "tracks/hyper_twister", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(track_inverted), 0, new ModelResourceLocation(Reference.RESOURCE_PREFIX + "tracks/hyper_twister", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(track_water), 0, new ModelResourceLocation(Reference.RESOURCE_PREFIX + "tracks/hyper_twister", "inventory"));
//			ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track_steel), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
//			ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track_wood), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
//			ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track_inverted), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
//			ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track_water), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
		}

		registerTE(TileEntityEntrance.class, entrance);
//		registerTE(TileEntityTrackSteel.class, track_steel);
//		registerTE(TileEntityTrackWood.class, track_wood);
//		registerTE(TileEntityTrackInverted.class, track_inverted);
//		registerTE(TileEntityTrackWater.class, track_water);
		registerTE(TileEntityTrack.class, track_steel);
		registerTE(TileEntityTrack.class, track_wood);
		registerTE(TileEntityTrack.class, track_inverted);
		registerTE(TileEntityTrack.class, track_water);
	}
	
	private static void registerBlock(Block block, String name) {
		GameRegistry.registerBlock(block, name);
		modelMap.put(block, Pair.of(name, null));
	}

	private static void registerBlock(Block block, Class<? extends ItemBlock> itemClass, String name, Side side) {
		if (block instanceof BlockTrack && side == Side.CLIENT) {
			ModelLoader.setCustomStateMapper(block, TrackStateMapper.INSTANCE);
//			ItemTrack track = new ItemTrack(block);
////			ModelLoader.setCustomMeshDefinition(track, ItemTrack.ItemTrackMeshDefinition.INSTANCE);
//			ModelLoader.setCustomModelResourceLocation(track, 0, ItemTrack.ItemTrackMeshDefinition.INSTANCE.getModelLocation(new ItemStack(track)));
		}
		GameRegistry.registerBlock(block, itemClass, name);
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
			if (side == Side.CLIENT) {
				if (block instanceof BlockTrack) {
//					ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
//					Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
//					Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, ItemTrack.ItemTrackMeshDefinition.INSTANCE.getModelLocation(new ItemStack(Item.getItemFromBlock(block))));
//					ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, ItemTrack.ItemTrackMeshDefinition.INSTANCE.getModelLocation(new ItemStack(Item.getItemFromBlock(block))));
				} else {
					Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, location);
				}
			}
		}
	}
}
