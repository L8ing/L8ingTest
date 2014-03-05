package test.jav.data;

import test.jav.util.JsonTools;

public class Actress {

	private String name;

	private String rank;

	private int uncensored = -1;;

	private String alias1;

	private String alias2;

	private String alias3;

	private String alias4;

	private String alias5;

	private String alias6;

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

	public String getAlias1() {
		return alias1;
	}

	public void setAlias1(String alias1) {
		this.alias1 = alias1;
	}

	public String getAlias2() {
		return alias2;
	}

	public void setAlias2(String alias2) {
		this.alias2 = alias2;
	}

	public String getAlias3() {
		return alias3;
	}

	public void setAlias3(String alias3) {
		this.alias3 = alias3;
	}

	public String getAlias4() {
		return alias4;
	}

	public void setAlias4(String alias4) {
		this.alias4 = alias4;
	}

	public String getAlias5() {
		return alias5;
	}

	public void setAlias5(String alias5) {
		this.alias5 = alias5;
	}

	public String getAlias6() {
		return alias6;
	}

	public void setAlias6(String alias6) {
		this.alias6 = alias6;
	}

	public int getUncensored() {
		return uncensored;
	}

	public void setUncensored(int uncensored) {
		this.uncensored = uncensored;
	}
}
