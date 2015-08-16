package rcteam.rc2.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.Pair;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.*;
import rcteam.rc2.item.ItemTrack;
import rcteam.rc2.rollercoaster.CategoryEnum;
import rcteam.rc2.rollercoaster.TrackPieceInfo;
import rcteam.rc2.rollercoaster.TrackPieceRegistry;

import java.util.List;
import java.util.Map;

public class RC2Blocks {
	public static final Map<Block, Pair<String, ModelResourceLocation>> modelMap = Maps.newHashMap();
	public static Block entrance;
	public static Block track_steel;
	public static Block track_wood;
	public static Block track_inverted;
	public static Block track_water;
	
	public static void preInit() {
		entrance = new BlockEntrance();

		TrackPieceInfo steelInfo = new TrackPieceInfo(CategoryEnum.STEEL, TrackPieceRegistry.INSTANCE.getPieces());         //TODO: distinguish between different category pieces and different Style pieces!
		TrackPieceInfo woodInfo = new TrackPieceInfo(CategoryEnum.WOODEN, TrackPieceRegistry.INSTANCE.getPieces());
		TrackPieceInfo invertedInfo = new TrackPieceInfo(CategoryEnum.INVERTED, TrackPieceRegistry.INSTANCE.getPieces());
		TrackPieceInfo waterInfo = new TrackPieceInfo(CategoryEnum.WATER, TrackPieceRegistry.INSTANCE.getPieces());

		track_steel = new BlockTrackSteel(steelInfo);
		track_wood = new BlockTrackWood(woodInfo);
		track_inverted = new BlockTrackInverted(invertedInfo);
		track_water = new BlockTrackWater(waterInfo);

		registerBlock(entrance, "entrance");
		registerBlock(track_steel, ItemTrack.class, "track_steel");
		registerBlock(track_wood, ItemTrack.class, "track_wood");
		registerBlock(track_inverted, ItemTrack.class, "track_inverted");
		registerBlock(track_water, ItemTrack.class, "track_water");

		registerTE(TileEntityEntrance.class, entrance);
		registerTE(TileEntityTrackSteel.class, track_steel);
		registerTE(TileEntityTrackWood.class, track_wood);
		registerTE(TileEntityTrackInverted.class, track_inverted);
		registerTE(TileEntityTrackWater.class, track_water);
	}
	
	private static void registerBlock(Block block, String name) {
		GameRegistry.registerBlock(block, name);
		modelMap.put(block, Pair.of(name, null));
	}

	private static void registerBlock(Block block, Class<? extends ItemBlock> itemClass, String name, Object ... itemConstructorArgs) {
		GameRegistry.registerBlock(block, itemClass, name, itemConstructorArgs);
		modelMap.put(block, Pair.of(name, null));
	}
	
	private static void registerTE(Class<? extends TileEntity> te, Block block) {
		GameRegistry.registerTileEntity(te, modelMap.get(block).getLeft());
	}

	public static void init(Side side) {
		List<Block> blocks = Lists.newArrayList(modelMap.keySet());
		for (Block block : blocks) {
			ModelResourceLocation location = new ModelResourceLocation(RC2.MODID.toLowerCase() + ":" + modelMap.get(block).getLeft(), "inventory");
			modelMap.put(block, Pair.of(modelMap.get(block).getLeft(), location));
			if (side == Side.CLIENT) {
				if (block instanceof BlockTrack) {
					ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, location);
				} else {
					Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, location);
				}
			}
		}
	}
}
