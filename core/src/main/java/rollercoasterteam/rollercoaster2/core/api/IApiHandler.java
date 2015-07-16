package rollercoasterteam.rollercoaster2.core.api;

import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.item.RCItem;
import rollercoasterteam.rollercoaster2.core.api.world.RCWorld;

public interface IApiHandler {

    public void registerBlock(RCBlock RCBlock);

    public void registerItem(RCItem rcItem);

    public void modContrcution(IXMod mod);

    public RCWorld getWorld(int dimID);

}
