package test.jav.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

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

	private static final String webSite = "sis001.us";

	public static List<String> searchUrlByDesignation(String designation)
			throws IOException {
		List<String> result = new ArrayList<String>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
				"http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q="
						+ designation + "+site:" + webSite);
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

	public static void main(String[] args) throws IOException {
		String designation = "wanz-151";
		List<String> urls = searchUrlByDesignation(designation);
		for (String url : urls) {

		}
	}
}