package otc.healthcare.dao;

import org.w3c.dom.css.ElementCSSInlineStyle;

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
		}else if (type.equals("sqlserver")){
			iConnection = new SQLServerConnection(url, username, password);
		}else
			return;
	}

	public IConnection getInstance() {
		return iConnection;
	}

	public DBUtil getDBUtil() {
		return new DBUtil(iConnection.getConnection());
	}
}
