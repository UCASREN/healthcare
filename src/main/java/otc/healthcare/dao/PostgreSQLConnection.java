package otc.healthcare.dao;

import java.util.List;

public class PostgreSQLConnection extends ConnectionBase implements IConnection {
	private final static String driverName = "org.postgresql.Driver";

	public PostgreSQLConnection(String url, String username, String password) {
		super(driverName, url, username, password);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> showTables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> showFields(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

}
