package me.modmuss50.abLayer;


import me.modmuss50.abLayer.api.AbLayerAPI;
import me.modmuss50.abLayer.api.IXMod;

import java.util.ArrayList;

public class ModRegistry {

    public static ArrayList<IXMod> mods = new ArrayList<IXMod>();

    public static void registerMod(IXMod newMod) {
        mods.add(newMod);
        AbLayerAPI.API.modContrcution(newMod);
    }


    public static void scanMods() throws Exception{
        //TODO scan for mods

        Class<IXMod> testMod = (Class<IXMod>) Class.forName("testmod.TestMod");
        registerMod(testMod.newInstance());
    }

    public static void loadMods(){
        for(IXMod mod : mods){
            mod.load();
        }
    }



}
