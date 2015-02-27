package ranker.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class JsonUtil {
	public static String[] parse(String s) {
		if (s == null) {
			return new String[0];
		}
		JSONArray array2 = JSON.parseArray(s);
		String[] ranks = new String[array2.size()];
		for (int i = 0; i < array2.size(); i++) {
			ranks[i] = array2.getString(i);
		}
		return ranks;
	}

	public static String build(String[] s) {
		if (s == null) {
			return "";
		}
		String prop = JSON.toJSONString(s);
		return prop;
	}

	public static void main(String[] args) {
		String[] s = { "111", "222", "333" };
		String t = build(s);
		System.out.println(t);
		String[] ss = parse(t);
		System.out.println(ss);
	}
}
