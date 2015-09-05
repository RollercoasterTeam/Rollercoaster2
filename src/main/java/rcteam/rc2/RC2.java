package rcteam.rc2;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import net.minecraft.command.CommandHandler;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.client.gui.GuiHandler;
import rcteam.rc2.command.GiveThemeParkCommand;
import rcteam.rc2.item.RC2Items;
import rcteam.rc2.network.packets.PacketPipeline;
import rcteam.rc2.proxy.CommonProxy;
import rcteam.rc2.util.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Mod(modid = RC2.MODID, name = RC2.NAME, version = RC2.VERSION)
public class RC2 {
	public static final String MODID = "rc2";
	public static final String NAME = "Roller Coaster 2";
	public static final String VERSION = "v0.1";

	public static final boolean isRunningInDev = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

//	public static HashMap<String, CoasterPack> packs = new HashMap<>();

	@Instance(MODID)
	public static RC2 instance;

	@SidedProxy(clientSide="rcteam.rc2.proxy.ClientProxy", serverSide="rcteam.rc2.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static Logger logger;
	public static CreativeTabs tab;
	public static Comparator<ItemStack> tabSorter;
	public static final PacketPipeline packetPipeline = new PacketPipeline();

	public static File configDir;

//	public static String srcDir;
//	public static String packsDir;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
		logger = LogManager.getLogger(NAME);
		configDir = new File(event.getModConfigurationDirectory() + "/" + Reference.CONFIG_FOLDER_NAME);
		configDir.mkdirs();
		FileManager.copyDefaultsFromJar(getClass(), configDir);
		ConfigHandler.init(new File(configDir.getPath(), MODID.toLowerCase() + ".cfg"));

		tab = new CreativeTabs(NAME) {
			@Override
			public void displayAllReleventItems(List items) {
				super.displayAllReleventItems(items);
				Collections.sort(items, tabSorter);
			}

			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return null;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public ItemStack getIconItemStack() {
				return new ItemStack(RC2Items.hammer);
			}
		};

		try {
//			CategoryEnum.values();
//			TrackPieceEnum.values();
			FileManager.readInfoFiles();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		RC2Items.preInit(event.getSide());
		RC2Blocks.preInit(event.getSide());

		List<Item> items = Lists.newArrayList(RC2Blocks.getItemList());
		items.addAll(RC2Items.modelMap.keySet());
		tabSorter = Ordering.explicit(items).onResultOf(ItemStack::getItem);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		packetPipeline.initalise();
		proxy.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		packetPipeline.postInitialise();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		((CommandHandler) event.getServer().getCommandManager()).registerCommand(new GiveThemeParkCommand());
	}
}
