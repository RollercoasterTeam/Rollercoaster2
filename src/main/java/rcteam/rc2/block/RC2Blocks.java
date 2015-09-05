package rcteam.rc2.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
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
import rcteam.rc2.multiblock.MultiBlockManager;
import rcteam.rc2.rollercoaster.CategoryEnum;
import rcteam.rc2.rollercoaster.CoasterStyle;
import rcteam.rc2.util.Reference;

import java.util.List;
import java.util.Map;

public class RC2Blocks {
	public static final Map<Block, Pair<String, ModelResourceLocation>> modelMap = Maps.newHashMap();
	public static Map<CategoryEnum, Block> trackMap = Maps.newEnumMap(CategoryEnum.class);
	public static Block entrance;
	public static Block dummy;
//	public static Block track_steel;
//	public static Block track_wooden;
//	public static Block track_inverted;
//	public static Block track_water;
	
	public static void preInit(Side side) {
		entrance = new BlockEntrance();
		registerBlock(entrance, "entrance");
		registerTE(TileEntityEntrance.class, entrance);

		dummy = new BlockDummy();
		registerBlock(dummy, "track_dummy");
		registerTE(TileEntityTrack.class, dummy);

		registerTracks(side);
		MultiBlockManager.instance.registerTrackTemplates();
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
		for (Block block : modelMap.keySet()) {
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
			if (categoryEnum.getProperty().getAllowedValues() != null && !categoryEnum.getProperty().getAllowedValues().isEmpty()) {
				Block track = new BlockTrack(categoryEnum.getInfo());
				trackMap.put(categoryEnum, track);
				GameRegistry.registerBlock(track, ItemTrack.class, "track_" + categoryEnum.getName(), categoryEnum.getInfo());
				GameRegistry.registerTileEntity(TileEntityTrack.class, "track_" + categoryEnum.getName());
				if (side == Side.CLIENT) {
					ModelLoader.setCustomStateMapper(track, BlockTrack.TrackStateMapper.INSTANCE);
					ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(track), ItemTrack.ItemTrackMeshDefinition.INSTANCE);
					for (CoasterStyle style : categoryEnum.getInfo().getStyles()) {
						ModelLoader.addVariantName(Item.getItemFromBlock(track), categoryEnum.BLOCKSTATE_DIR + style.getName());
					}
				}
			}
		}
	}
}
