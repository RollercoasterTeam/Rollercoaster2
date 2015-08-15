package rcteam.rc2.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import rcteam.rc2.block.BlockTrack;

public class ItemTrack extends ItemBlock {
	private BlockTrack track;

	public ItemTrack(Block track) {
		super(track);
		this.track = (BlockTrack) track;
	}

	public BlockTrack getTrack() {
		return this.track;
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		if ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) {   //this makes tracks placeable by hand when run in a dev env
			//TODO!
			world.setBlockState(pos, track.getStateFromMeta(0), 3);
			return true;
		}
		return false;
	}
}
