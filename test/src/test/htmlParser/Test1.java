package test.htmlParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.visitors.TextExtractingVisitor;

import org.htmlparser.Parser;

public class Test1 {
	private static String ENCODE = "GBK";

	private static void message(String szMsg) {
		try {
			System.out.println(new String(szMsg.getBytes(ENCODE), System
					.getProperty("file.encoding")));
		} catch (Exception e) {
		}
	}

	public static String openFile(String szFileName) {
		try {
			BufferedReader bis = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(szFileName)), ENCODE));
			String szContent = "";
			String szTemp;

			while ((szTemp = bis.readLine()) != null) {
				szContent += szTemp + "\n";
			}
			bis.close();
			return szContent;
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String[] args) throws Exception {

		String szContent = openFile("E:/My Sites/HTMLParserTester.html");

		// Parser parser = Parser.createParser(szContent, ENCODE);
		// Parser parser = new Parser( szContent );
		Parser parser = new Parser(
				(HttpURLConnection) (new URL(
						"http://www.23us.com/html/9/9362/2660200.html"))
						.openConnection());

		TextExtractingVisitor visitor = new TextExtractingVisitor();
		parser.visitAllNodesWith(visitor);
		String textInPage = visitor.getExtractedText();

		message(textInPage);
	}
}
