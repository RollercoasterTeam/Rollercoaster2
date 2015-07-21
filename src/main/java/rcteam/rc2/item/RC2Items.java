package rcteam.rc2.item;

import cpw.mods.fml.common.registry.GameRegistry;
import rcteam.rc2.RC2;
import net.minecraft.item.Item;

public class RC2Items {

	public static Item hammer;
	public static Item themePark;
	
	public static void init() {
		define();
		register();
	}

	private static void define() {
		hammer = new ItemHammer().setUnlocalizedName("hammer").setTextureName("rc2:hammer").setCreativeTab(RC2.tab);
		themePark = new ItemThemePark().setUnlocalizedName("themePark").setTextureName("rc2:themePark").setCreativeTab(RC2.tab);
	}
	
	private static void register() {
		registerItem(hammer);
		registerItem(themePark);
	}
	
	private static void registerItem(Item item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName());
	}
}
