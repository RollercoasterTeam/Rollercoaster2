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

import java.util.List;
import java.util.Map;

public class RC2Items {
	public static final Map<Item, Pair<String, ModelResourceLocation>> modelMap = Maps.newHashMap();
	public static Item hammer;
	public static Item themePark;
	public static Item scoop, goldenScoop;
	public static Item cone;

	public static void preInit() {
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
			ModelResourceLocation location = new ModelResourceLocation(RC2.MODID.toLowerCase() + ":" + modelMap.get(item).getLeft(), "inventory");
			modelMap.put(item, Pair.of(modelMap.get(item).getLeft(), location));
			if (side == Side.CLIENT) {
				if (item instanceof ItemTrack) {
					ModelLoader.setCustomModelResourceLocation(item, 0, location);
				} else if (item instanceof ItemCone) {
					//TODO: figure out how to best implement these!
					for (ItemCone.EnumFlavors flavor : ItemCone.EnumFlavors.values()) {
						ModelLoader.setCustomModelResourceLocation(item, flavor.meta, new ModelResourceLocation(RC2.MODID.toLowerCase() + ":" + "ice_cream/" + flavor.textureName, "inventory"));
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
