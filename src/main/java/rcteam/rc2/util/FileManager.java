package rcteam.rc2.util;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraftforge.fml.client.FMLClientHandler;
import rcteam.rc2.RC2;
import rcteam.rc2.util.CoasterPack;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipFile;

public class FileManager implements IResourceManagerReloadListener {
	private static Map<String, CoasterPack> packRegistry = new HashMap<>();

	public static CoasterPack readPack(File file) {
		CoasterPack.Type type;
		String name;
		ZipFile zipFile = null;

		if (file.isDirectory()) {
			name = file.getName();
			type = CoasterPack.Type.FOLDER;
		} else if (file.getName().endsWith(".zip")) {
			name = file.getName().substring(0, file.getName().length() - 4);
			type = CoasterPack.Type.ZIP;
			try {
				zipFile = new ZipFile(file);
			} catch (IOException e) {
				RC2.logger.error("Could not read zip file {} :\n", file.getName(), e);
			}
		} else {
			RC2.logger.error("Skipping {}, not a valid coaster pack file.", file.getName());
			return null;
		}
		CoasterPack pack = new CoasterPack(type, name, zipFile);
		packRegistry.put(name, pack);
		return pack;
	}

	public static Map<String, CoasterPack> getPackRegistry() {
		return packRegistry;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		//TODO!
//		FMLClientHandler.instance().getResourcePackFor(RC2.MODID).
	}
}
