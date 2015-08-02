package rcteam.rc2.client.gui.themePark.pane;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityEntrance;
import rcteam.rc2.client.gui.themePark.GuiEntrance;
import rcteam.rc2.network.NetworkHandler;
import rcteam.rc2.network.packets.PacketThemeParkEntrance;
import rcteam.rc2.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;

public class GuiEntrancePaneLogo extends GuiEntrancePane {

	public GuiEntrancePaneLogo() {
		super("Logo");
	}
	
	@Override
	public void initGui(GuiEntrance gui) {
		int k = (gui.width / 2) - (176 / 2);
        int l = (gui.height / 2) - (96 / 2);
        
        gui.parkName = new GuiTextField(gui.getFontRenderer(), k + 6, l + 6, 164, 12) {
			@Override
			public void mouseClicked(int i, int j, int k) {
				super.mouseClicked(i, j, k);
				
				/*((TileEntityEntrance) gui.world.getTileEntity(gui.x, gui.y, gui.z)).themePark.name = parkName.getText();
				world.markBlockForUpdate(x, y, z);*/
			}
		};
		gui.parkName.setFocused(false);
		gui.parkName.setMaxStringLength(26);
		gui.parkName.setText(((TileEntityEntrance) gui.world.getTileEntity(gui.x, gui.y, gui.z)).themePark.name);
		
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
	
	public void drawScreen(int i, int j, float f) {
		
	}
	
	@Override
	public void actionPerformed(GuiEntrance gui, GuiButton button) {
		if(button.id == 0) {
			if(gui.logo.bg > 0) {
				gui.logo.bg--;
			}
			NetworkHandler.updateThemeParkEntrance(PacketThemeParkEntrance.Packet.LOGO_BG, gui.x, gui.y, gui.z, gui.logo.bg, null);
		}
		else if(button.id == 1) {
			if(gui.logo.bg < gui.logo.bgs.size() - 1) {
				gui.logo.bg++;
			}
			NetworkHandler.updateThemeParkEntrance(PacketThemeParkEntrance.Packet.LOGO_BG, gui.x, gui.y, gui.z, gui.logo.bg, null);
		}
		else if(button.id == 2) {
			if(gui.logo.bgColour == 15) {
				gui.logo.bgColour = 0;
			}
			else {
				gui.logo.bgColour++;
			}
			NetworkHandler.updateThemeParkEntrance(PacketThemeParkEntrance.Packet.LOGO_BG_COLOUR, gui.x, gui.y, gui.z, gui.logo.bgColour, null);
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
}
