package rcteam.rc2.block;

import rcteam.rc2.block.te.TileEntityEntrance;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEntrance extends BlockContainer {

	public BlockEntrance() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityEntrance();
	}

}
