package test.jav.main;

import java.util.List;

import test.jav.data.Actress;
import test.jav.db.DBHandler;

public class JavGetter {

	public static void main(String[] args) throws Exception {
		List<Actress> actress = DBHandler.findActressByRank("A");
		for(Actress a : actress){
			String name = a.getName();
		}
	}
}
