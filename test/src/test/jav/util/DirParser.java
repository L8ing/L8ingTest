package test.jav.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import test.jav.data.Actress;
import test.jav.data.Video;
import test.jav.db.DBHandler;

public class DirParser {

	private static final String NAME_KEY = "name";

	private static final String CHILDREN_KEY = "children";

	public static JSONObject build(File file) throws JSONException {
		String name = file.getName();
		JSONObject object = new JSONObject();
		object.put(NAME_KEY, name);
		if (file.isDirectory()) {
			JSONArray resultsArray = new JSONArray();
			File[] children = file.listFiles();
			for (File child : children) {
				JSONObject childObject = build(child);
				resultsArray.put(childObject);
			}
			object.put(CHILDREN_KEY, resultsArray);
		}
		return object;
	}

	public static JSONObject parse(String json) throws JSONException {
		JSONObject object = new JSONObject(json);
		return object;
	}

	public static String readFileToString(File file, String encoding)
			throws IOException {
		InputStream fis = null;
		try {
			fis = new FileInputStream(file);
			StringWriter sw = new StringWriter();
			InputStreamReader in = new InputStreamReader(fis, encoding);
			char[] buffer = new char[1024];
			int n = 0;
			while (-1 != (n = in.read(buffer))) {
				sw.write(buffer, 0, n);
			}
			return sw.toString();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

	public static void writeStringToFile(File file, String data, String encoding)
			throws IOException {
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			if (data != null) {
				out.write(data.getBytes(encoding));
			}
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private static void parseActressArray(JSONObject jsonObject, String rank)
			throws JSONException {
		String uncensored = jsonObject.getString("name");
		JSONArray actressArray = jsonObject.getJSONArray("children");
		for (int j = 0; j < actressArray.length(); j++) {
			JSONObject actress = actressArray.getJSONObject(j);
			String actressName = actress.getString("name");
			push2ActressTable(actressName, rank);
			JSONArray videoArray = actress.getJSONArray("children");
			for (int k = 0; k < videoArray.length(); k++) {
				JSONObject video = videoArray.getJSONObject(k);
				String videoName = video.getString("name");
				int index = videoName.indexOf(' ');
				String designation = videoName;
				String videoRealName = "";
				if (index >= 0) {
					designation = videoName.substring(0, index);
					videoRealName = videoName.substring(index,
							videoName.length()).trim();
					int dotIndex = videoRealName.indexOf('.');
					if (dotIndex >= 0) {
						videoRealName = videoRealName.substring(0, dotIndex);
					}
				}
				push2VideoTable(designation, videoRealName, actressName,
						uncensored, null, null);
			}
		}
	}

	private static void parseThemeArray(JSONObject jsonObject, String theme)
			throws JSONException {
		JSONArray actressArray = jsonObject.getJSONArray("children");
		for (int j = 0; j < actressArray.length(); j++) {
			JSONObject actress = actressArray.getJSONObject(j);
			String name = actress.getString("name");
			int index = name.indexOf(' ');
			String designation = name;
			String comment = "";
			if (index >= 0) {
				designation = name.substring(0, index);
				comment = name.substring(index, name.length()).trim();
				int dotIndex = comment.indexOf('.');
				if (dotIndex >= 0) {
					comment = comment.substring(0, dotIndex);
				}
			}
			String actressName = "";
			if ("搜查官".equals(theme) || "触手".equals(theme)) {
				actressName = comment;
				comment = "";
				push2ActressTable(actressName, null);
			}
			push2VideoTable(designation, null, actressName, null, theme,
					comment);
		}
	}

	private static void push2Db(JSONObject jsonObject) throws JSONException {
		JSONArray resultsArray = jsonObject.getJSONArray("children");
		for (int i = 0; i < resultsArray.length(); i++) {
			JSONObject o = resultsArray.getJSONObject(i);
			String name = o.getString("name");
			if ("A".equals(name)) {
				parseActressArray(o, name);
			} else if ("B".equals(name)) {
				JSONArray zArray = o.getJSONArray("children");
				for (int z = 0; z < zArray.length(); z++) {
					JSONObject zz = zArray.getJSONObject(z);
					parseActressArray(zz, name);
				}
			} else if (!"sm".equals(name) && !"Thumbs.db".equals(name)
					&& !"花与蛇".equals(name)) {
				System.out.println(name);
				parseThemeArray(o, name);
			}
		}
	}

	private static void push2VideoTable(String designation,
			String videoRealName, String actressName, String uncensored,
			String theme, String comment) {
		// TODO Auto-generated method stub
		System.out.println(designation + " : " + videoRealName + " : "
				+ actressName + " : " + uncensored + " : " + theme + " : "
				+ comment);
		if ("unknown".equals(designation) || "Thumbs.db".equals(designation)) {
			return;
		}
		try {
			Video video = DBHandler.findVideoByDesignation(designation);
			if (video != null) {
				return;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			System.err.println(designation);
		}
		Video video = new Video();
		video.setDesignation(designation);
		if (videoRealName != null) {
			video.setVideo_name(videoRealName);
		}
		if (actressName != null) {
			video.setActress(actressName);
		}
		if ("infantry".equals(uncensored)) {
			video.setUncensored(0);
		} else if ("cavalry".equals(uncensored)) {
			video.setUncensored(1);
		} else {
			video.setUncensored(-1);
		}
		if (theme != null) {
			video.setTheme(theme);
		}
		if (comment != null) {
			video.setComment(comment);
		}
		try {
			DBHandler.insertVideo(video);
		} catch (Throwable e) {
			e.printStackTrace();
			System.err.println(designation + " : " + videoRealName + " : "
					+ actressName);
		}
	}

	private static void push2ActressTable(String actressName, String rank) {
		System.out.println(actressName + " : " + rank);
		try {
			Actress actress = DBHandler.findActressByName(actressName);
			if (actress != null) {
				return;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			System.err.println(actressName + " : " + rank);
		}
		Actress actress = new Actress();
		actress.setName(actressName);
		if (rank != null) {
			actress.setRank(rank);
		}
		try {
			DBHandler.insertActress(actress);
		} catch (Throwable e) {
			e.printStackTrace();
			System.err.println(actressName + " : " + rank);
		}
	}

	public static void main(String[] args) throws Exception {
		// File f = new File("E:\\video\\japs");
		// JSONObject o = build(f);
		// System.out.println(o);
		File parent = new File(".");
		// System.out.println(.getAbsolutePath());
		// File test = new File("E:\\books\\11.json");
		String name = DirParser.class.getPackage().getName();
		name = name.replaceAll("\\.", "/");
		File test = new File(parent, "src\\" + name + "\\dir.json");
		System.out.println(test.getAbsolutePath());
		String s = readFileToString(test, "gbk");
		JSONObject object = parse(s);
		push2Db(object);

	}
}
