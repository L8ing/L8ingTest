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

	public static void parse(String json) throws JSONException {
		JSONObject object = new JSONObject(json);
		System.out.println(object);
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

	public static void main(String[] args) throws Exception {
		File f = new File("E:\\video\\japs");
		JSONObject o = build(f);
		System.out.println(o);
		File test = new File("E:\\11.json");
		writeStringToFile(test, o.toString(), "gbk");
		String s = readFileToString(test, "gbk");
		parse(s);
	}
}
