package otc.healthcare.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import oracle.jdbc.OraclePreparedStatement;




public class OracleDBUtil extends DBUtil{

	public OracleDBUtil(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	public int insertDataReturnKeyByReturnInto(String insertsql,String selectsql) throws Exception {   
	    //String vsql = "insert into t1(id) values(seq_t1.nextval)";  
		//first you should insert into it
	    PreparedStatement pstmt =(PreparedStatement)getConn().prepareStatement(insertsql);  
	    pstmt.executeUpdate();  
	    pstmt.close();  
	    //then select it out
	    //vsql="select TABLE_TABLEID.currval as id from SYSTEM.HC_TABLE";  
	    pstmt =(PreparedStatement)getConn().prepareStatement(selectsql);  
	    ResultSet rs=pstmt.executeQuery();  
	    rs.next();  
	    int id=rs.getInt(1);  
	    rs.close();  
	    pstmt.close();  
	    System.out.print("id:"+id);  
	    return id; 
	}  
}
