package rcteam.rc2.item;

import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.common.util.Constants;
import rcteam.rc2.RC2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCone extends ItemFood {
	public ItemCone() {
		super(4, 0.3F, false);
		setMaxStackSize(1);
		setUnlocalizedName("cone");
		setCreativeTab(RC2.tab);
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
		NBTTagList scoops = new NBTTagList();

		for (int i = 0; i < 4; i++) {
			scoops.appendTag(new NBTTagString(Integer.toString(i)));
		}

		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setTag("scoops", scoops);

//		System.out.println("A: " + scoops.tagCount());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
		onCreated(stack, player.getEntityWorld(), player);
		
		NBTTagList scoops = stack.getTagCompound().getTagList("scoops", Constants.NBT.TAG_STRING);
		
		for(int i = 0; i < scoops.tagCount(); i++) {
			String tag = scoops.getStringTagAt(i);
			list.add(tag);
		}
	}

//	@Override
//	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
//		int damage = stack.getItemDamage();
//		NBTTagList scoops = stack.getTagCompound().getTagList("scoops", Constants.NBT.TAG_STRING);
//
//		return null;
//	}
}
