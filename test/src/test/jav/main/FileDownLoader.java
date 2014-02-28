package test.jav.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownLoader {

	public static void get(String url, String parent, String fileName)
			throws Exception {
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			URL server = new URL(url);
			System.setProperty("http.proxyType", "4");

			System.setProperty("http.proxyPort", "8087");

			System.setProperty("http.proxyHost", "127.0.0.1");

			System.setProperty("http.proxySet", "true");
			HttpURLConnection connection = (HttpURLConnection) server
					.openConnection();
			connection.connect();
			is = connection.getInputStream();
			fos = new FileOutputStream(new File(parent, fileName));
			byte[] chunk = new byte[4096];
			int length = 0;
			while ((length = is.read(chunk)) > 0) {
				fos.write(chunk, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String desDir = "e:\\books\\";
		String url = "http://e.sis001.us/forum/attachment.php?aid=2352881";
		String fileName = "download.torrent";
		get(url, desDir, fileName);
	}

}