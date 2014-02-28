package test.jav.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParser {

	public static void copyUrl(FileOutputStream fos, String url)
			throws Exception {
		InputStream is = null;
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
			Document doc = Jsoup.parse(is, "gbk", "sis001.us");

			fos.write(doc.html().getBytes("gbk"));

			// String title = doc.title();
			// Element body = doc.body();
			// Element content = body.getElementsByClass("mainzt").first();
			// Elements e = content.getElementsByTag("p");
			// StringBuffer sb = new StringBuffer();
			// for (Element ee : e) {
			// sb.append(ee.text() + "\r\n\r\n");
			// }
			// String text = sb.toString();

			// int titleIndex1 = title.indexOf('/');
			// int titleIndex2 = title.lastIndexOf('/');
			// if (titleIndex1 > 0 && titleIndex2 > 0 && titleIndex2 !=
			// titleIndex1)
			// {
			// title = title.substring(titleIndex1 + 1, titleIndex2);
			// } else {
			// throw new Exception("title : " + title);
			// }
			// Element contents = content.getElementById("content");
			// Element e1 = contents.getElementsByAttribute("src").first();
			// String src = e1.attr("src");
			// String charset = e1.attr("charset");
			// String text = handle(src, charset);

			// System.out.println(title.getBytes("UTF-8"));
			// System.out.println(text.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	private static void handleFile(String url, String desDir) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(desDir + "html.txt"));
			copyUrl(fos, url);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String desDir = "e:\\books\\";
		String url = "http://e.sis001.us/forum/thread-8894986-1-37.html";

		handleFile(url, desDir);
		// String s = new String(b, "gbk");
		// System.out.println(s);

		// handleDir(url, desDir);

		// String url = "http://www.23us.com/html/9/9362/2660200.html";
		// handleRef(url);
	}
}
