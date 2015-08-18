package otc.healthcare.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import otc.healthcare.dao.ConnectionFactory;
import otc.healthcare.pojo.DatabaseInfo;
import otc.healthcare.pojo.FieldInfo;
import otc.healthcare.pojo.TableInfo;
import otc.healthcare.util.DBUtil;
import otc.healthcare.util.HealthcareConfiguration;
import otc.healthcare.util.SpringWiredBean;


public class dbConnect {
	
	private  HealthcareConfiguration hcConfiguration;
	private  HealthcareConfiguration getHealthcareConfiguration() {
		if(hcConfiguration==null)
			hcConfiguration = SpringWiredBean.getInstance().getBeanByClass(HealthcareConfiguration.class);
		return hcConfiguration;
	}
	
	public static void main(String[] args){
		
		List<DatabaseInfo> resultList = new ArrayList<DatabaseInfo>();
		
//		String oracle_url = getHealthcareConfiguration().getProperty(HealthcareConfiguration.DB_URL);
//		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
//		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		
		String oracle_username = "SYSTEM";  
	    String oracle_password = "123456"; 
	    String oracle_url = "jdbc:oracle:thin:@localhost:1521:XE";
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url,oracle_username, oracle_password);
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		
		//GET
		List<String> dbList = getDataBaseList(dbUtil);//得到用户（数据库）
		Map<String,List<TableInfo>> tableMap = getTableMap(dbUtil,dbList);
		Map<String,List<FieldInfo>> fieldMap = getFieldMap(dbUtil,tableMap);
		
		//DELETE
		InitOracle(dbUtil);
		
		//INSERT
		insertDB(dbUtil,dbList);
		insertTable(dbUtil,tableMap);
		insertField(dbUtil,fieldMap);
		
		dbUtil.close();
		System.out.println("All Done!");
	}

	//信息初始化
	private static void InitOracle(DBUtil dbUtil) {
		dbUtil.execute("delete from SYSTEM.HC_DATABASE");
		dbUtil.execute("delete from SYSTEM.HC_TABLE");
		dbUtil.execute("delete from SYSTEM.HC_FIELD");
		
	}
	
	//获取列相关信息
	private static Map<String, List<FieldInfo>> getFieldMap(DBUtil dbUtil, Map<String, List<TableInfo>> tableMap) {
		Map<String,List<FieldInfo>> fieldMap = new HashMap<String, List<FieldInfo>>();
		
		try {
			for(String dbName : tableMap.keySet()){
				for(TableInfo table : tableMap.get(dbName)){
					String tableName = table.getName();
					List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
					String query_sql = "SELECT t1.COLUMN_NAME, t1.DATA_TYPE, t1.DATA_LENGTH , t1.NULLABLE,t2.comments"
							+ " FROM ALL_TAB_COLS t1 inner join ALL_col_comments t2 on t2.TABLE_NAME = t1.TABLE_NAME and t1.COLUMN_NAME = t2.COLUMN_NAME "
							+ "and t1.OWNER = '"+dbName+"' and t1.TABLE_NAME='"+tableName+"'";
					ResultSet rs = dbUtil.query(query_sql);
					while(rs.next()){
						FieldInfo field = new FieldInfo();
						field.setName(rs.getString(1));
						field.setDatatype(rs.getString(2));
						field.setDatalength(rs.getString(3));
						field.setNullable(rs.getString(4));
						field.setComments(rs.getString(5));
						fieldList.add(field);
					}
					rs.close();
					fieldMap.put(dbName+","+tableName, fieldList);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fieldMap;
	}

	//获取表相关信息
	private static Map<String,List<TableInfo>> getTableMap(DBUtil dbUtil, List<String> dbList) {
		Map<String,List<TableInfo>> TableMap = new HashMap<String,List<TableInfo>>();
		try {
			for(String dbName : dbList){
				List<TableInfo> tableList = new ArrayList<TableInfo>();
				String query_sql = "select TABLE_NAME,COMMENTS from all_tab_comments t2 WHERE OWNER='"+dbName+"' AND table_type='TABLE'";
				ResultSet rs = dbUtil.query(query_sql);
				while(rs.next()){
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

	//获取数据库相关信息（用户信息）
	private static List<String> getDataBaseList(DBUtil dbUtil) {
		List<String> dataBaseList = new ArrayList<String>();
		try {
			/*
			 * SELECT * from ALL_TABLES where OWNER = 'SYSTEM' AND INSTR(TABLE_NAME,'SQLPLUS')=0
         		AND INSTR(TABLE_NAME，'$')=0 AND INSTR(TABLE_NAME，'HELP')=0 AND INSTR(TABLE_NAME,'LOGMNR')=0;
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


	private static void insertField(DBUtil dbUtil , Map<String, List<FieldInfo>> fieldMap) {
		String sql = "insert into HC_FIELD( "
				+ "FIELDID,NAME,DATATYPE,DATALENGTH,COMMENTS,NOTNULL,TABLEID,DATABASEID) "
				+ "values(FIELD_FIELDID.nextval,?,?,?,?,?,?,?)";
		
		for(String key : fieldMap.keySet()){
			String dbName = key.split(",")[0];
			String tableName = key.split(",")[1];
			String dbID = getDBid(dbUtil,dbName);
			String tableID = getTableID(dbUtil,tableName);
			List<FieldInfo> tmpList = fieldMap.get(key);
			for(FieldInfo f : tmpList){
				List<String> list = new ArrayList<String>();
				list.add(f.getName());
				list.add(f.getDatatype());
				list.add(f.getDatalength());
				list.add(f.getComments());
				list.add(f.getNullable());
				list.add(tableID);
				list.add(dbID);
				dbUtil.execute(sql,list);
			}
		}
	}

	private static void insertTable(DBUtil dbUtil , Map<String, List<TableInfo>> tableMap) {
		String sql = "insert into HC_TABLE(TABLEID,NAME,COMMENTS,DATABASEID) values(TABLE_TABLEID.nextval,?,?,?)";
		for(String dbName : tableMap.keySet()){
			String dbID = getDBid(dbUtil,dbName);
			List<TableInfo> tmpList = tableMap.get(dbName);
			for(TableInfo t : tmpList){
				List<String> data = new ArrayList<String>();
				data.add(t.getName());
				data.add(t.getComments());
				data.add(String.valueOf(dbID));
				dbUtil.execute(sql,data);
			}
		}
	}

	private static List<String> toList(String[] data) {
		List<String> rs = new ArrayList<String>();
		for(int i=0; i<data.length; i++)
			rs.add(data[i]);
		return rs;
	}

	private static void insertDB(DBUtil dbUtil , List<String> dataBaseList) {
		String sql = "insert into SYSTEM.HC_DATABASE (DATABASEID,NAME) values(DATABASE_DATABASEID.nextval,?)";
		for(String dbName : dataBaseList){
			List<String> tmp = new ArrayList<String>();
			tmp.add(dbName);
			dbUtil.execute(sql,tmp);
		}
	}
	
	private static String getTableID(DBUtil dbUtil , String tableName) {
		String tableID = new String();
		String query_sql = "select TABLEID from HC_TABLE where NAME = '"+tableName+"'";
		ResultSet rs = dbUtil.query(query_sql);
		try {
			while(rs.next()){
				tableID = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableID;
	}
	
	private static String getDBid(DBUtil dbUtil , String dbName) {
		String DBid = new String();
		String query_sql = "select DATABASEID from HC_DATABASE where NAME = '"+dbName+"'";
		ResultSet rs = dbUtil.query(query_sql);
		try {
			while(rs.next()){
				DBid = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return DBid;
	}

}//end class
