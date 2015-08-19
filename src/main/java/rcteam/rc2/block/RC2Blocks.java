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
import rcteam.rc2.rollercoaster.TrackPieceRegistry;
import rcteam.rc2.rollercoaster.TrackStateMapper;
import rcteam.rc2.util.Reference;

import java.util.List;
import java.util.Map;

public class RC2Blocks {
	public static final Map<Block, Pair<String, ModelResourceLocation>> modelMap = Maps.newHashMap();
	public static Block entrance;
	public static Block track_steel;
	public static Block track_wood;
	public static Block track_inverted;
	public static Block track_water;
	
	public static void preInit(Side side) {
		entrance = new BlockEntrance();

//		for (CategoryEnum categoryEnum : CategoryEnum.values()) {
//			categoryEnum.getInfo().setValidPieces(TrackPieceRegistry.INSTANCE.getPieces());  //TODO: distinguish between different categories!!!
//			categoryEnum.getInfo().setCurrentPiece("straight");
//		}

		CategoryEnum.STEEL.setValidPieces(TrackPieceRegistry.INSTANCE.getPieces(CategoryEnum.STEEL)).setCurrentPiece("straight");
		CategoryEnum.WOODEN.setValidPieces(TrackPieceRegistry.INSTANCE.getPieces(CategoryEnum.WOODEN)).setCurrentPiece("straight");
		CategoryEnum.INVERTED.setValidPieces(TrackPieceRegistry.INSTANCE.getPieces(CategoryEnum.INVERTED)).setCurrentPiece("straight");
		CategoryEnum.WATER.setValidPieces(TrackPieceRegistry.INSTANCE.getPieces(CategoryEnum.WATER)).setCurrentPiece("straight");

//		BlockTrack.PIECE_PROPERTY.setAllowedValues(CategoryEnum.STEEL.getInfo().getPieces());
		track_steel = new BlockTrack(CategoryEnum.STEEL.getInfo());
//		BlockTrack.PIECE_PROPERTY.setAllowedValues(CategoryEnum.WOODEN.getInfo().getPieces());
		track_wood = new BlockTrack(CategoryEnum.WOODEN.getInfo());
//		BlockTrack.PIECE_PROPERTY.setAllowedValues(CategoryEnum.INVERTED.getInfo().getPieces());
		track_inverted = new BlockTrack(CategoryEnum.INVERTED.getInfo());
//		BlockTrack.PIECE_PROPERTY.setAllowedValues(CategoryEnum.WATER.getInfo().getPieces());
		track_water = new BlockTrack(CategoryEnum.WATER.getInfo());

		if (side == Side.CLIENT) {
			ModelLoader.setCustomStateMapper(track_steel, TrackStateMapper.INSTANCE);
			ModelLoader.setCustomStateMapper(track_wood, TrackStateMapper.INSTANCE);
			ModelLoader.setCustomStateMapper(track_inverted, TrackStateMapper.INSTANCE);
			ModelLoader.setCustomStateMapper(track_water, TrackStateMapper.INSTANCE);
		}

		registerBlock(entrance, "entrance");
		registerBlock(track_steel, ItemTrack.class, "track_steel", CategoryEnum.STEEL.getInfo());
		registerBlock(track_wood, ItemTrack.class, "track_wooden", CategoryEnum.WOODEN.getInfo());
		registerBlock(track_inverted, ItemTrack.class, "track_inverted", CategoryEnum.INVERTED.getInfo());
		registerBlock(track_water, ItemTrack.class, "track_water", CategoryEnum.WATER.getInfo());

		if (side == Side.CLIENT) {
			ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track_steel), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
			ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track_wood), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
			ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track_inverted), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
			ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track_water), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
			ModelBakery.addVariantName(Item.getItemFromBlock(track_steel), Reference.RESOURCE_PREFIX + "tracks/hyper_twister");
		}

		registerTE(TileEntityEntrance.class, entrance);
		registerTE(TileEntityTrack.class, track_steel);
		registerTE(TileEntityTrack.class, track_wood);
		registerTE(TileEntityTrack.class, track_inverted);
		registerTE(TileEntityTrack.class, track_water);
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
}
