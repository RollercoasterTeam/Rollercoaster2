package rcteam.rc2.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.block.te.TileEntityTrack;
import rcteam.rc2.item.ItemHammer;

public class GuiThemeParkOverlay extends Gui {
	private Minecraft mc;
	private FontRenderer fontRenderer;
	private int itemTextureWidth = 16;
	private int itemTextureHeight = 16;
	private int xPos = 2;
	private int yPos = 2;

	public GuiThemeParkOverlay(Minecraft mc) {
		super();
		this.mc = mc;
		this.fontRenderer = mc.fontRendererObj;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event) {
		if (this.mc.gameSettings.showDebugInfo || (event.isCancelable() || event.type != ElementType.ALL)) {
			return;
		}

		int drawingTexture = 0;
		boolean shouldDrawIcons = true;

		GL11.glPushMatrix();
		ItemStack stack = this.mc.thePlayer.getCurrentEquippedItem();
		if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemHammer) {
			xPos = 2;
			yPos = 2;
			ItemHammer hammer = (ItemHammer) stack.getItem();
			int fontHeight = this.fontRenderer.FONT_HEIGHT;
			int stringWidth = this.fontRenderer.getStringWidth(hammer.mode.name);
			int offset = 1 + fontHeight;
			drawRect(xPos - 1, yPos - 1, xPos + stringWidth + 1, yPos + offset - 2, 0x90505050);
			this.fontRenderer.drawString(hammer.mode.name, xPos, yPos, 0xFFE0E0E0);
			yPos = offset + 2;
			shouldDrawIcons = false;
			if (this.mc.theWorld.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock() instanceof BlockTrack) {
				yPos -= 1;
				TileEntityTrack tileEntityTrack = (TileEntityTrack) this.mc.theWorld.getTileEntity(this.mc.objectMouseOver.getBlockPos());
				stringWidth = this.fontRenderer.getStringWidth(tileEntityTrack.info.getCurrentPiece().getDisplayName());
//				offset *= 2;
				drawRect(xPos - 1, yPos - 1, xPos + stringWidth + 1, yPos + offset - 1, 0x90505050);
				this.fontRenderer.drawString(tileEntityTrack.info.getCurrentPiece().getDisplayName(), xPos, yPos, 0xFFE0E0E0);
				yPos = offset + 2;
			}
		} else {
			xPos = 2;
			yPos = 2;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);

		if (shouldDrawIcons) {
			this.mc.getTextureManager().bindTexture(new ResourceLocation("rc2:textures/gui/icons/coin.png"));
			this.drawTexturedModalRect(xPos, yPos, 0, 0, itemTextureWidth, itemTextureHeight);
			drawingTexture++;

			yPos = itemTextureHeight * drawingTexture;
			this.mc.getTextureManager().bindTexture(new ResourceLocation("rc2:textures/gui/icons/entrance.png"));
			this.drawTexturedModalRect(xPos, yPos, 0, 0, itemTextureWidth, itemTextureHeight);
		}
		GL11.glPopMatrix();
	}
}