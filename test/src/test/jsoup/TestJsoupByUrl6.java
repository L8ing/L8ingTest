package test.jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import test.jav.util.DirParser;

/**
 * @author Administrator 日译
 */
public class TestJsoupByUrl6 {

	private static boolean first = true;

	private static boolean isSysout = true;

	private static void handleRef(String desDir, String url) throws IOException {

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

			Document doc = Jsoup.parse(is, "big5", "http://www.xbookcn.com");
			String title = doc.title();
			Element body = doc.body();
			// Elements content = body.getElementsByTag("p");
			// for(Element e : content){
			// }
			// Elements center = body.getElementsByTag("center");
			// Elements contents = body.getElementsByTag("br");
			String linkText = body.text();
			// char c = linkText.charAt(0);
			// char[] cs = new char[4];
			// Arrays.fill(cs, c);
			// String s = new String(cs);
			linkText = linkText.replaceAll("\\s", "\r\n\r\n");

			fos = new FileOutputStream(new File(desDir + title + ".txt"));
			if (first) {
				first = false;
			} else {
				fos.write(("\r\n\r\n-------------------------------\r\n\r\n")
						.getBytes("GBK"));
			}

			// title = title.replaceAll(titleRedundant, "");
			fos.write(url.getBytes("GBK"));
			fos.write(title.getBytes("GBK"));
			fos.write(linkText.getBytes("GBK"));
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

	private static void handleDir(String url, String desDir) throws IOException {
		InputStream is = null;
		Document doc = null;
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

			doc = Jsoup.parse(is, "big5", "http://www.xbookcn.com");

		} finally {
			if (is != null) {
				is.close();
			}
		}
		// String title = doc.title();
		// title = title.replaceAll(titleRedundant, "");
		if (doc == null) {
			return;
		}
		Element body = doc.body();
		Elements links = body.getElementsByTag("a");
		int index = url.lastIndexOf('/');
		String parent = url.substring(0, index);

		for (Element link : links) {
			String linkHref = link.attr("href");
			String linkText = link.text();
			if (linkHref.endsWith("htm")) {
				if (isSysout) {
					System.out.println("linkHref:" + linkHref + " ; linkText:"
							+ linkText);
				}
				String refUrl = parent + "/" + linkHref;
				if (isSysout) {
					System.out.println(linkText);
				}
				handleRef(desDir, refUrl);
			}
		}

	}

	@SuppressWarnings("unused")
	private static void index() throws IOException {
		String content = DirParser.readFileToString(new File(
				"E:\\books\\jp\\index"), "UTF-8");
		String[] names = content.split("\\s");
		Set<String> set = new HashSet<String>();
		for (String name : names) {
			if (name.length() > 0) {
				set.add(name);
			}
		}

		File f = new File("E:\\books\\jp\\");
		File[] ff = f.listFiles();
		for (File file : ff) {
			String name = file.getName();
			int dotIndex = name.indexOf('.');
			if (dotIndex >= 0) {
				name = name.substring(0, dotIndex);
				if (!set.contains(name)) {
					System.out.println(name);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String desDir = "e:\\books\\jp\\";

		String url = "http://www.xbookcn.com/class/riyi.htm";
		handleDir(url, desDir);

		// index();
	}
}
