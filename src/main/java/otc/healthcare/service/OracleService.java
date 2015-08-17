package otc.healthcare.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import otc.healthcare.dao.ConnectionFactory;
import otc.healthcare.pojo.DatabaseInfo;
import otc.healthcare.pojo.FieldInfo;
import otc.healthcare.pojo.TableInfo;
import otc.healthcare.util.DBUtil;
@Component
public class OracleService implements IService {

	
	public List<DatabaseInfo> getALLDatabaseInfo() {
		List<DatabaseInfo> resultList=new ArrayList<DatabaseInfo>();
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", "jdbc:oracle:thin:@localhost:1521:XE",
				"system", "cuiguangfan");
		DBUtil dbUtil=new DBUtil(connectionFactory.getInstance().getConnection());
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
		DBUtil dbUtil=new DBUtil(connectionFactory.getInstance().getConnection());
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
		DBUtil dbUtil=new DBUtil(connectionFactory.getInstance().getConnection());
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
		DBUtil dbUtil=new DBUtil(connectionFactory.getInstance().getConnection());
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
		DBUtil dbUtil=new DBUtil(connectionFactory.getInstance().getConnection());
		try {
			return dbUtil.execute("delete from \"SYSTEM\".\"table\" where \"table\".\"databaseid\"="+databaseid
					+"\"table\".\"tableid\"="+tableid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return false;
	}
	public boolean createTable(String databaseid,String tablename,String comment){//insert into database table one row
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", "jdbc:oracle:thin:@localhost:1521:XE",
				"system", "cuiguangfan");
		DBUtil dbUtil=new DBUtil(connectionFactory.getInstance().getConnection());
		try {
			return dbUtil.execute("insert into \"SYSTEM\".\"table\" (\"tableid\",\"databaseid\",\"name\",\"comments\") values(\"table_tableid\".nextval,"+databaseid
					+","+tablename+","+comment+");");
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
