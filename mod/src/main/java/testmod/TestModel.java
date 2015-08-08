package testmod;

import rollercoasterteam.rollercoaster2.core.api.block.RCMeta;
import rollercoasterteam.rollercoaster2.core.api.textures.model.ModelPart;
import rollercoasterteam.rollercoaster2.core.api.textures.model.RCModel;
import rollercoasterteam.rollercoaster2.core.api.textures.model.vecMath.Vecs3dCube;

public class TestModel extends RCModel{

    public TestModel(RCMeta meta) {
		if(meta.getMeta() == 0){
			cubes.add(new ModelPart(new Vecs3dCube(4, 4, 4, 12, 12, 12), "minecraft:cobblestone"));
		} else {
			cubes.add(new ModelPart(new Vecs3dCube(4, 4, 4, 12, 12, 12), "minecraft:glowstone"));
		}


    }
}
