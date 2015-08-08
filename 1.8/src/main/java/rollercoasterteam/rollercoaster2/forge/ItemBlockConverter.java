package rollercoasterteam.rollercoaster2.forge;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import rollercoasterteam.rollercoaster2.core.api.block.RCMeta;
import rollercoasterteam.rollercoaster2.core.api.tile.RCTile;

import java.util.List;

public class ItemBlockConverter extends ItemBlock {

	BlockConverter converter;

	public ItemBlockConverter(Block block) {
		super(block);
		if(block instanceof BlockConverter){
			this.converter = (BlockConverter) block;
		}
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List list) {
		RCTile tile = converter.rcBlock.getTile();
		if(tile instanceof RCMeta){
			for(Integer i : ((RCMeta) converter.rcBlock.getTile()).types()){
				list.add(new ItemStack(itemIn, 1, i));
			}
		}
		super.getSubItems(itemIn, tab, list);
	}
}
