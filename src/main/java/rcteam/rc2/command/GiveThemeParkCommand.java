package rcteam.rc2.command;

import java.util.List;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import scala.actors.threadpool.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class GiveThemeParkCommand implements ICommand {

	@Override
	public String getCommandName() {
		return "rc";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/rc <player> <size> <maxBuildHeight>";
	}

	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length < 3) {
			sender.addChatMessage(new ChatComponentText("Invalid number of arguments!"));
			return;
		}
		
		EntityPlayer player = (EntityPlayer) sender;
		//List entities = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, MinecraftServer.getServer().)
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return MinecraftServer.getServer().getConfigurationManager().func_152596_g(((EntityPlayer) sender).getGameProfile());
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] string, int i) {
		return i == 0 ? true : false;
	}
	
	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
