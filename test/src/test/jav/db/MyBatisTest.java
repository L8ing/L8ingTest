package test.jav.db;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import test.jav.data.Actress;
import test.jav.data.Video;
import test.jav.util.JsonTools;

public class MyBatisTest {

	private static SqlSessionFactory getSessionFactory() throws IOException {
		SqlSessionFactory sessionFactory = null;
		String resource = "test/jav/db/configuration.xml";
		sessionFactory = new SqlSessionFactoryBuilder().build(Resources
				.getResourceAsReader(resource));
		return sessionFactory;
	}

	public static void main(String[] args) throws Exception {
		SqlSession sqlSession = getSessionFactory().openSession();
		MybatisMapper userMapper = sqlSession.getMapper(MybatisMapper.class);
		Actress actress = userMapper.findActressByName("本多成实");
		System.out.println(actress);

		Video video = userMapper.findVideoByDesignation("n0738");
		video.setAc(actress);
		String s = JsonTools.buildVideo(video);
		System.out.println(s);
		Video video2 =JsonTools.parseVideo(s);
		System.out.println(video2);

	}

}