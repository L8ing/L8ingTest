package test.jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestJsoupByUrl3 {

	private static boolean first = true;

	private static final String titleRedundant = "-顶点小说";

	private static boolean isSysout = false;

	private static void handleRef(FileOutputStream fos, String url)
			throws Exception {
		Document doc = Jsoup.connect(url).get();
		String title = doc.title();
		Element body = doc.body();
		Element content = body.getElementById("maincontent");
		int titleIndex1 = title.indexOf('/');
		int titleIndex2 = title.lastIndexOf('/');
		if (titleIndex1 > 0 && titleIndex2 > 0 && titleIndex2 != titleIndex1) {
			title = title.substring(titleIndex1 + 1, titleIndex2);
		} else {
			throw new Exception("title : " + title);
		}
		Element contents = content.getElementById("content");
		Element e1 = contents.getElementsByAttribute("src").first();
		String src = e1.attr("src");
		String charset = e1.attr("charset");
		// String linkText = "";
		// char c = linkText.charAt(0);
		// char[] cs = new char[4];
		// Arrays.fill(cs, c);
		// String s = new String(cs);
		// linkText = linkText.replaceAll(s, "\r\n\r\n");
		String text = handle(src, charset);
		if (first) {
			first = false;
		} else {
			fos.write(("\r\n\r\n-------------------------------\r\n\r\n")
					.getBytes("UTF-8"));
		}

		title = title.replaceAll(titleRedundant, "");
		fos.write(title.getBytes("UTF-8"));
		fos.write(text.getBytes("UTF-8"));
	}

	private static String handle(String url, String charset) throws Exception {
		// Document doc = Jsoup.parse(url);
		// Document doc = Jsoup.connect(url).get();
		Document doc = Jsoup.parse(new URL(url).openStream(), charset, url);
		// String title = doc.title();
		Element body = doc.body();
		Elements linkHref = doc.getElementsByAttribute("href");
		linkHref.remove();
		// Node Node = doc.removeAttr("href");
		String content = body.text();
		String s1 = "document.write('";
		String s2 = "');";
		if (content.startsWith(s1)) {
			content = content.substring(s1.length());
		}
		if (content.endsWith(s2)) {
			content = content.substring(0, content.length() - s2.length());
		}
		// Elements content = body.getElementsByTag("p");
		// Element contents = content.getElementById("content");
		// Element e1 = content.getElementsByAttribute("src").first();
		// String src = e1.attr("src");;
		// String charset = e1.attr("charset");
		// String linkText =content;
		char c = content.charAt(0);
		char[] cs = new char[2];
		Arrays.fill(cs, c);
		String s = new String(cs);
		content = content.replaceAll(s, "\r\n\r\n");

		// title = title.replaceAll(titleRedundant, "");
		return content;
	}

	private static void handleFile(String url, String desDir) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(desDir + "test11.txt"));
			handleRef(fos, url);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	@SuppressWarnings("unused")
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
		String desDir = "e:\\books\\";

		String url = "http://read.qidian.com/BookReader/182451,4656097.aspx";
		// handleDir(url, desDir);

		// String url = "http://www.23us.com/html/9/9362/2660200.html";
		handleFile(url, desDir);
	}
}
