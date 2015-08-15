package rcteam.rc2.client.gui;

import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import rcteam.rc2.client.gui.themePark.GuiEditThemePark;
import rcteam.rc2.client.gui.themePark.GuiEntrance;
import rcteam.rc2.util.Reference;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == Reference.GUI_ID_ENTRANCE) {
			
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == Reference.GUI_ID_ENTRANCE) {
			return new GuiEntrance(player, world, new BlockPos(x, y, z));
		} else if(id == Reference.GUI_ID_EDIT_THEME_PARK) {
			return new GuiEditThemePark(player, world, new BlockPos(x, y, z));
		}
		return null;
	}
}
