package otc.healthcare.dao;

import java.util.List;

import otc.healthcare.util.DBUtil;

public class SQLServerConnection extends ConnectionBase implements IConnection {
	private final static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	public SQLServerConnection(String url, String username, String password) {
		super(driverName, url, username, password);
	}

	@Override
	public List<String> showTables() {
		String sql = "SELECT Name FROM SysObjects Where XType='U' ORDER BY Name";
		DBUtil utils = new DBUtil(this.getConnection());
		return utils.showListResults(sql);
	}

	@Override
	public List<String> showFields(String tableName) {
		String sql = "SELECT Name FROM SysColumns WHERE id=Object_Id('" + tableName + "')";
		DBUtil utils = new DBUtil(this.getConnection());
		return utils.showListResults(sql);
	}

	public static void main(String[] args) {
		String url = "jdbc:sqlserver://133.133.133.103\\SQLEXPRESS;databaseName=csdi_";
		String username = "otcaix";
		String password = "otcaix";
		IConnection connection = new SQLServerConnection(url, username, password);
		for (String s : connection.showFields("TB_Inpatient_FirstPage")) {
			System.out.println(s);
		}
	}

}
