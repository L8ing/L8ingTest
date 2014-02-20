package test.web;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CopyURL {

	public static byte[] copyUrl(String url) throws Exception {
		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			URL server = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) server
					.openConnection();
			connection.connect();
			is = connection.getInputStream();
			byte[] chunk = new byte[4096];
			int length = 0;
			while ((length = is.read(chunk)) > 0) {
				baos.write(chunk, 0, length);
			}
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return baos.toByteArray();
	}

	@SuppressWarnings("unused")
	private static String copy(String url) throws Exception {
		String s = "";
		StringBuffer sb = new StringBuffer();
		URL server = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) server
				.openConnection();
		connection.connect();
		InputStream is = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"GBK"));
		while ((s = reader.readLine()) != null) {
			sb.append(s + "\r\n");
		}
		is.close();
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		String url = "http://www.23us.com/html/9/9362/2660200.html";
		// String url = "http://www.23us.com/html/9/9362/";

		byte[] b = copyUrl(url);
		String s = new String(b, "gbk");
		System.out.println(s);

		// copyUrl1(url);

		// String s = copy(url);
		// System.out.println(s);
	}

}