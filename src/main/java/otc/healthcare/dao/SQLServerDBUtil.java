package otc.healthcare.dao;

import java.sql.Connection;

import otc.healthcare.util.DBUtil;


public class SQLServerDBUtil extends DBUtil{

	public SQLServerDBUtil(Connection conn) {
		super(conn);
	}
	
}
