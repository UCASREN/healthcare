package otc.healthcare.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import otc.healthcare.dao.ConnectionFactory;
import otc.healthcare.dao.HcApplydataDao;
import otc.healthcare.dao.HcApplyenvDao;
import otc.healthcare.dao.OracleDBUtil;
import otc.healthcare.pojo.ClassificationInfo;
import otc.healthcare.pojo.DatabaseInfo;
import otc.healthcare.pojo.FieldInfo;
import otc.healthcare.pojo.HcApplydata;
import otc.healthcare.pojo.HcApplyenv;
import otc.healthcare.pojo.TableInfo;
import otc.healthcare.util.DBUtil;
import otc.healthcare.util.HealthcareConfiguration;

@Component
public class OracleService implements IService {

	@Autowired
	private HealthcareConfiguration hcConfiguration;
	@Autowired
	private HcApplydataDao hcApplydataDao;
	@Autowired
	private HcApplyenvDao hcApplyenvDao;

	public HcApplydataDao getHcApplydataDao() {
		return hcApplydataDao;
	}

	public void setHcApplydataDao(HcApplydataDao hcApplydataDao) {
		this.hcApplydataDao = hcApplydataDao;
	}

	public HcApplyenvDao getHcApplyenvDao() {
		return hcApplyenvDao;
	}

	public void setHcApplyenvDao(HcApplyenvDao hcApplyenvDao) {
		this.hcApplyenvDao = hcApplyenvDao;
	}

	public boolean testConnection(String oracle_url, String oracle_username, String oracle_password) {
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		if (connectionFactory.getInstance().getConnection() != null)
			return true;
		return false;
	}

	public List<ClassificationInfo> getAllClassificationDatabaseInfoWithClass() {
		List<ClassificationInfo> classificationInfoList = new ArrayList<ClassificationInfo>();
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		// ConnectionFactory connectionFactory1 = new
		// ConnectionFactory("oracle", oracle_url, oracle_username,
		// oracle_password);
		// OracleDBUtil dbUtil1 = new
		// OracleDBUtil(connectionFactory1.getInstance().getConnection());
		try {
			ResultSet res = dbUtil.query("select * from SYSTEM.HC_CLASSIFICATION");
			while (res.next()) {
				ClassificationInfo classificationInfo = new ClassificationInfo();
				classificationInfo.setClassificationid(res.getString(1));
				classificationInfo.setName(res.getString(2));
				classificationInfo.setComments(res.getString(3));
				List<DatabaseInfo> databaseInfoList = new ArrayList<DatabaseInfo>();
				ResultSet res_list = dbUtil
						.query("select * from SYSTEM.HC_DATABASE WHERE SUBJECTCLASSIFICATION=" + res.getString(1));
				while (res_list.next()) {
					DatabaseInfo dim = new DatabaseInfo();
					dim.setDatabaseid(res_list.getString(1));
					dim.setName(res_list.getString(2));
					dim.setComments(res_list.getString(3));
					dim.setIdentifier(res_list.getString(4));
					dim.setLanguage(res_list.getString(5));
					dim.setCharset(res_list.getString(6));
					dim.setSubjectclassification(res_list.getString(7));
					dim.setKeywords(res_list.getString(8));
					dim.setCredibility(res_list.getString(9));
					dim.setResinstitution(res_list.getString(10));
					dim.setResname(res_list.getString(11));
					dim.setResaddress(res_list.getString(12));
					dim.setRespostalcode(res_list.getString(13));
					dim.setResphone(res_list.getString(14));
					dim.setResemail(res_list.getString(15));
					dim.setResourceurl(res_list.getString(16));
					dim.setZhcnname(res_list.getString(17));
					databaseInfoList.add(dim);
				}
				classificationInfo.setDatabaseinfolist(databaseInfoList);
				classificationInfoList.add(classificationInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return classificationInfoList;
	}

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
				dim.setIdentifier(res.getString(4));
				dim.setLanguage(res.getString(5));
				dim.setCharset(res.getString(6));
				dim.setSubjectclassification(res.getString(7));
				dim.setKeywords(res.getString(8));
				dim.setCredibility(res.getString(9));
				dim.setResinstitution(res.getString(10));
				dim.setResname(res.getString(11));
				dim.setResaddress(res.getString(12));
				dim.setRespostalcode(res.getString(13));
				dim.setResphone(res.getString(14));
				dim.setResemail(res.getString(15));
				dim.setResourceurl(res.getString(16));
				dim.setZhcnname(res.getString(17));
				resultList.add(dim);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return resultList;
	}

	public List<DatabaseInfo> getDatabaseInfoWithClass(String classificationid) {
		List<DatabaseInfo> resultList = new ArrayList<DatabaseInfo>();
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil
					.query("select * from SYSTEM.HC_DATABASE WHERE SUBJECTCLASSIFICATION=" + classificationid);
			while (res.next()) {
				DatabaseInfo dim = new DatabaseInfo();
				dim.setDatabaseid(res.getString(1));
				dim.setName(res.getString(2));
				dim.setComments(res.getString(3));
				dim.setIdentifier(res.getString(4));
				dim.setLanguage(res.getString(5));
				dim.setCharset(res.getString(6));
				dim.setSubjectclassification(res.getString(7));
				dim.setKeywords(res.getString(8));
				dim.setCredibility(res.getString(9));
				dim.setResinstitution(res.getString(10));
				dim.setResname(res.getString(11));
				dim.setResaddress(res.getString(12));
				dim.setRespostalcode(res.getString(13));
				dim.setResphone(res.getString(14));
				dim.setResemail(res.getString(15));
				dim.setResourceurl(res.getString(16));
				dim.setZhcnname(res.getString(17));
				resultList.add(dim);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return resultList;
	}
	public Map<String,String> getClassificationSummary(String classificationid){
		Map<String, String> classificationSummary = new HashMap<String, String>();
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil.query("select * from SYSTEM.HC_CLASSIFICATION where CLASSIFICATIONID=" + classificationid);
			while (res.next()) {
				classificationSummary.put("classificationid", res.getString(1));
				classificationSummary.put("name", res.getString(2));
				classificationSummary.put("comments", res.getString(3) == null ? "空" : res.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return classificationSummary;
	}
	public Map<String, String> getDatabaseSummary(String databaseid) {
		Map<String, String> databaseSummary = new HashMap<String, String>();
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil.query("select * from SYSTEM.HC_DATABASE where DATABASEID=" + databaseid);
			while (res.next()) {
				databaseSummary.put("databaseid", res.getString(1));
				databaseSummary.put("name", res.getString(2));
				databaseSummary.put("comments", res.getString(3) == null ? "空" : res.getString(3));
				databaseSummary.put("identifier", res.getString(4));
				databaseSummary.put("language", res.getString(5));
				databaseSummary.put("charset", res.getString(6));
				databaseSummary.put("subjectclassification", res.getString(7));
				databaseSummary.put("keywords", res.getString(8));
				databaseSummary.put("credibility", res.getString(9));
				databaseSummary.put("resinstitution", res.getString(10));
				databaseSummary.put("resname", res.getString(11));
				databaseSummary.put("resaddress", res.getString(12));
				databaseSummary.put("respostalcode", res.getString(13));
				databaseSummary.put("resphone", res.getString(14));
				databaseSummary.put("resemail", res.getString(15));
				databaseSummary.put("resourceurl", res.getString(16));
				databaseSummary.put("zhcnname", res.getString(17) == null ? "空" : res.getString(17));
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
				tim.setNumrows(res.getString(5) == null ? "0" : res.getString(5));
				tim.setZhcnname(res.getString(6));
				resultList.add(tim);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return resultList;
	}

	//
	public List<TableInfo> getDatabaseInfo(String oracle_url, String oracle_username, String oracle_password,
			String databaseid) {
		List<TableInfo> resultList = new ArrayList<TableInfo>();
		// String oracle_url =
		// hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		// String oracle_username =
		// hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		// String oracle_password =
		// hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
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
				tim.setNumrows(res.getString(5) == null ? "0" : res.getString(5));
				resultList.add(tim);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return resultList;
	}

	public Map<String, String> getTableSummary(String databaseid, String tableid) {
		Map<String, String> tableSummary = new HashMap<String, String>();
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			ResultSet res = dbUtil
					.query("select * from SYSTEM.HC_TABLE where DATABASEID=" + databaseid + " AND TABLEID=" + tableid);
			while (res.next()) {
				tableSummary.put("name", res.getString(3));
				tableSummary.put("comments", res.getString(4));
				tableSummary.put("others", "still need to be filled");
				tableSummary.put("numrows", res.getString(5) == null ? "0" : res.getString(5));
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
					.query("select FIELDID,TABLEID,DATABASEID,NAME,COMMENTS,DATADICTIONARY,ZHCNNAME from SYSTEM.HC_FIELD where DATABASEID="
							+ databaseid + " and TABLEID=" + tableid);
			while (res.next()) {

				FieldInfo fim = new FieldInfo();
				fim.setFieldid(res.getString(1));
				fim.setTableid(res.getString(2));
				fim.setDatabaseid(res.getString(3));
				fim.setName(res.getString(4));
				fim.setComments(res.getString(5));
				fim.setDatadictionary(res.getString(6));
				fim.setZhcnname(res.getString(7));
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
			ResultSet res = dbUtil.query("select FIELDID,TABLEID,DATABASEID,NAME,COMMENTS from SYSTEM.HC_FIELD");
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

	public boolean insertRemoteDB(String oracle_url, String oracle_username, String oracle_password,
			String selectedtables) {
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		ConnectionFactory connectionFactoryNative = new ConnectionFactory("oracle",
				hcConfiguration.getProperty(HealthcareConfiguration.DB_URL),
				hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME),
				hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		DBUtil dbUtilNative = new DBUtil(connectionFactoryNative.getInstance().getConnection());
		String[] tableList = selectedtables.split(",");
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		for (String temp : tableList) {
			String databaseName = temp.split(";")[0];
			String tableName = temp.split(";")[1];
			if (map.get(databaseName) == null) {
				map.put(databaseName, new ArrayList<String>());
			}
			map.get(databaseName).add(tableName);

		}
		for (String databaseName : map.keySet()) {
			dbUtilNative.query("select DATABASE_DATABASEID.nextval from dual");
			String databaseId = dbUtilNative.showListResults("select DATABASE_DATABASEID.currval from dual").get(0);
			dbUtilNative.execute("insert into SYSTEM.HC_DATABASE (DATABASEID,NAME) values(" + databaseId + ",'"
					+ databaseName + "')");
			List<String> tempList = map.get(databaseName);
			for (String tableName : tempList) {
				String talbeifno_query_sql = "select TABLE_NAME,COMMENTS from all_tab_comments t2 WHERE OWNER='"
						+ databaseName + "' AND table_type='TABLE' AND TABLE_NAME='" + tableName + "'";
				String talbeifno_query_sql_num_rows = "select NUM_ROWS from SYS.ALL_TABLES where TABLE_NAME='"
						+ tableName + "'";
				String numRows = dbUtil.showListResults(talbeifno_query_sql_num_rows).get(0);
				ResultSet rs = dbUtil.query(talbeifno_query_sql);
				try {
					while (rs.next()) {
						dbUtilNative.query("select TABLE_TABLEID.nextval from dual");
						String tableId = dbUtilNative.showListResults("select TABLE_TABLEID.currval from dual").get(0);
						dbUtilNative.execute("insert into HC_TABLE(TABLEID,NAME,COMMENTS,NUMROWS,DATABASEID) values("
								+ tableId + ",'" + tableName + "','" + rs.getString(2) + "'," + numRows + ","
								+ databaseId + ")");
						//
						String fieldinfo_query_sql = "SELECT t1.COLUMN_NAME, t1.DATA_TYPE, t1.DATA_LENGTH , t1.NULLABLE,t2.comments"
								+ " FROM ALL_TAB_COLS t1 inner join ALL_col_comments t2 on t2.TABLE_NAME = t1.TABLE_NAME and t1.COLUMN_NAME = t2.COLUMN_NAME "
								+ "and t1.OWNER = '" + databaseName + "' and t1.TABLE_NAME='" + tableName + "'";
						ResultSet field_rs = dbUtil.query(fieldinfo_query_sql);
						while (field_rs.next()) {
							dbUtilNative.query("select FIELD_FIELDID.nextval from dual");
							String fieldId = dbUtilNative.showListResults("select FIELD_FIELDID.currval from dual")
									.get(0);
							dbUtilNative.execute("insert into HC_FIELD( "
									+ "FIELDID,NAME,DATATYPE,DATALENGTH,COMMENTS,NOTNULL,TABLEID,DATABASEID) "
									+ "values(" + fieldId + ",'" + field_rs.getString(1) + "','" + field_rs.getString(2)
									+ "','" + field_rs.getString(3) + "','" + field_rs.getString(4) + "','"
									+ field_rs.getString(5) + "'," + tableId + "," + databaseId + ")");
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		dbUtil.close();
		dbUtilNative.close();
		return true;
	}

	// 信息初始化
	private static void InitOracle(DBUtil dbUtil) {
		dbUtil.execute("delete from SYSTEM.HC_DATABASE");
		dbUtil.execute("delete from SYSTEM.HC_TABLE");
		dbUtil.execute("delete from SYSTEM.HC_FIELD");

	}

	// 获取列相关信息
	public static Map<String, List<FieldInfo>> getFieldMap(DBUtil dbUtil, Map<String, List<TableInfo>> tableMap) {
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

	public Map<String, List<FieldInfo>> getFieldMap(String oracle_url, String oracle_username, String oracle_password,
			Map<String, List<TableInfo>> tableMap) {
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
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
	public static Map<String, List<TableInfo>> getTableMap(DBUtil dbUtil, List<String> dbList) {
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

	// 获取表相关信息
	public Map<String, List<TableInfo>> getTableMap(String oracle_url, String oracle_username, String oracle_password,
			List<String> dbList) {
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
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

	// 获取表相关信息
	public List<TableInfo> getTargetTableMap(String oracle_url, String oracle_username, String oracle_password,
			String dbName) {
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		List<TableInfo> tableList = new ArrayList<TableInfo>();
		try {
			String query_sql = "select TABLE_NAME,COMMENTS from all_tab_comments t2 WHERE OWNER='" + dbName
					+ "' AND table_type='TABLE'";
			ResultSet rs = dbUtil.query(query_sql);
			while (rs.next()) {
				TableInfo tableInfo = new TableInfo();
				tableInfo.setName(rs.getString(1));
				tableInfo.setComments(rs.getString(2));
				tableList.add(tableInfo);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableList;
	}

	// 获取数据库相关信息（用户信息）
	public static List<String> getDataBaseList(DBUtil dbUtil) {
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

	public List<String> getDataBaseList(String oracle_url, String oracle_username, String oracle_password) {
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
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

	public void insertTableToDatabase(String databaseId, List<TableInfo> tableInfoList) {
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle",
				hcConfiguration.getProperty(HealthcareConfiguration.DB_URL),
				hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME),
				hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		for (TableInfo tableInfo : tableInfoList) {
			dbUtil.query("select TABLE_TABLEID.nextval from dual");
			String tableId = dbUtil.showListResults("select TABLE_TABLEID.currval from dual").get(0);
			dbUtil.execute("insert into HC_TABLE(TABLEID,NAME,ZHCNNAME,COMMENTS,DATABASEID) values(" + tableId + ",'"
					+ tableInfo.getName() + "','" + tableInfo.getZhcnname() + "','" + tableInfo.getComments() + "',"
					+ databaseId + ")");
			List<FieldInfo> fieldInfoList = tableInfo.getFieldlist();
			for (FieldInfo fieldInfo : fieldInfoList) {
				dbUtil.query("select FIELD_FIELDID.nextval from dual");
				String fieldId = dbUtil.showListResults("select FIELD_FIELDID.currval from dual").get(0);
				dbUtil.execute("insert into HC_FIELD( "
						+ "FIELDID,NAME,ZHCNNAME,COMMENTS,DATADICTIONARY,TABLEID,DATABASEID) " + "values(" + fieldId
						+ ",'" + fieldInfo.getName() + "','" + fieldInfo.getZhcnname() + "','" + fieldInfo.getComments()
						+ "','" + fieldInfo.getDatadictionary() + "'," + tableId + "," + databaseId + ")");
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

	public boolean deleteClassification(String classificationid) {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			// 首先检测这个类别下是否还有表
			ResultSet res = dbUtil
					.query("select * from SYSTEM.HC_DATABASE where SUBJECTCLASSIFICATION=" + classificationid);
			int count = 0;
			while (res.next()) {
				count = count + 1;
			}
			if (count == 0) {// 只有类别下没有任何表时才能删除
				dbUtil.execute("delete from SYSTEM.HC_CLASSIFICATION where CLASSIFICATIONID=" + classificationid);// 删除
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return false;
	}

	public boolean deleteDatabase(String databaseid) {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			dbUtil.execute("delete from SYSTEM.HC_DATABASE where DATABASEID=" + databaseid);// 删除HC_DATABASE表
			dbUtil.execute("delete from SYSTEM.HC_TABLE where DATABASEID=" + databaseid);// 删除HC_TABLE表
			dbUtil.execute("delete from SYSTEM.HC_FIELD where DATABASEID=" + databaseid);// 删除HC_FIELD表
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

	public boolean deleteField(String databaseid, String tableid, String fieldid) {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			return dbUtil.execute("delete from SYSTEM.HC_FIELD where DATABASEID=" + databaseid + " and " + "TABLEID="
					+ tableid + " and " + "FIELDID=" + fieldid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return false;
	}

	public Integer createClassification(String classificationname,String comments) {// insert
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			String vsql = "insert into SYSTEM.HC_CLASSIFICATION (CLASSIFICATIONID,NAME,COMMENTS) values(CLASSIFICATIONID.nextval,"
					+ "'" + classificationname + "','" + comments + "')";
			return dbUtil.insertDataReturnKeyByReturnInto(vsql,
					"select CLASSIFICATIONID.currval as id from SYSTEM.HC_CLASSIFICATION");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return null;
	}

	public Integer createDatabase(String databasename, String zhcnname, String comments) {// insert
		// into
		// database
		// table
		// row
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			String vsql = "insert into SYSTEM.HC_DATABASE (DATABASEID,NAME,ZHCNNAME,COMMENTS) values(DATABASE_DATABASEID.nextval,"
					+ "'" + databasename + "','" + "'" + zhcnname + "','" + comments + "')";
			return dbUtil.insertDataReturnKeyByReturnInto(vsql,
					"select DATABASE_DATABASEID.currval as id from SYSTEM.HC_DATABASE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return null;
	}

	public Integer createTable(String databaseid, String tablename, String comments, String numRows) {// insert
		// into
		// database
		// table
		// row
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			String vsql = "insert into SYSTEM.HC_TABLE (TABLEID,DATABASEID,NAME,COMMENTS,NUMROWS) values(TABLE_TABLEID.nextval,"
					+ databaseid + ",'" + tablename + "','" + comments + "','" + numRows + "')";
			return dbUtil.insertDataReturnKeyByReturnInto(vsql,
					"select TABLE_TABLEID.currval as id from SYSTEM.HC_TABLE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return null;
	}

	public Integer createField(String databaseid, String tableid, String fieldname, String zhcnname, String comments,
			String datadictionary) {// insert
		// into database table one row
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		try {
			String vsql = "insert into SYSTEM.HC_FIELD (FIELDID,TABLEID,DATABASEID,NAME,ZHCNNAME,COMMENTS,DATADICTIONARY) values(FIELD_FIELDID.nextval,"
					+ tableid + "," + databaseid + ",'" + fieldname + "','" + zhcnname + "','" + comments + "','"
					+ datadictionary + "')";
			return dbUtil.insertDataReturnKeyByReturnInto(vsql,
					"select FIELD_FIELDID.currval as id from SYSTEM.HC_FIELD");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return null;
	}
	public boolean changeClassification(String classificationid, String newName, String newComments) {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		String sql = "";
		try {
			if (newName != null) {
				sql = "update SYSTEM.HC_CLASSIFICATION set NAME='" + newName + "'  where CLASSIFICATIONID=" + classificationid;
				dbUtil.execute(sql);
			}
			if (newComments != null) {
				sql = "update SYSTEM.HC_CLASSIFICATION set COMMENTS='" + newComments + "'  where CLASSIFICATIONID=" + classificationid;
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
	public boolean changeDatabase(String databaseid, String newName, String newComments,String classification) {
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
			if (classification != null) {
				sql = "update SYSTEM.HC_DATABASE set SUBJECTCLASSIFICATION='" + classification + "'  where DATABASEID=" + databaseid;
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

	public boolean changeDatabase(DatabaseInfo databaseinfo) {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		String sql = "";
		try {
			sql = "update SYSTEM.HC_DATABASE set NAME='" + databaseinfo.getName() + "'," + "COMMENTS='"
					+ databaseinfo.getComments() + "'," + "IDENTIFIER='" + databaseinfo.getIdentifier() + "',"
					+ "LANGUAGE='" + databaseinfo.getLanguage() + "'," + "CHARSET='" + databaseinfo.getCharset() + "',"
					+ "SUBJECTCLASSIFICATION='" + databaseinfo.getSubjectclassification() + "'," + "KEYWORDS='"
					+ databaseinfo.getKeywords() + "'," + "CREDIBILITY='" + databaseinfo.getResinstitution() + "',"
					+ "RESINSTITUTION='" + databaseinfo.getResinstitution() + "'," + "RESNAME='"
					+ databaseinfo.getResname() + "'," + "RESADDRESS='" + databaseinfo.getResaddress() + "',"
					+ "RESPOSTALCODE='" + databaseinfo.getRespostalcode() + "'," + "RESPHONE='"
					+ databaseinfo.getResphone() + "'," + "RESEMAIL='" + databaseinfo.getResemail() + "',"
					+ "RESOURCEURL='" + databaseinfo.getResourceurl() + "'," + "ZHCNNAME='" + databaseinfo.getZhcnname()
					+ "'" + " where DATABASEID=" + databaseinfo.getDatabaseid();
			dbUtil.execute(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return false;
	}
	public boolean changeClassification(ClassificationInfo classificationinfo) {
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		String sql = "";
		try {
			sql = "update SYSTEM.HC_CLASSIFICATION set NAME='" + classificationinfo.getName() + "'," + "COMMENTS='"
					+ classificationinfo.getComments() 
					+ "'" + " where CLASSIFICATIONID=" + classificationinfo.getClassificationid();
			dbUtil.execute(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return false;
	}
	public boolean changeTable(String databaseid, String tableid, String newName, String zhcnname, String newComments,
			String newNumRows) {
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
			if (zhcnname != null) {
				sql = "update SYSTEM.HC_TABLE set ZHCNNAME='" + zhcnname + "'  where DATABASEID=" + databaseid
						+ " and TABLEID=" + tableid;
				dbUtil.execute(sql);
			}
			if (newComments != null) {
				sql = "update SYSTEM.HC_TABLE set COMMENTS='" + newComments + "'   where DATABASEID=" + databaseid
						+ " and TABLEID=" + tableid;
				dbUtil.execute(sql);
			}
			if (newNumRows != null) {
				sql = "update SYSTEM.HC_TABLE set NUMROWS='" + newNumRows + "'   where DATABASEID=" + databaseid
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

	public boolean changeField(String fieldid, String databaseid, String tableid, String newName, String newZhcnname,
			String newComments, String newDatadictionary) {
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
						+ " and TABLEID=" + tableid + " and FIELDID=" + fieldid;
				dbUtil.execute(sql);
			}
			if (newZhcnname != null) {
				sql = "update SYSTEM.HC_FIELD set ZHCNNAME='" + newZhcnname + "'  where DATABASEID=" + databaseid
						+ " and TABLEID=" + tableid + " and FIELDID=" + fieldid;
				dbUtil.execute(sql);
			}
			if (newComments != null) {
				sql = "update SYSTEM.HC_FIELD set COMMENTS='" + newComments + "'   where DATABASEID=" + databaseid
						+ " and TABLEID=" + tableid + " and FIELDID=" + fieldid;
				dbUtil.execute(sql);
			}
			if (newDatadictionary != null) {
				sql = "update SYSTEM.HC_FIELD set DATADICTIONARY='" + newDatadictionary + "'   where DATABASEID="
						+ databaseid + " and TABLEID=" + tableid + " and FIELDID=" + fieldid;
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

	/*
	 * 数据申请
	 */
	@Transactional
	public void insertApplyData(HttpServletRequest req, String f_name, boolean update) {

		if (update) {
			// begin update
			HcApplydata hc_applydata = hcApplydataDao.findByDocName(f_name);

			String curStatus = hc_applydata.getFlagApplydata();
			if (!curStatus.equals("2")) {
				System.out.println("applyData 状态不为2，此时已经无法更新申请！");
				return;
			}

			String hc_userName = req.getParameter("userName");
			String hc_userDepartment = req.getParameter("userDepartment");
			String hc_userAddress = req.getParameter("userAddress");
			String hc_userTel = req.getParameter("userTel");
			String hc_userEmail = req.getParameter("userEmail");

			String hc_userDemandType = req.getParameter("userDemandType");
			String hc_userDemand = req.getParameter("userDemand");

			String hc_useFields = req.getParameter("allUseField");//
			String hc_projectName = req.getParameter("projectName");
			String hc_projectChairman = req.getParameter("projectChairman");
			String hc_projectSource = req.getParameter("projectSource");
			String hc_projectUndertaking = req.getParameter("projectUndertaking");
			String hc_applyDate = req.getParameter("applyDate");
			String hc_projectRemarks = req.getParameter("projectRemarks");

			String applydata = req.getParameter("applydata");

			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			hc_applydata.setHcUsername(user.getUsername());// hc系统用户名
			// hc_applydata.setDocName(f_name);// 主键

			hc_applydata.setName(hc_userName);// 申请表填写用户
			hc_applydata.setDepartment(hc_userDepartment);
			hc_applydata.setAddress(hc_userAddress);
			hc_applydata.setTel(hc_userTel);
			hc_applydata.setEmail(hc_userEmail);

			hc_applydata.setDemandtype(hc_userDemandType);
			hc_applydata.setDemand(hc_userDemand);

			hc_applydata.setProUsefield(hc_useFields);
			hc_applydata.setProName(hc_projectName);
			hc_applydata.setProChair(hc_projectChairman);
			hc_applydata.setProSource(hc_projectSource);
			hc_applydata.setProUndertake(hc_projectUndertaking);
			hc_applydata.setApplyTime(hc_applyDate);
			hc_applydata.setProRemark(hc_projectRemarks);

			hc_applydata.setApplyData(applydata);

			// 只有在未进行审核情况下才可以update，apply标志为1 --- status=1（待审核状态）
			hc_applydata.setFlagApplydata("1");

			hcApplydataDao.attachDirty(hc_applydata);
			System.out.println("update hc_applydata ok");
			return;
		}

		// begin insert
		HcApplydata hc_applydata = new HcApplydata();

		String hc_userName = req.getParameter("userName");
		String hc_userDepartment = req.getParameter("userDepartment");
		String hc_userAddress = req.getParameter("userAddress");
		String hc_userTel = req.getParameter("userTel");
		String hc_userEmail = req.getParameter("userEmail");

		String hc_userDemandType = req.getParameter("userDemandType");
		String hc_userDemand = req.getParameter("userDemand");

		String hc_useFields = req.getParameter("allUseField");//
		String hc_projectName = req.getParameter("projectName");
		String hc_projectChairman = req.getParameter("projectChairman");
		String hc_projectSource = req.getParameter("projectSource");
		String hc_projectUndertaking = req.getParameter("projectUndertaking");
		String hc_applyDate = req.getParameter("applyDate");
		String hc_projectRemarks = req.getParameter("projectRemarks");

		String applydata = req.getParameter("applydata");

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		hc_applydata.setHcUsername(user.getUsername());// hc系统用户名
		hc_applydata.setDocName(f_name);// 主键

		hc_applydata.setName(hc_userName);// 申请表填写用户
		hc_applydata.setDepartment(hc_userDepartment);
		hc_applydata.setAddress(hc_userAddress);
		hc_applydata.setTel(hc_userTel);
		hc_applydata.setEmail(hc_userEmail);

		hc_applydata.setDemandtype(hc_userDemandType);
		hc_applydata.setDemand(hc_userDemand);

		hc_applydata.setProUsefield(hc_useFields);
		hc_applydata.setProName(hc_projectName);
		hc_applydata.setProChair(hc_projectChairman);
		hc_applydata.setProSource(hc_projectSource);
		hc_applydata.setProUndertake(hc_projectUndertaking);
		hc_applydata.setApplyTime(hc_applyDate);
		hc_applydata.setProRemark(hc_projectRemarks);
		hc_applydata.setApplyData(applydata);

		// 提交后，apply标志为1 --- status=1（待审核状态）
		hc_applydata.setFlagApplydata("1");

		hcApplydataDao.attachDirty(hc_applydata);
		System.out.println("insert hc_applydata ok");
	}

	/*
	 * get the apply docdata from db by docName
	 */
	@Transactional
	public HcApplydata getDocBydocID(String docid) {
		HcApplydata docData = hcApplydataDao.findByDocName(docid);
		return docData;
	}

	@Transactional
	public HcApplydata getDataDocByApplyDataID(String applydataId) {
		BigDecimal bd = new BigDecimal(applydataId);
		HcApplydata docData = hcApplydataDao.findByApplyID(bd);
		return docData;
	}

	/*
	 * get the apply docdata from db by hc_userName(系统用户)
	 */
	@Transactional
	public List getDocByHcUserName(String hcUserName) {
		List docDataList = hcApplydataDao.findByHcUserName(hcUserName);
		return docDataList;
	}

	/*
	 * get all the apply docdata from db(ROLE_ADMIN管理员用)
	 */
	@Transactional
	public List<HcApplydata> getAllDocData() {
		List ALLdocDataList = hcApplydataDao.findAll();
		return ALLdocDataList;
	}

	@Transactional
	public String getApplyDataByDocId(String docid) {
		HcApplydata hcapplydata = hcApplydataDao.findByDocName(docid);
		return hcapplydata.getApplyData();
	}

	/*
	 * 删除applydata信息
	 */
	@Transactional
	public boolean deleteApplyData(String[] applydataid) {
		try {
			for (int i = 0; i < applydataid.length; i++) {
				BigDecimal bd = new BigDecimal(applydataid[i]);
				HcApplydata tmp = hcApplydataDao.findByApplyID(bd);
				hcApplydataDao.delete(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Transactional
	public void changeApplyDataStatus(String applyid, String status) {
		BigDecimal bd = new BigDecimal(applyid);
		hcApplydataDao.changeApplyStatus(bd, status);
	}

	@Transactional
	public void insertApplyDataFailReason(String applyid, String rejectReason) {
		BigDecimal bd = new BigDecimal(applyid);
		hcApplydataDao.setApplyFailReason(bd, rejectReason);
	}

	/*
	 * 虚拟环境申请
	 */
	@Transactional
	public boolean deleteApplyEnv(String[] applydataid) {
		try {
			for (int i = 0; i < applydataid.length; i++) {
				BigDecimal bd = new BigDecimal(applydataid[i]);
				HcApplyenv tmp = hcApplyenvDao.findByApplyID(bd);
				hcApplyenvDao.delete(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Transactional
	public String getApplyDataByEnvDocId(String docid) {
		HcApplyenv hcApplyenv = hcApplyenvDao.findByDocName(docid);
		return hcApplyenv.getApplyData();
	}
	
	@Transactional
	public void insertApplyEnv(HttpServletRequest req, String f_name, boolean update) {

		if (update) {
			// begin update envApply
			HcApplyenv hc_applyenv = hcApplyenvDao.findByDocName(f_name);

			String hc_userName = req.getParameter("userName");
			String hc_userDepartment = req.getParameter("userDepartment");
			String hc_userAddress = req.getParameter("userAddress");
			String hc_userTel = req.getParameter("userTel");
			String hc_userEmail = req.getParameter("userEmail");

			String hc_userDemandType = req.getParameter("userDemandType");
			String hc_userDemand = req.getParameter("userDemand");

			String hc_useFields = req.getParameter("allUseField");//
			String hc_projectName = req.getParameter("projectName");
			String hc_projectChairman = req.getParameter("projectChairman");
			String hc_projectSource = req.getParameter("projectSource");
			String hc_projectUndertaking = req.getParameter("projectUndertaking");
			String hc_applyDate = req.getParameter("applyDate");
			String hc_projectRemarks = req.getParameter("projectRemarks");
			String applydata = req.getParameter("applydata");

			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			hc_applyenv.setHcUsername(user.getUsername());// hc系统用户名
			// hc_applyenv.setDocName(f_name);// 主键

			hc_applyenv.setName(hc_userName);// 申请表填写用户
			hc_applyenv.setDepartment(hc_userDepartment);
			hc_applyenv.setAddress(hc_userAddress);
			hc_applyenv.setTel(hc_userTel);
			hc_applyenv.setEmail(hc_userEmail);

			hc_applyenv.setDemandtype(hc_userDemandType);
			hc_applyenv.setDemand(hc_userDemand);

			hc_applyenv.setProUsefield(hc_useFields);
			hc_applyenv.setProName(hc_projectName);
			hc_applyenv.setProChair(hc_projectChairman);
			hc_applyenv.setProSource(hc_projectSource);
			hc_applyenv.setProUndertake(hc_projectUndertaking);
			hc_applyenv.setApplyTime(hc_applyDate);
			hc_applyenv.setProRemark(hc_projectRemarks);
			hc_applyenv.setApplyData(applydata);
			// 提交后，apply标志为1
			hc_applyenv.setFlagApplydata("1");

			hcApplyenvDao.attachDirty(hc_applyenv);
			System.out.println("update hc_applyenv ok");
			return;
		}

		HcApplyenv hc_applyenv = new HcApplyenv();

		String hc_userName = req.getParameter("userName");
		String hc_userDepartment = req.getParameter("userDepartment");
		String hc_userAddress = req.getParameter("userAddress");
		String hc_userTel = req.getParameter("userTel");
		String hc_userEmail = req.getParameter("userEmail");

		String hc_userDemandType = req.getParameter("userDemandType");
		String hc_userDemand = req.getParameter("userDemand");

		String hc_useFields = req.getParameter("allUseField");//
		String hc_projectName = req.getParameter("projectName");
		String hc_projectChairman = req.getParameter("projectChairman");
		String hc_projectSource = req.getParameter("projectSource");
		String hc_projectUndertaking = req.getParameter("projectUndertaking");
		String hc_applyDate = req.getParameter("applyDate");
		String hc_projectRemarks = req.getParameter("projectRemarks");
		String applydata = req.getParameter("applydata");

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		hc_applyenv.setHcUsername(user.getUsername());// hc系统用户名
		hc_applyenv.setDocName(f_name);// 主键

		hc_applyenv.setName(hc_userName);// 申请表填写用户
		hc_applyenv.setDepartment(hc_userDepartment);
		hc_applyenv.setAddress(hc_userAddress);
		hc_applyenv.setTel(hc_userTel);
		hc_applyenv.setEmail(hc_userEmail);

		hc_applyenv.setDemandtype(hc_userDemandType);
		hc_applyenv.setDemand(hc_userDemand);

		hc_applyenv.setProUsefield(hc_useFields);
		hc_applyenv.setProName(hc_projectName);
		hc_applyenv.setProChair(hc_projectChairman);
		hc_applyenv.setProSource(hc_projectSource);
		hc_applyenv.setProUndertake(hc_projectUndertaking);
		hc_applyenv.setApplyTime(hc_applyDate);
		hc_applyenv.setProRemark(hc_projectRemarks);
		hc_applyenv.setApplyData(applydata);
		// 提交后，apply标志为1
		hc_applyenv.setFlagApplydata("1");

		hcApplyenvDao.attachDirty(hc_applyenv);
		System.out.println("insert hc_applyenv ok");
	}

	public HcApplyenv getEnvDocBydocID(String docid) {
		HcApplyenv docData = hcApplyenvDao.findByDocName(docid);
		return docData;
	}

	public HcApplyenv getDocEnvByApplyDataID(String applyid) {
		BigDecimal bd = new BigDecimal(applyid);
		HcApplyenv docEnv = hcApplyenvDao.findByApplyID(bd);
		return docEnv;
	}

	public List<HcApplyenv> getEnvDocByHcUserName(String hcUserName) {
		List docEnvDataList = hcApplyenvDao.findByHcUserName(hcUserName);
		return docEnvDataList;
	}

	public List<HcApplyenv> getAllDocEnv() {
		List ALLdocEnvList = hcApplyenvDao.findAll();
		return ALLdocEnvList;
	}

	@Transactional
	public void changeApplyEnvStatus(String applyid, String status) {
		BigDecimal bd = new BigDecimal(applyid);
		hcApplyenvDao.changeApplyStatus(bd, status);
	}

	@Transactional
	public void insertApplyEnvFailReason(String applyid, String rejectReason) {
		BigDecimal bd = new BigDecimal(applyid);
		hcApplyenvDao.setApplyFailReason(bd, rejectReason);
	}

	@Transactional
	public void updateEnvUrlByApplyID(String applyid, String envUrl) {
		BigDecimal bd = new BigDecimal(applyid);
		hcApplyenvDao.setApplyEnvUrl(bd, envUrl);
		System.out.println("update EnvUrl ok");
	}

}
