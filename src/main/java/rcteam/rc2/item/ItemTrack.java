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

import javax.vecmath.Vector3f;
import java.util.Iterator;
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
			world.setBlockState(pos, newState.withProperty(this.info.getCategory().PIECE_PROPERTY, this.info.getCurrentStyle().getCurrentPiece()).withProperty(BlockTrack.FACING, Utils.getFacingFromEntity(world, pos, player, false, false)), 3);
			Vector3f dimensions = this.info.getCurrentStyle().getCurrentPiece().getSize();
//			Iterator iterator = BlockPos.getAllInBox()
			setTileEntityNBT(world, pos, stack, player);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List subItems) {
		if (item instanceof ItemTrack) {
			CategoryEnum categoryEnum = ((ItemTrack) item).getInfo().getCategory();
//			for (CategoryEnum categoryEnum : CategoryEnum.values()) {
				if (categoryEnum.PIECE_PROPERTY.getAllowedValues() != null && !categoryEnum.PIECE_PROPERTY.getAllowedValues().isEmpty()) {
					ItemStack stack = new ItemStack(this.block, 1, categoryEnum.ordinal());
					stack.setTagInfo("info", this.info.writeToNBT());
					subItems.add(stack);
				}
//			}
		}
	}

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
		tooltip.add(info.getCurrentStyle().getCurrentPiece().getDisplayName());
		tooltip.add(info.getCurrentStyle().getCurrentPiece().getName());
	}

	public static class ItemTrackMeshDefinition implements ItemMeshDefinition {
		public static final ItemTrackMeshDefinition INSTANCE = new ItemTrackMeshDefinition();

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			ItemTrack track = (ItemTrack) stack.getItem();
			String loc = "tracks/" + track.getInfo().getCategory().getName() + "/" + track.getInfo().getCurrentStyle().getName();
//			String name = track.getInfo().getCurrentStyle().getCurrentPiece().getName();
//			name = name == null ? "inventory" : name;
			String name = "inventory";
			ModelResourceLocation location = new ModelResourceLocation(Reference.RESOURCE_PREFIX + loc, name);
			return location;
		}
	}
}
