package rcteam.rc2.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.stream.JsonReader;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.apache.commons.io.FileUtils;
import rcteam.rc2.RC2;
import rcteam.rc2.rollercoaster.CategoryEnum;
import rcteam.rc2.rollercoaster.TrackPieceInfo;
import rcteam.rc2.util.CoasterPack;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;

public class FileManager implements IResourceManagerReloadListener {
	private static Map<String, CoasterPack> packRegistry = Maps.newHashMap();

//	public static void makeInfoDirs() {
//		for (int i = 0; i < CategoryEnum.values().length; i++) {
//			File file = new File(TRACKS_DIR + CategoryEnum.values()[i].getName());
//			file.mkdirs();
//		}
//	}

	public static void readInfoFiles() throws FileNotFoundException {
		for (int i = 0; i < CategoryEnum.values().length; i++) {
			File file = new File(Reference.TRACKS_DIR + CategoryEnum.values()[i].getName());
			if (file.listFiles() == null || file.listFiles().length == 0) continue;
			List<File> styles = Lists.newArrayList(file.listFiles());
			for (File style : styles) {
				InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(style));
				TrackPieceInfo info = JsonParser.GSON.fromJson(inputStreamReader, TrackPieceInfo.class);
				info.setCurrentStyle(info.getStyleNames().get(0));
				CategoryEnum.values()[i].setInfo(info);
			}
			CategoryEnum.values()[i].setValidPieces();
		}
	}

	public static void copyDefaultsFromJar(Class<?> jarClass, File to) {
		RC2.logger.info("Copying default packs from jar");
		File defFolder = new File(jarClass.getResource("/assets/" + RC2.MODID.toLowerCase() + "/defaults").getFile());

		try {
			FileUtils.copyDirectory(defFolder, to);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

//	@Nonnull
//	public File extractZip(File zip) {
//		String zipPath = zip.getParent() + "";
//	}

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
//		FMLClientHandler.instance().getResourcePackFor(RC2.MODID)
	}
}
