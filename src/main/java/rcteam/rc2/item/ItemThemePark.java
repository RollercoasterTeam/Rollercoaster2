package rcteam.rc2.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import rcteam.rc2.RC2;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.block.te.TileEntityEntrance;
import rcteam.rc2.rollercoaster.ThemePark;

public class ItemThemePark extends Item {
	
	public ItemThemePark() {
		setUnlocalizedName("themePark");
		setTextureName("rc2:themePark");
		setCreativeTab(RC2.tab);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			world.setBlock(x, y, z, RC2Blocks.entrance);
			
			int l = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
	        ++l;
	        l %= 4;
	        System.out.println(l);
			
			TileEntityEntrance entrance = (TileEntityEntrance) world.getTileEntity(x, y, z);
			
			entrance.themePark = new ThemePark(l, stack.getDisplayName(), 25, -1).place(world, x, y, z);
			
			return true;
		}
		return false;
	}
}
