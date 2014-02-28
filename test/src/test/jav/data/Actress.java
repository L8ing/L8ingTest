package test.jav.data;

import test.jav.util.JsonTools;

public class Actress {

	private String name;

	private String rank;

	public String toString() {
		String s = null;
		try {
			s = JsonTools.buildActress(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
}
