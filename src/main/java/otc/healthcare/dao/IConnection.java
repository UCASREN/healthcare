package otc.healthcare.dao;

import java.sql.Connection;
import java.util.List;

public interface IConnection {
	public Connection getConnection();

	public void closeConnection();

	// public List<String> showDatabases();

	public List<String> showTables();

	// public List<String> showTables(String databaseName);

	public List<String> showFields(String tableName);
}
