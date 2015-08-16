package rcteam.rc2.item;

import java.util.List;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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
	public enum EnumFlavors {
		CONE(0, "cone"),
		CHOCOLATE(1, "chocolate"),
		MINT(2, "mint"),
		STRAWBERRY(3, "strawberry"),
		VANILLA(4, "vanilla");

		public int meta;
		public String textureName;

		EnumFlavors(int meta, String textureName) {
			this.meta = meta;
			this.textureName = textureName;
		}

		public static ItemMeshDefinition getItemMeshDefinition() {
			return new ItemMeshDefinition() {
				@Override
				public ModelResourceLocation getModelLocation(ItemStack stack) {
					if (stack == null || !(stack.getItem() instanceof ItemCone)) {
						return null;
					} else {
						return new ModelResourceLocation(RC2.MODID.toLowerCase() + ":" + "ice_cream/" + EnumFlavors.values()[stack.getItemDamage()].textureName);
					}
				}
			};
		}
	}

	public ItemCone() {
		super(4, 0.3F, false);
		setHasSubtypes(true);
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

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
		for (int i = 0; i < EnumFlavors.values().length; i++) {
			subItems.add(new ItemStack(itemIn.setCreativeTab(tab), 1, i));
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
