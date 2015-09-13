package rcteam.rc2.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rcteam.rc2.RC2;
import rcteam.rc2.block.te.TileEntityTrack;
import rcteam.rc2.multiblock.MultiBlockManager;
import rcteam.rc2.rollercoaster.CategoryEnum;

import java.util.Collections;

public class BlockDummy extends Block {
	public BlockDummy() {
		super(Material.barrier);
		if (!RC2.isRunningInDev) setBlockUnbreakable();
		setBlockBounds(0f, 0f, 0f, 1f, 0.75f, 1f);
		setUnlocalizedName("track_dummy");
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isVisuallyOpaque() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 0;
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (RC2.isRunningInDev && player.isSneaking()) this.breakBlock(world, pos, state);
		else {
			TileEntityTrack tileEntityTrack = (TileEntityTrack) world.getTileEntity(pos);
			MultiBlockManager.instance.destroyStructures(world, Lists.newArrayList(tileEntityTrack.getParentPositions()));
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTrack().setDummy(true);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		world.removeTileEntity(pos);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer renderer) {
		renderer.addEffect(new BlockTrack.TrackParticleFX(world, pos.getX(), pos.getY(), pos.getZ(), Item.getItemFromBlock(RC2Blocks.trackMap.get(CategoryEnum.STEEL))));
		return false;
	}
}
