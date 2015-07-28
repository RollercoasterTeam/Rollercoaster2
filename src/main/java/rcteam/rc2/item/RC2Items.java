package rcteam.rc2.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import rcteam.rc2.RC2;

public class RC2Items {

	public static Item hammer;
	public static Item themePark;
	public static Item scoop, goldenScoop;
	public static Item cone;
	
	public static void init() {
		define();
		register();
	}

	private static void define() {
		hammer = new ItemHammer().setUnlocalizedName("hammer").setTextureName("rc2:hammer").setCreativeTab(RC2.tab);
		
		themePark = new ItemThemePark().setUnlocalizedName("themePark").setTextureName("rc2:themePark").setCreativeTab(RC2.tab);
		
		scoop = new ItemIceCreamScoop().setUnlocalizedName("scoop").setTextureName("rc2:iceCream/scoop").setCreativeTab(RC2.tab);
		goldenScoop = new ItemIceCreamScoop().setUnlocalizedName("goldenScoop").setTextureName("rc2:iceCream/goldenScoop").setCreativeTab(RC2.tab);
		
		cone = new ItemCone().setUnlocalizedName("cone").setTextureName("rc2:iceCream/cone").setCreativeTab(RC2.tab);
	}
	
	private static void register() {
		registerItem(hammer);
		
		registerItem(themePark);
		
		registerItem(scoop);
		registerItem(goldenScoop);
		
		registerItem(cone);
	}
	
	private static void registerItem(Item item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName());
	}
}
