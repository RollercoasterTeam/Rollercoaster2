package rollercoasterteam.rollercoaster2.core.api.block;


import rollercoasterteam.rollercoaster2.core.BlockPosition;
import rollercoasterteam.rollercoaster2.core.api.textures.IMultiFaceTexture;
import rollercoasterteam.rollercoaster2.core.api.textures.MultiFaceTexture;
import rollercoasterteam.rollercoaster2.core.api.textures.Texture;
import rollercoasterteam.rollercoaster2.core.api.tile.RCTile;
import rollercoasterteam.rollercoaster2.core.api.world.RCWorld;

public class RCBlock {

    String name;

    String texture;

    public RCBlock(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean onActivated(RCWorld world, BlockPosition position){
        return false;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    @Deprecated
    public String getTexture() {
        return texture;
    }

    public IMultiFaceTexture getTexturesWithFaces(){
        return new MultiFaceTexture(new Texture(texture),new Texture(texture),new Texture(texture),new Texture(texture),new Texture(texture),new Texture(texture));
    }

	public RCTile getTile(){
		return null;
	}

}
