package rcteam.rc2.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rcteam.rc2.block.BlockTrack;

public class ItemTrack extends ItemBlock {
	private BlockTrack track;

	public ItemTrack(Block track) {
		super(track);
		this.track = (BlockTrack) track;
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		return false;
	}
}
