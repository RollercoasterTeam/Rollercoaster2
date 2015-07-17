package rollercoasterteam.rollercoaster2.core.api.textures;

public class Texture implements ITexture {

    String name;

    public Texture(String name) {
        this.name = name;
    }

    @Override
    public String textureName() {
        return name;
    }
}
