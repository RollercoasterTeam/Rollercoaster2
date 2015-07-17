package testmod;

import rollercoasterteam.rollercoaster2.core.api.textures.model.ModelPart;
import rollercoasterteam.rollercoaster2.core.api.textures.model.RCModel;
import rollercoasterteam.rollercoaster2.core.api.textures.model.vecMath.Vecs3dCube;

public class TestModel extends RCModel{

    public TestModel() {
        cubes.add(new ModelPart(new Vecs3dCube(0, 0, 0, 1, 1, 1), "minecraft:cobblestone"));
    }
}
