package rcteam.rc2.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityEntrance;
import rcteam.rc2.util.Reference;

public class BlockEntrance extends BlockContainer {

	public BlockEntrance() {
		super(Material.rock);
		setUnlocalizedName("entrance");
//		setBlockTextureName("rc2:entrance");
		setCreativeTab(RC2.tab);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityEntrance();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote) {
			FMLNetworkHandler.openGui(playerIn, RC2.instance, Reference.GUI_ID_ENTRANCE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

}
