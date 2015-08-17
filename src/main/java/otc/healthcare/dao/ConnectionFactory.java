package otc.healthcare.dao;

public class ConnectionFactory {
	private IConnection iConnection;

	public ConnectionFactory(String url, String username, String password) {
		String type = url.split(":")[1].trim().toLowerCase();
		initInstance(type, url, username, password);
	}

	public ConnectionFactory(String type, String url, String username,
			String password) {
		initInstance(type.trim().toLowerCase(), url, username, password);
	}

	private void initInstance(String type, String url, String username,
			String password) {
		if (type.equals("mysql"))
			iConnection = new MySQLConnection(url, username, password);
		else if (type.equals("dm"))
			iConnection = new DMConnection(url, username, password);
		else if (type.equals("oracle")) {
			iConnection = new OracleConnection(url, username, password);
			System.out.println("Oracle herer");
		}
		// else if (type.equals("sqlserver"))
		// driverName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
		// else if (type.equals("db2"))
		// driverName = "com.ibm.db2.jdbc.app.DB2Driver";
		// else if (type.equals("informix"))
		// driverName = "com.informix.jdbc.IfxDriver";
		// else if (type.equals("sybase"))
		// driverName = "com.sybase.jdbc.SybDriver";
		// else if (type.equals("postgresql"))
		// driverName = "org.postgresql.Driver";
		else
			return;
	}

	public IConnection getInstance() {
		return iConnection;
	}

	public DBUtil getDBUtil() {
		return new DBUtil(iConnection.getConnection());
	}
}
