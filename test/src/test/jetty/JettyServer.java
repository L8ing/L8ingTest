package test.jetty;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.xml.sax.SAXException;

public class JettyServer {

	public static void main(String[] args) {
		Server server = new Server(8080);
		server.setHandler(new DefaultHandler());
		XmlConfiguration configuration = null;
		try {
			configuration = new XmlConfiguration(
					new FileInputStream(
							"D:/Files/Docs/jars/jetty/jetty-distribution-8.1.14.v20131031/etc/jetty.xml"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			configuration.configure(server);
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}