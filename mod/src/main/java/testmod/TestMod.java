package testmod;

import rollercoasterteam.rollercoaster2.core.api.BaseAPIProxy;
import rollercoasterteam.rollercoaster2.core.api.IXMod;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;

public class TestMod implements IXMod {
   public static RCBlock testBlock;
    public static RCBlock modelBlock;

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

        testBlock = new TestBlock("TestBlock");
        modelBlock = new ModeledBlock("ModeledBlock");

        BaseAPIProxy.API.registerBlock(testBlock);
        BaseAPIProxy.API.registerBlock(modelBlock);
        BaseAPIProxy.API.registerItem(new TestItem("TestItem"));
    }
}
