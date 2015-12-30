package otc.healthcare.dao;

import java.util.List;

import otc.healthcare.util.DBUtil;

public class DMConnection extends ConnectionBase implements IConnection {

	private final static String driverName = "dm.jdbc.driver.DmDriver";

	public DMConnection(String url, String username, String password) {
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
		String url = "jdbc:dm://localhost:12345/SG";
		String username = "SYSDBA";
		String password = "SYSDBA";
		IConnection connection = new DMConnection(url, username, password);
		for (String s : connection.showFields("diagnose")) {
			System.out.println(s);
		}
	}
}
