package rcteam.rc2.client.gui.themePark;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import rcteam.rc2.RC2;
import rcteam.rc2.client.gui.themePark.pane.GuiEntrancePane;
import rcteam.rc2.client.gui.themePark.pane.GuiEntrancePaneFinance;
import rcteam.rc2.client.gui.themePark.pane.GuiEntrancePaneLogo;
import rcteam.rc2.client.gui.themePark.pane.GuiEntrancePaneMain;
import rcteam.rc2.client.gui.themePark.pane.GuiEntrancePaneSettings;
import rcteam.rc2.client.gui.themePark.pane.GuiEntrancePaneStatus;

import java.io.IOException;

public class GuiEntrance extends GuiScreen {
	private static final GuiEntrancePane[] panes = {
		new GuiEntrancePaneMain(), 
		new GuiEntrancePaneStatus(), 
		new GuiEntrancePaneFinance(), 
		new GuiEntrancePaneLogo(), 
		new GuiEntrancePaneSettings()
	};
	
	private static final ResourceLocation tabs = new ResourceLocation(RC2.MODID + ":" + "textures/gui/entrance/tabs.png");
	public EntityPlayer player;
	public World world;
	public BlockPos pos;
	private int tabIndex = 0;
	
	public GuiEntrance(EntityPlayer player, World world, BlockPos pos) {
		this.player = player;
		this.world = world;
		this.pos = pos;
	}
	
	@Override
	public void initGui() {
		buttonList.clear();
		panes[tabIndex].initGui(this);
	}
	
	@Override
	public void drawScreen(int i, int j, float f) {
		int k = (this.width / 2) - (176 / 2);
        int l = (this.height / 2) - (96 / 2);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(tabs);
        
        this.drawTexturedModalRect(k, l - 26, 0, 0, 140, 30);
        
        this.mc.getTextureManager().bindTexture(panes[tabIndex].texture);
        
        this.drawTexturedModalRect(k, l, 0, 0, 176, 96);
        
        this.mc.getTextureManager().bindTexture(tabs);
        
        this.drawTexturedModalRect(k + (tabIndex * 28), l - 28, tabIndex * 28, 30, 28, 32);
        
        this.fontRendererObj.drawString(panes[tabIndex].name, k + 5, l + 6, 0x555555);
        
        super.drawScreen(i, j, f);
        
        panes[tabIndex].drawScreen(this, i, j, f);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		panes[tabIndex].actionPerformed(this, button);
	}
	
	@Override
	public void keyTyped(char c, int i) throws IOException {
		super.keyTyped(c, i);
		
		panes[tabIndex].keyTyped(this, c, i);
	}
	
	@Override
	public void mouseClicked(int a, int b, int c) throws IOException {
		super.mouseClicked(a, b, c);
		
		int k = (this.width / 2) - (176 / 2);
        int l = (this.height / 2) - (96 / 2);
		
		Rectangle mouse = new Rectangle(a, b, 1, 1);
		for(int i = 0; i < 5; i++) {
			Rectangle bounds = new Rectangle(k + (i * 28), l - 28, 28, 32);
			if(mouse.intersects(bounds)) {
				tabIndex = i;
				initGui();
			}
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public FontRenderer getFontRenderer() {
		return fontRendererObj;
	}

	public void addButton(GuiButton button) {
		buttonList.add(button);
	}
}
