package rcteam.rc2.client.gui.themePark.pane;

import rcteam.rc2.client.gui.themePark.GuiEntrance;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiEntrancePane {
	
	public String name;
	
	public ResourceLocation texture;
	
	public GuiEntrancePane(String name) {
		this.name = name;
		
		texture = new ResourceLocation("rc2:textures/gui/entrance/pane_" + name.toLowerCase() + ".png");
	}
	
	public void initGui(GuiEntrance gui) {
		
	}
	
	public void drawScreen(int i, int j, float f) {
		
	}
	
	public void actionPerformed(GuiEntrance gui, GuiButton button) {
		
	}
}
