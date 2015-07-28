package rcteam.rc2.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import rcteam.rc2.client.gui.GuiThemeParkOverlay;
import rcteam.rc2.rollercoaster.ThemeParkLogo;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void initRenderers() {
        ThemeParkLogo.init();
        MinecraftForge.EVENT_BUS.register(new GuiThemeParkOverlay(Minecraft.getMinecraft()));
	}
}
