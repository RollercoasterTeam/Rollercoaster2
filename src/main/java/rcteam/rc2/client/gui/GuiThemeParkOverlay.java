package rcteam.rc2.client.gui;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

public class GuiThemeParkOverlay extends Gui {
	private Minecraft mc;

	public GuiThemeParkOverlay(Minecraft mc) {
		super();

		this.mc = mc;
	}

	private static final int BUFF_ICON_SIZE = 18;
	private static final int BUFF_ICON_SPACING = BUFF_ICON_SIZE + 2; // 2 pixels
																		// between
																		// buff
																		// icons
	private static final int BUFF_ICON_BASE_U_OFFSET = 0;
	private static final int BUFF_ICON_BASE_V_OFFSET = 198;
	private static final int BUFF_ICONS_PER_ROW = 8;

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event) {
		if (event.isCancelable() || event.type != ElementType.ALL) {
			return;
		}
		
		int xPos = 2;
		int yPos = 2;

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		this.mc.getTextureManager().bindTexture(new ResourceLocation("rc2:textures/gui/icons/coin.png"));
		this.drawTexturedModalRect(xPos, yPos, 0, 0, 16, 16);		
		
		this.mc.getTextureManager().bindTexture(new ResourceLocation("rc2:textures/gui/icons/entrance.png"));
		this.drawTexturedModalRect(xPos, yPos + 18, 0, 0, 16, 16);	
		GL11.glPopMatrix();
	}
}