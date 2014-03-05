package test.jsoup;

import java.io.File;
import java.io.FileOutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Administrator 历史的细节
 * 
 */
public class TestJsoupByUrl5 {

	private static boolean first = true;

	private static final String titleRedundant = null;

	private static void handleRef(FileOutputStream fos, String url)
			throws Exception {
		Document doc = Jsoup.connect(url).get();
		String title = doc.title();
		Element body = doc.body();
		Element content = body.getElementsByClass("mainzt").first();
		Elements e = content.getElementsByTag("p");
		StringBuffer sb = new StringBuffer();
		for (Element ee : e) {
			sb.append(ee.text() + "\r\n\r\n");
		}
		String text = sb.toString();
		// int titleIndex1 = title.indexOf('/');
		// int titleIndex2 = title.lastIndexOf('/');
		// if (titleIndex1 > 0 && titleIndex2 > 0 && titleIndex2 != titleIndex1)
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
		if (first) {
			first = false;
		} else {
			fos.write(("\r\n\r\n-------------------------------\r\n\r\n")
					.getBytes("UTF-8"));
		}
		if (titleRedundant != null) {
			title = title.replaceAll(titleRedundant, "");
		}
		fos.write(title.getBytes("UTF-8"));
		fos.write(text.getBytes("UTF-8"));
	}

	private static void handleFile(String url, String desDir) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(desDir + "历史的细节.txt"));
			handleRef(fos, url);
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

		String url = "http://www.360doc.com/content/11/0524/16/0_119075203.shtml";
		// handleDir(url, desDir);

		// String url = "http://www.23us.com/html/9/9362/2660200.html";
		handleFile(url, desDir);
	}
}
