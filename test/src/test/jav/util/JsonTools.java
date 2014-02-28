package test.jav.util;

import java.io.StringReader;
import java.util.Map;

import test.jav.data.Actress;
import test.jav.data.Video;
import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

@SuppressWarnings("rawtypes")
public class JsonTools {
	public static void main(String[] args) {
		// Object o = parse(s);
	}

	public static Map parse(String s) throws TokenStreamException,
			RecognitionException, MapperException {
		JSONParser parser1 = new JSONParser(new StringReader(s));
		JSONValue jsonValue1 = parser1.nextValue();
		Map map = (Map) JSONMapper.toJava(jsonValue1, Map.class);
		return map;
	}

	public static String buildVideo(Video video) throws MapperException {
		JSONValue jsonValue = JSONMapper.toJSON(video);
		String s = jsonValue.render(true);
		return s;

	}

	public static Video parseVideo(String s) throws TokenStreamException,
			RecognitionException, MapperException {
		JSONParser parser1 = new JSONParser(new StringReader(s));
		JSONValue jsonValue1 = parser1.nextValue();
		Video video = (Video) JSONMapper.toJava(jsonValue1, Video.class);
		return video;
	}

	public static String buildActress(Actress actress) throws MapperException {
		JSONValue jsonValue = JSONMapper.toJSON(actress);
		String s = jsonValue.render(true);
		return s;
	}

	public static Actress parseActress(String s) throws TokenStreamException,
			RecognitionException, MapperException {
		JSONParser parser1 = new JSONParser(new StringReader(s));
		JSONValue jsonValue1 = parser1.nextValue();
		Actress actress = (Actress) JSONMapper
				.toJava(jsonValue1, Actress.class);
		return actress;
	}
}
