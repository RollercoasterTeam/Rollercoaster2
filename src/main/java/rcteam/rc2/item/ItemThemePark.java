package rcteam.rc2.item;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import rcteam.rc2.RC2;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.block.te.TileEntityEntrance;
import rcteam.rc2.rollercoaster.ThemePark;
import rcteam.rc2.util.Utils;

public class ItemThemePark extends Item {
	public ItemThemePark() {
		setUnlocalizedName("themePark");
		setCreativeTab(RC2.tab);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			world.setBlockState(pos, RC2Blocks.entrance.getDefaultState());
			TileEntityEntrance entrance = (TileEntityEntrance) world.getTileEntity(pos);
			entrance.themePark = new ThemePark(Utils.getFacingFromEntity(world, pos, player), stack.getDisplayName(), 25, -1).place(world, pos);
			return true;
		}
		return false;
	}
}
