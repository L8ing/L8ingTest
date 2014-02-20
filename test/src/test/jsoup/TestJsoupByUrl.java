package test.jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestJsoupByUrl {

	private static boolean first = true;

	private static final String titleRedundant = "-顶点小说";

	private static boolean isSysout = false;

	private static void handleRef(FileOutputStream fos, String url)
			throws Exception {
		Document doc = Jsoup.connect(url).get();
		String title = doc.title();
		Element body = doc.body();
		Element content = body.getElementById("a_main");
		Element contents = content.getElementById("contents");
		String linkText = contents.text();
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
	}

	@SuppressWarnings("unused")
	private static void handleFile(String url, String desDir) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(desDir + "test11.txt"));
			handleRef(fos, url);
		} finally {
			if (fos != null) {
				fos.close();
			}
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
			Element content = body.getElementById("a_main");
			Elements links = content.getElementsByTag("a");
			for (Element link : links) {
				String linkHref = link.attr("href");
				String linkText = link.text();
				int index1 = linkText.indexOf("第");
				int index2 = linkText.indexOf("章");
				if (index1 >= 0 && index2 > 0 && index1 < index2) {
					if (isSysout) {
						System.out.println("linkHref:" + linkHref
								+ " ; linkText:" + linkText);
					}
					String refUrl = url + linkHref;
					if (isSysout) {
						System.out.println(refUrl);
					}
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
		String desDir = "e:\\";

		String url = "http://www.23us.com/html/9/9362/";
		handleDir(url, desDir);

		// String url = "http://www.23us.com/html/9/9362/2660200.html";
		// handleFile(url, desDir);
	}
}
