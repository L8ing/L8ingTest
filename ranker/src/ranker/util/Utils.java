package ranker.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

public class Utils {

	private static final String rankFile = "rank.properties";

	private static final String propFile = "prop.properties";

	public static String getRank(File file) {
		File rankFile = getRankFile();
		Properties rankProperties = Utils.loadPropertiesFromFile(rankFile);
		return rankProperties.getProperty(file.getAbsolutePath());
	}

	public static File getRankFile() {
		return getFile(rankFile);
	}

	public static File getPropFile() {
		return getFile(propFile);
	}

	private static File getFile(String fileName) {
		File configDir = null;
		try {
			configDir = new File(FileLocator.toFileURL(
					Platform.getConfigurationLocation().getURL()).getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = null;
		if (configDir != null) {
			file = new File(configDir, fileName);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}

	public static Properties loadPropertiesFromFile(File file) {
		Properties properties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			properties.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}

	public static void writeProperties2File(Properties properties, File file) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			properties.store(fos, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
