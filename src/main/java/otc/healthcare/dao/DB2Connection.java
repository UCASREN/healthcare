package otc.healthcare.dao;

import java.util.List;

import otc.healthcare.util.DBUtil;

public class DB2Connection extends ConnectionBase implements IConnection {

	private final static String driverName = "com.ibm.db2.jcc.DB2Driver";

	public DB2Connection(String url, String username, String password) {
		super(driverName, url, username, password);
	}

	@Override
	public List<String> showTables() {
		String[] tokens = url.split("/");
		String sql = "select tabname from syscat.tables where tabschema='" + tokens[tokens.length - 1] + "'";
		DBUtil utils = new DBUtil(this.getConnection());
		return utils.showListResults(sql);
	}

	@Override
	public List<String> showFields(String tableName) {
		String sql = "select * from syscolumns where tbname='" + tableName + "'";
		DBUtil utils = new DBUtil(this.getConnection());
		return utils.showListResults(sql);
	}

	public static void main(String[] args) {
		String url = "jdbc:db2://133.133.30.6:50000/TEST";
		String username = "db2admin";
		String password = "db2admin";
		IConnection connection = new DB2Connection(url, username, password);
		for (String s : connection.showFields("T1")) {
			System.out.println(s);
		}
	}
}
