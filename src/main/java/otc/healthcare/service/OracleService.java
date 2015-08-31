package otc.healthcare.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import otc.healthcare.dao.ConnectionFactory;
import otc.healthcare.dao.OracleDBUtil;
import otc.healthcare.pojo.DatabaseInfo;
import otc.healthcare.pojo.FieldInfo;
import otc.healthcare.pojo.TableInfo;
import otc.healthcare.util.DBUtil;
import otc.healthcare.util.HealthcareConfiguration;

@Component
public class OracleService implements IService {

	@Autowired
	private HealthcareConfiguration hcConfiguration;

	public List<DatabaseInfo> getALLDatabaseInfo() {
		List<DatabaseInfo> resultList = new ArrayList<DatabaseInfo>();
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil.query("select * from SYSTEM.HC_DATABASE");
			while (res.next()) {
				DatabaseInfo dim = new DatabaseInfo();
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
	public Map<String,String> getDatabaseSummary(String databaseid){
		Map<String,String> databaseSummary=new HashMap<String,String>();
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil.query("select * from SYSTEM.HC_DATABASE where DATABASEID=" + databaseid);
			while (res.next()) {
				databaseSummary.put("name", res.getString(2));
				databaseSummary.put("comments", res.getString(3));
				databaseSummary.put("others", "still need to be filled");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return databaseSummary;
	}
	public List<TableInfo> getDatabaseInfo(String databaseid) {
		List<TableInfo> resultList = new ArrayList<TableInfo>();
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil.query("select * from SYSTEM.HC_TABLE where DATABASEID=" + databaseid);
			while (res.next()) {

				TableInfo tim = new TableInfo();
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
	public Map<String,String> getTableSummary(String databaseid,String tableid) {
		Map<String,String> tableSummary=new HashMap<String,String>();
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil.query("select * from SYSTEM.HC_TABLE where DATABASEID=" + databaseid+" AND TABLEID="+tableid);
			while (res.next()) {
				tableSummary.put("name", res.getString(3));
				tableSummary.put("comments", res.getString(4));
				tableSummary.put("others", "still need to be filled");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return tableSummary;
	}
	
	public List<FieldInfo> getTableInfo(String databaseid, String tableid) {
		List<FieldInfo> resultList = new ArrayList<FieldInfo>();
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil
					.query("select FIELDID,TABLEID,DATABASEID,NAME,COMMENTS from SYSTEM.HC_FIELD where DATABASEID="
							+ databaseid + " and TABLEID=" + tableid);
			while (res.next()) {

				FieldInfo fim = new FieldInfo();
				fim.setFieldid(res.getString(1));
				fim.setTableid(res.getString(2));
				fim.setDatabaseid(res.getString(3));
				fim.setName(res.getString(4));
				fim.setComments(res.getString(5));
				resultList.add(fim);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return resultList;
	}
	public List<FieldInfo> getAllTableInfo() {
		List<FieldInfo> resultList = new ArrayList<FieldInfo>();
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil
					.query("select FIELDID,TABLEID,DATABASEID,NAME,COMMENTS from SYSTEM.HC_FIELD");
			while (res.next()) {

				FieldInfo fim = new FieldInfo();
				fim.setFieldid(res.getString(1));
				fim.setTableid(res.getString(2));
				fim.setDatabaseid(res.getString(3));
				fim.setName(res.getString(4));
				fim.setComments(res.getString(5));
				resultList.add(fim);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return resultList;
	}

	public boolean createHcDB() {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		try {

			// GET
			List<String> dbList = getDataBaseList(dbUtil);// 得到用户（数据库）
			Map<String, List<TableInfo>> tableMap = getTableMap(dbUtil, dbList);
			Map<String, List<FieldInfo>> fieldMap = getFieldMap(dbUtil, tableMap);

			// DELETE
			InitOracle(dbUtil);

			// INSERT
			insertDB(dbUtil, dbList);
			insertTable(dbUtil, tableMap);
			insertField(dbUtil, fieldMap);

			System.out.println("create healthCare DataBase Done!");
			return true;
		} catch (Exception e) {
			System.out.println("create healthCare DataBase Error!");
			e.printStackTrace();
			return false;
		} finally {
			dbUtil.close();
		}
	}

	// 信息初始化
	private static void InitOracle(DBUtil dbUtil) {
		dbUtil.execute("delete from SYSTEM.HC_DATABASE");
		dbUtil.execute("delete from SYSTEM.HC_TABLE");
		dbUtil.execute("delete from SYSTEM.HC_FIELD");

	}

	// 获取列相关信息
	private static Map<String, List<FieldInfo>> getFieldMap(DBUtil dbUtil, Map<String, List<TableInfo>> tableMap) {
		Map<String, List<FieldInfo>> fieldMap = new HashMap<String, List<FieldInfo>>();

		try {
			for (String dbName : tableMap.keySet()) {
				for (TableInfo table : tableMap.get(dbName)) {
					String tableName = table.getName();
					List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
					String query_sql = "SELECT t1.COLUMN_NAME, t1.DATA_TYPE, t1.DATA_LENGTH , t1.NULLABLE,t2.comments"
							+ " FROM ALL_TAB_COLS t1 inner join ALL_col_comments t2 on t2.TABLE_NAME = t1.TABLE_NAME and t1.COLUMN_NAME = t2.COLUMN_NAME "
							+ "and t1.OWNER = '" + dbName + "' and t1.TABLE_NAME='" + tableName + "'";
					ResultSet rs = dbUtil.query(query_sql);
					while (rs.next()) {
						FieldInfo field = new FieldInfo();
						field.setName(rs.getString(1));
						field.setDatatype(rs.getString(2));
						field.setDatalength(rs.getString(3));
						field.setNullable(rs.getString(4));
						field.setComments(rs.getString(5));
						fieldList.add(field);
					}
					rs.close();
					fieldMap.put(dbName + "," + tableName, fieldList);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fieldMap;
	}

	// 获取表相关信息
	private static Map<String, List<TableInfo>> getTableMap(DBUtil dbUtil, List<String> dbList) {
		Map<String, List<TableInfo>> TableMap = new HashMap<String, List<TableInfo>>();
		try {
			for (String dbName : dbList) {
				List<TableInfo> tableList = new ArrayList<TableInfo>();
				String query_sql = "select TABLE_NAME,COMMENTS from all_tab_comments t2 WHERE OWNER='" + dbName
						+ "' AND table_type='TABLE'";
				ResultSet rs = dbUtil.query(query_sql);
				while (rs.next()) {
					TableInfo tableInfo = new TableInfo();
					tableInfo.setName(rs.getString(1));
					tableInfo.setComments(rs.getString(2));
					tableList.add(tableInfo);
				}
				TableMap.put(dbName, tableList);
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return TableMap;
	}

	// 获取数据库相关信息（用户信息）
	private static List<String> getDataBaseList(DBUtil dbUtil) {
		List<String> dataBaseList = new ArrayList<String>();
		try {
			/*
			 * SELECT * from ALL_TABLES where OWNER = 'SYSTEM' AND
			 * INSTR(TABLE_NAME,'SQLPLUS')=0 AND INSTR(TABLE_NAME，'$')=0 AND
			 * INSTR(TABLE_NAME，'HELP')=0 AND INSTR(TABLE_NAME,'LOGMNR')=0;
			 */
			ResultSet res = dbUtil.query("select * from all_users where username='HR'");
			while (res.next()) {
				dataBaseList.add(res.getString(1));
			}
			res.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataBaseList;
	}

	private static void insertField(DBUtil dbUtil, Map<String, List<FieldInfo>> fieldMap) {
		String sql = "insert into HC_FIELD( " + "FIELDID,NAME,DATATYPE,DATALENGTH,COMMENTS,NOTNULL,TABLEID,DATABASEID) "
				+ "values(FIELD_FIELDID.nextval,?,?,?,?,?,?,?)";

		for (String key : fieldMap.keySet()) {
			String dbName = key.split(",")[0];
			String tableName = key.split(",")[1];
			String dbID = getDBid(dbUtil, dbName);
			String tableID = getTableID(dbUtil, tableName);
			List<FieldInfo> tmpList = fieldMap.get(key);
			for (FieldInfo f : tmpList) {
				List<String> list = new ArrayList<String>();
				list.add(f.getName());
				list.add(f.getDatatype());
				list.add(f.getDatalength());
				list.add(f.getComments());
				list.add(f.getNullable());
				list.add(tableID);
				list.add(dbID);
				dbUtil.execute(sql, list);
			}
		}
	}

	private static void insertTable(DBUtil dbUtil, Map<String, List<TableInfo>> tableMap) {
		String sql = "insert into HC_TABLE(TABLEID,NAME,COMMENTS,DATABASEID) values(TABLE_TABLEID.nextval,?,?,?)";
		for (String dbName : tableMap.keySet()) {
			String dbID = getDBid(dbUtil, dbName);
			List<TableInfo> tmpList = tableMap.get(dbName);
			for (TableInfo t : tmpList) {
				List<String> data = new ArrayList<String>();
				data.add(t.getName());
				data.add(t.getComments());
				data.add(String.valueOf(dbID));
				dbUtil.execute(sql, data);
			}
		}
	}

	private static List<String> toList(String[] data) {
		List<String> rs = new ArrayList<String>();
		for (int i = 0; i < data.length; i++)
			rs.add(data[i]);
		return rs;
	}

	private static void insertDB(DBUtil dbUtil, List<String> dataBaseList) {
		String sql = "insert into SYSTEM.HC_DATABASE (DATABASEID,NAME) values(DATABASE_DATABASEID.nextval,?)";
		for (String dbName : dataBaseList) {
			List<String> tmp = new ArrayList<String>();
			tmp.add(dbName);
			dbUtil.execute(sql, tmp);
		}
	}

	private static String getTableID(DBUtil dbUtil, String tableName) {
		String tableID = new String();
		String query_sql = "select TABLEID from HC_TABLE where NAME = '" + tableName + "'";
		ResultSet rs = dbUtil.query(query_sql);
		try {
			while (rs.next()) {
				tableID = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableID;
	}

	private static String getDBid(DBUtil dbUtil, String dbName) {
		String DBid = new String();
		String query_sql = "select DATABASEID from HC_DATABASE where NAME = '" + dbName + "'";
		ResultSet rs = dbUtil.query(query_sql);
		try {
			while (rs.next()) {
				DBid = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return DBid;
	}

	public boolean deleteDatabase(String databaseid) {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			dbUtil.execute("delete from SYSTEM.HC_DATABASE where DATABASEID=" + databaseid);//删除HC_DATABASE表
			dbUtil.execute("delete from SYSTEM.HC_TABLE where DATABASEID=" + databaseid);//删除HC_TABLE表
			dbUtil.execute("delete from SYSTEM.HC_FIELD where DATABASEID=" + databaseid);//删除HC_FIELD表
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return false;
	}

	public boolean deleteTable(String databaseid, String tableid) {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			return dbUtil.execute(
					"delete from SYSTEM.HC_TABLE where DATABASEID=" + databaseid + " and " + "TABLEID=" + tableid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return false;
	}
	public boolean deleteField(String databaseid, String tableid,String fieldid) {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			return dbUtil.execute(
					"delete from SYSTEM.HC_FIELD where DATABASEID=" + databaseid + " and " + "TABLEID=" + tableid+" and "+"FIELDID="+fieldid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return false;
	}
	public Integer createDatabase(String databasename, String comments) {// insert into database table row
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			String vsql = "insert into SYSTEM.HC_DATABASE (DATABASEID,NAME,COMMENTS) values(DATABASE_DATABASEID.nextval,"
					+ "'" + databasename + "','" + comments + "')";
			return dbUtil.insertDataReturnKeyByReturnInto(vsql,"select DATABASE_DATABASEID.currval as id from SYSTEM.HC_DATABASE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return null;
	}
	public Integer createTable(String databaseid, String tablename, String comments) {// insert into database table row
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			String vsql = "insert into SYSTEM.HC_TABLE (TABLEID,DATABASEID,NAME,COMMENTS) values(TABLE_TABLEID.nextval,"
					+ databaseid + ",'" + tablename + "','" + comments + "')";
			return dbUtil.insertDataReturnKeyByReturnInto(vsql,"select TABLE_TABLEID.currval as id from SYSTEM.HC_TABLE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return null;
	}

	public Integer createField(String databaseid, String tableid, String fieldname,String comments) {// insert
		// into database table one row
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			String vsql = "insert into SYSTEM.HC_FIELD (FIELDID,TABLEID,DATABASEID,NAME,COMMENTS) values(FIELD_FIELDID.nextval,"
					+ tableid + ","+ databaseid + ",'" + fieldname + "','" + comments + "')";
			return dbUtil.insertDataReturnKeyByReturnInto(vsql,"select FIELD_FIELDID.currval as id from SYSTEM.HC_FIELD");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return null;
	}

	public boolean changeDatabase(String databaseid, String newName, String newComments) {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		String sql = "";
		try {
			if (newName != null) {
				sql = "update SYSTEM.HC_DATABASE set NAME='" + newName + "'  where DATABASEID=" + databaseid;
				dbUtil.execute(sql);
			}
			if (newComments != null) {
				sql = "update SYSTEM.HC_DATABASE set COMMENTS='" + newComments + "'  where DATABASEID=" + databaseid;
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

	public boolean changeTable(String databaseid, String tableid, String newName, String newComments) {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		String sql = "";
		try {
			if (newName != null) {
				sql = "update SYSTEM.HC_TABLE set NAME='" + newName + "'  where DATABASEID=" + databaseid
						+ " and TABLEID=" + tableid;
				dbUtil.execute(sql);
			}
			if (newComments != null) {
				sql = "update SYSTEM.HC_TABLE set COMMENTS='" + newComments + "'   where DATABASEID=" + databaseid
						+ " and TABLEID=" + tableid;
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
	public boolean changeField(String fieldid,String databaseid, String tableid, String newName, String newComments) {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		String sql = "";
		try {
			if (newName != null) {
				sql = "update SYSTEM.HC_FIELD set NAME='" + newName + "'  where DATABASEID=" + databaseid
						+ " and TABLEID=" + tableid+ " and FIELDID=" + fieldid;
				dbUtil.execute(sql);
			}
			if (newComments != null) {
				sql = "update SYSTEM.HC_FIELD set COMMENTS='" + newComments + "'   where DATABASEID=" + databaseid
						+ " and TABLEID=" + tableid+ " and FIELDID=" + fieldid;
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
