package rcteam.rc2.proxy;

import net.minecraftforge.fml.relauncher.Side;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.item.RC2Items;

public class CommonProxy {
	public void preInit() {
//		initRenderers();
	}

	public void init() {
		RC2Items.init(Side.SERVER);
		RC2Blocks.init(Side.SERVER);
		initRenderers();
	}
	
	public void initRenderers() {
		
	}
}
