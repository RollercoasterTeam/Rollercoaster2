package rcteam.rc2.item;

import com.google.common.collect.Maps;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import rcteam.rc2.RC2;
import rcteam.rc2.block.BlockSupport;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.block.te.TileEntitySupport;
import rcteam.rc2.block.te.TileEntityTrack;
import rcteam.rc2.network.NetworkHandler;
import rcteam.rc2.rollercoaster.SupportUtils;
import rcteam.rc2.util.HammerMode;

import java.util.List;
import java.util.Map;

public class ItemHammer extends Item {
	public HammerMode mode = HammerMode.ROTATE;

	public ItemHammer() {
		this.mode = HammerMode.ROTATE;
		setMaxStackSize(1);
		setMaxDamage(HammerMode.values().length);
		setUnlocalizedName("hammer");
		setCreativeTab(RC2.tab);
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		if (damage >= 0 && damage < HammerMode.values().length) {
			super.setDamage(stack, damage);
			this.mode = HammerMode.values()[damage];
		}
	}

	@Override
	public int getDamage(ItemStack stack) {
		if (this.mode != null) return this.mode.ordinal();
		return stack.getItemDamage();
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	private void writeNBT(NBTTagCompound compound) {
		compound.setByte("mode", (byte) this.mode.ordinal());
	}

	@Override
	public boolean updateItemStackNBT(NBTTagCompound compound) {
		if (compound.hasKey("mode")) {
			this.mode = HammerMode.values()[compound.getByte("mode")];
		} else {
			this.writeNBT(compound);
		}
		return true;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
		list.add(HammerMode.values()[stack.getItemDamage()].getDisplayName());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote && player.isSneaking()) {
//			RC2.logger.info("changing mode");
			int index = this.mode.ordinal();
			if (index + 1 == HammerMode.values().length) {
				this.mode = HammerMode.ROTATE;
				this.setDamage(stack, 0);
				NetworkHandler.updateHammerDamage(0);
			} else {
				this.mode = HammerMode.values()[index + 1];
				this.setDamage(stack, index + 1);
				NetworkHandler.updateHammerDamage(index + 1);
			}
		}
		return stack;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (RC2.isRunningInDev) {
			if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityTrack) {
				if (this.mode == null) this.mode = HammerMode.values()[stack.getItemDamage()];
				switch (this.mode) {
					case ROTATE:
						//TODO!
//						world.setBlockToAir(pos);
						break;
					case CHANGE_TYPE: break;
					case ADJUSTMENT:
						TileEntityTrack tileEntityTrack = (TileEntityTrack) world.getTileEntity(pos);
						tileEntityTrack.info.getCurrentStyle().cycleCurrentPiece();
						world.markBlockForUpdate(pos);
						break;
				}
//				if (this.mode.name.equalsIgnoreCase("Rotate")) {
//					world.setBlockToAir(pos);
//				} else if (this.mode.name.equalsIgnoreCase("Adjustment")) {
//					TileEntityTrack tileEntityTrack = (TileEntityTrack) world.getTileEntity(pos);
//					tileEntityTrack.info.getCurrentStyle().cycleCurrentPiece();
//					world.markBlockForUpdate(pos);
//				}
				return true;
			} else if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntitySupport) {
				TileEntitySupport tileEntitySupport = (TileEntitySupport) world.getTileEntity(pos);
				if (this.mode == HammerMode.ADJUSTMENT) {
//					tileEntitySupport.info.setVisibility(SupportUtils.SupportSlot.CENTER, !tileEntitySupport.info.getVisibility(SupportUtils.SupportSlot.CENTER));
//					tileEntitySupport.info.setBasePlateVisibility(SupportUtils.SupportSlot.CENTER, !tileEntitySupport.info.getBasePlateVisibility(SupportUtils.SupportSlot.CENTER));
//					tileEntitySupport.info.setTopVisibility(SupportUtils.SupportSlot.SOUTH_EAST, true);
					world.markBlockRangeForRenderUpdate(pos, pos);
				} else if (this.mode == HammerMode.CHANGE_TYPE) {
//					tileEntitySupport.info.setBasePlateVisibility(SupportUtils.SupportSlot.CENTER, !tileEntitySupport.info.getBasePlateVisibility(SupportUtils.SupportSlot.CENTER));
//					tileEntitySupport.info.setBasePlateVisibility(SupportUtils.SupportSlot.NORTH_WEST, !tileEntitySupport.info.getBasePlateVisibility(SupportUtils.SupportSlot.NORTH_WEST));
//					tileEntitySupport.info.setTopVisibility(SupportUtils.SupportSlot.SOUTH_EAST, true);
					world.markBlockRangeForRenderUpdate(pos, pos);
				} else if (this.mode == HammerMode.ROTATE) {
					tileEntitySupport.info.setTopVisibility(SupportUtils.SupportSlot.WEST, !tileEntitySupport.info.getTopVisibility(SupportUtils.SupportSlot.WEST));
//					tileEntitySupport.info.setVisibility(SupportUtils.SupportSlot.NORTH_WEST, !tileEntitySupport.info.getVisibility(SupportUtils.SupportSlot.NORTH_WEST));
//					tileEntitySupport.info.setBasePlateVisibility(SupportUtils.SupportSlot.NORTH_WEST, !tileEntitySupport.info.getBasePlateVisibility(SupportUtils.SupportSlot.NORTH_WEST));
//					tileEntitySupport.info.setTopVisibility(SupportUtils.SupportSlot.SOUTH_EAST, true);
					world.markBlockRangeForRenderUpdate(pos, pos);
				}
//				if (this.mode.name.equalsIgnoreCase("Change Type")) {
//					List<Pair<EnumFacing, EnumFacing>> validOrients = Lists.newArrayList();
//					Arrays.asList(EnumFacing.HORIZONTALS).forEach(facing -> validOrients.add(Pair.of(facing, EnumFacing.UP)));
//					MultiBlockTest test = new MultiBlockTest(MultiBlockManager.templateMap.get("test"), validOrients);
//					EnumFacing facing = Utils.getFacingFromEntity(world, pos, player, true, false);
//					test.buildStructure(world, pos.offset(side).offset(facing), facing, EnumFacing.UP);
//					return true;
//				}
			}
		}
		return false;
	}
}
