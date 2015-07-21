package rcteam.rc2.client.gui;

import rcteam.rc2.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

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
			return new GuiEntrance(player, world, x, y, z);
		}
		return null;
	}

}
