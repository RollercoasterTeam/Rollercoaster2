package rcteam.rc2.multiblock;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import rcteam.rc2.block.RC2Blocks;
import rcteam.rc2.rollercoaster.CategoryEnum;
import rcteam.rc2.rollercoaster.TrackPiece;

import java.util.Arrays;
import java.util.Map;

public class MultiBlockManager {
	public static final MultiBlockManager instance = new MultiBlockManager();
	public static Map<TrackPiece, MultiBlockStructure> trackMap = Maps.newEnumMap(TrackPiece.class);
	public static Map<BlockPos, MultiBlockStructure> structureMap = Maps.newHashMap();
	public static Map<String, MultiBlockTemplate> templateMap = Maps.newHashMap();
	public static BiMap<MultiBlockTemplate, MultiBlockStructure> registry = HashBiMap.create();
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
			large_corner_left.addLayer(RC2Blocks.trackMap.get(CategoryEnum.STEEL), "DD", "DT", 'D', RC2Blocks.dummy, 'T', RC2Blocks.trackMap.get(CategoryEnum.STEEL));
			large_corner_right.addLayer(RC2Blocks.trackMap.get(CategoryEnum.STEEL), "DD", "TD", 'D', RC2Blocks.dummy, 'T', RC2Blocks.trackMap.get(CategoryEnum.STEEL));
//			for (CategoryEnum categoryEnum : CategoryEnum.values()) {
//				straight.addLayer(RC2Blocks.trackMap.get(categoryEnum), "T", 'T', RC2Blocks.trackMap.get(categoryEnum));
//				small_corner.addLayer(RC2Blocks.trackMap.get(categoryEnum), "DD", "TD", 'D', RC2Blocks.dummy, 'T', RC2Blocks.trackMap.get(categoryEnum));
//				medium_corner.addLayer(RC2Blocks.trackMap.get(categoryEnum), "DDD", "DDD", "TDD", 'D', RC2Blocks.dummy, 'T', RC2Blocks.trackMap.get(categoryEnum));
//				large_corner_left.addLayer(RC2Blocks.trackMap.get(categoryEnum), "DD", "DT", 'D', RC2Blocks.dummy, 'T', RC2Blocks.trackMap.get(categoryEnum));
//				large_corner_right.addLayer(RC2Blocks.trackMap.get(categoryEnum), "DD", "TD", 'D', RC2Blocks.dummy, 'T', RC2Blocks.trackMap.get(categoryEnum));
//			}
			this.registerTemplate(straight);
			this.registerTemplate(small_corner);
			this.registerTemplate(medium_corner);
			this.registerTemplate(large_corner_left);
			this.registerTemplate(large_corner_right);

			MultiBlockTemplate test_template = new MultiBlockTemplate("test");
			test_template.addLayer(Blocks.furnace, "DFD", "GEG", "DGD", 'F', Blocks.furnace, 'D', Blocks.diamond_block, 'E', Blocks.emerald_block, 'G', Blocks.gold_block);
			test_template.addLayer(null, "GEG", "E E", "GEG", 'E', Blocks.emerald_block, 'G', Blocks.gold_block);
			test_template.addLayer(null, "DGD", "GEG", "DGD", 'D', Blocks.diamond_block, 'E', Blocks.emerald_block, 'G', Blocks.gold_block);
			this.registerTemplate(test_template);
		}
	}
}
