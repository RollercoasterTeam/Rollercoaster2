package rcteam.rc2.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.Pair;
import rcteam.rc2.RC2;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.rollercoaster.CategoryEnum;
import rcteam.rc2.rollercoaster.TrackPieceInfo;
import rcteam.rc2.rollercoaster.TrackPieceRegistry;
import rcteam.rc2.util.Reference;

import java.util.List;
import java.util.Map;

public class RC2Items {
	public static final Map<Item, Pair<String, ModelResourceLocation>> modelMap = Maps.newHashMap();
	public static Item hammer;
	public static Item themePark;
	public static Item scoop, goldenScoop;
	public static Item cone;
	public static Item track_steel, track_wood, track_inverted, track_water;

	public static void preInit(Side side) {
		hammer = new ItemHammer();
		themePark = new ItemThemePark();
		scoop = new ItemIceCreamScoop(false);
		goldenScoop = new ItemIceCreamScoop(true);
		cone = new ItemCone();
		
		registerItem(hammer, "hammer");
		registerItem(themePark, "theme_park");
		registerItem(scoop, "scoop");
		registerItem(goldenScoop, "scoop_golden");
		registerItem(cone, "cone");

//		RC2Blocks.infoMap.put(CategoryEnum.STEEL, new TrackPieceInfo(CategoryEnum.STEEL, TrackPieceRegistry.INSTANCE.getPieces()));
//		RC2Blocks.infoMap.put(CategoryEnum.WOODEN, new TrackPieceInfo(CategoryEnum.WOODEN, TrackPieceRegistry.INSTANCE.getPieces()));
//		RC2Blocks.infoMap.put(CategoryEnum.INVERTED, new TrackPieceInfo(CategoryEnum.INVERTED, TrackPieceRegistry.INSTANCE.getPieces()));
//		RC2Blocks.infoMap.put(CategoryEnum.WATER, new TrackPieceInfo(CategoryEnum.WATER, TrackPieceRegistry.INSTANCE.getPieces()));
//
//		BlockTrack.PIECE_PROPERTY.setAllowedValues(RC2Blocks.infoMap.get(CategoryEnum.STEEL).getPieces());
//		RC2Blocks.track_steel = new BlockTrack(new TrackPieceInfo(CategoryEnum.STEEL, TrackPieceRegistry.INSTANCE.getPieces()));
//		BlockTrack.PIECE_PROPERTY.setAllowedValues(RC2Blocks.infoMap.get(CategoryEnum.WOODEN).getPieces());
//		RC2Blocks.track_wooden = new BlockTrack(new TrackPieceInfo(CategoryEnum.WOODEN, TrackPieceRegistry.INSTANCE.getPieces()));
//		BlockTrack.PIECE_PROPERTY.setAllowedValues(RC2Blocks.infoMap.get(CategoryEnum.INVERTED).getPieces());
//		RC2Blocks.track_inverted = new BlockTrack(new TrackPieceInfo(CategoryEnum.INVERTED, TrackPieceRegistry.INSTANCE.getPieces()));
//		BlockTrack.PIECE_PROPERTY.setAllowedValues(RC2Blocks.infoMap.get(CategoryEnum.WATER).getPieces());
//		RC2Blocks.track_water = new BlockTrack( new TrackPieceInfo(CategoryEnum.WATER, TrackPieceRegistry.INSTANCE.getPieces()));
//
//		track_steel = new ItemTrack(RC2Blocks.track_steel);
//		track_wooden = new ItemTrack(RC2Blocks.track_wooden);
//		track_inverted = new ItemTrack(RC2Blocks.track_inverted);
//		track_water = new ItemTrack(RC2Blocks.track_water);
//
//		if (side == Side.CLIENT) {
//			ModelLoader.setCustomModelResourceLocation(track_steel, 0, new ModelResourceLocation(Reference.RESOURCE_PREFIX + "tracks/hyper_twister", "inventory"));
//			ModelLoader.setCustomModelResourceLocation(track_wooden, 0, new ModelResourceLocation(Reference.RESOURCE_PREFIX + "tracks/hyper_twister", "inventory"));
//			ModelLoader.setCustomModelResourceLocation(track_inverted, 0, new ModelResourceLocation(Reference.RESOURCE_PREFIX + "tracks/hyper_twister", "inventory"));
//			ModelLoader.setCustomModelResourceLocation(track_water, 0, new ModelResourceLocation(Reference.RESOURCE_PREFIX + "tracks/hyper_twister", "inventory"));
//		}
//
//		registerItem(track_steel, "track_" + CategoryEnum.STEEL.getName());
//		registerItem(track_wooden, "track_" + CategoryEnum.WOODEN.getName());
//		registerItem(track_inverted, "track_" + CategoryEnum.INVERTED.getName());
//		registerItem(track_water, "track_" + CategoryEnum.WATER.getName());
	}
	
	private static void registerItem(Item item, String name) {
//		GameRegistry.registerItem(item, item.getUnlocalizedName());
		GameRegistry.registerItem(item, name);
		if (item instanceof ItemCone) name = "ice_cream/" + name;
		modelMap.put(item, Pair.of(name, null));
	}

	public static void init(Side side) {
		List<Item> items = Lists.newArrayList(modelMap.keySet());
		for (Item item : items) {
			ModelResourceLocation location = new ModelResourceLocation(Reference.RESOURCE_PREFIX + modelMap.get(item).getLeft(), "inventory");
			modelMap.put(item, Pair.of(modelMap.get(item).getLeft(), location));
			if (side == Side.CLIENT) {
				if (item instanceof ItemCone) {
					//TODO: figure out how to best implement these!
					for (ItemCone.EnumFlavors flavor : ItemCone.EnumFlavors.values()) {
						ModelLoader.setCustomModelResourceLocation(item, flavor.meta, new ModelResourceLocation(Reference.RESOURCE_PREFIX + "ice_cream/" + flavor.textureName, "inventory"));
						ModelLoader.addVariantName(item, RC2.MODID.toLowerCase() + ":" + /*"ice_cream/" +*/ flavor.textureName);
					}
//					List<Item> subitems = Lists.newArrayList();
//					item.getSubItems(item, RC2.tab, subitems);
//					for (int i = 0; i < subitems.size(); i++) {
//						Item subitem = subitems.get(i);
//						ModelLoader.setCustomModelResourceLocation(RC2.MODID.toLowerCase() + ":" + "ice_cream/" + ItemCone.EnumFlavors.values()[i].textureName, "inventory");
//					}
//					Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, ItemCone.EnumFlavors.getItemMeshDefinition());
				} else {
					Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, location);
				}
			}
		}
	}
}
