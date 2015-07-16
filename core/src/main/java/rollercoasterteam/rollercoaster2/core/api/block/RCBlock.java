package rollercoasterteam.rollercoaster2.core.api.block;


import rollercoasterteam.rollercoaster2.core.BlockPosition;
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

    public String getTexture() {
        return texture;
    }
}
