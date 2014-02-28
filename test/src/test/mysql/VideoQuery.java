package test.mysql;

public class VideoQuery {
	// public static String[] getAllTradeSort(MySqlProperties prop)
	// throws Exception {
	// String sql = "SELECT " + TradeSortTableKey.SORT_NAME + " FROM "
	// + TradeSortTableKey.TRADE_SORT_TABLE_NAME;
	// List<String> list = MySqlUtil.handleQuery(prop, sql);
	//
	// String[] result = new String[list.size()];
	// for (int i = 0; i < list.size(); i++) {
	// result[i] = list.get(i);
	// }
	// return result;
	// }
	//
	// private static String queryOneTradeSort(MySqlProperties prop,
	// String sortName) throws Exception {
	// String sql = "SELECT " + TradeSortTableKey.COMMIT_USER_NAME + " FROM "
	// + TradeSortTableKey.TRADE_SORT_TABLE_NAME + " WHERE `"
	// + TradeSortTableKey.SORT_NAME + "`='" + sortName + "'";
	// List<String> list = MySqlUtil.handleQuery(prop, sql);
	// if (list.isEmpty()) {
	// return null;
	// } else {
	// return list.get(0);
	// }
	// }
	//
	// public static int addOneTradeSort(MySqlProperties prop, String sortName,
	// String commitUser, String commitTime) throws Exception {
	// String sql = "INSERT INTO " + TradeSortTableKey.TRADE_SORT_TABLE_NAME
	// + " VALUES ('" + sortName + "', '" + commitUser + "','"
	// + commitTime + "')";
	// int result = -1;
	// try {
	// result = MySqlUtil.handleUpdate(prop, sql);
	// } catch (SQLException e) {
	// int errorCode = e.getErrorCode();
	// if (errorCode == 1062) {
	// throw new Exception("��������� : " + sortName + " �Ѵ���");
	// }
	// }
	// return result;
	// }
	//
	// public static int deleteOneTradeSort(MySqlProperties prop, String
	// sortName,
	// String commitUser) throws Exception {
	// String formerCommiter = queryOneTradeSort(prop, sortName);
	// // ���֮ǰ�Ƿ����䱾����ӵģ���������ɾ��
	// if (formerCommiter != null && formerCommiter.length() != 0
	// && !formerCommiter.equals(commitUser)) {
	// throw new Exception("��������� �� " + sortName + " �����Ϊ �� "
	// + formerCommiter);
	// }
	//
	// String sql = "DELETE FROM " + TradeSortTableKey.TRADE_SORT_TABLE_NAME
	// + " WHERE `" + TradeSortTableKey.SORT_NAME + "`='" + sortName
	// + "'";
	// int result = MySqlUtil.handleUpdate(prop, sql);
	// return result;
	// }

	public static void main(String[] args) throws Exception {
//		MySqlProperties prop = new MySqlProperties();
		// addOneTradeSort(prop, "����", "teller1", "20110501");
		// addOneTradeSort(prop, "��ݺ˲�", "teller2", "20110505");
		// addOneTradeSort(prop, "�Թ�ҵ��", "teller3", "20110506");
		// addOneTradeSort(prop, "��ҵ��", "teller4", "20110507");
		// addOneTradeSort(prop, "���ÿ�", "teller2", "20110505");

		// String[] s = getAllTradeSort(prop);
		// for (int i = 0; i < s.length; i++)
		// {
		// System.out.println(s[i]);
		// }

		// deleteOneTradeSort(prop, "���ÿ�", "teller4");
	}
}
