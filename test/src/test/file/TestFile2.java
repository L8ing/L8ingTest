package test.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class TestFile2 {

	public static void main(String[] args) {
		String dir = "D:\\Files\\Entertainment\\Books\\jy\\jy";
		File f = new File(dir);
		readDir(f);
	}

	private static void readDir(File dir) {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile() && file.getName().endsWith(".txt")) {
				List<Entry<Character, Integer>> l = readFile(file);
				System.out.println(file.getName() + " : " + l);
				System.out.println("********************");
			}
			if (file.isDirectory()) {
				readDir(file);
			}
		}
	}

	private static List<Entry<Character, Integer>> readFile(File file) {
		BufferedReader br = null;
		Map<Character, Integer> result = new HashMap<Character, Integer>();
		try {
			String str = null;
			br = new BufferedReader(new FileReader(file));
			while ((str = br.readLine()) != null) {
				char[] array = str.toCharArray();
				for (Character c : array) {
					if (isCJKCharacter(c)) {
						if (result.containsKey(c)) {
							Integer i = result.get(c);
							i++;
							result.put(c, i);
						} else {
							result.put(c, new Integer(1));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		List<Entry<Character, Integer>> list = new ArrayList<Entry<Character, Integer>>();
		for (Iterator<Entry<Character, Integer>> it = result.entrySet()
				.iterator(); it.hasNext();) {
			Entry<Character, Integer> e = it.next();
			list.add(e);
		}
		Collections.sort(list, new Comparator<Entry<Character, Integer>>() {
			public int compare(Entry<Character, Integer> o1,
					Entry<Character, Integer> o2) {
				Integer r1 = o1.getValue();
				Integer r2 = o2.getValue();
				return r2 - r1;
			}
		});
		// System.out.println("total : " + list.size());
		List<Entry<Character, Integer>> l = list.subList(0, 15);
		return l;
	}

	private static Set<Character.UnicodeBlock> CJK_UNICODE_BLOCK_SET = new HashSet<Character.UnicodeBlock>();

	private static Set<Character.UnicodeBlock> CJK_UNICODE_BLOCK_SET_SYMBOLS = new HashSet<Character.UnicodeBlock>();

	static {
		CJK_UNICODE_BLOCK_SET
				.add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
		CJK_UNICODE_BLOCK_SET
				.add(Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS);
		CJK_UNICODE_BLOCK_SET
				.add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
		CJK_UNICODE_BLOCK_SET_SYMBOLS
				.add(Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION);
		CJK_UNICODE_BLOCK_SET_SYMBOLS
				.add(Character.UnicodeBlock.GENERAL_PUNCTUATION);
		CJK_UNICODE_BLOCK_SET_SYMBOLS
				.add(Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
	}

	public static boolean isCJKCharacter(char c) {
		return CJK_UNICODE_BLOCK_SET.contains(Character.UnicodeBlock.of(c));
	}

	public static boolean isCJKSymbol(char c) {
		return CJK_UNICODE_BLOCK_SET_SYMBOLS.contains(Character.UnicodeBlock
				.of(c));
	}
}
