package rollercoasterteam.rollercoaster2.core;


import rollercoasterteam.rollercoaster2.core.api.BaseAPIProxy;
import rollercoasterteam.rollercoaster2.core.api.IXMod;

import java.util.ArrayList;

public class ModRegistry {

    public static ArrayList<IXMod> mods = new ArrayList<IXMod>();

    public static void registerMod(IXMod newMod) {
        mods.add(newMod);
        BaseAPIProxy.API.modContrcution(newMod);
    }


    public static void scanMods() throws Exception{
        Class<IXMod> testMod = (Class<IXMod>) Class.forName("testmod.TestMod");
        registerMod(testMod.newInstance());
    }

    public static void loadMods(){
        for(IXMod mod : mods){
            mod.load();
        }
    }



}
