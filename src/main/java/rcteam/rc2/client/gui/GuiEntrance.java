package rcteam.rc2.client.gui;

import java.lang.reflect.Field;

import org.lwjgl.opengl.GL11;

import rcteam.rc2.block.te.TileEntityEntrance;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiEntrance extends GuiScreen {

	private static final ResourceLocation texture = new ResourceLocation("rc2:textures/gui/entrance.png");
	
	private GuiTextField parkName;
	
	private World world;
	
	private int x;
	private int y;
	private int z;
	
	public GuiEntrance(EntityPlayer player, World world, int x, int y, int z) {
		this.world = world;
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void initGui() {
		int k = (this.width / 2) - (176 / 2);
        int l = (this.height / 2) - (96 / 2);
        
		parkName = new GuiTextField(fontRendererObj, k + 6, l + 6, 164, 12) {
			@Override
			public void mouseClicked(int i, int j, int k) {
				super.mouseClicked(i, j, k);
				
				((TileEntityEntrance) world.getTileEntity(x, y, z)).themePark.name = parkName.getText();
				world.markBlockForUpdate(x, y, z);
			}
		};
		parkName.setFocused(false);
		parkName.setMaxStringLength(26);
		parkName.setText(((TileEntityEntrance) world.getTileEntity(x, y, z)).themePark.name);
	}
	
	@Override
	public void drawScreen(int i, int j, float f) {
		int k = (this.width / 2) - (176 / 2);
        int l = (this.height / 2) - (96 / 2);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(k, l, 0, 0, 176, 96);
        
        parkName.drawTextBox();
	}
	
	@Override
	public void keyTyped(char c, int i) {
		super.keyTyped(c, i);
		
		parkName.textboxKeyTyped(c, i);
	}
	
	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		
		parkName.mouseClicked(i, j, k);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
