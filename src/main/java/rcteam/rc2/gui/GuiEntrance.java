package rcteam.rc2.gui;

import java.lang.reflect.Field;

import org.lwjgl.opengl.GL11;

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

public class GuiEntrance extends GuiContainerCreative {

	private static final ResourceLocation texture = new ResourceLocation("rc2:textures/gui/entrance.png");
	
	private GuiTextField parkName;
	
	private static CreativeTabs[] copy;
	
	public GuiEntrance(EntityPlayer player, World world, int x, int y, int z) {
		super(player);
		
		copy = CreativeTabs.creativeTabArray.clone();
		
		GuiContainerCreative ge = new GuiContainerCreative(Minecraft.getMinecraft().thePlayer);
		Field f = null;
		try {
			f = ge.getClass().getDeclaredField("selectedTabIndex");
			f.setAccessible(true);
			f.setInt(ge, 0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		
		CreativeTabs[] tabs = new CreativeTabs[4];
		tabs[0] = new CreativeTabs(0, "rc2.entrance.main") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return Item.getItemFromBlock(Blocks.rail);
			}
		};
		tabs[1] = new CreativeTabs(1, "rc2.entrance.info") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return Item.getItemFromBlock(Blocks.rail);
			}
		};
		tabs[2] = new CreativeTabs(2, "rc2.entrance.customise") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return Item.getItemFromBlock(Blocks.rail);
			}
		};
		tabs[3] = new CreativeTabs(3, "rc2.entrance.manage") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return Item.getItemFromBlock(Blocks.rail);
			}
		};
		CreativeTabs.creativeTabArray = tabs;
	}
	
	/*@Override
	public void initGui() {
		int k = (this.width / 2) - (176 / 2);
        int l = (this.height / 2) - (96 / 2);
        
		parkName = new GuiTextField(fontRendererObj, k + 6, l + 6, 164, 12);
		parkName.setFocused(false);
		parkName.setMaxStringLength(26);
		parkName.setText("");
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
	}*/
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		CreativeTabs.creativeTabArray = copy;
	}
}
