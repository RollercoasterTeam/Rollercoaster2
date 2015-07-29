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
		hammer = new ItemHammer();
		
		themePark = new ItemThemePark();
		
		scoop = new ItemIceCreamScoop(false);
		goldenScoop = new ItemIceCreamScoop(true);
		
		cone = new ItemCone();
		
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
