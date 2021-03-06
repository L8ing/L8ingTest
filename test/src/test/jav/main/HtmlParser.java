package test.jav.main;

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

	public static final String VIDEO_NAME2 = "影片名称";

	public static final String ACTRESS_NAME = "出演女優";

	public static final String ACTRESS_NAME2 = "出演女优";

	public static final String FORMAT = "影片格式";

	public static final String FORMAT2 = "影片格式";

	public static final String SIZE = "影片大小";

	public static final String SIZE2 = "影片大小";

	public static final String TIME = "影片時間";

	public static final String TIME2 = "影片时间";

	public static final String UNCENSORED = "是否有碼";

	public static final String UNCENSORED2 = "是否有码";

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

	public static void parse(String url, String designation, Document doc,
			boolean useProxy) throws IOException {
		if (doc == null) {
			InputStream is = null;
			try {
				URL server = new URL(url);
				if (useProxy) {
					System.setProperty("http.proxyType", "4");
					System.setProperty("http.proxyPort", "8087");
					System.setProperty("http.proxyHost", "127.0.0.1");
					System.setProperty("http.proxySet", "true");
				}
				HttpURLConnection connection = (HttpURLConnection) server
						.openConnection();
				connection.connect();
				is = connection.getInputStream();
				doc = Jsoup.parse(is, "gbk", "sis001.us");
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
		Elements elements = doc.getElementsMatchingOwnText(".torrent");

		boolean simplified = false;
		Elements videoName = doc.getElementsMatchingOwnText(VIDEO_NAME);
		if (videoName.size() == 0) {
			videoName = doc.getElementsMatchingOwnText(VIDEO_NAME2);
			if (videoName.size() != 0) {
				simplified = true;
			}
		}

		String actressName = null;

		if (videoName.size() != 0) {
			String s = videoName.first().text();
			String[] list = s.split("【");
			for (String element : list) {
				int index = element.indexOf('：');
				if (index >= 0) {
					String key = element.substring(0, index);
					String value = element.substring(index + 1).trim();
					if (!simplified) {
						if (key.indexOf(VIDEO_NAME) >= 0) {
							System.out.println(VIDEO_NAME + " : " + value);
						} else if (key.indexOf(ACTRESS_NAME) >= 0) {
							actressName = value;
							System.out.println(ACTRESS_NAME + " : " + value);
						} else if (key.indexOf(FORMAT) >= 0) {
							System.out.println(FORMAT + " : " + value);
						} else if (key.indexOf(SIZE) >= 0) {
							System.out.println(SIZE + " : " + value);
						} else if (key.indexOf(TIME) >= 0) {
							System.out.println(TIME + " : " + value);
						} else if (key.indexOf(UNCENSORED) >= 0) {
							System.out.println(UNCENSORED + " : " + value);
						}
					} else {
						if (key.indexOf(VIDEO_NAME2) >= 0) {
							System.out.println(VIDEO_NAME2 + " : " + value);
						} else if (key.indexOf(ACTRESS_NAME2) >= 0) {
							actressName = value;
							System.out.println(ACTRESS_NAME2 + " : " + value);
						} else if (key.indexOf(FORMAT2) >= 0) {
							System.out.println(FORMAT2 + " : " + value);
						} else if (key.indexOf(SIZE2) >= 0) {
							System.out.println(SIZE2 + " : " + value);
						} else if (key.indexOf(TIME2) >= 0) {
							System.out.println(TIME2 + " : " + value);
						} else if (key.indexOf(UNCENSORED2) >= 0) {
							System.out.println(UNCENSORED2 + " : " + value);
						}
					}
				}
			}
		}

		String fileName = designation;
		if (actressName != null) {
			fileName += "|" + actressName;
		}

		int index = url.lastIndexOf('/');
		String parent = url.substring(0, index);
		for (Element element : elements) {
			String href = element.attr("href");
			String realRef = parent + '/' + href;
			System.out.println(realRef);
			try {
				fileName += "|" + System.currentTimeMillis() + ".torrent";
				FileDownLoader.get(realRef, JavGetter.parentDir, fileName,
						useProxy);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String url = "http://sis001.us/bbs/thread-8968372-1-1.html";
		// Document doc = Jsoup.parse(new File("E:\\books\\html.html"), "gbk");
		Document doc = null;
		parse(url, "n0738", doc, true);
	}
}
