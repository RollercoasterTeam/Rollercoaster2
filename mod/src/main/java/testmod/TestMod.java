package testmod;

import me.modmuss50.abLayer.api.IXMod;

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
    }
}
