package otc.healthcare.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import otc.healthcare.dao.ConnectionFactory;
import otc.healthcare.pojo.BaseHospitalModel;
import otc.healthcare.pojo.CommunityModel;
import otc.healthcare.pojo.YearStatisticsModel;
import otc.healthcare.util.DBUtil;
import otc.healthcare.util.HealthcareConfiguration;

@Component
public class MySQLService implements IService {
	@Autowired
	private HealthcareConfiguration hcConfiguration;
	private static Map<String, List<String>> yearMap;

	public Map<String, HashSet<BaseHospitalModel>> getJoinBaseHosiptalInfo() {
		System.out.println("正在获取基地医院数据");
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		DBUtil dbUtil_inner = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, HashSet<BaseHospitalModel>> map = new HashMap<String, HashSet<BaseHospitalModel>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				HashSet<BaseHospitalModel> list = new HashSet<BaseHospitalModel>();
				ResultSet res = dbUtil
						.query("select distinct userunit.uuCode,uuName,uull,uuCity,uuProvince,archivescases.acid from userunit join archivescases on userunit.uuCode=archivescases.acCodeUp join acid"
								+ yearArray[i] + " on " + "acid" + yearArray[i]
								+ ".acid=archivescases.acid where userunit.uuType=3000");
				while (res.next()) {
					System.out.print(".");
					BaseHospitalModel bhm = new BaseHospitalModel();
					bhm.setUuCode(res.getString(1));
					bhm.setName(res.getString(2));
					bhm.setUull(res.getString(3));
					bhm.setUuProvince(res.getString(5));
					ResultSet res_inner = dbUtil_inner
							.query("SELECT COUNT(*) FROM ArchivesCases WHERE acEndState=1 and acCodeUp="
									+ res.getString(1) + " and acid=" + res.getString(6));
					if (res_inner.next()) {
						bhm.setEndCount(res_inner.getInt(1));
					}
					res_inner.close();
					res_inner = dbUtil_inner.query("SELECT COUNT(*) FROM ArchivesCases WHERE acStatus=3 and acCodeUp="
							+ res.getString(1) + " and acid=" + res.getString(6));
					if (res_inner.next()) {
						bhm.setDangerCount(res_inner.getInt(1));
					}
					res_inner.close();
					res_inner = dbUtil_inner.query("SELECT COUNT(*) FROM ArchivesCases WHERE acStatus=3 and uuCode="
							+ res.getString(1) + " and acid=" + res.getString(6));
					if (res_inner.next()) {
						bhm.setDangerCount(bhm.getDangerCount() + res_inner.getInt(1));
					}
					res_inner.close();
					list.add(bhm);
				}
				res.close();
				map.put(yearArray[i], list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return map;
	}

	public Map<String, HashSet<CommunityModel>> getJoinCommunityInfo() {
		System.out.println("正在获取社区数据");
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		DBUtil dbUtil_inner = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, HashSet<CommunityModel>> map = new HashMap<String, HashSet<CommunityModel>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				HashSet<CommunityModel> list = new HashSet<CommunityModel>();
				ResultSet res = dbUtil
						.query("select distinct userunit.uuCode,uuName,uull,acCodeUp,archivescases.acid from userunit join archivescases on userunit.uuCode=archivescases.acCodeUp join acid"
								+ yearArray[i] + " on " + "acid" + yearArray[i]
								+ ".acid=archivescases.acid where userunit.uuType=5000 or userunit.uuType=6000");
				while (res.next()) {
					System.out.print(".");
					CommunityModel cm = new CommunityModel();
					cm.setUuCode(res.getString(1));
					cm.setName(res.getString(2));
					cm.setUull(res.getString(3));
					cm.setAcCodeUp(res.getString(4));
					ResultSet res_inner = dbUtil_inner
							.query("SELECT DISTINCT uuName from UserUnit where uuCode=" + res.getString(4));
					if (res_inner.next()) {
						cm.setUpName(res_inner.getString(1));
					}
					res_inner.close();
					res_inner = dbUtil_inner.query("SELECT COUNT(*) FROM ArchivesCases WHERE acEndState=1 and acCodeUp="
							+ res.getString(1) + " and acid=" + res.getString(6));
					if (res_inner.next()) {
						cm.setEndCount(res_inner.getInt(1));
					}
					res_inner.close();
					res_inner = dbUtil_inner.query("SELECT COUNT(*) FROM ArchivesCases WHERE acStatus=3 and acCodeUp="
							+ res.getString(1) + " and acid=" + res.getString(6));
					if (res_inner.next()) {
						cm.setDangerCount(res_inner.getInt(1));
					}
					res_inner.close();
					res_inner = dbUtil_inner.query("SELECT COUNT(*) FROM ArchivesCases WHERE acStatus=3 and uuCode="
							+ res.getString(1) + " and acid=" + res.getString(6));
					if (res_inner.next()) {
						cm.setDangerCount(cm.getDangerCount() + res_inner.getInt(1));
					}
					res_inner.close();
					list.add(cm);
				}
				res.close();
				map.put(yearArray[i], list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return map;
	}

	public Map<String, Object> getProvinceCityInfo() {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, Object> returnMap = new HashMap<String, Object>();
		HashMap<String, ArrayList<String>> provinceCityMap = new HashMap<String, ArrayList<String>>();
		Map<String, String> provinceMap = new HashMap<String, String>();
		Map<String, String> cityMap = new HashMap<String, String>();
		try {
			ResultSet res = dbUtil.query("SELECT DISTINCT neCode,neName FROM GB_native WHERE neCode LIKE '____0000'");
			while (res.next()) {
				System.out.print(".");
				String neCode = res.getString(1);
				String neName = res.getString(2);
				if (neCode.indexOf("000000") != -1
						&& !provinceCityMap.containsKey(neCode.substring(0, 2) + "_" + neName)) {
					provinceMap.put(neCode.substring(0, 2), neName);
					provinceCityMap.put(neCode.substring(0, 2) + "_" + neName, new ArrayList());
				} else if (neCode.indexOf("0000") != -1) {
					cityMap.put(neCode.substring(0, 4), neName);
					provinceCityMap.get(neCode.substring(0, 2) + "_" + provinceMap.get(neCode.substring(0, 2)))
							.add(neCode.substring(0, 4) + "_" + neName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		returnMap.put("all", provinceCityMap);
		returnMap.put("province", provinceMap);
		returnMap.put("city", cityMap);
		return returnMap;
	}

	public Map<String, String> getProvinceInfo() {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, String> provinceMap = new HashMap<String, String>();
		try {
			ResultSet res = dbUtil.query("SELECT DISTINCT neCode,neName FROM GB_native WHERE neCode LIKE '__000000'");
			while (res.next()) {
				System.out.print(".");
				String neCode = res.getString(1);
				String neName = res.getString(2);
				if (neCode.indexOf("000000") != -1) {
					provinceMap.put(neName, neCode.substring(0, 2));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return provinceMap;
	}

	public Map<String, String> getCityInfo(String provinceId) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, String> cityMap = new HashMap<String, String>();
		try {
			ResultSet res = dbUtil
					.query("SELECT DISTINCT neCode,neName FROM GB_native WHERE neCode LIKE '" + provinceId + "__0000'");
			while (res.next()) {
				System.out.print(".");
				String neCode = res.getString(1);
				String neName = res.getString(2);
				if (neCode.indexOf("000000") == -1) {
					cityMap.put(neName, neCode.substring(2, 4));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return cityMap;
	}

	// unused
	public Map<String, ArrayList<String>> getYearAcid() {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, ArrayList<String>> yearMap = new HashMap<String, ArrayList<String>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		for (String year : yearArray) {
			ArrayList<String> list = new ArrayList<String>();
			ResultSet res = dbUtil.query("Select acid from acid" + year);
			try {
				while (res.next()) {
					System.out.print(".");
					list.add(res.getString(1));
				}
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			yearMap.put(year, list);
		}
		return yearMap;
	}

	public Map<String, YearStatisticsModel> getYearInfo() {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, YearStatisticsModel> map = new HashMap<String, YearStatisticsModel>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				YearStatisticsModel ysm = new YearStatisticsModel();
				Set<String> citySet = new HashSet<String>();
				Set<String> provinceSet = new HashSet<String>();
				Set<String> joinBaseHospitalSet = new HashSet<String>();
				Set<String> joinCommunitySet = new HashSet<String>();
				ResultSet res = dbUtil
						.query("select distinct userunit.uuCode,uuCity,uuProvince,uuType from userunit join archivescases on userunit.uuCode=archivescases.acCodeUp join acid"
								+ yearArray[i] + " on " + "acid" + yearArray[i] + ".acid=archivescases.acid");
				while (res.next()) {
					System.out.print(".");
					citySet.add(res.getString(2));
					provinceSet.add(res.getString(3));
					if (res.getString(4).equals("3000"))
						joinBaseHospitalSet.add(res.getString(1));
					else if (res.getString(4).equals("5000") || res.getString(4).equals("6000"))
						joinCommunitySet.add(res.getString(1));
				}
				ysm.setCityCount(citySet.size());
				ysm.setProvinceCount(provinceSet.size());
				ysm.setJoinBaseHospitalCount(joinBaseHospitalSet.size());
				ysm.setJoinCommunityCount(joinCommunitySet.size());
				res.close();
				res = dbUtil.query("select count(*) from archivescases join acid" + yearArray[i] + " on " + "acid"
						+ yearArray[i] + ".acid=archivescases.acid where archivescases.acEndState=1");
				if (res.next())
					ysm.setEndCount(res.getInt(1));
				res.close();
				res = dbUtil.query("select count(*) from archivescases join acid" + yearArray[i] + " on " + "acid"
						+ yearArray[i] + ".acid=archivescases.acid where archivescases.acStatus=3");
				if (res.next())
					ysm.setDangerCount(res.getInt(1));
				res.close();
				res = dbUtil.query("select count(*) from archivescases join acid" + yearArray[i] + " on " + "acid"
						+ yearArray[i] + ".acid=archivescases.acid where archivescases.acStatus=5");
				if (res.next())
					ysm.setStrokeCount(res.getInt(1));
				res.close();
				map.put(yearArray[i], ysm);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return map;
	}

	public Map<String, List<HashMap<String, String>>> getGenderInfo(String province, String acCodeUp,
			String community) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, List<HashMap<String, String>>> returnMap = new HashMap<String, List<HashMap<String, String>>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				String sql = "SELECT (CASE aSex WHEN '1' THEN '男' WHEN '2' THEN '女' END) "
						+ "AS gender, COUNT(1) / a.total AS percentage  " + "FROM ( " + "SELECT COUNT(1) AS total "
						+ "FROM Archives  " + "JOIN ArchivesCases  " + "ON Archives.aid = ArchivesCases.aid  "
						+ "JOIN acid" + yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
						+ "JOIN UserUnit  " + "ON Archives.uucode = UserUnit.uucode  " + "JOIN GB_native  "
						+ "ON UserUnit.uuprovince = GB_native.necode  " + "WHERE (GB_native.necode = '" + province
						+ "' or '" + province + "'='') " + "AND (ArchivesCases.accodeup = '" + acCodeUp + "' or '"
						+ acCodeUp + "'='') " + "AND (ArchivesCases.uucode = '" + community + "' or '" + community
						+ "'='') " + ")a, Archives  " + "JOIN ArchivesCases  " + "ON Archives.aid = ArchivesCases.aid  "
						+ "JOIN acid" + yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
						+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode  "
						+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode  " + "WHERE (GB_native.necode = '"
						+ province + "' or '" + province + "'='') AND (ArchivesCases.accodeup = '" + acCodeUp + "' or '"
						+ acCodeUp + "'='') " + "AND (ArchivesCases.uucode = '" + community + "' or '" + community
						+ "'='') GROUP BY aSex, a.total";
				ResultSet res = dbUtil.query(sql);
				while (res.next()) {
					System.out.print(".");
					Map<String, String> map = new HashMap<String, String>();
					map.put("gender", res.getString(1));
					map.put("percentage", res.getString(2));
					list.add((HashMap<String, String>) map);
				}
				res.close();
				returnMap.put(yearArray[i], list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return returnMap;
	}

	public Map<String, List<HashMap<String, String>>> getGenderWithStrokeInfo(String province, String acCodeUp,
			String community) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, List<HashMap<String, String>>> returnMap = new HashMap<String, List<HashMap<String, String>>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				String sql = "SELECT " + "(CASE aSex " + "	WHEN '1' THEN " + "		'男' " + "	WHEN '2' THEN "
						+ "		'女' " + "	END " + ") AS gender, "
						+ "	SUM(IF(dfstroke = 1, 1, 0)) / COUNT(1) AS percentage " + "FROM Archives "
						+ "JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid "
						+ "JOIN acid" + yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
						+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
						+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode "
						+ "JOIN DangerFactors"+yearArray[i]+" ON Archives.aid = DangerFactors"+yearArray[i]+".aid " + "WHERE "
						+ "	GB_native.necode = '" + province + "' or '" + province + "'='' "
						+ "AND ArchivesCases.accodeup = '" + acCodeUp + "' or ''='' " + "AND ArchivesCases.uucode = '"
						+ community + "' or '" + community + "'='' " + "GROUP BY aSex";
				ResultSet res = dbUtil.query(sql);
				while (res.next()) {
					System.out.print(".");
					Map<String, String> map = new HashMap<String, String>();
					map.put("gender", res.getString(1));
					map.put("percentage", res.getString(2));
					list.add((HashMap<String, String>) map);
				}
				res.close();
				returnMap.put(yearArray[i], list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return returnMap;
	}

	public Map<String, List<HashMap<String, String>>> getAgeInfo(String province, String acCodeUp, String community) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, List<HashMap<String, String>>> returnMap = new HashMap<String, List<HashMap<String, String>>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				ResultSet res = dbUtil.query("SELECT " + "(CASE " + "	WHEN acAge = 40 " + "	AND acAge <= 44 THEN "
						+ "		'40-44' " + "	WHEN acAge >= 45 " + "	AND acAge <= 49 THEN " + "		'45-49' "
						+ "	WHEN acAge >= 50 " + "	AND acAge <= 54 THEN " + "		'50-54' " + "	WHEN acAge >= 55 "
						+ "	AND acAge <= 59 THEN " + "		'55-59' " + "	WHEN acAge >= 60 "
						+ "	AND acAge <= 64 THEN " + "		'60-64' " + "	WHEN acAge >= 65 "
						+ "	AND acAge <= 69 THEN " + "		'65-69' " + "	WHEN acAge >= 70 "
						+ "	AND acAge <= 74 THEN " + "		'70-74' " + "	WHEN acAge >= 75 "
						+ "	AND acAge <= 79 THEN " + "		'75-79' " + "	WHEN acAge >= 80 THEN " + "		'80+' "
						+ "	END " + ") AS age, " + "COUNT(1) / a.total AS percentage " + "FROM " + "	(SELECT "
						+ "			COUNT(1) AS total " + "		FROM " + "			Archives "
						+ "		JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid " + " JOIN acid" + yearArray[i]
						+ " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
						+ "		JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
						+ "		JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "		WHERE "
						+ "			(GB_native.necode = '" + province + "' or '" + province
						+ "'='')		AND (ArchivesCases.accodeup = '" + acCodeUp + "' or '" + acCodeUp + "'='')"
						+ "		AND (ArchivesCases.uucode = '" + community + "' or '" + community + "'='')" + "	) a, "
						+ "	Archives " + "JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid " + " JOIN acid"
						+ yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
						+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
						+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "WHERE "
						+ "	(GB_native.necode = '" + province + "' or '" + province
						+ "'='')	AND (ArchivesCases.accodeup = '" + acCodeUp + "' or '" + acCodeUp + "'='')"
						+ "AND (ArchivesCases.uucode = '" + community + "' or '" + community + "'='')" + "GROUP BY "
						+ "	age, " + "	a.total");
				while (res.next()) {
					System.out.print(".");
					Map<String, String> map = new HashMap<String, String>();
					map.put("age", res.getString(1));
					map.put("percentage", res.getString(2));
					list.add((HashMap<String, String>) map);
				}
				res.close();
				returnMap.put(yearArray[i], list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return returnMap;
	}

	public Map<String, List<HashMap<String, String>>> getAgeWithStrokeInfo(String province, String acCodeUp,
			String community) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, List<HashMap<String, String>>> returnMap = new HashMap<String, List<HashMap<String, String>>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				ResultSet res = dbUtil.query("SELECT " + "(CASE " + "	WHEN acAge >= 40 " + "	AND acAge <= 44 THEN "
						+ "		'40-44' " + "	WHEN acAge >= 45 " + "	AND acAge <= 49 THEN " + "		'45-49' "
						+ "	WHEN acAge >= 50 " + "	AND acAge <= 54 THEN " + "		'50-54' " + "	WHEN acAge >= 55 "
						+ "	AND acAge <= 59 THEN " + "		'55-59' " + "	WHEN acAge >= 60 "
						+ "	AND acAge <= 64 THEN " + "		'60-64' " + "	WHEN acAge >= 65 "
						+ "	AND acAge <= 69 THEN " + "		'65-69' " + "	WHEN acAge >= 70 "
						+ "	AND acAge <= 74 THEN " + "		'70-74' " + "	WHEN acAge >= 75 "
						+ "	AND acAge <= 79 THEN " + "		'75-79' " + "	WHEN acAge >= 80 THEN " + "		'80+' "
						+ "	END " + ") AS age, " + "SUM(IF(dfstroke = 1, 1, 0)) / COUNT(1) AS percentage "
						+ "FROM Archives " + "JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid " + " JOIN acid"
						+ yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
						+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
						+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode "
						+ "JOIN DangerFactors"+yearArray[i]+" ON Archives.aid = DangerFactors"+yearArray[i]+".aid " + "WHERE "
						+ "	GB_native.necode = '" + province + "' or '" + province + "'='' "
						+ "AND ArchivesCases.accodeup = '" + acCodeUp + "' or '" + acCodeUp + "'='' "
						+ "AND ArchivesCases.uucode = '" + community + "' or '" + community + "'='' " + "GROUP BY ( "
						+ "		CASE " + "		WHEN acAge >= 40 " + "		AND acAge <= 44 THEN "
						+ "			'40-44' " + "		WHEN acAge >= 45 " + "		AND acAge <= 49 THEN "
						+ "			'45-49' " + "		WHEN acAge >= 50 " + "		AND acAge <= 54 THEN "
						+ "			'50-54' " + "		WHEN acAge >= 55 " + "		AND acAge <= 59 THEN "
						+ "			'55-59' " + "		WHEN acAge >= 60 " + "		AND acAge <= 64 THEN "
						+ "			'60-64' " + "		WHEN acAge >= 65 " + "		AND acAge <= 69 THEN "
						+ "			'65-69' " + "		WHEN acAge >= 70 " + "		AND acAge <= 74 THEN "
						+ "			'70-74' " + "		WHEN acAge >= 75 " + "		AND acAge <= 79 THEN "
						+ "			'75-79' " + "		WHEN acAge >= 80 THEN " + "			'80+' " + "		END)");
				while (res.next()) {
					System.out.print(".");
					Map<String, String> map = new HashMap<String, String>();
					map.put("age", res.getString(1));
					map.put("percentage", res.getString(2));
					list.add((HashMap<String, String>) map);
				}
				res.close();
				returnMap.put(yearArray[i], list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return returnMap;
	}

	public Map<String, List<HashMap<String, String>>> getRegionInfo(String province, String acCodeUp,
			String community) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, List<HashMap<String, String>>> returnMap = new HashMap<String, List<HashMap<String, String>>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				ResultSet res = dbUtil.query("SELECT " + "(CASE acType " + "	WHEN '50' THEN " + "		'城市' "
						+ "	WHEN '60' THEN " + "		'乡镇' " + "	END " + ") AS region, "
						+ "COUNT(1) / a.total AS percentage " + "FROM " + "( " + "	SELECT "
						+ "		COUNT(1) AS total " + "	FROM " + "		Archives "
						+ "	JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid " + " JOIN acid" + yearArray[i]
						+ " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
						+ "	JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
						+ "	JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "	WHERE "
						+ "		(GB_native.necode = '" + province + "' or '" + province
						+ "'='')	AND (ArchivesCases.accodeup = '" + acCodeUp + "' or '" + acCodeUp
						+ "'='')	AND (ArchivesCases.uucode = '" + community + "' or '" + community + "'='') ) a, "
						+ " Archives " + "JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid " + " JOIN acid"
						+ yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
						+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
						+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "WHERE "
						+ "	(GB_native.necode = '" + province + "' or '" + province
						+ "'='')  AND (ArchivesCases.accodeup = '" + acCodeUp + "' or '" + acCodeUp
						+ "'='') AND (ArchivesCases.uucode = '" + community + "' or '" + community + "'='') GROUP BY "
						+ "	acType, " + "	a.total");
				while (res.next()) {
					System.out.print(".");
					Map<String, String> map = new HashMap<String, String>();
					map.put("region", res.getString(1));
					map.put("percentage", res.getString(2));
					list.add((HashMap<String, String>) map);
				}
				res.close();
				returnMap.put(yearArray[i], list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return returnMap;
	}

	public Map<String, List<HashMap<String, String>>> getRegionWithStrokeInfo(String province, String acCodeUp,
			String community) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, List<HashMap<String, String>>> returnMap = new HashMap<String, List<HashMap<String, String>>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				ResultSet res = dbUtil.query("SELECT " + "(CASE acType " + "	WHEN '50' THEN " + "		'城市' "
						+ "	WHEN '60' THEN " + "		'乡镇' " + "	END " + ") AS region, "
						+ "SUM(IF(dfstroke = 1, 1, 0)) / COUNT(1) AS percentage " + "FROM " + "	Archives "
						+ "JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid " + " JOIN acid" + yearArray[i]
						+ " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
						+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
						+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode "
						+ "JOIN DangerFactors"+yearArray[i]+" ON Archives.aid = DangerFactors"+yearArray[i]+".aid " + "WHERE "
						+ "	(GB_native.necode = '" + province + "' or '" + province + "'='' )"
						+ "AND (ArchivesCases.accodeup = '" + acCodeUp + "' or '" + acCodeUp + "'='' )"
						+ "AND (ArchivesCases.uucode = '" + community + "' or '" + community + "'='' )"
						+ "GROUP BY acType");
				while (res.next()) {
					System.out.print(".");
					Map<String, String> map = new HashMap<String, String>();
					map.put("region", res.getString(1));
					map.put("percentage", res.getString(2));
					list.add((HashMap<String, String>) map);
				}
				res.close();
				returnMap.put(yearArray[i], list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return returnMap;
	}

	public Map<String, List<HashMap<String, String>>> getEducationWithStrokeInfo(String province, String acCodeUp,
			String community) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, List<HashMap<String, String>>> returnMap = new HashMap<String, List<HashMap<String, String>>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				ResultSet res = dbUtil.query("SELECT " + "(CASE aEdu " + "	WHEN '1' THEN " + "		'小学' "
						+ "	WHEN '2' THEN " + "		'初中' " + "	WHEN '3' THEN " + "		'高中' " + "	WHEN '4' THEN "
						+ "		'大专大学' " + "	WHEN '5' THEN " + "		'硕士及以上' " + "	ELSE " + "		'未知' "
						+ "	END " + ") AS Education, " + "SUM(IF(dfstroke = 1, 1, 0)) / COUNT(1) AS percentage "
						+ "FROM " + "	Archives " + "JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid "
						+ " JOIN acid" + yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
						+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
						+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode "
						+ "JOIN DangerFactors"+yearArray[i]+" ON Archives.aid = DangerFactors"+yearArray[i]+".aid " + "WHERE "
						+ "	(GB_native.necode = '" + province + "' or '" + province + "'='' )"
						+ "AND (ArchivesCases.accodeup = '" + acCodeUp + "' or '" + acCodeUp + "'='' )"
						+ "AND (ArchivesCases.uucode = '" + community + "' or '" + community + "'='' )" + "GROUP BY "
						+ "	( " + "		CASE aEdu " + "		WHEN '1' THEN " + "			'小学' " + "		WHEN '2' THEN "
						+ "			'初中' " + "		WHEN '3' THEN " + "			'高中' " + "		WHEN '4' THEN "
						+ "			'大专大学' " + "		WHEN '5' THEN " + "			'硕士及以上' " + "		ELSE "
						+ "			'未知' " + "		END " + "	)");
				while (res.next()) {
					System.out.print(".");
					Map<String, String> map = new HashMap<String, String>();
					map.put("region", res.getString(1));
					map.put("percentage", res.getString(2));
					list.add((HashMap<String, String>) map);
				}
				res.close();
				returnMap.put(yearArray[i], list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return returnMap;
	}

	public Map<String, Map<String, List<HashMap<String, String>>>> getGenderDangerFactorInfo(String province,
			String dangertype, String ageclassification) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, Map<String, List<HashMap<String, String>>>> returnMap = new HashMap<String, Map<String, List<HashMap<String, String>>>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				Map<String, List<HashMap<String, String>>> map = new HashMap<String, List<HashMap<String, String>>>();
				String sql = "";
				String[] sexArray = new String[] { "1", "2", "" };
				String tempPartSql = dangertype + "=1";
				if (dangertype.equals("dfLDL"))
					tempPartSql += " or " + dangertype + "=9";
				for (String sex : sexArray) {
					if (ageclassification.equals("1")) {
						sql = "SELECT " + "	acAge AS age ,COUNT(1) AS agecount " + "FROM " + "DangerFactors"+yearArray[i]
								+ " JOIN Archives ON DangerFactors"+yearArray[i]+".aid = Archives.aid "
								+ "JOIN ArchivesCases ON DangerFactors"+yearArray[i]+".aid = ArchivesCases.aid "
								+ "JOIN acid" + yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
								+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
								+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "WHERE "
								+ "	(aSex = '" + sex + "' or '" + sex + "'='') AND " + tempPartSql + "  "
								+ "AND (GB_native.necode = '" + province + "' or '" + province + "'='')"
								+ " GROUP BY acAge " + " ORDER BY acAge;";
					}
					if (ageclassification.equals("5")) {
						sql = "SELECT " + "(CASE " + "	WHEN acAge = 40 " + "	AND acAge <= 44 THEN "
								+ "		'40-44' " + "	WHEN acAge >= 45 " + "	AND acAge <= 49 THEN "
								+ "		'45-49' " + "	WHEN acAge >= 50 " + "	AND acAge <= 54 THEN "
								+ "		'50-54' " + "	WHEN acAge >= 55 " + "	AND acAge <= 59 THEN "
								+ "		'55-59' " + "	WHEN acAge >= 60 " + "	AND acAge <= 64 THEN "
								+ "		'60-64' " + "	WHEN acAge >= 65 " + "	AND acAge <= 69 THEN "
								+ "		'65-69' " + "	WHEN acAge >= 70 " + "	AND acAge <= 74 THEN "
								+ "		'70-74' " + "	WHEN acAge >= 75 " + "	AND acAge <= 79 THEN "
								+ "		'75-79' " + "	WHEN acAge >= 80 THEN " + "		'80+' " + "	END "
								+ ") AS age,COUNT(1) AS agecount " + "FROM DangerFactors"+yearArray[i]
								+ " JOIN Archives ON DangerFactors"+yearArray[i]+".aid = Archives.aid "
								+ "JOIN ArchivesCases ON DangerFactors"+yearArray[i]+".aid = ArchivesCases.aid "
								+ "JOIN acid" + yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
								+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
								+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "WHERE "
								+ "	(aSex = '" + sex + "' or '" + sex + "'='') AND " + tempPartSql
								+ " AND (GB_native.necode = '" + province + "' or '" + province + "'='') "
								+ "GROUP BY( " + "CASE " + "		WHEN acAge = 40 " + "		AND acAge <= 44 THEN "
								+ "			'40-44' " + "		WHEN acAge >= 45 " + "		AND acAge <= 49 THEN "
								+ "			'45-49' " + "		WHEN acAge >= 50 " + "		AND acAge <= 54 THEN "
								+ "			'50-54' " + "		WHEN acAge >= 55 " + "		AND acAge <= 59 THEN "
								+ "			'55-59' " + "		WHEN acAge >= 60 " + "		AND acAge <= 64 THEN "
								+ "			'60-64' " + "		WHEN acAge >= 65 " + "		AND acAge <= 69 THEN "
								+ "			'65-69' " + "		WHEN acAge >= 70 " + "		AND acAge <= 74 THEN "
								+ "			'70-74' " + "		WHEN acAge >= 75 " + "		AND acAge <= 79 THEN "
								+ "			'75-79' " + "		WHEN acAge >= 80 THEN " + "			'80+' "
								+ "		END )";
					}
					if (ageclassification.equals("10")) {
						sql = "SELECT " + "(CASE " + "	WHEN acAge >= 40 " + "	AND acAge <= 49 THEN "
								+ "		'40-49' " + "	WHEN acAge >= 50 " + "	AND acAge <= 59 THEN "
								+ "		'50-59' " + "	WHEN acAge >= 60 " + "	AND acAge <= 69 THEN "
								+ "		'60-69' " + "	WHEN acAge >= 70 " + "	AND acAge <= 79 THEN "
								+ "		'70-79' " + "	WHEN acAge >= 80 THEN " + "		'80+' " + "	END " + ") AS age, "
								+ "COUNT(1) AS agecount " + "FROM " + "	DangerFactors"+yearArray[i]
								+ " JOIN Archives ON DangerFactors"+yearArray[i]+".aid = Archives.aid "
								+ "JOIN ArchivesCases ON DangerFactors"+yearArray[i]+".aid = ArchivesCases.aid "
								+ "JOIN acid" + yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
								+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
								+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "WHERE "
								+ "	(aSex = '" + sex + "' or '" + sex + "'='') " + "AND " + tempPartSql + " "
								+ "AND (GB_native.necode = '" + province + "' or '" + province + "'='' )" + "GROUP BY "
								+ "	( " + "		CASE " + "		WHEN acAge >= 40 " + "		AND acAge <= 49 THEN "
								+ "			'40-49' " + "		WHEN acAge >= 50 " + "		AND acAge <= 59 THEN "
								+ "			'50-59' " + "		WHEN acAge >= 60 " + "		AND acAge <= 69 THEN "
								+ "			'60-69' " + "		WHEN acAge >= 70 " + "		AND acAge <= 79 THEN "
								+ "			'70-79' " + "		WHEN acAge >= 80 THEN " + "			'80+' "
								+ "		END )";
					}
					List<HashMap<String, String>> tempList = new ArrayList<HashMap<String, String>>();
					ResultSet res = dbUtil.query(sql);
					while (res.next()) {
						System.out.print(".");
						Map<String, String> tempmap = new HashMap<String, String>();
						tempmap.put("age", res.getString(1));
						tempmap.put("count", res.getString(2));
						tempList.add((HashMap<String, String>) tempmap);
					}
					map.put(sex, tempList);
					res.close();
				}
				returnMap.put(yearArray[i], map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return returnMap;
	}

	public Map<String, Map<String, List<HashMap<String, String>>>> getRegionDangerFactorInfo(String province,
			String dangertype, String ageclassification) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, Map<String, List<HashMap<String, String>>>> returnMap = new HashMap<String, Map<String, List<HashMap<String, String>>>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				Map<String, List<HashMap<String, String>>> map = new HashMap<String, List<HashMap<String, String>>>();
				String sql = "";
				String[] regionArray = new String[] { "50", "60", "" };
				String tempPartSql = dangertype + "=1";
				if (dangertype.equals("dfLDL"))
					tempPartSql += " or " + dangertype + "=9";
				for (String region : regionArray) {
					if (ageclassification.equals("1")) {
						sql = "SELECT " + "	acAge AS age ,COUNT(1) AS agecount " + "FROM " + "DangerFactors"+yearArray[i]
								+ " JOIN Archives ON DangerFactors"+yearArray[i]+".aid = Archives.aid "
								+ "JOIN ArchivesCases ON DangerFactors"+yearArray[i]+".aid = ArchivesCases.aid "
								+ "JOIN acid" + yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
								+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
								+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "WHERE "
								+ "	(acType = '" + region + "' or '" + region + "'='') AND " + tempPartSql + "  "
								+ "AND (GB_native.necode = '" + province + "' or '" + province + "'='')"
								+ " GROUP BY acAge " + " ORDER BY acAge;";
					}
					if (ageclassification.equals("5")) {
						sql = "SELECT " + "(CASE " + "	WHEN acAge = 40 " + "	AND acAge <= 44 THEN "
								+ "		'40-44' " + "	WHEN acAge >= 45 " + "	AND acAge <= 49 THEN "
								+ "		'45-49' " + "	WHEN acAge >= 50 " + "	AND acAge <= 54 THEN "
								+ "		'50-54' " + "	WHEN acAge >= 55 " + "	AND acAge <= 59 THEN "
								+ "		'55-59' " + "	WHEN acAge >= 60 " + "	AND acAge <= 64 THEN "
								+ "		'60-64' " + "	WHEN acAge >= 65 " + "	AND acAge <= 69 THEN "
								+ "		'65-69' " + "	WHEN acAge >= 70 " + "	AND acAge <= 74 THEN "
								+ "		'70-74' " + "	WHEN acAge >= 75 " + "	AND acAge <= 79 THEN "
								+ "		'75-79' " + "	WHEN acAge >= 80 THEN " + "		'80+' " + "	END "
								+ ") AS age,COUNT(1) AS agecount " + "FROM DangerFactors"+yearArray[i]
								+ "JOIN Archives ON DangerFactors"+yearArray[i]+".aid = Archives.aid "
								+ "JOIN ArchivesCases ON DangerFactors"+yearArray[i]+".aid = ArchivesCases.aid "
								+ "JOIN acid" + yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
								+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
								+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "WHERE "
								+ "	(acType = '" + region + "' or '" + region + "'='') AND " + tempPartSql
								+ " AND (GB_native.necode = '" + province + "' or '" + province + "'='') "
								+ "GROUP BY( " + "CASE " + "		WHEN acAge = 40 " + "		AND acAge <= 44 THEN "
								+ "			'40-44' " + "		WHEN acAge >= 45 " + "		AND acAge <= 49 THEN "
								+ "			'45-49' " + "		WHEN acAge >= 50 " + "		AND acAge <= 54 THEN "
								+ "			'50-54' " + "		WHEN acAge >= 55 " + "		AND acAge <= 59 THEN "
								+ "			'55-59' " + "		WHEN acAge >= 60 " + "		AND acAge <= 64 THEN "
								+ "			'60-64' " + "		WHEN acAge >= 65 " + "		AND acAge <= 69 THEN "
								+ "			'65-69' " + "		WHEN acAge >= 70 " + "		AND acAge <= 74 THEN "
								+ "			'70-74' " + "		WHEN acAge >= 75 " + "		AND acAge <= 79 THEN "
								+ "			'75-79' " + "		WHEN acAge >= 80 THEN " + "			'80+' "
								+ "		END )";
					}
					if (ageclassification.equals("10")) {
						sql = "SELECT " + "(CASE " + "	WHEN acAge >= 40 " + "	AND acAge <= 49 THEN "
								+ "		'40-49' " + "	WHEN acAge >= 50 " + "	AND acAge <= 59 THEN "
								+ "		'50-59' " + "	WHEN acAge >= 60 " + "	AND acAge <= 69 THEN "
								+ "		'60-69' " + "	WHEN acAge >= 70 " + "	AND acAge <= 79 THEN "
								+ "		'70-79' " + "	WHEN acAge >= 80 THEN " + "		'80+' " + "	END " + ") AS age, "
								+ "COUNT(1) AS agecount " + "FROM " + "	DangerFactors"+yearArray[i]
								+ " JOIN Archives ON DangerFactors"+yearArray[i]+".aid = Archives.aid "
								+ "JOIN ArchivesCases ON DangerFactors"+yearArray[i]+".aid = ArchivesCases.aid "
								+ "JOIN acid" + yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
								+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
								+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "WHERE "
								+ "	(acType = '" + region + "' or '" + region + "'='') " + "AND " + tempPartSql + " "
								+ "AND (GB_native.necode = '" + province + "' or '" + province + "'='' )" + "GROUP BY "
								+ "	( " + "		CASE " + "		WHEN acAge >= 40 " + "		AND acAge <= 49 THEN "
								+ "			'40-49' " + "		WHEN acAge >= 50 " + "		AND acAge <= 59 THEN "
								+ "			'50-59' " + "		WHEN acAge >= 60 " + "		AND acAge <= 69 THEN "
								+ "			'60-69' " + "		WHEN acAge >= 70 " + "		AND acAge <= 79 THEN "
								+ "			'70-79' " + "		WHEN acAge >= 80 THEN " + "			'80+' "
								+ "		END )";
					}
					List<HashMap<String, String>> tempList = new ArrayList<HashMap<String, String>>();
					ResultSet res = dbUtil.query(sql);
					while (res.next()) {
						System.out.print(".");
						Map<String, String> tempmap = new HashMap<String, String>();
						tempmap.put("age", res.getString(1));
						tempmap.put("count", res.getString(2));
						tempList.add((HashMap<String, String>) tempmap);
					}
					map.put(region, tempList);
					res.close();
				}
				returnMap.put(yearArray[i], map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return returnMap;
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

	/**
	 * @return the hcConfiguration
	 */
	public HealthcareConfiguration getHcConfiguration() {
		return hcConfiguration;
	}

	/**
	 * @param hcConfiguration
	 *            the hcConfiguration to set
	 */
	public void setHcConfiguration(HealthcareConfiguration hcConfiguration) {
		this.hcConfiguration = hcConfiguration;
	}

	/**
	 * @return the yearMap
	 */
	public static Map<String, List<String>> getYearMap() {
		return yearMap;
	}

	/**
	 * @param yearMap
	 *            the yearMap to set
	 */
	public static void setYearMap(Map<String, List<String>> yearMap) {
		MySQLService.yearMap = yearMap;
	}

}
