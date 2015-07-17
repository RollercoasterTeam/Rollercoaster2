package rollercoasterteam.rollercoaster2.core.api.textures.model;


import rollercoasterteam.rollercoaster2.core.api.textures.model.vecMath.Vecs3dCube;

public class ModelPart {

    Vecs3dCube cube;

    String texture;

    public ModelPart(Vecs3dCube cube, String texture) {
        this.cube = cube;
        this.texture = texture;
    }

    public Vecs3dCube getCube() {
        return cube;
    }

    public String getTexture() {
        return texture;
    }
}
