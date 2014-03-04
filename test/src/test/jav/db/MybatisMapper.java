package test.jav.db;

import java.util.List;

import test.jav.data.Actress;
import test.jav.data.Video;

public interface MybatisMapper {

	public Actress findActressByName(String name);

	public Video findVideoByDesignation(String designation);

	public int insertActress(Actress actress);

	public int insertVideo(Video video);

	public List<Actress> findActressByRank(String rank);

	public List<Video> findVideoByActress(String actressName);

	public List<Video> findVideoByTheme(String theme);

	public List<String> findAllThemes();

	public int updateActress(Actress actress);

	public int updateVideo(Video video);
}