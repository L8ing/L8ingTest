package test.jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 * @author Administrator 新宋
 */
public class TestJsoupByUrl7 {

	private static boolean first = true;

	private static final String titleRedundant = "";

	private static void handleRef(FileOutputStream fos, String url)
			throws Exception {
		try {
			Connection connection = Jsoup.connect(url);
			connection.timeout(120000);
			Document doc = connection.get();
			String title = doc.title();
			Element body = doc.body();
			Element content = body.getElementById("content");
			Element toplink = content.getElementById("toplink");
			Element titlebottom = content.getElementById("titlebottom");
			toplink.remove();
			titlebottom.remove();
			// Element contents = content.getElementById("content");
			String linkText = content.text();
			char c = linkText.charAt(0);
			char[] cs = new char[4];
			Arrays.fill(cs, c);
			String s = new String(cs);
			linkText = linkText.replaceAll(s, "\r\n\r\n");
			if (first) {
				first = false;
			} else {
				fos.write(("\r\n\r\n-------------------------------\r\n\r\n")
						.getBytes("GBK"));
			}

			title = title.replaceAll(titleRedundant, "");
			fos.write(title.getBytes("GBK"));
			fos.write(linkText.getBytes("GBK"));
		} catch (Exception e) {
			System.err.println(url);
			throw e;
		}
	}

	private static void handleDir(String url, String desDir) throws IOException {
		FileOutputStream fos = null;
		try {
			Document doc = Jsoup.connect(url).get();
			String title = doc.title();
			title = title.replaceAll(titleRedundant, "");
			fos = new FileOutputStream(new File(desDir + title + ".txt"));
			Element body = doc.body();
			Elements content = body.getElementsByClass("acss");
			Elements links = content.get(0).getElementsByClass("ccss");

			int index = url.lastIndexOf('/');
			url = url.substring(0, index + 1);
			for (Element link : links) {
				List<Node> children = link.childNodes();
				String linkHref = "";
//				String linkText = link.text();
				for (Node child : children) {
					String href = child.attr("href");
					if (href != null && href.length() > 0) {
						linkHref = href;
						break;
					}
				}
				if (linkHref != null && linkHref.length() > 0) {
					String refUrl = url + linkHref;
					// int index1 = linkText.indexOf("第");
					// int index2 = linkText.indexOf("章");
					// if (index1 >= 0 && index2 > 0 && index1 < index2) {
					// if (isSysout) {
					// System.out.println("linkHref:" + linkHref
					// + " ; linkText:" + linkText);
					// }
					// String refUrl = url + linkHref;
					// if (isSysout) {
					// System.out.println(refUrl);
					// }
					handleRef(fos, refUrl);
				}
			}
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

		String url = "http://www.5ccc.net/xiaoshuo/6/6968/index.html";
		handleDir(url, desDir);

		// String url = "http://www.23us.com/html/9/9362/2660200.html";
		// handleFile(url, desDir);
	}
}
