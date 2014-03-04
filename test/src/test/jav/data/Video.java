package test.jav.data;

import test.jav.util.JsonTools;

public class Video {

	private String designation;

	private String video_name;

	private int uncensored = -1;

	private String actress;

	private String theme;

	private double size = 0.0;

	private String format;

	private String comment;

	public String toString() {
		String s = null;
		try {
			s = JsonTools.buildVideo(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getVideo_name() {
		return video_name;
	}

	public void setVideo_name(String video_name) {
		this.video_name = video_name;
	}

	public int isUncensored() {
		return uncensored;
	}

	public void setUncensored(int uncensored) {
		this.uncensored = uncensored;
	}

	public String getActress() {
		return actress;
	}

	public void setActress(String actress) {
		this.actress = actress;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
