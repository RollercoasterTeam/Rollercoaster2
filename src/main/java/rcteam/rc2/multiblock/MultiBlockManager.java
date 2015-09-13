package rcteam.rc2.multiblock;

import com.google.common.collect.Maps;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.rollercoaster.CategoryEnum;

import java.util.List;
import java.util.Map;

public class MultiBlockManager {
	public static final MultiBlockManager instance = new MultiBlockManager();
	public static Map<BlockPos, MultiBlockStructure> structureMap = Maps.newHashMap();
	public static Map<String, MultiBlockTemplate> templateMap = Maps.newHashMap();
//	public static BiMap<MultiBlockTemplate, MultiBlockStructure> registry = HashBiMap.create();
	private static boolean hasRegistered = false;

	public void registerTemplate(MultiBlockTemplate template) {
		if (!templateMap.containsKey(template.getName())) {
			templateMap.put(template.getName(), template);
		}
	}

	public void registerTrackTemplates() {
		if (!hasRegistered) {
			hasRegistered = true;
			MultiBlockTemplate straight = new MultiBlockTemplate("straight");
			MultiBlockTemplate small_corner = new MultiBlockTemplate("small_corner");
			MultiBlockTemplate medium_corner = new MultiBlockTemplate("medium_corner");
			MultiBlockTemplate large_corner_left = new MultiBlockTemplate("large_corner_left");
			MultiBlockTemplate large_corner_right = new MultiBlockTemplate("large_corner_right");
			straight.addLayer(RC2Blocks.trackMap.get(CategoryEnum.STEEL), "T", 'T', RC2Blocks.trackMap.get(CategoryEnum.STEEL));
			small_corner.addLayer(RC2Blocks.trackMap.get(CategoryEnum.STEEL), "DD", "TD", 'D', RC2Blocks.dummy, 'T', RC2Blocks.trackMap.get(CategoryEnum.STEEL));
			medium_corner.addLayer(RC2Blocks.trackMap.get(CategoryEnum.STEEL), " DD", "DD ", "T  ", 'D', RC2Blocks.dummy, 'T', RC2Blocks.trackMap.get(CategoryEnum.STEEL));
			large_corner_left.addLayer(RC2Blocks.trackMap.get(CategoryEnum.STEEL), "D ", "DD", " T", 'D', RC2Blocks.dummy, 'T', RC2Blocks.trackMap.get(CategoryEnum.STEEL));
			large_corner_right.addLayer(RC2Blocks.trackMap.get(CategoryEnum.STEEL), " D", "DD", "T ", 'D', RC2Blocks.dummy, 'T', RC2Blocks.trackMap.get(CategoryEnum.STEEL));
			this.registerTemplate(straight);
			this.registerTemplate(small_corner);
			this.registerTemplate(medium_corner);
			this.registerTemplate(large_corner_left);
			this.registerTemplate(large_corner_right);
//			MultiBlockTemplate test_template = new MultiBlockTemplate("test");
//			test_template.addLayer(Blocks.furnace, "DFD", "GEG", "DGD", 'F', Blocks.furnace, 'D', Blocks.diamond_block, 'E', Blocks.emerald_block, 'G', Blocks.gold_block);
//			test_template.addLayer(null, "GEG", "E E", "GEG", 'E', Blocks.emerald_block, 'G', Blocks.gold_block);
//			test_template.addLayer(null, "DGD", "GEG", "DGD", 'D', Blocks.diamond_block, 'E', Blocks.emerald_block, 'G', Blocks.gold_block);
//			this.registerTemplate(test_template);
		}
	}

	public void destroyStructures(World world, List<BlockPos> masterPositions) {
		for (BlockPos masterPos : masterPositions) {
			MultiBlockStructure structure = structureMap.get(masterPos);
			structure.destroyStructure(world, masterPos);
		}
	}
}
