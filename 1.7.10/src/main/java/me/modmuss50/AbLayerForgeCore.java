package me.modmuss50;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import me.modmuss50.abLayer.AbLayerInfo;
import me.modmuss50.abLayer.ModRegistry;
import me.modmuss50.abLayer.api.AbLayerAPI;

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
