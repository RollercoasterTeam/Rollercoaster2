package rcteam.rc2.item;

import cpw.mods.fml.common.registry.GameRegistry;
import rcteam.rc2.RC2;
import net.minecraft.item.Item;

public class RC2Items {

	public static Item hammer;
	
	public static void init() {
		define();
		register();
	}

	private static void define() {
		hammer = new ItemHammer().setUnlocalizedName("hammer").setTextureName("rc2:hammer").setCreativeTab(RC2.tab);
	}
	
	private static void register() {
		registerItem(hammer);
	}
	
	private static void registerItem(Item item) {
		GameRegistry.registerItem(hammer, hammer.getUnlocalizedName());
	}
}
