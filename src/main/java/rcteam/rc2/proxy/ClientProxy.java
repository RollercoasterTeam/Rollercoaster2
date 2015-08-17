package rcteam.rc2.proxy;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import rcteam.rc2.RC2;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.client.gui.GuiThemeParkOverlay;
import rcteam.rc2.item.RC2Items;
import rcteam.rc2.rollercoaster.CategoryEnum;
import rcteam.rc2.rollercoaster.ThemeParkLogo;
import rcteam.rc2.util.FileManager;
import rcteam.rc2.util.OBJLoader;
import rcteam.rc2.util.TrackStateMapper;

import java.io.File;
import java.util.Map;

public class ClientProxy extends CommonProxy {
	public static int trackRenderId = -1;

	@Override
	public void preInit() {
		ModelLoaderRegistry.registerLoader(OBJLoader.instance);
		OBJLoader.instance.addDomain(RC2.MODID.toLowerCase());
	}

	@Override
	public void init() {
		RC2Items.init(Side.CLIENT);
		RC2Blocks.init(Side.CLIENT);
		initRenderers();
	}

	@Override
	public void initRenderers() {
        ThemeParkLogo.init();
		MinecraftForge.EVENT_BUS.register(new GuiThemeParkOverlay(Minecraft.getMinecraft()));
	}
}
