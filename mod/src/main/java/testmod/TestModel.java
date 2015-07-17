package testmod;

import rollercoasterteam.rollercoaster2.core.api.textures.model.ModelPart;
import rollercoasterteam.rollercoaster2.core.api.textures.model.RCModel;
import rollercoasterteam.rollercoaster2.core.api.textures.model.vecMath.Vecs3dCube;

public class TestModel extends RCModel{

    public TestModel() {
        cubes.add(new ModelPart(new Vecs3dCube(4, 4, 4, 12, 12, 12), "minecraft:cobblestone"));
    }
}
