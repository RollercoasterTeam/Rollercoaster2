package rcteam.rc2.client.gui.themePark.pane;

import net.minecraft.util.BlockPos;
import rcteam.rc2.block.te.TileEntityEntrance;
import rcteam.rc2.client.gui.themePark.GuiEntrance;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;

public class GuiEntrancePaneMain extends GuiEntrancePane {
	public GuiTextField parkName;
	
	public GuiEntrancePaneMain() {
		super("Main");
	}
	
	@Override
	public void initGui(GuiEntrance gui) {
		int k = (gui.width / 2) - (176 / 2);
        int l = (gui.height / 2) - (96 / 2);
        
        parkName = new GuiTextField(0, gui.getFontRenderer(), k + 6, l + 6, 164, 12) {
			@Override
			public void mouseClicked(int i, int j, int k) {
				super.mouseClicked(i, j, k);
				
				/*((TileEntityEntrance) gui.world.getTileEntity(gui.x, gui.y, gui.z)).themePark.name = parkName.getText();
				world.markBlockForUpdate(x, y, z);*/
			}
		};
		
		parkName.setFocused(false);
		parkName.setMaxStringLength(26);
		parkName.setText(((TileEntityEntrance) gui.world.getTileEntity(gui.pos)).themePark.name);
	}
	
	@Override
	public void drawScreen(GuiEntrance gui, int i, int j, float f) {
		parkName.drawTextBox();
	}
	
	@Override
	public void actionPerformed(GuiEntrance gui, GuiButton button) {
		
	}
	
	@Override
	public void keyTyped(GuiEntrance gui, char c, int i) {
		parkName.textboxKeyTyped(c, i);
	}
}
