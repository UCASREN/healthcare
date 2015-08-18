package otc.healthcare.service;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import otc.healthcare.dao.ConnectionFactory;
import otc.healthcare.dao.OracleDBUtil;
import otc.healthcare.pojo.DatabaseInfo;
import otc.healthcare.pojo.FieldInfo;
import otc.healthcare.pojo.TableInfo;
/**
 * @author xingkong
 */
@Component
public class OracleService implements IService {

	
	public List<DatabaseInfo> getALLDatabaseInfo() {
		List<DatabaseInfo> resultList=new ArrayList<DatabaseInfo>();
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", "jdbc:oracle:thin:@localhost:1521:XE",
				"system", "cuiguangfan");
		OracleDBUtil dbUtil=new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil.query("select * from \"SYSTEM\".\"database\"");
			while (res.next()) {
				DatabaseInfo dim=new DatabaseInfo();
				dim.setDatabaseid(res.getString(1));
				dim.setName(res.getString(2));
				dim.setComments(res.getString(3));
				resultList.add(dim);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return resultList;
	}
	public List<TableInfo> getDatabaseInfo(String databaseid){
		List<TableInfo> resultList=new ArrayList<TableInfo>();
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", "jdbc:oracle:thin:@localhost:1521:XE",
				"system", "cuiguangfan");
		OracleDBUtil dbUtil=new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil.query("select * from \"SYSTEM\".\"table\" where \"table\".\"databaseid\"="+databaseid);
			while (res.next()) {
				
				TableInfo tim=new TableInfo();
				tim.setTableid(res.getString(1));
				tim.setDatabaseid(res.getString(2));
				tim.setName(res.getString(3));
				tim.setComments(res.getString(4));
				resultList.add(tim);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return resultList;
	}
	public List<FieldInfo> getTableInfo(String tableid){
		List<FieldInfo> resultList=new ArrayList<FieldInfo>();
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", "jdbc:oracle:thin:@localhost:1521:XE",
				"system", "cuiguangfan");
		OracleDBUtil dbUtil=new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil.query("select * from \"SYSTEM\".\"field\"");
			while (res.next()) {
				FieldInfo fim=new FieldInfo();
				fim.setFieldid(res.getString(1));
				fim.setDatabaseid(res.getString(2));
				fim.setTableid(res.getString(3));
				fim.setName(res.getString(4));
				fim.setDatatype(res.getString(5));
				fim.setComments(res.getString(6));
				resultList.add(fim);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return resultList;
	}
	public boolean deleteDatabase(String databaseid){
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", "jdbc:oracle:thin:@localhost:1521:XE",
				"system", "cuiguangfan");
		OracleDBUtil dbUtil=new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			return dbUtil.execute("delete from \"SYSTEM\".\"database\" where \"database\".\"databaseid\"="+databaseid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return false;
	}
	public boolean deleteTable(String databaseid,String tableid){
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", "jdbc:oracle:thin:@localhost:1521:XE",
				"system", "cuiguangfan");
		OracleDBUtil dbUtil=new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			return dbUtil.execute("delete from \"SYSTEM\".\"table\" where \"table\".\"databaseid\"="+databaseid+" and "
					+"\"table\".\"tableid\"="+tableid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return false;
	}
	public Integer createTable(String databaseid,String tablename,String comment){//insert into database table one row
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", "jdbc:oracle:thin:@localhost:1521:XE",
				"system", "cuiguangfan");
		OracleDBUtil dbUtil=new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			String vsql="insert into \"SYSTEM\".\"table\" (\"tableid\",\"databaseid\",\"name\",\"comments\") values(\"table_tableid\".nextval,"+databaseid
					+",'"+tablename+"','"+comment+"')";
			return dbUtil.insertDataReturnKeyByReturnInto(vsql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return null;
	}
	public boolean changeDatabase(String databaseid,String newName,String newComments){
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", "jdbc:oracle:thin:@localhost:1521:XE",
				"system", "cuiguangfan");
		OracleDBUtil dbUtil=new OracleDBUtil(connectionFactory.getInstance().getConnection());
		String sql="";
		try {
		if(newName!=null){
			sql="update \"SYSTEM\".\"database\" set \"database\".\"name\"='"+newName+"'  where \"database\".\"databaseid\"="+databaseid;
			dbUtil.execute(sql);
		}
		if(newComments!=null){
			sql="update \"SYSTEM\".\"database\" set \"database\".\"comments\"='"+newComments+"'  where \"database\".\"databaseid\"="+databaseid;
			dbUtil.execute(sql);
		}
		return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return false;
	}
	public boolean changeTable(String databaseid,String tableid,String newName,String newComments){
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", "jdbc:oracle:thin:@localhost:1521:XE",
				"system", "cuiguangfan");
		OracleDBUtil dbUtil=new OracleDBUtil(connectionFactory.getInstance().getConnection());
		String sql="";
		try {
		if(newName!=null){
			sql="update \"SYSTEM\".\"table\" set \"table\".\"name\"='"+newName+"'  where \"table\".\"databaseid\"="+databaseid+" and \"table\".\"tableid\"="+tableid;
			dbUtil.execute(sql);
		}
		if(newComments!=null){
			sql="update \"SYSTEM\".\"table\" set \"table\".\"comments\"='"+newComments+"'  where \"table\".\"databaseid\"="+databaseid+" and \"table\".\"tableid\"="+tableid;
			dbUtil.execute(sql);
		}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return false;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int checkStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

}
