package testmod;

import rollercoasterteam.rollercoaster2.core.api.BaseAPIProxy;
import rollercoasterteam.rollercoaster2.core.api.IXMod;

public class TestMod implements IXMod {
    @Override
    public String modID() {
        return "test";
    }

    @Override
    public String name() {
        return "TestMod";
    }

    @Override
    public String version() {
        return "0";
    }

    @Override
    public void load() {
        System.out.println("Test mod loaded!");


        BaseAPIProxy.API.registerBlock(new TestBlock("TestBlock"));
        BaseAPIProxy.API.registerItem(new TestItem("TestItem"));
    }
}
