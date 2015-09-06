package rcteam.rc2.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public enum HammerMode {
	ROTATE("rotate", "hammer.mode.rotate"),
	CHANGE_TYPE("change_type", "hammer.mode.change_type"),
	ADJUSTMENT("adjustment", "hammer.mode.adjustment");

	private String internalName;
	private String unlocalizedName;

	HammerMode(String internalName, String unlocalizedName) {
		this.internalName = internalName;
		this.unlocalizedName = unlocalizedName;
	}

	public String getInternalName() {
		return this.internalName;
	}

	public String getDisplayName() {
		return LanguageRegistry.instance().getStringLocalization(this.unlocalizedName);
	}

	public void onRightClick(TileEntity tileEntity, PlayerInteractEvent event) {

	}
}
