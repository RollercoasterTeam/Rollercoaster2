package rcteam.rc2.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.rollercoaster.TrackPiece;
import rcteam.rc2.util.Reference;
import rcteam.rc2.util.TrackStateMapper;
import rcteam.rc2.util.Utils;

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
			if (!stack.hasTagCompound()) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setTag("current_piece", track.getInfo().getCurrentPiece().writeToNBT());
				stack.setTagCompound(compound);
			}
//			TrackPiece piece = TrackPiece.readFromNBT(stack.getSubCompound("current_piece", true));
			TrackPiece piece = this.track.getInfo().getNextPiece();
			this.track.getInfo().setCurrentPiece(piece);
			if (player.isSneaking()) {
				this.track.getInfo().setCurrentPiece(this.track.getInfo().getPiece("straight"));
				world.setBlockState(pos, this.track.getDefaultState().withProperty(BlockTrack.FACING, Utils.getFacingFromEntity(world, pos, player, false, false)).withProperty(BlockTrack.PIECE_PROPERTY, this.track.getInfo().getPiece("straight")));
			} else {
				world.setBlockState(pos, track.getStateFromMeta(Utils.getFacingFromEntity(world, pos, player, false, false).getHorizontalIndex()).withProperty(BlockTrack.PIECE_PROPERTY, piece), 3);
			}
//			world.setBlockState(pos, newState.withProperty(BlockTrack.PIECE_PROPERTY, piece), 3);
			return true;
		}
		return false;
	}

	public static class ItemTrackMeshDefinition implements ItemMeshDefinition {
		public static final ItemTrackMeshDefinition INSTANCE = new ItemTrackMeshDefinition();

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			ModelResourceLocation location = new ModelResourceLocation(Reference.RESOURCE_PREFIX + "tracks/hyper_twister", "inventory");
			return location;
		}
	}
}
