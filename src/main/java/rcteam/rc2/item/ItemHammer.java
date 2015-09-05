package rcteam.rc2.item;

import com.google.common.collect.Lists;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.apache.commons.lang3.tuple.Pair;
import rcteam.rc2.RC2;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.block.te.TileEntityTrack;
import rcteam.rc2.multiblock.MultiBlockManager;
import rcteam.rc2.multiblock.MultiBlockTemplate;
import rcteam.rc2.multiblock.structures.MultiBlockTest;
import rcteam.rc2.util.HammerMode;
import rcteam.rc2.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemHammer extends Item {
	public static List<HammerMode> modes = Lists.newArrayList(
			new HammerMode("Rotate") {      //TODO: make these pull from lang files? make HammerMode an enum?
				@Override
				public void onRightClick(TileEntity tileEntity, PlayerInteractEvent event) {

				}
			},
	        new HammerMode("Change Type") {
		        @Override
	            public void onRightClick(TileEntity tileEntity, PlayerInteractEvent event) {

		        }
	        },
	        new HammerMode("Adjustment") {
		        @Override
	            public void onRightClick(TileEntity tileEntity, PlayerInteractEvent event) {

		        }
	        }
	);

	public HammerMode mode = modes.get(0);

	public ItemHammer() {
		setMaxStackSize(1);
		setMaxDamage(100);
		setUnlocalizedName("hammer");
		setCreativeTab(RC2.tab);
	}

	@Override
	public int getDamage(ItemStack stack) {
		if (stack.getTagCompound().hasKey("mode")) {
			return stack.getTagCompound().getInteger("mode");
		}
		return 0;
	}

	@Override
	public boolean updateItemStackNBT(NBTTagCompound compound) {
		if (compound.hasKey("mode")) {
			this.mode = modes.get(compound.getInteger("mode"));
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
		stack.getTagCompound().setInteger("mode", modes.indexOf(this.mode));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
		onCreated(stack, player.getEntityWorld(), player);
		list.add(modes.get(stack.getTagCompound().getInteger("mode")).name + " Mode");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && player.isSneaking()) {
			RC2.logger.info("changing mode");
			int index = modes.indexOf(this.mode);
			if (index + 1 == modes.size()) {
				this.mode = modes.get(0);
			} else {
				this.mode = modes.get(index + 1);
			}
		}
		return stack;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (RC2.isRunningInDev) {
			if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityTrack) {
				if (this.mode.name.equalsIgnoreCase("Rotate")) {
					world.setBlockToAir(pos);
				} else if (this.mode.name.equalsIgnoreCase("Adjustment")) {
					TileEntityTrack tileEntityTrack = (TileEntityTrack) world.getTileEntity(pos);
					tileEntityTrack.info.getCurrentStyle().cycleCurrentPiece();
					world.markBlockForUpdate(pos);
				}
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
