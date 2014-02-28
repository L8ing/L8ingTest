package test.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MySqlUtil {
	private static final Log logger = LogFactory.getLog(MySqlUtil.class);

	// public static MySqlProperties load(String path) throws Exception {
	// MySqlProperties properties = null;
	// Reader reader = null;
	// try {
	// reader = new InputStreamReader(new FileInputStream(path), "UTF-8");
	// properties = (MySqlProperties) PropXStreamUtil.getXStream()
	// .fromXML(reader);
	// } catch (Exception e) {
	// throw e;
	// } finally {
	// if (reader != null) {
	// try {
	// reader.close();
	// } catch (IOException e) {
	// throw e;
	// }
	// }
	// }
	// return properties;
	// }
	//
	// public static void save(String file, MySqlProperties properties) {
	// Writer writer = null;
	// FileOutputStream testFos = null;
	// try {
	// testFos = new FileOutputStream(file);
	// writer = new OutputStreamWriter(testFos, "UTF-8");
	// PropXStreamUtil.getXStream().toXML(properties, writer);
	// } catch (IOException e) {
	// logger.error(e);
	// } finally {
	// if (testFos != null) {
	// try {
	// testFos.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// if (writer != null) {
	// try {
	// writer.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }

	public static String generateUrl(MySqlProperties prop) throws Exception {
		String ip = prop.getIp();
		if (ip == null || ip.trim().length() == 0) {
			throw new Exception("ip错误");
		}
		String port = Integer.toString(prop.getPort());
		String dbName = prop.getDataBaseName();
		if (dbName == null || dbName.trim().length() == 0) {
			throw new Exception("dbName错误");
		}
		String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
		return url;
	}

	public static int handleUpdate(MySqlProperties prop, String sql)
			throws Exception {
		String url = MySqlUtil.generateUrl(prop);
		Connection conn = null;
		int result = -1;
		try {
			conn = DriverManager.getConnection(url, prop.getUser(),
					prop.getPwd());
			conn.setReadOnly(false);
			conn.setAutoCommit(true);
			result = conn.createStatement().executeUpdate(sql);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}

	public static List<String[]> handleQuery(MySqlProperties prop, String sql)
			throws Exception {
		List<String[]> list = new ArrayList<String[]>();

		String url = MySqlUtil.generateUrl(prop);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, prop.getUser(),
					prop.getPwd());
			conn.setReadOnly(true);
			conn.setAutoCommit(true);
			ResultSet rs = conn.createStatement().executeQuery(sql);
			if (rs != null) {
				ResultSetMetaData meta = rs.getMetaData();
				while (rs.next()) {
					String[] row = new String[meta.getColumnCount()];
					for (int i = 0; i < meta.getColumnCount(); i++) {
						String value = rs.getString(i + 1);
						row[i] = value == null ? "" : value;
					}
					list.add(row);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return list;
	}
}
