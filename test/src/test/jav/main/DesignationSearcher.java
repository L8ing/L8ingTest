package test.jav.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import test.jav.data.Actress;

@SuppressWarnings({ "resource", "deprecation" })
public class DesignationSearcher {

	public static final String RESULTS_CONTENT = "content";

	public static final String RESULTS_GSEARCHRESULTCLASS = "GsearchResultClass";

	public static final String RESULTS_TITLENOFORMATTING = "titleNoFormatting";

	public static final String RESULTS_TITLE = "title";

	public static final String RESULTS_CACHEURL = "cacheUrl";

	public static final String RESULTS_UNESCAPEDURL = "unescapedUrl";

	public static final String RESULTS_URL = "url";

	public static final String RESULTS_VISIBLEURL = "visibleUrl";

	private static final List<String> seedSite = Arrays.asList("sis001.us");

	private static final List<String> censoredSite = Arrays
			.asList("javimdb.com/tw");

	private static final List<String> uncensoredSite = Arrays
			.asList("avimdb.com/tw");

	public static List<String> searchDesignationByActress(Actress actress) {
		List<String> result;
		String name = actress.getName();
		int uncensored = actress.getUncensored();
		switch (uncensored) {
		case 0:
			result = searchDesignationByName(name, uncensoredSite);
			break;
		case 1:
			result = searchDesignationByName(name, censoredSite);
			break;
		default:
			result = new ArrayList<String>();
			List<String> result1 = searchDesignationByName(name, uncensoredSite);
			List<String> result2 = searchDesignationByName(name, censoredSite);
			result.addAll(result1);
			result.addAll(result2);
			break;
		}

		return result;
	}

	private static List<String> searchDesignationByName(String name,
			List<String> sites) {
		List<String> result = new ArrayList<String>();
		for (String s : sites) {
			try {
				List<String> list = searchDesignationByActress(name, s);
				result.addAll(list);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static List<String> searchUrlByDesignation(String designation) {
		List<String> result = new ArrayList<String>();
		for (String s : seedSite) {
			try {
				List<String> list = searchUrlByDesignation(designation, s);
				result.addAll(list);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static List<String> searchDesignationByActress(String key,
			String webSite) throws IOException {
		List<String> result = new ArrayList<String>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
				"http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q="
						+ key + "+site:" + webSite);

		HttpResponse response = httpclient.execute(httpget);
		System.out.println(response.getStatusLine());

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent(), "UTF-8"));
			try {
				String s = reader.readLine();
				System.out.println(s);
				JSONObject jsonObject = new JSONObject(s);
				JSONObject responseData = jsonObject
						.getJSONObject("responseData");

				JSONArray resultsArray = responseData.getJSONArray("results");
				for (int i = 0; i < resultsArray.length(); i++) {
					JSONObject addressObject = resultsArray.getJSONObject(i);
					// String url = addressObject.getString(RESULTS_URL);
					String title = addressObject.getString(RESULTS_TITLE);
					System.out.print("title: ");
					System.out.println(title);
					int index = title.indexOf(' ');
					if (index >= 0) {
						title = title.substring(0, index);
					}
					// String content =
					// addressObject.getString(RESULTS_CONTENT);
					// System.out.print("content: ");
					// System.out.println(content);
					result.add(title);
					// ParseHtml.copyUrl(url);
				}

			} catch (IOException e) {
				throw e;
			} catch (RuntimeException e) {
				httpget.abort();
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				reader.close();
			}
		}
		httpclient.getConnectionManager().shutdown();
		return result;
	}

	private static List<String> searchUrlByDesignation(String key,
			String webSite) throws IOException {
		List<String> result = new ArrayList<String>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
				"http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q="
						+ key + "+site:" + webSite);

		HttpResponse response = httpclient.execute(httpget);
		System.out.println(response.getStatusLine());

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent(), "UTF-8"));
			try {
				String s = reader.readLine();
				System.out.println(s);
				JSONObject jsonObject = new JSONObject(s);
				JSONObject responseData = jsonObject
						.getJSONObject("responseData");

				JSONArray resultsArray = responseData.getJSONArray("results");
				for (int i = 0; i < resultsArray.length(); i++) {
					JSONObject addressObject = resultsArray.getJSONObject(i);
					String url = addressObject.getString(RESULTS_URL);
					System.out.print("url: ");
					System.out.println(url);
					String content = addressObject.getString(RESULTS_CONTENT);
					System.out.print("content: ");
					System.out.println(content);
					result.add(url);
					// ParseHtml.copyUrl(url);
				}

			} catch (IOException e) {
				throw e;
			} catch (RuntimeException e) {
				httpget.abort();
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				reader.close();
			}
		}
		httpclient.getConnectionManager().shutdown();
		return result;
	}

	public static void searchActress(Actress actress) {
		List<String> designations = searchDesignationByActress(actress);
		for (String designation : designations) {
			List<String> urls = searchUrlByDesignation(designation);
			for (String url : urls) {
				try {
					HtmlParser.parse(url, JavGetter.parentDir, null, true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// String designation = "wanz-151";
		Actress actress = new Actress();
		actress.setName("RIO");
		actress.setRank("A");
		actress.setUncensored(1);
		actress.setAlias1("柚木提娜");
		actress.setAlias2("柚木蒂娜");
		actress.setAlias3("柚木ティナ");
		actress.setAlias4("Yunoki Tina");
		searchActress(actress);
	}
}