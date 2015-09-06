package rcteam.rc2.item;

import com.google.common.collect.Maps;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityTrack;
import rcteam.rc2.util.HammerMode;

import java.util.List;
import java.util.Map;

public class ItemHammer extends Item {
	private static Map<String, HammerMode> modes = Maps.newHashMap();
	public HammerMode mode = HammerMode.ROTATE;

	public ItemHammer() {
		if (modes.isEmpty()) {
			for (HammerMode hammerMode : HammerMode.values()) {
				modes.put(hammerMode.getInternalName(), hammerMode);
			}
		}
		this.mode = HammerMode.ROTATE;
		setMaxStackSize(1);
		setMaxDamage(100);
		setUnlocalizedName("hammer");
		setCreativeTab(RC2.tab);
	}

	@Override
	public int getDamage(ItemStack stack) {
		if (stack.getTagCompound().hasKey("mode")) {
			return modes.get(stack.getTagCompound().getString("mode")).ordinal();
		}
		return 0;
	}

	@Override
	public boolean updateItemStackNBT(NBTTagCompound compound) {
		if (compound.hasKey("mode")) {
			this.mode = modes.get(compound.getString("mode"));
		}
		return true;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}
	
	@Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack copiedStack = itemStack.copy();
        copiedStack.setItemDamage(copiedStack.getItemDamage() + 1);
        copiedStack.stackSize = 1;
        return copiedStack;
    }
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setString("mode", this.mode.getInternalName());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
		onCreated(stack, player.getEntityWorld(), player);
		list.add(modes.get(stack.getTagCompound().getString("mode")).getDisplayName());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && player.isSneaking()) {
			RC2.logger.info("changing mode");
			int index = this.mode.ordinal();
			if (index + 1 == HammerMode.values().length) {
				this.mode = HammerMode.ROTATE;
			} else {
				this.mode = HammerMode.values()[index + 1];
			}
		}
		return stack;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (RC2.isRunningInDev) {
			if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityTrack) {
				switch (this.mode) {
					case ROTATE:
						//TODO!
//						world.setBlockToAir(pos);
						break;
					case CHANGE_TYPE:
						break;
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
			} else {
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
