package rcteam.rc2.rollercoaster;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ThemePark {

	private static final String DEFAULT_NAME = "New Theme Park";
	
	private static final int DEFAULT_XSIZE = 10;
	private static final int DEFAULT_ZSIZE = 10;
	
	private static final int DEFAULT_BUILD_HEIGHT_LIMIT = -1;
	
	public String name;
	
	public int xSize;
	public int zSize;
	
	public int buildHeightLimit;
	
	public int direction;
	
	/**
	 * @param direction The direction that the park is facing
	 * @param name The name of the theme park 
	 * @param xSize The x size of the theme park
	 * @param zSize The z size of the theme park
	 * @param buildHeightLimit The maximum construction height that the park can build to
	 */
	public ThemePark(int direction, String name, int xSize, int zSize, int buildHeightLimit) {
		this.name = name;
		
		this.xSize = xSize;
		this.zSize = zSize;
		
		this.buildHeightLimit = buildHeightLimit;
		
		this.direction = direction;
	}
	
	public ThemePark() {
		this(0, DEFAULT_NAME, DEFAULT_XSIZE, DEFAULT_ZSIZE, DEFAULT_BUILD_HEIGHT_LIMIT);
	}
	
	public ThemePark(int direction, String name) {
		this(direction, name, DEFAULT_XSIZE, DEFAULT_ZSIZE, DEFAULT_BUILD_HEIGHT_LIMIT);
	}
	
	public ThemePark(int direction, String name, int xSize, int zSize) {
		this(direction, name, xSize, zSize, DEFAULT_BUILD_HEIGHT_LIMIT);
	}

	public ThemePark place(World world, int x, int y, int z) {
		if(direction == 0 || direction == 2) {
			world.setBlock(x, y + 1, z + 3, Blocks.stone);
			world.setBlock(x, y + 2, z + 3, Blocks.stone);
			world.setBlock(x, y + 3, z + 3, Blocks.stone);
			world.setBlock(x, y + 4, z + 3, Blocks.stone);
			world.setBlock(x, y + 4, z + 2, Blocks.stone);
			world.setBlock(x, y + 5, z + 2, Blocks.stone);
			world.setBlock(x, y + 5, z + 1, Blocks.stone);
			world.setBlock(x, y + 5, z + 0, Blocks.stone);
			world.setBlock(x, y + 5, z - 1, Blocks.stone);
			world.setBlock(x, y + 5, z - 2, Blocks.stone);
			world.setBlock(x, y + 4, z - 2, Blocks.stone);
			world.setBlock(x, y + 4, z - 3, Blocks.stone);
			world.setBlock(x, y + 3, z - 3, Blocks.stone);
			world.setBlock(x, y + 2, z - 3, Blocks.stone);
			world.setBlock(x, y + 1, z - 3, Blocks.stone);
		}
		else if(direction == 1 || direction == 3) {
			world.setBlock(x + 3, y + 1, z, Blocks.stone);
			world.setBlock(x + 3, y + 2, z, Blocks.stone);
			world.setBlock(x + 3, y + 3, z, Blocks.stone);
			world.setBlock(x + 3, y + 4, z, Blocks.stone);
			world.setBlock(x + 2, y + 4, z, Blocks.stone);
			world.setBlock(x + 2, y + 5, z, Blocks.stone);
			world.setBlock(x + 1, y + 5, z, Blocks.stone);
			world.setBlock(x + 0, y + 5, z, Blocks.stone);
			world.setBlock(x - 1, y + 5, z, Blocks.stone);
			world.setBlock(x - 2, y + 5, z, Blocks.stone);
			world.setBlock(x - 2, y + 4, z, Blocks.stone);
			world.setBlock(x - 3, y + 4, z, Blocks.stone);
			world.setBlock(x - 3, y + 3, z, Blocks.stone);
			world.setBlock(x - 3, y + 2, z, Blocks.stone);
			world.setBlock(x - 3, y + 1, z, Blocks.stone);
		}
		return this;
	}
}
