package rollercoasterteam.rollercoaster2.forge;

import rollercoasterteam.rollercoaster2.core.api.IApiHandler;
import rollercoasterteam.rollercoaster2.core.api.IXMod;
import rollercoasterteam.rollercoaster2.core.api.block.Block;

public class APIHandler implements IApiHandler {
    @Override
    public void registerBlock(Block block) {
        System.out.println("Registering block");
    }

    @Override
    public void modContrcution(IXMod mod) {

    }
}
