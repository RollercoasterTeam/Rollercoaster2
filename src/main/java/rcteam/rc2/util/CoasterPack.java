package rcteam.rc2.util;

import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import org.apache.commons.io.FileUtils;
import rcteam.rc2.RC2;
import rcteam.rc2.block.BlockTrack;
import rcteam.rc2.item.ItemTrack;
import rcteam.rc2.rollercoaster.CoasterStyle;
import rcteam.rc2.rollercoaster.StyleRegistry;

import javax.swing.text.Style;
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

	private HashMap<String, BlockTrack> tracks = new HashMap<>();

	public CoasterPack(Type type, String name, ZipFile zipFile) {
		this.type = type;
		this.name = name;
		this.zipFile = zipFile;
		//TODO: lang files?
	}

	public String getName() {
		return this.name;
	}

	public Type getType() {
		return this.type;
	}

	public String getDirectory() {
		return "./rollercoaster2/packs/" + name + "/";
	}

	public InputStream getInputStream(String path) throws IOException {
		switch (type) {
			case FOLDER:
				File file = new File(getDirectory() + path);
				if (!file.isFile()) return null;
				return FileUtils.openInputStream(file);
			case ZIP:
				if (zipFile != null) {
					ZipEntry entry = zipFile.getEntry(path);
					return entry == null ? null : zipFile.getInputStream(entry);
				}
			default: throw new IOException("Unknown pack type: " + type);
		}
	}

	public void registerStyles() {
		RC2.logger.info("Registering Styles");
		CoasterStyle style = null;
		OBJModel model = null;
		String styleName = "";
		for (File file : FileUtils.getFile(this.getDirectory() + "coasters/").listFiles()) {
			styleName = file.getName();
			RC2.logger.info(styleName);
			for (File file1 : file.listFiles()) {
				RC2.logger.info(file1.getName());
				if (file1.getName().equals("style.json")) {
					try {
						style = JsonParser.GSON.fromJson(new InputStreamReader(new FileInputStream(file1)), CoasterStyle.class);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (file1.getName().equals("model.obj")) {
					try {
						model = new OBJModel(new FileInputStream(file1), file.getName());
					} catch (FileNotFoundException e) {
						RC2.logger.info("FAILED TO LOAD MODEL");
					}
				}
			}
		}
		RC2.logger.info("Style null: " + Boolean.toString(style == null));
		RC2.logger.info("Model null: " + Boolean.toString(model == null));
		if (style != null) {
			if (model != null) {
				StyleRegistry.INSTANCE.register(styleName.isEmpty() ? style.getName() : styleName, style, model);
			} else {
				StyleRegistry.INSTANCE.register(styleName.isEmpty() ? style.getName() : styleName, style);
			}
		}
	}
}
