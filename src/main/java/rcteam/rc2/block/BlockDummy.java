package rcteam.rc2.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import rcteam.rc2.rollercoaster.CategoryEnum;

public class BlockDummy extends Block {
	private BlockPos parentPos;

	public BlockDummy(CategoryEnum categoryEnum) {
		super(categoryEnum.getMaterial());

	}


}
