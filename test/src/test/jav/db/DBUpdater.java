package test.jav.db;

import java.io.IOException;

import test.jav.data.Actress;
import test.jav.data.Video;

public class DBUpdater {

	public static void updateActress() throws IOException {
		// Actress actress2 = new Actress();
		// actress2.setName("RIO");
		// actress2.setRank("A");
		// actress2.setUncensored(1);
		// actress2.setAlias1("柚木提娜");
		// actress2.setAlias2("柚木蒂娜");
		// actress2.setAlias3("柚木ティナ");
		// actress2.setAlias4("Yunoki Tina");
		// DBHandler.updateActress(actress2);
		// Actress actress = DBHandler.findActressByName("RIO");
		// System.out.println(actress);

		Actress actress2 = new Actress();
		actress2.setName("樱井步");
		actress2.setRank("B");
		actress2.setUncensored(1);
		actress2.setAlias1("桜井あゆ");
		actress2.setAlias2("櫻井步");
		DBHandler.updateActress(actress2);
		Actress actress = DBHandler.findActressByName("樱井步");
		System.out.println(actress);

	}

	public static void updateVideo() throws IOException {
		// Video video = new Video();
		// video.setActress("樱井步");
		// video.setDesignation("WANZ-151");
		// video.setComment("美人潜入捜査官 桜井あゆ");
		// video.setUncensored(1);
		// video.setFormat("AVI");
		// video.setSize(0.977);
		// video.setVideo_name("美人潜入捜査官 桜井あゆ");
		// DBHandler.updateVideo(video);
		// Video v = DBHandler.findVideoByDesignation("WANZ-151");
		// System.out.println(v);

		Video video = new Video();
		video.setActress("本多成实");
		video.setDesignation("n0738");
		video.setUncensored(0);
		video.setFormat("MP4");
		video.setSize(1.03);
		video.setVideo_name("超美形小恶魔本最强治疗效果 本多成实之初裏绝顶中出");
		DBHandler.updateVideo(video);
		Video v = DBHandler.findVideoByDesignation("n0738");
		System.out.println(v);
	}

	public static void main(String[] args) throws IOException {
		// updateActress();
		updateVideo();
	}
}
