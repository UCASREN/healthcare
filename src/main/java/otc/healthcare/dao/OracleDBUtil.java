package otc.healthcare.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import oracle.jdbc.OraclePreparedStatement;

public class OracleDBUtil extends DBUtil{

	public OracleDBUtil(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	public int insertDataReturnKeyByReturnInto(String vsql) throws Exception {  
	    //String vsql = "insert into t1(id) values(seq_t1.nextval) returning id into :1";  
	    OraclePreparedStatement pstmt =(OraclePreparedStatement)getConn().prepareStatement(vsql);  
	    pstmt.registerReturnParameter(1, Types.BIGINT);  
	    pstmt.executeUpdate();  
	    ResultSet rs=pstmt.getReturnResultSet();  
	    rs.next();  
	    int id=rs.getInt(1);  
	    rs.close();  
	    pstmt.close();  
	    System.out.print("id:"+id);  
	    return id;  
	}  
}
