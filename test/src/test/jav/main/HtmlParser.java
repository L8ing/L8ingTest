package test.jav.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {

	public static final String VIDEO_NAME = "影片名稱";
	public static final String ACTRESS_NAME = "出演女優";
	public static final String FORMAT = "影片格式";
	public static final String SIZE = "影片大小";
	public static final String TIME = "影片時間";
	public static final String UNCENSORED = "是否有碼";

	public static void copyUrl(FileOutputStream fos, String url)
			throws Exception {
		InputStream is = null;
		try {
			URL server = new URL(url);
			// System.setProperty("http.proxyType", "4");
			//
			// System.setProperty("http.proxyPort", "8087");
			//
			// System.setProperty("http.proxyHost", "127.0.0.1");
			//
			// System.setProperty("http.proxySet", "true");

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

	public static void fromFile(String url) throws IOException {
		Document doc = Jsoup.parse(new File("E:\\books\\html.html"), "gbk");
		Elements elements = doc.getElementsMatchingOwnText(".torrent");

		Elements videoName = doc.getElementsMatchingOwnText(VIDEO_NAME);
		String s = videoName.first().text();
		
		int index = url.lastIndexOf('/');
		String parent = url.substring(0, index);
		for (Element e : elements) {
			String href = e.attr("href");
			String realRef = parent + '/' + href;
			System.out.println(realRef);
		}
	}

	public static void main(String[] args) throws Exception {
		String desDir = "e:\\books\\";
		String url = "http://sis001.us/bbs/thread-8968372-1-1.html";

		// handleFile(url, desDir);

		fromFile(url);
	}
}
