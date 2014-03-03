package test.jav.db;

import java.io.IOException;

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

	public static int insertVideo(Video video) throws IOException {
		SqlSession session = openSession();
		MybatisMapper userMapper = session.getMapper(MybatisMapper.class);
		int t = userMapper.insertVideo(video);
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

	public static Video findVideoByDesignation(String name) throws IOException {
		SqlSession session = openSession();
		MybatisMapper userMapper = session.getMapper(MybatisMapper.class);
		Video video = userMapper.findVideoByDesignation(name);
		session.commit();
		session.close();
		return video;
	}

	public static void main(String[] args) throws Exception {
		Actress actress = findActressByName("本多成实");
		System.out.println(actress);

		Actress actress3 = findActressByName("RIO");
		System.out.println(actress3);

		Actress actress2 = new Actress();
		actress2.setName("RIO");
		actress2.setRank("A");
		int t = insertActress(actress2);
		System.out.println(t);
	}

}