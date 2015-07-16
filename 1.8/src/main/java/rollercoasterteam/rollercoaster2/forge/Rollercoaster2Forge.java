package rollercoasterteam.rollercoaster2.forge;

import rollercoasterteam.rollercoaster2.core.ModInfo;
import rollercoasterteam.rollercoaster2.core.ModRegistry;
import rollercoasterteam.rollercoaster2.core.api.BaseAPIProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

//1.8
@Mod(modid = ModInfo.MODID)
public class Rollercoaster2Forge {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        BaseAPIProxy.API = new APIHandler();

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
