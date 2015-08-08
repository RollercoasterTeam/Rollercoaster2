package rollercoasterteam.rollercoaster2.forge;

import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import rollercoasterteam.rollercoaster2.core.api.block.RCBlock;
import rollercoasterteam.rollercoaster2.core.api.tile.RCTile;

public class TileConverter extends TileEntity implements IUpdatePlayerListBox {

	RCTile rcTile;

	public TileConverter(RCTile rcTile, RCBlock block) {
		this.rcTile = rcTile;
		if(rcTile == null){
			rcTile = new RCTile(block);
		}
		rcTile.setWorld(Rollercoaster2Forge.handler.getWorld(worldObj));
	}

	@Override
	public void update() {
		rcTile.tick();
	}

	public RCTile getRcTile() {
		return rcTile;
	}

	public void setRcTile(RCTile rcTile) {
		this.rcTile = rcTile;
	}
}
