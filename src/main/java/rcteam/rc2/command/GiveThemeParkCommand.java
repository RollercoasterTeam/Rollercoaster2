package rcteam.rc2.command;

import com.google.common.collect.Lists;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class GiveThemeParkCommand implements ICommand {
	public GiveThemeParkCommand() {}

	@Override
	public String getName() {
		return "rc";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/rc tp <player> [size] [maxBuildHeight]";
	}

	@Override
	public List getAliases() {
		return Lists.newArrayList();
	}

	@Override
	public void execute(ICommandSender sender, String[] args) {
		if(args.length < 3) {
			sender.addChatMessage(new ChatComponentText("Invalid number of arguments!"));
			return;
		}
		
		EntityPlayer player = (EntityPlayer) sender;
		//List entities = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, MinecraftServer.getServer().)
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return MinecraftServer.getServer().getConfigurationManager().canSendCommands(((EntityPlayer) sender).getGameProfile());
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		//TODO
		return Lists.newArrayList();
	}

	@Override
	public boolean isUsernameIndex(String[] string, int i) {
		//TODO
		return i == 0;
	}
	
	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
