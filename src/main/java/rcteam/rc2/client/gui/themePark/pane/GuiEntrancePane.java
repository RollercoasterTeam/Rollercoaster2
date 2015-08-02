package rcteam.rc2.client.gui.themePark.pane;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiEntrancePane {
	
	public String name;
	
	public ResourceLocation texture;
	
	public GuiEntrancePane(String name) {
		this.name = name;
		
		texture = new ResourceLocation("rc2:textures/gui/entrance/pane_" + name.toLowerCase() + ".png");
	}
	
	public void initGui() {
		
	}
	
	public void drawScreen(int i, int j, float f) {
		
	}
	
	public void actionPerformed(GuiButton button) {
		
	}
}
