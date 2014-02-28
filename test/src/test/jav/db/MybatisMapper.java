package test.jav.db;

import test.jav.data.Actress;
import test.jav.data.Video;

public interface MybatisMapper {

	public Actress findActressByName(String name);

	public Video findVideoByDesignation(String designation);

}