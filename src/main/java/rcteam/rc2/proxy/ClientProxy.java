package rcteam.rc2.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;
import rcteam.rc2.client.gui.GuiThemeParkOverlay;
import rcteam.rc2.rollercoaster.ThemeParkLogo;
import rcteam.rc2.util.OBJLoader;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void initRenderers() {
		AdvancedModelLoader.registerModelHandler(OBJLoader.instance);
        ThemeParkLogo.init();
        MinecraftForge.EVENT_BUS.register(new GuiThemeParkOverlay(Minecraft.getMinecraft()));
	}
}
