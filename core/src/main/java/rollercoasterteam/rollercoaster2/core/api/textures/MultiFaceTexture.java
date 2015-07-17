package rollercoasterteam.rollercoaster2.core.api.textures;


public class MultiFaceTexture implements IMultiFaceTexture {

    ITexture up, down, north, south, east, west;

    public MultiFaceTexture(ITexture up, ITexture down, ITexture north, ITexture south, ITexture east, ITexture west) {
        this.up = up;
        this.down = down;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    @Override
    public ITexture getUp() {
        return up;
    }

    @Override
    public ITexture getDown() {
        return down;
    }

    @Override
    public ITexture getNorth() {
        return north;
    }

    @Override
    public ITexture getSouth() {
        return south;
    }

    @Override
    public ITexture getEast() {
        return east;
    }

    @Override
    public ITexture getWest() {
        return west;
    }
}
