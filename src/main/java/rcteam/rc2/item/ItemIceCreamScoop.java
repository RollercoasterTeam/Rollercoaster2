package rcteam.rc2.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import rcteam.rc2.RC2;
import rcteam.rc2.util.HammerMode;

import java.util.List;

public class ItemIceCreamScoop extends Item {
	public ItemIceCreamScoop(boolean isGolden) {
		setMaxStackSize(1);
		setMaxDamage(100);
		setUnlocalizedName(isGolden ? "goldenScoop" : "scoop");
//		setTextureName("rc2:iceCream/" + (isGolden ? "goldenScoop" : "scoop"));
		setCreativeTab(RC2.tab);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return stack.getItem() == RC2Items.goldenScoop;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
		if(stack.getItem() == RC2Items.goldenScoop) {
			list.add("Extra Shiny!");
		}
	}
}
