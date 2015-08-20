package rcteam.rc2.util;

import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;
import rcteam.rc2.RC2;
import rcteam.rc2.rollercoaster.CoasterStyle;
import rcteam.rc2.rollercoaster.StyleRegistry;

import java.io.*;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CoasterPack {
	public enum Type {
		FOLDER, ZIP
	}

	private Type type;
	private String name;
	private ZipFile zipFile;

//	private HashMap<String, BlockTrack> tracks = new HashMap<>();
	private HashMap<String, CoasterStyle> styles = Maps.newHashMap();

	public CoasterPack(Type type, String name, ZipFile zipFile) {
		this.type = type;
		this.name = name;
		this.zipFile = zipFile;
		//TODO: lang files?
	}

//	public String getName() {
//		return this.name;
//	}
//
//	public Type getType() {
//		return this.type;
//	}
//
//	public String getDirectory() {
//		return null;
////		return RC2.packsDir + "\\";
////		return Reference.RELATIVE_PACKS_DIR + name + "/";
//	}
//
//	public InputStream getInputStream(String path) throws IOException {
//		switch (type) {
//			case FOLDER:
//				File file = new File(getDirectory() + path);
//				if (!file.isFile()) return null;
//				return FileUtils.openInputStream(file);
//			case ZIP:
//				if (zipFile != null) {
//					ZipEntry entry = zipFile.getEntry(path);
//					return entry == null ? null : zipFile.getInputStream(entry);
//				}
//			default: throw new IOException("Unknown pack type: " + type);
//		}
//	}
//
//	public void registerStyles() {
//		RC2.logger.info("Registering Styles");
//		CoasterStyle style = null;
//		OBJModel model = null;
//		String styleName = "";
//		for (File packs : FileUtils.getFile(this.getDirectory()).listFiles()) {
//			for (File file : FileUtils.getFile(packs.getPath() + "/coasters/").listFiles()) {
//				styleName = file.getName();
//				RC2.logger.info(styleName);
//				for (File file1 : file.listFiles()) {
//					RC2.logger.info(file1.getName());
//					if (file1.getName().equals("style.json")) {
//						try {
//							style = JsonParser.GSON.fromJson(new InputStreamReader(new FileInputStream(file1)), CoasterStyle.class);
//							style.setJsonLocation(new ResourceLocation(RC2.MODID + ":" + file1.getPath()));
//							style.setModelLocation(new ResourceLocation(RC2.MODID + ":" + file1.getPath().substring(0, file1.getPath().length() - "style.json".length()) + "model.obj"));
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					} else if (file1.getName().equals("model.obj")) {
////					    model = new OBJModel(new FileInputStream(file1), file.getPath());
////						style.setModelLocation(new ResourceLocation(RC2.MODID + ":" + file1.getPath()));
////						model = (OBJModel) OBJLoader.instance.loadModel(new ResourceLocation(RC2.MODID + ":" + file1.getPath()));
//						model = (OBJModel) OBJLoader.instance.loadModel(new ResourceLocation(RC2.MODID + ":" + file1.getPath().substring(file1.getPath().indexOf("packs"), file1.getPath().length())));
//					}
//				}
//			}
//		}
//		RC2.logger.info("Style null: " + Boolean.toString(style == null));
//		RC2.logger.info("Model null: " + Boolean.toString(model == null));
//		if (style != null) {
//			if (model != null) {
//				style.setModel(model);
//				StyleRegistry.INSTANCE.register(styleName.isEmpty() ? style.getName() : styleName, style, model);
//			} else {
//				StyleRegistry.INSTANCE.register(styleName.isEmpty() ? style.getName() : styleName, style);
//			}
//			this.styles.put(styleName.isEmpty() ? style.getName() : styleName, style);
//		}
//	}
//
//	public HashMap<String, CoasterStyle> getStyles() {
//		return this.styles;
//	}
}
