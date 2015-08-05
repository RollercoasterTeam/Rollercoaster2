package rcteam.rc2.block;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import rcteam.rc2.RC2;
import rcteam.rc2.rollercoaster.CoasterStyle;

public class BlockTrack extends Block {
	private CoasterStyle style;
	private String pieceName;

	public BlockTrack(CoasterStyle style, String pieceName) {
		super(style.getBlockMaterial());
		this.setBlockUnbreakable();
		this.style = style;
		this.pieceName = pieceName;
		this.setBlockName(style.getName() + "." + this.pieceName);
		this.setCreativeTab(RC2.tab);
	}
}
