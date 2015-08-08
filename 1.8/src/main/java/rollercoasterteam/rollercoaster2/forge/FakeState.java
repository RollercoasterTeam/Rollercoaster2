package rollercoasterteam.rollercoaster2.forge;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Collection;


public class FakeState implements IBlockState {

	public IBlockAccess blockAccess;
	public BlockPos pos;
	public int meta = -1;
	BlockConverter converter;

	public FakeState(IBlockAccess w, BlockPos p, BlockConverter block)
	{
		this.blockAccess = w;
		this.pos = p;
		this.converter = block;
	}

	@Override
	public Collection getPropertyNames() {
		return null;
	}

	@Override
	public Comparable getValue(IProperty property) {
		return null;
	}

	@Override
	public IBlockState withProperty(IProperty property, Comparable value) {
		return null;
	}

	@Override
	public IBlockState cycleProperty(IProperty property) {
		return null;
	}

	@Override
	public ImmutableMap getProperties() {
		return null;
	}

	@Override
	public Block getBlock() {
		return converter;
	}
}
