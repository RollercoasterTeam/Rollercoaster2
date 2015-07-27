package rcteam.rc2;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.client.gui.GuiHandler;
import rcteam.rc2.client.gui.GuiThemeParkOverlay;
import rcteam.rc2.command.GiveThemeParkCommand;
import rcteam.rc2.item.RC2Items;
import rcteam.rc2.proxy.CommonProxy;
import rcteam.rc2.rollercoaster.ThemeParkLogo;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = RC2.MODID, name = RC2.NAME, version = RC2.VERSION)
public class RC2 {
	
	public static final String MODID = "rc2";
	public static final String NAME = "Rollercoaster 2";
	public static final String VERSION = "v0.1";

	@Instance
	public static RC2 instance;
	
	@SidedProxy(clientSide="rcteam.rc2.proxy.ClientProxy", serverSide="rcteam.rc2.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static CreativeTabs tab;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
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
		
		proxy.init();
		RC2Items.init();
		RC2Blocks.init();
		ThemeParkLogo.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new GuiThemeParkOverlay(Minecraft.getMinecraft()));
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new GiveThemeParkCommand());
	}
}
