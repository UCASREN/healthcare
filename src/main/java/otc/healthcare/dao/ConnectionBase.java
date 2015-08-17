package otc.healthcare.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public abstract class ConnectionBase implements IConnection {
	protected String driverName;
	protected String url;
	protected String username;
	protected String password;

	// private BoneCP connectionPool = null;
	private Connection conn = null;

	public ConnectionBase(String driverName, String url, String username,
			String password) {
		this.driverName = driverName;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public Connection getConnection() {
		try {
			Class.forName(driverName);
			// setup the connection pool
			// BoneCPConfig config = new BoneCPConfig();
			// config.setJdbcUrl(url); // jdbc url specific to
			// config.setUsername(username);
			// config.setPassword(password);
			// // 设置每60秒检查数据库中的空闲连接数
			// // 设置每个分区中的最大连接数 30
			// config.setMaxConnectionsPerPartition(30);
			// // 设置每个分区中的最小连接数 10
			// config.setMinConnectionsPerPartition(10);
			// // 当连接池中的连接耗尽的时候 BoneCP一次同时获取的连接数
			// config.setAcquireIncrement(5);
			// // 连接释放处理
			// config.setReleaseHelperThreads(3);
			// // 设置分区 分区数为3
			// config.setPartitionCount(3);
			// connectionPool = new BoneCP(config); // setup the connection pool
			//
			// conn = connectionPool.getConnection(); // fetch a connection
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	public void closeConnection() {
		try {
			if (null != conn)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
