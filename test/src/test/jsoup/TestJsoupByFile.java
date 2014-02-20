package test.jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestJsoupByFile {

	private static boolean first = true;

	private static boolean isSysout = false;

	private static void handleRef(FileOutputStream fos, String file)
			throws Exception {
		Document doc = Jsoup.parse(new File(file), "GBK");
		String title = doc.title();
		Element body = doc.body();
		Element contents = body.getElementById("content");
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

		fos.write(title.getBytes("GBK"));
		fos.write(linkText.getBytes("GBK"));
	}

	@SuppressWarnings("unused")
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

	private static void handleDir(String file, String desDir)
			throws IOException {
		FileOutputStream fos = null;
		try {
			Document doc = Jsoup.parse(new File(file), "GBK");
			String title = doc.title();
			fos = new FileOutputStream(new File(desDir + title + ".txt"));
			Element body = doc.body();
			Elements tables = body.getElementsByClass("acss");
			Element table = tables.first();
			Elements links = table.getElementsByTag("a");
			for (Element link : links) {
				String linkHref = link.attr("href");
				String linkText = link.text();
				if (isSysout) {
					System.out.println("linkHref:" + linkHref + " ; linkText:"
							+ linkText);
				}
				int endIndex = file.lastIndexOf('\\');
				String refUrl = file.substring(0, endIndex + 1);
				refUrl += linkHref;
				if (isSysout) {
					System.out.println(refUrl);
				}
				handleRef(fos, refUrl);
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

		String url = "D:\\Files\\Entertainment\\Books\\qwerty\\新建文件夹 (2)\\HMWX\\index.html";
		handleDir(url, desDir);

		// String url = "http://www.23us.com/html/9/9362/2660200.html";
		// handleFile(url, desDir);
	}
}
