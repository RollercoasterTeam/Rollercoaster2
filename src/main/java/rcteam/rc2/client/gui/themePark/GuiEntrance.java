package rcteam.rc2.client.gui.themePark;

import java.util.ArrayList;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityEntrance;
import rcteam.rc2.client.gui.themePark.pane.GuiEntrancePane;
import rcteam.rc2.client.gui.themePark.pane.GuiEntrancePaneFinance;
import rcteam.rc2.client.gui.themePark.pane.GuiEntrancePaneLogo;
import rcteam.rc2.client.gui.themePark.pane.GuiEntrancePaneMain;
import rcteam.rc2.client.gui.themePark.pane.GuiEntrancePaneSettings;
import rcteam.rc2.client.gui.themePark.pane.GuiEntrancePaneStatus;
import rcteam.rc2.network.NetworkHandler;
import rcteam.rc2.network.packets.PacketThemeParkEntrance;
import rcteam.rc2.rollercoaster.ThemeParkLogo;
import rcteam.rc2.util.Reference;

public class GuiEntrance extends GuiScreen {

	private static final GuiEntrancePane[] panes = {
		new GuiEntrancePaneMain(), 
		new GuiEntrancePaneStatus(), 
		new GuiEntrancePaneFinance(), 
		new GuiEntrancePaneLogo(), 
		new GuiEntrancePaneSettings()
	};
	
	public GuiTextField parkName;
	
	public ThemeParkLogo logo;
	
	public EntityPlayer player;
	
	public World world;
	
	public int x;
	public int y;
	public int z;
	
	private int tabIndex = 0;
	
	public GuiEntrance(EntityPlayer player, World world, int x, int y, int z) {
		this.player = player;
		
		this.world = world;
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.logo = ((TileEntityEntrance) world.getTileEntity(x, y, z)).themePark.logo;
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
        this.mc.getTextureManager().bindTexture(panes[tabIndex].texture);
        
        this.drawTexturedModalRect(k, l - 28, 0, 96, 140, 30);
        
        this.drawTexturedModalRect(k, l, 0, 0, 176, 96);
        
        this.drawTexturedModalRect(k + (tabIndex * 28), l - 28, tabIndex * 28, 126, 28, 32);
        
        super.drawScreen(i, j, f);
        
        //parkName.drawTextBox();
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(logo.bgs.get(logo.bg));
        this.drawTexturedModalRect(k + 8, l + 24, (logo.bgColour % 4) * 64, (logo.bgColour >= 12 ? 3 : logo.bgColour >= 8 ? 2 : logo.bgColour >= 4 ? 1 : 0) * 64, 64, 64);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		panes[tabIndex].actionPerformed(this, button);
	}
	
	@Override
	public void keyTyped(char c, int i) {
		super.keyTyped(c, i);
		
		parkName.textboxKeyTyped(c, i);
	}
	
	@Override
	public void mouseClicked(int a, int b, int c) {
		super.mouseClicked(a, b, c);
		
		//parkName.mouseClicked(i, j, k);
		
		int k = (this.width / 2) - (176 / 2);
        int l = (this.height / 2) - (96 / 2);
		
		Rectangle mouse = new Rectangle(a, b, 1, 1);
		System.out.println("A: " + a + ", B: " + b + ", C: " + c + ", K: " + k + ", L: " + l);
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
