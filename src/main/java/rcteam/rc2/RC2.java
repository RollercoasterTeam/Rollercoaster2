package rcteam.rc2;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.command.CommandHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLLog;
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
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.client.gui.GuiHandler;
import rcteam.rc2.command.GiveThemeParkCommand;
import rcteam.rc2.item.RC2Items;
import rcteam.rc2.network.packets.PacketPipeline;
import rcteam.rc2.proxy.CommonProxy;
import rcteam.rc2.rollercoaster.CoasterStyle;
import rcteam.rc2.rollercoaster.StyleRegistry;
import rcteam.rc2.rollercoaster.TrackPieceRegistry;
import rcteam.rc2.util.CoasterPack;
import rcteam.rc2.util.FileManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;

import rcteam.rc2.util.JsonParser;
import rcteam.rc2.util.Reference;

@Mod(modid = RC2.MODID, name = RC2.NAME, version = RC2.VERSION)
public class RC2 {
	public static final String MODID = "rc2";
	public static final String NAME = "Roller Coaster 2";
	public static final String VERSION = "v0.1";

	public static HashMap<String, CoasterPack> packs = new HashMap<>();

	@Instance
	public static RC2 instance;

	@SidedProxy(clientSide="rcteam.rc2.proxy.ClientProxy", serverSide="rcteam.rc2.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static String srcDir;
	public static String packsDir;
	public static Logger logger;
	public static CreativeTabs tab;

	public static final PacketPipeline packetPipeline = new PacketPipeline();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
		srcDir = event.getSourceFile().getAbsolutePath();
		packsDir = srcDir + "\\assets\\" + MODID + "\\packs\\";
		logger = LogManager.getLogger(NAME);

		tab = new CreativeTabs(NAME) {
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

		TrackPieceRegistry.INSTANCE.registerDefaultPieces();

//		if (event.getSide() == Side.CLIENT) {
//			readPackFolder();
//		}

//		readPackFolder();
//		getListPacks().stream().forEach(CoasterPack::registerStyles);
//		StyleRegistry.INSTANCE.registerBlocks();

		RC2Items.preInit();
		RC2Blocks.preInit(event.getSide());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		packetPipeline.initalise();
		proxy.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

//		readPackFolder();
//		getListPacks().stream().forEach(CoasterPack::registerStyles);
//		StyleRegistry.INSTANCE.registerBlocks();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		packetPipeline.postInitialise();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		((CommandHandler) event.getServer().getCommandManager()).registerCommand(new GiveThemeParkCommand());
//		event.registerServerCommand(new GiveThemeParkCommand());
	}

	public static void readPackFolder() {
//		logger.info("reading pack folder");
//		logger.info(srcDir + "\\assets\\rc2\\packs\\");
		File packsFolder = new File(packsDir);
		for (File pack : packsFolder.listFiles()) {
			CoasterPack coasterPack = FileManager.readPack(pack);
			if (coasterPack != null) register(coasterPack);
		}
	}

	public static Collection<CoasterPack> getListPacks() {
		return packs.values();
	}

	public static void register(CoasterPack pack) {
		logger.info("Registering pack: " + pack.getName());
		if (packs.get(pack.getName()) == null) RC2.packs.put(pack.getName(), pack);
		else logger.error("A coaster pack has already been registered with name {}", pack.getName());
	}

	public static CoasterPack getPack(String name) {
		return packs.get(name);
	}

	public static CoasterPack getPackContainingStyle(CoasterStyle style) {
		return packs.values().stream().filter(pack -> pack.getStyles().containsKey(style.getName())).findFirst().get();
	}
}
