package rcteam.rc2.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityEntrance;

public class RC2Blocks {
	
	public static Block entrance;
	
	public static void init() {
		define();
		register();
		registerTEs();
	}

	private static void define() {
		entrance = new BlockEntrance().setBlockName("entrance").setBlockTextureName("rc2:entrance").setCreativeTab(RC2.tab);
	}
	
	private static void register() {
		registerBlock(entrance);
	}
	
	private static void registerTEs() {
		registerTE(TileEntityEntrance.class, entrance);
	}
	
	private static void registerBlock(Block block) {
		GameRegistry.registerBlock(block, block.getUnlocalizedName());
	}
	
	private static void registerTE(Class<? extends TileEntity> te, Block block) {
		GameRegistry.registerTileEntity(te, "te_" + block.getUnlocalizedName());
	}
}
