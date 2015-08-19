package rcteam.rc2.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import rcteam.rc2.RC2;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.block.te.TileEntityTrack;
import rcteam.rc2.rollercoaster.CategoryEnum;
import rcteam.rc2.rollercoaster.TrackPieceInfo;
import rcteam.rc2.util.Reference;
import rcteam.rc2.util.Utils;

import java.util.List;

public class ItemTrack extends ItemBlock {
	private BlockTrack track;
	private TrackPieceInfo info;

	public ItemTrack(Block track) {
		super(track);
		this.track = (BlockTrack) track;
	}

	public ItemTrack(Block track, TrackPieceInfo info) {
		super(track);
		this.track = (BlockTrack) track;
		this.info = info;
	}

	public BlockTrack getTrack() {
		return this.track;
	}

	public TrackPieceInfo getInfo() {
		return this.info;
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		if (RC2.isRunningInDev) {   //this makes tracks placeable by hand when run in a dev env
			//TODO!
			stack.setTagInfo("BlockEntityTag", new NBTTagCompound());
			stack.setTagInfo("info", this.info.writeToNBT());
			world.setBlockState(pos, newState.withProperty(this.info.getCategory().PIECE_PROPERTY, this.info.getCurrentPiece()).withProperty(BlockTrack.FACING, Utils.getFacingFromEntity(world, pos, player, false, false)), 3);
			setTileEntityNBT(world, pos, stack, player);
			return true;
		}
		return false;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public void getSubItems(Item item, CreativeTabs tabs, List subItems) {
//		if (item instanceof ItemTrack) {
//			for (CategoryEnum categoryEnum : CategoryEnum.values()) {
//				ItemStack stack = new ItemStack(this.block, 1, categoryEnum.ordinal());
//				stack.setTagInfo("info", this.info.writeToNBT());
//				subItems.add(stack);
//			}
//		}
//	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		//TODO: clean up this nbt stuff
		if (!stack.hasTagCompound()) {
			NBTTagCompound compound = new NBTTagCompound();
			stack.setTagCompound(compound);
			compound.setTag("info", this.info.writeToNBT());
		} else if (!stack.getTagCompound().hasKey("info")) {
			stack.getTagCompound().setTag("info", this.info.writeToNBT());
		}
		TrackPieceInfo info = TrackPieceInfo.readFromNBT(stack.getTagCompound().getCompoundTag("info"));
		tooltip.add(info.getCurrentPiece().getDisplayName());
		tooltip.add(info.getCurrentPiece().getName());
	}

	public static class ItemTrackMeshDefinition implements ItemMeshDefinition {
		public static final ItemTrackMeshDefinition INSTANCE = new ItemTrackMeshDefinition();
		private String name = "inventory";

		public void setVariantName(String name) {
			if (name != null && !name.isEmpty()) {
				this.name = name;
			}
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			ModelResourceLocation location = new ModelResourceLocation(Reference.RESOURCE_PREFIX + "tracks/hyper_twister", name);
			return location;
		}
	}
}
