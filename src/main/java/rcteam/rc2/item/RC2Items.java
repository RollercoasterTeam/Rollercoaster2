package rcteam.rc2.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
		modelMap.put(item, Pair.of(name, null));
	}

	public static void init(Side side) {
		List<Item> items = Lists.newArrayList(modelMap.keySet());
		for (Item item : items) {
			ModelResourceLocation location = new ModelResourceLocation(RC2.MODID.toLowerCase() + ":" + modelMap.get(item).getLeft(), "inventory");
			modelMap.put(item, Pair.of(modelMap.get(item).getLeft(), location));
			if (side == Side.CLIENT) ModelLoader.setCustomModelResourceLocation(item, 0, location);
		}
	}
}
