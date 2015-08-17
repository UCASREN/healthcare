package otc.healthcare.dao;

import java.util.List;

public class OracleConnection extends ConnectionBase implements IConnection {
	private final static String driverName = "oracle.jdbc.OracleDriver";

	public OracleConnection(String url, String username, String password) {
		super(driverName, url, username, password);
	}

	@Override
	public List<String> showTables() {
		String sql = "select table_name from user_tables";
		DBUtil utils = new DBUtil(this.getConnection());
		return utils.showListResults(sql);
	}

	@Override
	public List<String> showFields(String tableName) {
		String sql = "select column_name from user_tab_columns where table_name = '"
				+ tableName.toUpperCase() + "'";
		DBUtil utils = new DBUtil(this.getConnection());
		return utils.showListResults(sql);
	}

	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@133.133.133.96:1521:orcl";
		String username = "test";
		String password = "otcaix2014dataanalyze";
		IConnection connection = new OracleConnection(url, username, password);
		for (String s : connection.showFields("diagnose")) {
			System.out.println(s);
		}
	}
}
