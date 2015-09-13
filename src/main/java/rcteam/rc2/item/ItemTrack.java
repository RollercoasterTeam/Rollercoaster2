package rcteam.rc2.item;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import org.apache.commons.lang3.tuple.Pair;
import rcteam.rc2.RC2;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.block.te.TileEntityTrack;
import rcteam.rc2.multiblock.MultiBlockManager;
import rcteam.rc2.multiblock.MultiBlockStructure;
import rcteam.rc2.multiblock.structures.MultiBlockTracks;
import rcteam.rc2.rollercoaster.CategoryEnum;
import rcteam.rc2.rollercoaster.TrackPiece;
import rcteam.rc2.rollercoaster.TrackPieceInfo;
import rcteam.rc2.util.OBJModel;
import rcteam.rc2.util.Reference;
import rcteam.rc2.util.Utils;

import javax.vecmath.Vector3f;
import java.io.IOException;
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
		if (RC2.isRunningInDev && !world.isRemote) {   //this makes tracks placeable by hand when run in a dev env
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("index")) {
				int index = stack.getTagCompound().getInteger("index");
				index = index < TrackPiece.values().length ? index : 0;
				this.info.getCurrentStyle().setCurrentPiece(TrackPiece.values()[index]);
			}
			stack.setTagInfo("BlockEntityTag", new NBTTagCompound());
			stack.setTagInfo("info", this.info.writeToNBT());
			List<Pair<EnumFacing, EnumFacing>> validOrients = Lists.newArrayList();
			for (EnumFacing facing : EnumFacing.HORIZONTALS) validOrients.add(Pair.of(facing, EnumFacing.UP));
			MultiBlockTracks structure = new MultiBlockTracks(MultiBlockManager.templateMap.get(this.info.getCurrentStyle().getCurrentPiece().name), validOrients);
			EnumFacing facing = Utils.getFacingFromEntity(pos, player, false, false);
			newState = newState.withProperty(this.info.getCategory().getProperty(), this.info.getCurrentStyle().getCurrentPiece()).withProperty(BlockTrack.FACING, facing);
			structure.buildTrack(world, pos, newState, facing);
			MultiBlockManager.structureMap.put(pos, structure);
			setTileEntityNBT(world, pos, stack, player);
			return true;
		}
		if (world.isRemote) {
			try {
				//TODO: fix this!
				ModelResourceLocation location = ItemTrackMeshDefinition.INSTANCE.getModelLocation(stack);
				IModel model = ModelLoaderRegistry.getModel(new ModelResourceLocation(new ResourceLocation(location.getResourceDomain(), "block/" + location.getResourcePath()), location.getVariant()));
				((OBJModel) model).getMatLib().changeMaterialColor(((OBJModel) model).getMatLib().getMaterialNames().get(0), 0xFFFF0000);
			} catch (IOException e) {
				RC2.logger.info("failed to get model");
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List subItems) {
		if (item instanceof ItemTrack) {
			CategoryEnum categoryEnum = ((ItemTrack) item).getInfo().getCategory();
//			for (CategoryEnum categoryEnum : CategoryEnum.values()) {
				if (categoryEnum.getProperty().getAllowedValues() != null && !categoryEnum.getProperty().getAllowedValues().isEmpty()) {
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
		tooltip.add(info.getCurrentStyle().getCurrentPiece().name);
	}

	public static class ItemTrackMeshDefinition implements ItemMeshDefinition {
		public static final ItemTrackMeshDefinition INSTANCE = new ItemTrackMeshDefinition();

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			ItemTrack track = (ItemTrack) stack.getItem();
			String loc = "tracks/" + track.getInfo().getCategory().getName() + "/" + track.getInfo().getCurrentStyle().getName();
			String name = "inventory";
			ModelResourceLocation location = new ModelResourceLocation(Reference.RESOURCE_PREFIX + loc, name);
			return location;
		}
	}
}
