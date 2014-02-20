package test.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestFile {

    public static void main(String[] args) {
        String dir = "D:\\Files\\Entertainment\\Books\\jy\\jy";
        // String[] keys = { "我", "是", "个", "一", "你", "有", "不", "的", "了", "在",
        // "道", "他", "她" };
        // String[] keys = { "绝伦","自忖"};
        String[] keys = { "琴儿" };
        File f = new File(dir);
        for (String key : keys) {
            System.out.println("********************");
            System.out.println(key + ": ");
            int total = readDir(f, key);
            System.out.println("total : " + total);
        }
    }

    private static int readDir(File dir, String key) {
        int total = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                int count = readFile(file, key);
                total += count;
                System.out.println(file.getName() + " : " + count);
            }
            if (file.isDirectory()) {
                total += readDir(file, key);
            }
        }
        return total;
    }

    private static int readFile(File file, String key) {
        int count = 0;
        BufferedReader br = null;
        try {
            String str = null;
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    file), "GBK"));
            // br = new BufferedReader(new FileReader(file));
            while ((str = br.readLine()) != null) {
                int i = countAppearence(str, key);
                count += i;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return count;
    }

    private static int countAppearence(String src, String key) {
        int count = 0;
        int index = src.indexOf(key);
        if (index >= 0) {
            count++;
            System.out.println(src);
            while (index <= src.length() && index >= 0) {
                index = src.indexOf(key, index + key.length());
                if (index >= 0) {
                    count++;
                    System.out.println(src);
                }
            }
        }
        return count;
    }
}
