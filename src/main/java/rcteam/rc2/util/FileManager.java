package rcteam.rc2.util;

import rcteam.rc2.RC2;

import java.io.*;
import java.util.zip.ZipFile;

public class FileManager {
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

		return new CoasterPack(type, name, zipFile);
	}
}
