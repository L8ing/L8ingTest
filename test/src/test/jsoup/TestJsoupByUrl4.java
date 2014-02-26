package test.jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Administrator 明朝那些事儿
 */
public class TestJsoupByUrl4 {

	private static boolean first = true;

	private static final String titleRedundant = null;

	private static boolean isSysout = false;

	private static void handleRef(FileOutputStream fos, String url)
			throws Exception {
		Document doc = null;
		try {
			Connection connection = Jsoup.connect(url);
			connection.timeout(120000);
			doc = connection.get();
		} catch (SocketTimeoutException e) {
			System.err.println(url);
			return;
		}
		String title = doc.title();
		Element body = doc.body();
		Element content = body.getElementsByClass("main").first();
		Element contents = content.getElementsByClass("content").first();
		Elements children = contents.children();
		Element commentlist = contents.getElementsByClass("commentlist")
				.first();
		int index = children.indexOf(commentlist);
		for (int i = 0; i < children.size(); i++) {
			if (i >= index) {
				Element child = children.get(i);
				child.remove();
			}
		}

		Elements p = contents.getElementsByTag("p");
		if (p.size() >= 1) {
			p.remove(p.size() - 1);
		}
		StringBuffer sb = new StringBuffer();
		for (Element e : p) {
			sb.append(e.text() + "\r\n\r\n");
		}
		String linkText = sb.toString();
		// char c = linkText.charAt(0);
		// char[] cs = new char[2];
		// Arrays.fill(cs, c);
		// String s = new String(cs);
		// linkText = linkText.replaceAll(s, "\r\n\r\n");
		if (first) {
			first = false;
		} else {
			fos.write(("\r\n\r\n-------------------------------\r\n\r\n")
					.getBytes("GBK"));
		}
		if (titleRedundant != null) {
			title = title.replaceAll(titleRedundant, "");
		}
		fos.write((title + "\r\n\r\n").getBytes("GBK"));
		fos.write(linkText.getBytes("GBK"));
	}

	@SuppressWarnings("unused")
	private static void handleIndex(FileOutputStream fos, String url)
			throws Exception {
		Document doc = null;
		try {
			Connection connection = Jsoup.connect(url);
			connection.timeout(120000);
			doc = connection.get();
		} catch (SocketTimeoutException e) {
			System.err.println(url);
			return;
		}
		String title = doc.title();
		Element body = doc.body();
		Element content = body.getElementsByClass("main").first();
		Elements links = content.getElementsByTag("a");
		StringBuffer sb = new StringBuffer();
		for (Element link : links) {
			String rel = link.attr("rel");
			if (!"nofollow".equals(rel)) {
				String linkText = link.text();
				sb.append(linkText + "\r\n");
			}

		}

		String linkText = sb.toString();
		// char c = linkText.charAt(0);
		// char[] cs = new char[4];
		// Arrays.fill(cs, c);
		// String s = new String(cs);
		// linkText = linkText.replaceAll(s, "\r\n\r\n");
		if (first) {
			first = false;
		} else {
			fos.write(("\r\n\r\n-------------------------------\r\n\r\n")
					.getBytes("GBK"));
		}
		if (titleRedundant != null) {
			title = title.replaceAll(titleRedundant, "");
		}
		fos.write((title + "\r\n\r\n").getBytes("GBK"));
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

	private static void handleDir(String url, String desDir) throws IOException {
		FileOutputStream fos = null;
		try {
			Document doc = Jsoup.connect(url).get();
			String title = doc.title();
			if (titleRedundant != null) {
				title = title.replaceAll(titleRedundant, "");
			}
			fos = new FileOutputStream(new File(desDir + title + ".txt"));
			Element body = doc.body();
			Element content = body.getElementsByClass("main").first();
			Elements links = content.getElementsByTag("a");
			for (Element link : links) {
				String linkHref = link.attr("href");
				String linkText = link.text();
				// int index1 = linkText.indexOf("第");
				// int index2 = linkText.indexOf("章");
				if (isSysout) {
					System.out.println("linkHref:" + linkHref + " ; linkText:"
							+ linkText);
				}
				// if (index1 >= 0 && index2 > 0 && index1 < index2) {
				// String refUrl = url + linkHref;
				// if (isSysout) {
				// System.out.println(refUrl);
				// }
				handleRef(fos, linkHref);
				// } else {
				// // String refUrl = url + linkHref;
				// // if (isSysout) {
				// // System.out.println(refUrl);
				// // }
				// handleIndex(fos, linkHref);
				// }
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

		String url = "http://www.mingchaonaxieshier.com/";
		handleDir(url, desDir);

		// String url = "http://www.23us.com/html/9/9362/2660200.html";
		// handleFile(url, desDir);
	}
}
