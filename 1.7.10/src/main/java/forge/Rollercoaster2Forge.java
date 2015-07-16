package forge;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import rollercoasterteam.rollercoaster2.core.ModInfo;
import rollercoasterteam.rollercoaster2.core.ModRegistry;
import rollercoasterteam.rollercoaster2.core.api.BaseAPIProxy;

//1.7.10
@Mod(modid = ModInfo.MODID)
public class Rollercoaster2Forge {

    public static APIHandler handler;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        handler = new APIHandler();
        BaseAPIProxy.API = handler;

        try {
            ModRegistry.scanMods();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        ModRegistry.loadMods();
    }

}
