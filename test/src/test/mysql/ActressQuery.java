package test.mysql;

import java.util.List;

public class ActressQuery {

	private static final String ACTRESS_TABLE_NAME = "t_actress";

	private static final String ACTRESS_NAME_KEY = "name";

	@SuppressWarnings("unused")
	private static final String ACTRESS_RANK_KEY = "rank";

	public static List<String[]> getAllActress(MySqlProperties prop) throws Exception {
		String sql = "SELECT * FROM " + ACTRESS_TABLE_NAME;
		List<String[]> result = MySqlUtil.handleQuery(prop, sql);
		return result;
	}

	public static List<String[]> queryActressByName(MySqlProperties prop,
			String actressName) throws Exception {
		String sql = "SELECT * FROM " + ACTRESS_TABLE_NAME + " WHERE `"
				+ ACTRESS_NAME_KEY + "`='" + actressName + "'";
		List<String[]> list = MySqlUtil.handleQuery(prop, sql);
		return list;
	}

	public static int addActress(MySqlProperties prop, String name, String rank)
			throws Exception {
		String sql = "INSERT INTO " + ACTRESS_TABLE_NAME + " VALUES ('" + name
				+ "', '" + rank + "')";
		int result = MySqlUtil.handleUpdate(prop, sql);
		return result;
	}

	//
	// public static int deleteOneTradeAttribute(MySqlProperties prop,
	// String attributeName, String commitUser) throws Exception {
	// String formerCommiter = queryOneTradeAttribute(prop, attributeName);
	// // ���֮ǰ�Ƿ����䱾����ӵģ���������ɾ��
	// if (formerCommiter != null && formerCommiter.length() != 0
	// && !formerCommiter.equals(commitUser)) {
	// throw new Exception("���������� �� " + attributeName
	// + " �����Ϊ �� " + formerCommiter);
	// }
	//
	// String sql = "DELETE FROM "
	// + TradeAttributeTableKey.TRADE_ATTRIBUTE_TABLE_NAME
	// + " WHERE `" + TradeAttributeTableKey.ATTRIBUTE_NAME + "`='"
	// + attributeName + "'";
	// int result = MySqlUtil.handleUpdate(prop, sql);
	// return result;
	// }

	public static void main(String[] args) throws Exception {
		MySqlProperties prop = new MySqlProperties();
		// addOneTradeAttribute(prop, "����", "teller1", "20110501");
		// addOneTradeAttribute(prop, "��ݺ˲�", "teller2", "20110505");
		// addOneTradeAttribute(prop, "�Թ�ҵ��", "teller3", "20110506");
		// addOneTradeAttribute(prop, "��ҵ��", "teller4", "20110507");
		// addOneTradeAttribute(prop, "���ÿ�", "teller2", "20110505");

		// String[] s = getAllTradeAttribute(prop);
		// for (int i = 0; i < s.length; i++)
		// {
		// System.out.println(s[i]);
		// }

		// addActress(prop, "爱川美里菜", "A");

//		queryActressByName(prop, "本多成实");
		
		getAllActress(prop);
	}
}
