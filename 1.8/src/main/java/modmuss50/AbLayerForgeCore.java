package modmuss50;

import me.modmuss50.abLayer.AbLayerInfo;
import me.modmuss50.abLayer.ModRegistry;
import me.modmuss50.abLayer.api.AbLayerAPI;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

//1.8
@Mod(modid = AbLayerInfo.MODID)
public class AbLayerForgeCore {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        AbLayerAPI.API = new APIHandler();

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
