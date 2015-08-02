package rcteam.rc2.client.gui.themePark;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import rcteam.rc2.block.te.TileEntityEntrance;
import rcteam.rc2.rollercoaster.ThemeParkLogo;

public class GuiEditThemePark extends GuiScreen {

	private static final ResourceLocation texture = new ResourceLocation("rc2:textures/gui/editThemePark.png");
	
	public ThemeParkLogo logo;
	
	private EntityPlayer player;
	
	private World world;
	
	private int x;
	private int y;
	private int z;
	
	public GuiEditThemePark(EntityPlayer player, World world, int x, int y, int z) {
		this.player = player;
		
		this.world = world;
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		logo = ((TileEntityEntrance) world.getTileEntity(x, y, z)).themePark.logo;
	}
	
	@Override
	public void initGui() {
		int k = (this.width / 2) - (176 / 2);
        int l = (this.height / 2) - (96 / 2);
	}
	
	@Override
	public void drawScreen(int i, int j, float f) {
		int k = (this.width / 2) - (176 / 2);
        int l = (this.height / 2) - (96 / 2);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        //this.mc.getTextureManager().bindTexture(texture);
        //this.drawTexturedModalRect(k, l, 0, 0, 176, 96);
        
        super.drawScreen(i, j, f);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(logo.bgs.get(logo.bg));
        this.drawTexturedModalRect(k + 8, l + 24, (logo.bgColour % 4) * 64, (logo.bgColour >= 12 ? 3 : logo.bgColour >= 8 ? 2 : logo.bgColour >= 4 ? 1 : 0) * 64, 64, 64);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		
	}
	
	@Override
	public void keyTyped(char c, int i) {
		super.keyTyped(c, i);
	}
	
	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
