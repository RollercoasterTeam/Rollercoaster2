package rollercoasterteam.rollercoaster2.forge;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import rollercoasterteam.rollercoaster2.core.api.item.RCItem;

public class ItemConverter extends Item {

    RCItem rcItem;

    public ItemConverter(RCItem rcItem) {
        this.rcItem = rcItem;
        setUnlocalizedName(rcItem.getName());
        setCreativeTab(CreativeTabs.tabAllSearch);
    }
}
