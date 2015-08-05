package rcteam.rc2;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.AdvancedModelLoader;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.client.gui.GuiHandler;
import rcteam.rc2.command.GiveThemeParkCommand;
import rcteam.rc2.item.RC2Items;
import rcteam.rc2.network.packets.PacketPipeline;
import rcteam.rc2.proxy.CommonProxy;
import rcteam.rc2.rollercoaster.StyleRegistry;
import rcteam.rc2.util.CoasterPack;
import rcteam.rc2.util.FileManager;
import rcteam.rc2.util.OBJLoader;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	public static Logger logger = LogManager.getLogger();
	public static CreativeTabs tab;

    public static final PacketPipeline packetPipeline = new PacketPipeline();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
//		proxy.preInit();
		tab = new CreativeTabs("rc2") {
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

		readPackFolder();
		getListPacks().stream().forEach(CoasterPack:: registerStyles);
		StyleRegistry.INSTANCE.registerBlocks();

		RC2Items.preInit();
		RC2Blocks.preInit();
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
		event.registerServerCommand(new GiveThemeParkCommand());
	}

	public static void readPackFolder() {
		File packDir = new File("./rollercoaster2/packs/");
		if (!packDir.exists()) packDir.mkdir();
		for (File file : packDir.listFiles()) {
			CoasterPack pack = FileManager.readPack(file);
			if (pack != null) register(pack);
		}
	}

	public static Collection<CoasterPack> getListPacks() {
		return packs.values();
	}

	public static void register(CoasterPack pack) {
		RC2.logger.info("Registering pack: " + pack.getName());
		if (RC2.packs.get(pack.getName()) == null) RC2.packs.put(pack.getName(), pack);
		else RC2.logger.error("A coaster pack has already been registered with name {}", pack.getName());
	}

	public static CoasterPack getPack(String name) {
		return packs.get(name);
	}
}
