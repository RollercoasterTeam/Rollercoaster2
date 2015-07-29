package rcteam.rc2.item;

import java.util.List;

import rcteam.rc2.RC2;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCone extends ItemFood {
	
	public ItemCone() {
		super(4, 0.3F, false);
		setMaxStackSize(1);
		setUnlocalizedName("cone");
		setTextureName("rc2:iceCream/cone");
		setCreativeTab(RC2.tab);
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack) {
		return false;
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
		stack.stackTagCompound = new NBTTagCompound();
		
		NBTTagList scoops = new NBTTagList();
		
		scoops.appendTag(new NBTTagString("0"));
		scoops.appendTag(new NBTTagString("1"));
		scoops.appendTag(new NBTTagString("2"));
		scoops.appendTag(new NBTTagString("3"));
		
		stack.stackTagCompound.setTag("scoops", scoops);
		
		System.out.println("A: " + scoops.tagCount());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			
			NBTTagList scoops = new NBTTagList();
			
			scoops.appendTag(new NBTTagString("0"));
			scoops.appendTag(new NBTTagString("1"));
			scoops.appendTag(new NBTTagString("2"));
			scoops.appendTag(new NBTTagString("3"));
			
			stack.stackTagCompound.setTag("scoops", scoops);
			
			System.out.println("Emergency!");
		}
		
		NBTTagList scoops = stack.stackTagCompound.getTagList("scoops", 8);
		
		for(int i = 0; i < scoops.tagCount(); i++) {
			String tag = scoops.getStringTagAt(i);
			list.add(tag);
		}
	}
}
