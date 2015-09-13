package rcteam.rc2.command;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import rcteam.rc2.block.te.TileEntitySupport;
import rcteam.rc2.rollercoaster.SupportUtils;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class TestSupportRenderCommand implements ICommand {
	private static BlockPos focusPosition;

	public TestSupportRenderCommand() {}

	@Override
	public String getName() {
		return "support";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/support [name of slot] [operation] <focus only> OR /support set <x> <y> <z>";
	}

	@Override
	public List getAliases() {
		return Lists.newArrayList("s");
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		World world = sender.getEntityWorld();
		if (args.length == 0 || args[0].equalsIgnoreCase("set")) {
			if (args.length > 1) focusPosition = CommandBase.func_175757_a(sender, args, 1, false);
			else if (world.isRemote) focusPosition = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
			sender.addChatMessage(new ChatComponentText(String.format("Position set to %d %d %d.", focusPosition.getX(), focusPosition.getY(), focusPosition.getZ())));
		} else {
			if (focusPosition == null) {
				if (world.isRemote) {
					focusPosition = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
					sender.addChatMessage(new ChatComponentText(String.format("Position set to %d %d %d.", focusPosition.getX(), focusPosition.getY(), focusPosition.getZ())));
				} else throw new CommandException("Tried to change slot visibility without specifying position data.");
			}
			String name = args[0];
			String operation = args.length > 1 ? args[1] : "toggle";
			boolean focusOnly = args.length > 2 && Boolean.valueOf(args[2]);
			if (name.equalsIgnoreCase("all")) {
				for (SupportUtils.SupportSlot slot : SupportUtils.SupportSlot.values()) {
					TileEntitySupport.updateSupportColumn(world, focusPosition, slot, operation, focusOnly);
				}
			} else {
				SupportUtils.SupportSlot slot = SupportUtils.getSlotFromName(name);
				if (slot == null) throw new CommandException("Provided slot name " + name + " does not exist.");
				else TileEntitySupport.updateSupportColumn(world, focusPosition, slot, operation, focusOnly);
			}
		}
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		List<String> list = Lists.newArrayList();
		list.clear();
		if (focusPosition == null) {
			list.add("set");
			return list;
		}
		if (args.length == 2) {
			list.add("true");
			list.add("false");
			list.add("toggle");
		} else if (sender.getEntityWorld().getTileEntity(focusPosition) != null && sender.getEntityWorld().getTileEntity(focusPosition) instanceof TileEntitySupport) {
			TileEntitySupport support = (TileEntitySupport) sender.getEntityWorld().getTileEntity(focusPosition);
			List<String> stringList = Lists.newArrayList("set");
			stringList.addAll(support.info.type.getValidSupportSlotNames());
			list.addAll(CommandBase.getListOfStringsMatchingLastWord(args, stringList.toArray(new String[stringList.size()])));
		}
		return list;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
