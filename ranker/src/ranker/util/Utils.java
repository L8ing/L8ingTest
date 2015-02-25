/*
 * 特别声明：本技术材料受《中华人民共和国著作权法》、《计算机软件保护条例》等法律、法规、行政
 * 规章以及有关国际条约的保护，赞同科技享有知识产权、保留一切权利并视其为技术秘密。未经本公司书
 * 面许可，任何人不得擅自（包括但不限于：以非法的方式复制、传播、展示、镜像、上载、下载）使用，
 * 不得向第三方泄露、透露、披露。否则，本公司将依法追究侵权者的法律责任。特此声明！
 *
 * Special Declaration: These technical material reserved as the technical secrets by AGREE 
 * TECHNOLOGY have been protected by the "Copyright Law" "ordinances on Protection of Computer 
 * Software" and other relevant administrative regulations and international treaties. Without 
 * the written permission of the Company, no person may use (including but not limited to the 
 * illegal copy, distribute, display, image, upload, and download) and disclose the above 
 * technical documents to any third party. Otherwise, any infringer shall afford the legal 
 * liability to the company.
 *
 * Created on 2015-2-17 上午08:32:15 by dwj
 */

package ranker.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

/**
 * <DL>
 * <DT>概要说明</DT>
 * <p>
 * <DD>详细说明</DD>
 * </DL>
 * <p>
 * 
 * <DL>
 * <DT><B>使用范例</B></DT>
 * <p>
 * <DD>使用范例说明</DD>
 * </DL>
 * <p>
 * 
 * @author leiting
 * @author Agree Tech
 * @version
 */
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
