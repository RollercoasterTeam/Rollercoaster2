package rcteam.rc2.client.gui.themePark.pane;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityEntrance;
import rcteam.rc2.client.gui.themePark.GuiEntrance;
import rcteam.rc2.network.NetworkHandler;
import rcteam.rc2.network.packets.PacketThemeParkEntrance;
import rcteam.rc2.rollercoaster.ThemeParkLogo;
import rcteam.rc2.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;

public class GuiEntrancePaneLogo extends GuiEntrancePane {
	
	public ThemeParkLogo logo;
	
	public GuiEntrancePaneLogo() {
		super("Logo");
	}
	
	@Override
	public void initGui(GuiEntrance gui) {
		int k = (gui.width / 2) - (176 / 2);
        int l = (gui.height / 2) - (96 / 2);
		
		logo = ((TileEntityEntrance) gui.world.getTileEntity(gui.x, gui.y, gui.z)).themePark.logo;
		
		gui.addButton(new GuiButton(0, k + 80, l + 24, 12, 20, "<"));
		gui.addButton(new GuiButton(1, k + 94, l + 24, 12, 20, ">"));
		gui.addButton(new GuiButton(2, k + 108, l + 24, 12, 20, "C"));
		
		gui.addButton(new GuiButton(3, k + 80, l + 46, 12, 20, "<"));
		gui.addButton(new GuiButton(4, k + 94, l + 46, 12, 20, ">"));
		gui.addButton(new GuiButton(5, k + 108, l + 46, 12, 20, "C"));
		
		gui.addButton(new GuiButton(6, k + 80, l + 68, 12, 20, "<"));
		gui.addButton(new GuiButton(7, k + 94, l + 68, 12, 20, ">"));
		gui.addButton(new GuiButton(8, k + 108, l + 68, 12, 20, "C"));
		
		gui.addButton(new GuiButton(9, k + 130, l + 24, 38, 20, "Edit"));
		gui.addButton(new GuiButton(10, k + 130, l + 46, 38, 20, "Save"));
		gui.addButton(new GuiButton(11, k + 130, l + 68, 38, 20, "Exit"));
	}
	
	@Override
	public void drawScreen(GuiEntrance gui, int i, int j, float f) {
		int k = (gui.width / 2) - (176 / 2);
        int l = (gui.height / 2) - (96 / 2);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        gui.mc.getTextureManager().bindTexture(logo.bgs.get(logo.bg));
        gui.drawTexturedModalRect(k + 8, l + 24, (logo.bgColour % 4) * 64, (logo.bgColour >= 12 ? 3 : logo.bgColour >= 8 ? 2 : logo.bgColour >= 4 ? 1 : 0) * 64, 64, 64);
	}
	
	@Override
	public void actionPerformed(GuiEntrance gui, GuiButton button) {
		if(button.id == 0) {
			if(logo.bg > 0) {
				logo.bg--;
			}
			NetworkHandler.updateThemeParkEntrance(PacketThemeParkEntrance.Packet.LOGO_BG, gui.x, gui.y, gui.z, logo.bg, null);
		}
		else if(button.id == 1) {
			if(logo.bg < logo.bgs.size() - 1) {
				logo.bg++;
			}
			NetworkHandler.updateThemeParkEntrance(PacketThemeParkEntrance.Packet.LOGO_BG, gui.x, gui.y, gui.z, logo.bg, null);
		}
		else if(button.id == 2) {
			if(logo.bgColour == 15) {
				logo.bgColour = 0;
			}
			else {
				logo.bgColour++;
			}
			NetworkHandler.updateThemeParkEntrance(PacketThemeParkEntrance.Packet.LOGO_BG_COLOUR, gui.x, gui.y, gui.z, logo.bgColour, null);
		}
		else if(button.id == 3) {
			
		}
		else if(button.id == 4) {
	
		}
		else if(button.id == 5) {
	
		}
		else if(button.id == 6) {
	
		}
		else if(button.id == 7) {
			
		}
		else if(button.id == 8) {
			
		}
		else if(button.id == 9) {
			FMLNetworkHandler.openGui(gui.player, RC2.instance, Reference.GUI_ID_EDIT_THEME_PARK, gui.world, gui.x, gui.y, gui.z);
		}
		else if(button.id == 10) {
	
		}
		else if(button.id == 11) {
			Minecraft.getMinecraft().currentScreen = null;
		}
	}
	
	@Override
	public void keyTyped(GuiEntrance gui, char c, int i) {
		
	}
}
