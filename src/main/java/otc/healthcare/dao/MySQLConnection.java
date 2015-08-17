package otc.healthcare.dao;

import java.util.List;

public class MySQLConnection extends ConnectionBase implements IConnection {

	private final static String driverName = "com.mysql.jdbc.Driver";

	public MySQLConnection(String url, String username, String password) {
		super(driverName, url + "?useUnicode=true&characterEncoding=utf8", username, password);
	}

	@Override
	public List<String> showTables() {
		String sql = "show tables";
		DBUtil utils = new DBUtil(this.getConnection());
		return utils.showListResults(sql);
	}

	@Override
	public List<String> showFields(String tableName) {
		String sql = "show columns from " + tableName;
		DBUtil utils = new DBUtil(this.getConnection());
		return utils.showListResults(sql);
	}

	public static void main(String[] args) {
		String url = "jdbc:mysql://133.133.2.150/medicine";
		String username = "root";
		String password = "123456";
		IConnection connection = new MySQLConnection(url, username, password);
		for (String s : connection.showTables()) {
			System.out.print(s + " ");
		}
	}
}
