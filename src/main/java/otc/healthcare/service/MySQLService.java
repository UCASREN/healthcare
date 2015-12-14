package otc.healthcare.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<BaseHospitalModel> getJoinBaseHosiptalInfo() {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		DBUtil dbUtil_inner = new DBUtil(connectionFactory.getInstance().getConnection());
		List<BaseHospitalModel> baseHospitalInfoList = new ArrayList<BaseHospitalModel>();
		try {
			ResultSet res = dbUtil
					.query("SELECT DISTINCT acCodeUp FROM ArchivesCases WHERE acHType=5000 or acHType=6000");
			while (res.next()) {
				BaseHospitalModel bhm = new BaseHospitalModel();
				ResultSet res_inner = dbUtil_inner
						.query("SELECT DISTINCT uuCode,uuName,uull from UserUnit where uuCode=" + res.getString(1));
				if (res_inner.next()) {
					bhm.setUuCode(res_inner.getString(1));
					bhm.setName(res_inner.getString(2));
					bhm.setUull(res_inner.getString(3));
				}
				res_inner = dbUtil_inner.query(
						"SELECT COUNT(*) FROM ArchivesCases WHERE acEndState=1 and acCodeUp=" + res.getString(1));
				if (res_inner.next()) {
					bhm.setEndCount(res_inner.getInt(1));
				}
				res_inner = dbUtil_inner
						.query("SELECT COUNT(*) FROM ArchivesCases WHERE acStatus=3 and acCodeUp=" + res.getString(1));
				if (res_inner.next()) {
					bhm.setDangerCount(res_inner.getInt(1));
				}
				res_inner = dbUtil_inner
						.query("SELECT COUNT(*) FROM ArchivesCases WHERE acStatus=3 and uuCode=" + res.getString(1));
				if (res_inner.next()) {
					bhm.setDangerCount(bhm.getDangerCount() + res_inner.getInt(1));
				}
				baseHospitalInfoList.add(bhm);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return baseHospitalInfoList;
	}

	public List<CommunityModel> getJoinCommunityInfo() {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		List<CommunityModel> communityInfoList = new ArrayList<CommunityModel>();
		try {
			List<String> community_uuCode = new ArrayList<String>();
			List<String> community_acCodeUp = new ArrayList<String>();
			ResultSet res = dbUtil
					.query("SELECT DISTINCT uuCode,acCodeUp FROM ArchivesCases WHERE acHType=5000 or acHType=6000");
			while (res.next()) {
				community_uuCode.add(res.getString(1));
				community_acCodeUp.add(res.getString(2));
			}
			for (int i = 0; i < community_uuCode.size(); i++) {
				CommunityModel cm = new CommunityModel();
				res = dbUtil.query(
						"SELECT DISTINCT uuCode,uuName,uull from UserUnit where uuCode=" + community_uuCode.get(i));
				if (res.next()) {
					cm.setUuCode(res.getString(1));
					cm.setName(res.getString(2));
					cm.setUull(res.getString(3));
				}
				cm.setAcCodeUp(community_acCodeUp.get(i));
				res = dbUtil.query("SELECT DISTINCT uuName from UserUnit where uuCode=" + community_acCodeUp.get(i));
				if (res.next()) {
					cm.setUpName(res.getString(1));
				}
				res = dbUtil.query(
						"SELECT COUNT(*) FROM ArchivesCases WHERE acEndState=1 and uuCode=" + community_uuCode.get(i));
				if (res.next()) {
					cm.setEndCount(res.getInt(1));
				}
				communityInfoList.add(cm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return communityInfoList;
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

	public Map<String, YearStatisticsModel> getYearInfo() {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, YearStatisticsModel> map = new HashMap<String, YearStatisticsModel>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		String[] yearArraySQL = new String[] { "(uuProYear LIKE '%2011%')",
				"(uuProYear LIKE '%2011%') or (uuProYear LIKE '%2012%')",
				"(uuProYear LIKE '%2011%') or (uuProYear LIKE '%2012%') or (uuProYear LIKE '%2013%')" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				YearStatisticsModel ysm = new YearStatisticsModel();
				ResultSet res = dbUtil
						.query("SELECT COUNT(DISTINCT uuProvince) FROM UserUnit WHERE " + yearArraySQL[i]);
				if (res.next()) {
					ysm.setProvinceCount(res.getInt(1));
				}
				res = dbUtil.query("SELECT COUNT(DISTINCT uuCity) FROM UserUnit WHERE " + yearArraySQL[i]);
				if (res.next()) {
					ysm.setCityCount(res.getInt(1));
				}
				res = dbUtil.query("SELECT DISTINCT uuCode FROM UserUnit WHERE " + yearArraySQL[i]
						+ " and (uuType=5000 or uuType=6000)");
				List<String> uuCodeList = new ArrayList<String>();
				while (res.next()) {
					uuCodeList.add(res.getString(1));
				}
				ysm.setJoinCommunityCount(uuCodeList.size());
				int count = 0;
				for (String uuCode : uuCodeList) {
					res = dbUtil.query("SELECT COUNT(*) FROM ArchivesCases WHERE acEndState=1 and uuCode=" + uuCode);
					if (res.next()) {
						count += res.getInt(1);
					}
				}
				ysm.setEndCount(count);
				count = 0;
				for (String uuCode : uuCodeList) {
					res = dbUtil.query("SELECT COUNT(*) FROM ArchivesCases WHERE acStatus=3 and uuCode=" + uuCode);
					if (res.next()) {
						count += res.getInt(1);
					}
				}
				ysm.setDangerCount(count);
				count = 0;
				for (String uuCode : uuCodeList) {
					res = dbUtil.query("SELECT COUNT(*) FROM ArchivesCases WHERE acStatus=5 and uuCode=" + uuCode);
					if (res.next()) {
						count += res.getInt(1);
					}
				}
				ysm.setStrokeCount(count);
				map.put(yearArray[i], ysm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return map;
	}

	public List<HashMap<String, String>> getGenderInfo(String province, String acCodeUp, String community) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		List<HashMap<String, String>> returnList=new ArrayList<HashMap<String, String>>();
		try {
			String sql="SELECT (CASE aSex WHEN '1' THEN '男' WHEN '2' THEN '女' END) "+
					"AS gender, COUNT(1) / a.total AS percentage  "+
					"FROM ( "+
					"SELECT COUNT(1) AS total "+ 
					"FROM Archives  "+
					"JOIN ArchivesCases  "+
					"ON Archives.aid = ArchivesCases.aid  "+
					"JOIN UserUnit  "+
					"ON Archives.uucode = UserUnit.uucode  "+
					"JOIN GB_native  "+
					"ON UserUnit.uuprovince = GB_native.necode  "+
					"WHERE (GB_native.necode = "+province+") "+
					"AND (ArchivesCases.accodeup = "+acCodeUp+") "+
					"AND (ArchivesCases.uucode = "+community+") "+
					")a, Archives  "+
					"JOIN ArchivesCases  "+
					"ON Archives.aid = ArchivesCases.aid  "+
					"JOIN UserUnit ON Archives.uucode = UserUnit.uucode  "+
					"JOIN GB_native ON UserUnit.uuprovince = GB_native.necode  "+
					"WHERE (GB_native.necode = "+province+" ) AND (ArchivesCases.accodeup = "+acCodeUp+" ) "+
					"AND (ArchivesCases.uucode = "+community+") GROUP BY aSex, a.total";
			ResultSet res = dbUtil.query(sql);
			while (res.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("gender", res.getString(1));
				map.put("percentage", res.getString(2));
				returnList.add((HashMap<String, String>) map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return returnList;
	}

	public Map<String, String> getAgeInfo(String province, String acCodeUp, String community) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, String> map = new HashMap<String, String>();
		try {
			ResultSet res = dbUtil.query("SELECT" + "(CASE " + "WHEN acAge >= 40 " + "AND acAge <= 49 THEN "
					+ "	'40-49' " + "WHEN acAge >= 50 " + "AND acAge <= 59 THEN " + "	'50-59' " + "WHEN acAge >= 60 "
					+ "AND acAge <= 69 THEN " + "	'60-69' " + "WHEN acAge >= 70 " + "AND acAge <= 79 THEN "
					+ "	'70-79' " + "WHEN acAge >= 80 THEN " + "	'80+' " + "END " + ") AS age, "
					+ "COUNT(1) / a.total AS percentage " + "GROUP BY " + "(CASE " + "	WHEN acAge >= 40 "
					+ "	AND acAge <= 49 THEN " + "		'40-49' " + "	WHEN acAge >= 50 " + "	AND acAge <= 59 THEN "
					+ "		'50-59' " + "	WHEN acAge >= 60 " + "	AND acAge <= 69 THEN " + "		'60-69' "
					+ "	WHEN acAge >= 70 " + "	AND acAge <= 79 THEN " + "		'70-79' " + "	WHEN acAge >= 80 THEN "
					+ "		'80+' " + "	END " + "), " + "a.total " + "FROM " + "(SELECT " + "		COUNT(1) AS total "
					+ "	FROM " + "		Archives " + "	JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid "
					+ "	JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
					+ "	JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "	WHERE "
					+ "		GB_native.necode = " + province + " or " + province + "=''AND ArchivesCases.accodeup = "
					+ acCodeUp + " or " + acCodeUp + "=''	AND ArchivesCases.uucode = " + community + " or "
					+ community + "='' ) a, " + "Archives " + "JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid "
					+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
					+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "WHERE " + "	GB_native.necode = "
					+ province + " or " + province + "='' AND ArchivesCases.accodeup = " + acCodeUp + " or " + acCodeUp
					+ "='' AND ArchivesCases.uucode = " + community + " or " + community + "=''");
			if (res.next()) {
				map.put("age", res.getString(1));
				map.put("percentage", res.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return map;
	}

	public Map<String, String> getRegionInfo(String province, String acCodeUp, String community) {
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql",
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_URL),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_USERNAME),
				this.getHcConfiguration().getProperty(HealthcareConfiguration.MYSQL_DB_PASSWORD));
		DBUtil dbUtil = new DBUtil(connectionFactory.getInstance().getConnection());
		Map<String, String> map = new HashMap<String, String>();
		try {
			ResultSet res = dbUtil.query("SELECT " + "(CASE acType " + "	WHEN '50' THEN " + "		'城市' "
					+ "	WHEN '60' THEN " + "		'乡镇' " + "	END " + ") AS region, "
					+ "COUNT(1) / a.total AS percentage " + "FROM " + "( " + "	SELECT " + "		COUNT(1) AS total "
					+ "	FROM " + "		Archives " + "	JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid "
					+ "	JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
					+ "	JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "	WHERE "
					+ "		GB_native.necode = "+province+" or "+province + "=''	AND ArchivesCases.accodeup = "+acCodeUp+" or "+acCodeUp
					+ "=''	AND ArchivesCases.uucode = "+community+" or "+community+ "='' ) a, " + " Archives "
					+ "JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid "
					+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
					+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "WHERE "
					+ "	GB_native.necode = "+province+" or "+province + "=''  AND ArchivesCases.accodeup = "+acCodeUp+" or "+acCodeUp
					+ "='' AND ArchivesCases.uucode = "+community+" or "+community + "='' GROUP BY " + "	acType, " + "	a.total");
			if (res.next()) {
				map.put("region", res.getString(1));
				map.put("percentage", res.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return map;
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

}
