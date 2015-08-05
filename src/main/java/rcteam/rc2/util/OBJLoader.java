package rcteam.rc2.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.IModelCustomLoader;
import net.minecraftforge.client.model.ModelFormatException;
import rcteam.rc2.RC2;

public class OBJLoader implements IModelCustomLoader {
	public static final OBJLoader instance = new OBJLoader();

	private OBJLoader() {}

	@Override
	public String getType() {
		return "Shade OBJ Model";
	}

	@Override
	public String[] getSuffixes() {
		return new String[] {"obj"};
	}

	@Override
	public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException {
		RC2.logger.info(resource.toString());
//		return new OBJModel(resource);
//		return new OBJModel()
		return null;
	}
}
