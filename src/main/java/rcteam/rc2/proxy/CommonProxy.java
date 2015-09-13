package rcteam.rc2.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.block.te.TileEntitySupport;
import rcteam.rc2.item.RC2Items;

public class CommonProxy {
	public void preInit() {
//		initRenderers();
	}

	public void init() {
		RC2Items.init(Side.SERVER);
		RC2Blocks.init(Side.SERVER);
		MinecraftForge.EVENT_BUS.register(new TileEntitySupport());
		initRenderers();
	}
	
	public void initRenderers() {
		
	}
}
