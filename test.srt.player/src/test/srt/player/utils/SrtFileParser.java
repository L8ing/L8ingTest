package test.srt.player.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SrtFileParser {

	public static List<SrtBody> parse(String path) {
		int status = 0;
		List<SrtBody> content = new ArrayList<SrtBody>();
		BufferedReader br = null;
		try {
			String str = null;
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					path)));
			// br = new BufferedReader(new FileReader(file));
			SrtBody srtBody = new SrtBody();
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				String s = str.trim();
				if (s.length() == 0) {
					status = 3;
				}
				switch (status) {
				case 0:
					srtBody = new SrtBody();
					int index = Integer.parseInt(s);
					srtBody.setIndex(index);
					status = 1;
					break;
				case 1:
					String[] arrays = s.split("\\s");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"hh:mm:ss,SS");
					Date startDate = dateFormat.parse(arrays[0]);
					Date endDate = dateFormat.parse(arrays[2]);
					srtBody.setStart(startDate);
					srtBody.setEnd(endDate);
					status = 2;
					break;
				case 2:
					sb.append(s);
					sb.append("\r\n");
					break;
				case 3:
					srtBody.setText(sb.toString());
					sb = new StringBuffer();
					content.add(srtBody);
					status = 0;
					break;
				default:
					break;
				}
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
		return content;
	}

	public static void main(String[] args) {
		List<SrtBody> l = parse("d:\\Forrest.Gump.1994.Sapphire.Bluray.1080p.DTS-HD.x264-Grym.¼òÌå&Ó¢ÎÄ.srt");
		System.out.println(l);
	}
}
