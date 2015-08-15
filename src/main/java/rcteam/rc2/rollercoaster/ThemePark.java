package rcteam.rc2.rollercoaster;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ThemePark {
	private static final String DEFAULT_NAME = "New Theme Park";
	private static final int DEFAULT_XSIZE = 11;
	private static final int DEFAULT_BUILD_HEIGHT_LIMIT = -1;
	private static final Block wall = Blocks.stonebrick;
	private static final Block fence = Blocks.iron_bars;
	
	public String name;
	public int size;
	public int maxBuildHeight;
	public EnumFacing direction;
	public ThemeParkLogo logo;
	
	/**
	 * @param direction The direction that the park is facing
	 * @param name The name of the theme park 
	 * @param xSize The x size of the theme park
	 * @param zSize The z size of the theme park
	 * @param buildHeightLimit The maximum construction height that the park can build to
	 */
	public ThemePark(EnumFacing direction, String name, int xSize, int buildHeightLimit) {
		this.name = name;
		this.size = xSize;
		this.maxBuildHeight = buildHeightLimit;
		this.direction = direction;
		this.logo = new ThemeParkLogo();
	}
	
	public ThemePark() {
		this(EnumFacing.NORTH, DEFAULT_NAME, DEFAULT_XSIZE, DEFAULT_BUILD_HEIGHT_LIMIT);
	}
	
	public ThemePark(EnumFacing direction, String name) {
		this(direction, name, DEFAULT_XSIZE, DEFAULT_BUILD_HEIGHT_LIMIT);
	}
	
	public ThemePark(EnumFacing direction, String name, int xSize) {
		this(direction, name, xSize, DEFAULT_BUILD_HEIGHT_LIMIT);
	}

	public ThemePark place(World world, BlockPos pos) {
		//TODO: replace with a multiblock structure
//		if(direction == 0 || direction == 2) {
//			world.setBlock(x, y + 1, z + 3, wall);
//			world.setBlock(x, y + 2, z + 3, wall);
//			world.setBlock(x, y + 3, z + 3, wall);
//			world.setBlock(x, y + 4, z + 3, wall);
//			world.setBlock(x, y + 4, z + 2, wall);
//			world.setBlock(x, y + 5, z + 2, wall);
//			world.setBlock(x, y + 5, z + 1, wall);
//			world.setBlock(x, y + 5, z + 0, wall);
//			world.setBlock(x, y + 5, z - 1, wall);
//			world.setBlock(x, y + 5, z - 2, wall);
//			world.setBlock(x, y + 4, z - 2, wall);
//			world.setBlock(x, y + 4, z - 3, wall);
//			world.setBlock(x, y + 3, z - 3, wall);
//			world.setBlock(x, y + 2, z - 3, wall);
//			world.setBlock(x, y + 1, z - 3, wall);
//
//			int count = 0;
//
//			for(int i = 4; i < ((size - 1) / 2) + 2; i++) {
//				world.setBlock(x, y + 1, z - i, wall);
//				world.setBlock(x, y + 1, z + i, wall);
//
//				world.setBlock(x, y + 2, z - i, count % 2 == 0 ? fence : wall);
//				world.setBlock(x, y + 2, z + i, count % 2 == 0 ? fence : wall);
//
//				if(direction == 0) {
//					world.setBlock(x + (size + 1), y + 1, z - i, wall);
//					world.setBlock(x + (size + 1), y + 1, z + i, wall);
//
//					world.setBlock(x + (size + 1), y + 2, z - i, count % 2 == 0 ? fence : wall);
//					world.setBlock(x + (size + 1), y + 2, z + i, count % 2 == 0 ? fence : wall);
//				}
//				else if(direction == 2) {
//					world.setBlock(x - (size + 1), y + 1, z - i, wall);
//					world.setBlock(x - (size + 1), y + 1, z + i, wall);
//
//					world.setBlock(x - (size + 1), y + 2, z - i, count % 2 == 0 ? fence : wall);
//					world.setBlock(x - (size + 1), y + 2, z + i, count % 2 == 0 ? fence : wall);
//				}
//
//				count++;
//			}
//
//			if(direction == 0) {
//				for(int i = 0; i <= 3; i++) {
//					world.setBlock(x + (size + 1), y + 1, z - i, wall);
//					world.setBlock(x + (size + 1), y + 1, z + i, wall);
//
//					world.setBlock(x + (size + 1), y + 2, z - i, count % 2 == 0 ? fence : wall);
//					world.setBlock(x + (size + 1), y + 2, z + i, count % 2 == 0 ? fence : wall);
//
//					count++;
//				}
//
//				for(int i = 0; i < size; i++) {
//					world.setBlock(x + i + 1, y + 1, z - (((size - 1) / 2) + 1), wall);
//					world.setBlock(x + i + 1, y + 1, z + (((size - 1) / 2) + 1), wall);
//
//					world.setBlock(x + i + 1, y + 2, z - (((size - 1) / 2) + 1), count % 2 == 0 ? fence : wall);
//					world.setBlock(x + i + 1, y + 2, z + (((size - 1) / 2) + 1), count % 2 == 0 ? fence : wall);
//
//					count++;
//				}
//			}
//			else if(direction == 2) {
//				for(int i = 0; i <= 3; i++) {
//					world.setBlock(x - (size + 1), y + 1, z - i, wall);
//					world.setBlock(x - (size + 1), y + 1, z + i, wall);
//
//					world.setBlock(x - (size + 1), y + 2, z - i, count % 2 == 0 ? fence : wall);
//					world.setBlock(x - (size + 1), y + 2, z + i, count % 2 == 0 ? fence : wall);
//
//					count++;
//				}
//
//				for(int i = 0; i < size; i++) {
//					world.setBlock(x - i - 1, y + 1, z - (((size - 1) / 2) + 1), wall);
//					world.setBlock(x - i - 1, y + 1, z + (((size - 1) / 2) + 1), wall);
//
//					world.setBlock(x - i - 1, y + 2, z - (((size - 1) / 2) + 1), count % 2 == 0 ? fence : wall);
//					world.setBlock(x - i - 1, y + 2, z + (((size - 1) / 2) + 1), count % 2 == 0 ? fence : wall);
//
//					count++;
//				}
//			}
//		}
//		else if(direction == 1 || direction == 3) {
//			world.setBlock(x + 3, y + 1, z, wall);
//			world.setBlock(x + 3, y + 2, z, wall);
//			world.setBlock(x + 3, y + 3, z, wall);
//			world.setBlock(x + 3, y + 4, z, wall);
//			world.setBlock(x + 2, y + 4, z, wall);
//			world.setBlock(x + 2, y + 5, z, wall);
//			world.setBlock(x + 1, y + 5, z, wall);
//			world.setBlock(x + 0, y + 5, z, wall);
//			world.setBlock(x - 1, y + 5, z, wall);
//			world.setBlock(x - 2, y + 5, z, wall);
//			world.setBlock(x - 2, y + 4, z, wall);
//			world.setBlock(x - 3, y + 4, z, wall);
//			world.setBlock(x - 3, y + 3, z, wall);
//			world.setBlock(x - 3, y + 2, z, wall);
//			world.setBlock(x - 3, y + 1, z, wall);
//
//			int count = 0;
//
//			for(int i = 4; i < ((size - 1) / 2) + 2; i++) {
//				world.setBlock(x - i, y + 1, z, wall);
//				world.setBlock(x + i, y + 1, z, wall);
//
//				world.setBlock(x - i, y + 2, z, count % 2 == 0 ? fence : wall);
//				world.setBlock(x + i, y + 2, z, count % 2 == 0 ? fence : wall);
//
//				if(direction == 1) {
//					world.setBlock(x - i, y + 1, z + (size + 1), wall);
//					world.setBlock(x + i, y + 1, z + (size + 1), wall);
//
//					world.setBlock(x - i, y + 2, z + (size + 1), count % 2 == 0 ? fence : wall);
//					world.setBlock(x + i, y + 2, z + (size + 1), count % 2 == 0 ? fence : wall);
//				}
//				else if(direction == 3) {
//					world.setBlock(x - i, y + 1, z - (size + 1), wall);
//					world.setBlock(x + i, y + 1, z - (size + 1), wall);
//
//					world.setBlock(x - i, y + 2, z - (size + 1), count % 2 == 0 ? fence : wall);
//					world.setBlock(x + i, y + 2, z - (size + 1), count % 2 == 0 ? fence : wall);
//				}
//
//				count++;
//			}
//
//			if(direction == 1) {
//				for(int i = 0; i <= 3; i++) {
//					world.setBlock(x - i, y + 1, z + (size + 1), wall);
//					world.setBlock(x + i, y + 1, z + (size + 1), wall);
//
//					world.setBlock(x - i, y + 2, z + (size + 1), count % 2 == 0 ? fence : wall);
//					world.setBlock(x + i, y + 2, z + (size + 1), count % 2 == 0 ? fence : wall);
//
//					count++;
//				}
//
//				for(int i = 0; i < size; i++) {
//					world.setBlock(x - (((size - 1) / 2) + 1), y + 1, z + i + 1, wall);
//					world.setBlock(x + (((size - 1) / 2) + 1), y + 1, z + i + 1, wall);
//
//					world.setBlock(x - (((size - 1) / 2) + 1), y + 2, z + i + 1, count % 2 == 0 ? fence : wall);
//					world.setBlock(x + (((size - 1) / 2) + 1), y + 2, z + i + 1, count % 2 == 0 ? fence : wall);
//
//					count++;
//				}
//			}
//			else if(direction == 3) {
//				for(int i = 0; i <= 3; i++) {
//					world.setBlock(x - i, y + 1, z - (size + 1), wall);
//					world.setBlock(x + i, y + 1, z - (size + 1), wall);
//
//					world.setBlock(x - i, y + 2, z - (size + 1), count % 2 == 0 ? fence : wall);
//					world.setBlock(x + i, y + 2, z - (size + 1), count % 2 == 0 ? fence : wall);
//
//					count++;
//				}
//
//				for(int i = 0; i < size; i++) {
//					world.setBlock(x - (((size - 1) / 2) + 1), y + 1, z - i - 1, wall);
//					world.setBlock(x + (((size - 1) / 2) + 1), y + 1, z - i - 1, wall);
//
//					world.setBlock(x - (((size - 1) / 2) + 1), y + 2, z - i - 1, count % 2 == 0 ? fence : wall);
//					world.setBlock(x + (((size - 1) / 2) + 1), y + 2, z - i - 1, count % 2 == 0 ? fence : wall);
//
//					count++;
//				}
//			}
//		}
		return this;
	}
}
