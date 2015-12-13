package otc.healthcare.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import oracle.jdbc.OraclePreparedStatement;


public class SQLServerDBUtil extends DBUtil{

	public SQLServerDBUtil(Connection conn) {
		super(conn);
	}
	
}
