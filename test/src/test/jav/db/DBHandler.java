package test.jav.db;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import test.jav.data.Actress;
import test.jav.data.Video;

public class DBHandler {

	private static SqlSessionFactory sessionFactory;

	private static SqlSessionFactory getSessionFactory() throws IOException {
		SqlSessionFactory sessionFactory = null;
		String resource = "test/jav/db/configuration.xml";
		sessionFactory = new SqlSessionFactoryBuilder().build(Resources
				.getResourceAsReader(resource));
		return sessionFactory;
	}

	private static SqlSession openSession() throws IOException {
		if (sessionFactory == null) {
			sessionFactory = getSessionFactory();
		}
		SqlSession sqlSession = sessionFactory.openSession();
		return sqlSession;
	}

	public static int insertActress(Actress actress) throws IOException {
		SqlSession session = openSession();
		MybatisMapper userMapper = session.getMapper(MybatisMapper.class);
		int t = userMapper.insertActress(actress);
		session.commit();
		session.close();
		return t;
	}

	private static void updateObject(Object src, Object des) {
		Field[] fields = src.getClass().getDeclaredFields();
		for (Field f : fields) {
			try {
				f.setAccessible(true);
				Object o = f.get(src);
				if (o != null) {
					String type = o.getClass().getName();
					if ("java.lang.String".equals(type)) {
						String s = (String) o;
						f.set(des, s);
					} else if ("java.lang.Integer".equals(type)) {
						Integer s = (Integer) o;
						if (s != -1) {
							f.set(des, s);
						}
					} else if ("java.lang.Double".equals(type)) {
						Double s = (Double) o;
						if (s >= 0.01) {
							f.set(des, s);
						}
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static int updateActress(Actress actress) throws IOException {
		SqlSession session = openSession();
		MybatisMapper userMapper = session.getMapper(MybatisMapper.class);
		Actress a = userMapper.findActressByName(actress.getName());
		updateObject(actress, a);
		int t = userMapper.updateActress(a);
		session.commit();
		session.close();
		return t;
	}

	public static int insertVideo(Video video) throws IOException {
		SqlSession session = openSession();
		MybatisMapper userMapper = session.getMapper(MybatisMapper.class);
		int t = userMapper.insertVideo(video);
		session.commit();
		session.close();
		return t;
	}

	public static int updateVideo(Video video) throws IOException {
		SqlSession session = openSession();
		MybatisMapper userMapper = session.getMapper(MybatisMapper.class);
		Video v = userMapper.findVideoByDesignation(video.getDesignation());
		updateObject(video, v);
		int t = userMapper.updateVideo(v);
		session.commit();
		session.close();
		return t;
	}

	public static Actress findActressByName(String name) throws IOException {
		SqlSession session = openSession();
		MybatisMapper userMapper = session.getMapper(MybatisMapper.class);
		Actress actress = userMapper.findActressByName(name);
		session.commit();
		session.close();
		return actress;
	}

	public static List<Actress> findActressByRank(String rank)
			throws IOException {
		SqlSession session = openSession();
		MybatisMapper userMapper = session.getMapper(MybatisMapper.class);
		List<Actress> actress = userMapper.findActressByRank(rank);
		session.commit();
		session.close();
		return actress;
	}

	public static Video findVideoByDesignation(String name) throws IOException {
		SqlSession session = openSession();
		MybatisMapper userMapper = session.getMapper(MybatisMapper.class);
		Video video = userMapper.findVideoByDesignation(name);
		session.commit();
		session.close();
		return video;
	}

	public static List<Video> findVideoByActress(String actressName)
			throws IOException {
		SqlSession session = openSession();
		MybatisMapper userMapper = session.getMapper(MybatisMapper.class);
		List<Video> video = userMapper.findVideoByActress(actressName);
		session.commit();
		session.close();
		return video;
	}

	public static List<Video> findVideoByTheme(String theme) throws IOException {
		SqlSession session = openSession();
		MybatisMapper userMapper = session.getMapper(MybatisMapper.class);
		List<Video> video = userMapper.findVideoByTheme(theme);
		session.commit();
		session.close();
		return video;
	}

	public static List<String> findAllThemes() throws IOException {
		SqlSession session = openSession();
		MybatisMapper userMapper = session.getMapper(MybatisMapper.class);
		List<String> theme = userMapper.findAllThemes();
		session.commit();
		session.close();
		return theme;
	}

	public static void main(String[] args) throws Exception {

		// List<Actress> actress3 = findActressByRank("A");
		// System.out.println(actress3);

		// List<Video> video = findVideoByActress("RIO");
		// System.out.println(video);

		// List<Video> v = findVideoByTheme("搜查官");
		// System.out.println(v);
		//
		// List<String> s = findAllThemes();
		// System.out.println(s);

		// Actress actress2 = new Actress();
		// actress2.setName("RIO");
		// actress2.setRank("A");
		// int t = insertActress(actress2);
		// System.out.println(t);
	}

}