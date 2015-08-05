package rcteam.rc2.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityEntrance;

public class RC2Blocks {
	public static Block entrance;
	
	public static void preInit() {
		entrance = new BlockEntrance();

		registerBlock(entrance);

		registerTE(TileEntityEntrance.class, entrance);
	}
	
	private static void registerBlock(Block block) {
		GameRegistry.registerBlock(block, block.getUnlocalizedName());
	}
	
	private static void registerTE(Class<? extends TileEntity> te, Block block) {
		GameRegistry.registerTileEntity(te, "te_" + block.getUnlocalizedName());
	}
}
