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
import otc.healthcare.dao.DataSourceFactory;
import otc.healthcare.pojo.BaseHospitalModel;
import otc.healthcare.pojo.CommunityModel;
import otc.healthcare.pojo.YearStatisticsModel;
import otc.healthcare.util.DBUtil;
import otc.healthcare.util.HealthcareConfiguration;

@Component
public class MySQLServiceScreen implements IService {
	@Autowired
	private HealthcareConfiguration hcConfiguration;
	private Map<String, HashMap<String, String>> yearMap;

	public Map<String, List<BaseHospitalModel>> getJoinBaseHosiptalInfo() {
		System.out.println("正在获取基地医院数据");
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
		Map<String, List<BaseHospitalModel>> map = new HashMap<String, List<BaseHospitalModel>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				System.out.println(yearArray[i]);
				List<BaseHospitalModel> list = new ArrayList<BaseHospitalModel>();
				ResultSet res = dbUtil
						.query("select distinct userunit.uuCode,uuName,uull,uuProvince from userunit join archivescases on userunit.uuCode=archivescases.acCodeUp join acid"
								+ yearArray[i] + " on " + "acid" + yearArray[i]
								+ ".acid=archivescases.acid where userunit.uuType=3000");
				while (res.next()) {
					BaseHospitalModel bhm = new BaseHospitalModel();
					System.out.println("筛选" + res.getString(1));
					bhm.setUuCode(res.getString(1));
					bhm.setName(res.getString(2));
					bhm.setUull(res.getString(3));
					bhm.setUuProvince(res.getString(4));

					list.add(bhm);
				}
				res.close();
				System.out.println("筛选endCount和DangerCount");
				ResultSet res1 = dbUtil
						.query("select archivescases.acCodeUp,acEndState,acStatus from archivescases join (select distinct userunit.uuCode from userunit join archivescases on userunit.uuCode=archivescases.acCodeUp join acid"
								+ yearArray[i] + " on " + "acid" + yearArray[i]
								+ ".acid=archivescases.acid where userunit.uuType=3000) as tempt where tempt.uuCode=archivescases.acCodeUp");
				Map<String, Integer> uuCodeEndCountMap = new HashMap<String, Integer>();
				Map<String, Integer> uuCodeDangerCountMap = new HashMap<String, Integer>();
				while (res1.next()) {
					// acEndState=1
					if (!uuCodeEndCountMap.containsKey(res1.getString(1)))
						uuCodeEndCountMap.put(res1.getString(1), 0);
					if (res1.getString(2).equals("1"))
						uuCodeEndCountMap.put(res1.getString(1), uuCodeEndCountMap.get(res1.getString(1)) + 1);
					// acStatus=3
					if (!uuCodeDangerCountMap.containsKey(res1.getString(1)))
						uuCodeDangerCountMap.put(res1.getString(1), 0);
					if (res1.getString(3).equals("3"))
						uuCodeDangerCountMap.put(res1.getString(1), uuCodeDangerCountMap.get(res1.getString(1)) + 1);
				}
				res1.close();
				System.out.println("设置endCount和DangerCount");
				for (BaseHospitalModel bhm : list) {
					System.out.println(uuCodeEndCountMap.get(bhm.getUuCode())+":"+uuCodeDangerCountMap.get(bhm.getUuCode()));
					bhm.setEndCount(uuCodeEndCountMap.get(bhm.getUuCode()));
					bhm.setDangerCount(uuCodeDangerCountMap.get(bhm.getUuCode()));
				}
				map.put(yearArray[i], list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
		return map;
	}

	public Map<String, List<CommunityModel>> getJoinCommunityInfo() {
		System.out.println("正在获取社区数据");
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
		DBUtil dbUtil_inner = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
		Map<String, List<CommunityModel>> map = new HashMap<String, List<CommunityModel>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				System.out.println(yearArray[i]);
				List<CommunityModel> list = new ArrayList<CommunityModel>();
				ResultSet res = dbUtil
						.query("select distinct userunit.uuCode,uuName,uull,acCodeUp from userunit join archivescases on userunit.uuCode=archivescases.uuCode join acid"
								+ yearArray[i] + " on " + "acid" + yearArray[i]
								+ ".acid=archivescases.acid where userunit.uuType=5000 or userunit.uuType=6000");
				while (res.next()) {
					// System.out.print(".");
					CommunityModel cm = new CommunityModel();
					System.out.println("筛选" + res.getString(1));
					cm.setUuCode(res.getString(1));
					cm.setName(res.getString(2));
					cm.setUull(res.getString(3));
					cm.setAcCodeUp(res.getString(4));
					System.out.println("获取上级单位");
					ResultSet res_inner = dbUtil_inner
							.query("SELECT DISTINCT uuName from UserUnit where uuCode=" + res.getString(4));
					if (res_inner.next()) {
						cm.setUpName(res_inner.getString(1));
					}
					res_inner.close();
					list.add(cm);
				}
				System.out.println("筛选endCount和DangerCount");
				ResultSet res1 = dbUtil
						.query("select archivescases.uuCode,acEndState,acStatus from archivescases join (select distinct userunit.uuCode from userunit join archivescases on userunit.uuCode=archivescases.uuCode join acid"
								+ yearArray[i] + " on " + "acid" + yearArray[i]
								+ ".acid=archivescases.acid where userunit.uuType=5000 or userunit.uuType=6000) as tempt where tempt.uuCode=archivescases.uuCode");
				Map<String, Integer> uuCodeEndCountMap = new HashMap<String, Integer>();
				Map<String, Integer> uuCodeDangerCountMap = new HashMap<String, Integer>();
				while (res1.next()) {
					// acEndState=1
					if (!uuCodeEndCountMap.containsKey(res1.getString(1)))
						uuCodeEndCountMap.put(res1.getString(1), 0);
					if (res1.getString(2).equals("1"))
						uuCodeEndCountMap.put(res1.getString(1), uuCodeEndCountMap.get(res1.getString(1)) + 1);
					// acStatus=3
					if (!uuCodeDangerCountMap.containsKey(res1.getString(1)))
						uuCodeDangerCountMap.put(res1.getString(1), 0);
					if (res1.getString(3).equals("3"))
						uuCodeDangerCountMap.put(res1.getString(1), uuCodeDangerCountMap.get(res1.getString(1)) + 1);
				}
				res1.close();
				System.out.println("设置endCount和DangerCount");
				for (CommunityModel cm : list) {
					cm.setEndCount(uuCodeEndCountMap.get(cm.getUuCode()));
					cm.setDangerCount(uuCodeDangerCountMap.get(cm.getUuCode()));
				}
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
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
		Map<String, Object> returnMap = new HashMap<String, Object>();
		HashMap<String, ArrayList<String>> provinceCityMap = new HashMap<String, ArrayList<String>>();
		Map<String, String> provinceMap = new HashMap<String, String>();
		Map<String, String> cityMap = new HashMap<String, String>();
		try {
			ResultSet res = dbUtil.query("SELECT DISTINCT neCode,neName FROM GB_native WHERE neCode LIKE '____0000'");
			while (res.next()) {
				// System.out.print(".");
				String neCode = res.getString(1);
				String neName = res.getString(2);
				if (neCode.indexOf("000000") != -1
						&& !provinceCityMap.containsKey(neCode.substring(0, 2) + "_" + neName)) {
					provinceMap.put(neCode.substring(0, 2), neName);
					provinceCityMap.put(neCode.substring(0, 2) + "_" + neName, new ArrayList());
				} else if (neCode.indexOf("0000") != -1) {
					cityMap.put(neCode.substring(0, 4), neName);
					if (provinceCityMap
							.containsKey(neCode.substring(0, 2) + "_" + provinceMap.get(neCode.substring(0, 2))))
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
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
		Map<String, String> provinceMap = new HashMap<String, String>();
		try {
			ResultSet res = dbUtil.query("SELECT DISTINCT neCode,neName FROM GB_native WHERE neCode LIKE '__000000'");
			while (res.next()) {
				// System.out.print(".");
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
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
		Map<String, String> cityMap = new HashMap<String, String>();
		try {
			ResultSet res = dbUtil
					.query("SELECT DISTINCT neCode,neName FROM GB_native WHERE neCode LIKE '" + provinceId + "__0000'");
			while (res.next()) {
				// System.out.print(".");
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

	public Map<String, HashMap<String, String>> getYearAcid() {
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
		Map<String, HashMap<String, String>> yearMap = new HashMap<String, HashMap<String, String>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		for (String year : yearArray) {
			HashMap<String, String> tempmap = new HashMap<String, String>();
			ResultSet res = dbUtil.query("Select acid from acid" + year);
			try {
				while (res.next()) {
					// System.out.print(".");
					tempmap.put(res.getString(1), "1");
				}
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			yearMap.put(year, tempmap);
		}
		return yearMap;
	}

	public Map<String, YearStatisticsModel> getYearInfo() {
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
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
					// System.out.print(".");
					citySet.add(res.getString(2));
					provinceSet.add(res.getString(3));
					if (res.getString(4).equals("3000"))
						joinBaseHospitalSet.add(res.getString(1));
				}
				res.close();
				res = dbUtil
						.query("select distinct userunit.uuCode,uuCity,uuProvince,acHType from userunit join archivescases on userunit.uuCode=archivescases.uuCode join acid"
								+ yearArray[i] + " on " + "acid" + yearArray[i] + ".acid=archivescases.acid");
				while (res.next()) {
					// System.out.print(".");
					citySet.add(res.getString(2));
					provinceSet.add(res.getString(3));
					if (res.getString(4).equals("5000") || res.getString(4).equals("6000"))
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
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
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
					// System.out.print(".");
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
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
		Map<String, List<HashMap<String, String>>> returnMap = new HashMap<String, List<HashMap<String, String>>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				String sql = "SELECT " + "(CASE aSex " + "	WHEN '1' THEN " + "		'男' " + "	WHEN '2' THEN "
						+ "		'女' " + "	END " + ") AS gender, "
						+ "	SUM(IF(dfstroke = 1, 1, 0)) / COUNT(1) AS percentage " + "FROM Archives "
						+ "JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid " + "JOIN acid" + yearArray[i]
						+ " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
						+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
						+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "JOIN DangerFactors"
						+ yearArray[i] + " ON Archives.aid = DangerFactors" + yearArray[i] + ".aid " + "WHERE "
						+ "	GB_native.necode = '" + province + "' or '" + province + "'='' "
						+ "AND ArchivesCases.accodeup = '" + acCodeUp + "' or '"+acCodeUp+"'='' " + "AND ArchivesCases.uucode = '"
						+ community + "' or '" + community + "'='' " + "GROUP BY aSex";
				ResultSet res = dbUtil.query(sql);
				while (res.next()) {
					// System.out.print(".");
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
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
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
					// System.out.print(".");
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
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
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
						+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "JOIN DangerFactors"
						+ yearArray[i] + " ON Archives.aid = DangerFactors" + yearArray[i] + ".aid " + "WHERE "
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
					// System.out.print(".");
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
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
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
					// System.out.print(".");
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
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
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
						+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "JOIN DangerFactors"
						+ yearArray[i] + " ON Archives.aid = DangerFactors" + yearArray[i] + ".aid " + "WHERE "
						+ "	(GB_native.necode = '" + province + "' or '" + province + "'='' )"
						+ "AND (ArchivesCases.accodeup = '" + acCodeUp + "' or '" + acCodeUp + "'='' )"
						+ "AND (ArchivesCases.uucode = '" + community + "' or '" + community + "'='' )"
						+ "GROUP BY acType");
				while (res.next()) {
					// System.out.print(".");
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
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
		Map<String, List<HashMap<String, String>>> returnMap = new HashMap<String, List<HashMap<String, String>>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				ResultSet res = dbUtil.query("SELECT " + "(CASE acEdu " + "	WHEN '1' THEN " + "		'小学' "
						+ "	WHEN '2' THEN " + "		'初中' " + "	WHEN '3' THEN " + "		'高中' " + "	WHEN '4' THEN "
						+ "		'大专大学' " + "	WHEN '5' THEN " + "		'硕士及以上' " + "	ELSE " + "		'未知' "
						+ "	END " + ") AS Education, " + "SUM(IF(dfstroke = 1, 1, 0)) / COUNT(1) AS percentage "
						+ "FROM " + "	Archives " + "JOIN ArchivesCases ON Archives.aid = ArchivesCases.aid "
						+ " JOIN acid" + yearArray[i] + " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
						+ "JOIN UserUnit ON Archives.uucode = UserUnit.uucode "
						+ "JOIN GB_native ON UserUnit.uuprovince = GB_native.necode " + "JOIN DangerFactors"
						+ yearArray[i] + " ON Archives.aid = DangerFactors" + yearArray[i] + ".aid " + "WHERE "
						+ "	(GB_native.necode = '" + province + "' or '" + province + "'='' )"
						+ "AND (ArchivesCases.accodeup = '" + acCodeUp + "' or '" + acCodeUp + "'='' )"
						+ "AND (ArchivesCases.uucode = '" + community + "' or '" + community + "'='' )" + "GROUP BY "
						+ "	( " + "		CASE acEdu " + "		WHEN '1' THEN " + "			'小学' "
						+ "		WHEN '2' THEN " + "			'初中' " + "		WHEN '3' THEN " + "			'高中' "
						+ "		WHEN '4' THEN " + "			'大专大学' " + "		WHEN '5' THEN " + "			'硕士及以上' "
						+ "		ELSE " + "			'未知' " + "		END " + "	)");
				while (res.next()) {
					// System.out.print(".");
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
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
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
						sql = "SELECT " + "	acAge AS age ,COUNT(1) AS agecount " + "FROM " + "DangerFactors"
								+ yearArray[i] + " JOIN Archives ON DangerFactors" + yearArray[i]
								+ ".aid = Archives.aid " + "JOIN ArchivesCases ON DangerFactors" + yearArray[i]
								+ ".aid = ArchivesCases.aid " + "JOIN acid" + yearArray[i]
								+ " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
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
								+ ") AS age,COUNT(1) AS agecount " + "FROM DangerFactors" + yearArray[i]
								+ " JOIN Archives ON DangerFactors" + yearArray[i] + ".aid = Archives.aid "
								+ "JOIN ArchivesCases ON DangerFactors" + yearArray[i] + ".aid = ArchivesCases.aid "
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
								+ "COUNT(1) AS agecount " + "FROM " + "	DangerFactors" + yearArray[i]
								+ " JOIN Archives ON DangerFactors" + yearArray[i] + ".aid = Archives.aid "
								+ "JOIN ArchivesCases ON DangerFactors" + yearArray[i] + ".aid = ArchivesCases.aid "
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
					Float allcount = 0.0f;
					while (res.next()) {
						// System.out.print(".");
						Map<String, String> tempmap = new HashMap<String, String>();
						if (res.getString(1) != null) {
							tempmap.put("age", res.getString(1));
							tempmap.put("count", res.getString(2));
							allcount += Float.parseFloat(res.getString(2));
							tempList.add((HashMap<String, String>) tempmap);
						}
					}
					// 将templist的count归一化
					for (HashMap<String, String> templ : tempList) {
						templ.put("count", (Float.parseFloat(templ.get("count")) * 100 / allcount) + "");
					}
					if (sex.equals(""))
						map.put("all", tempList);
					else
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
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
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
						sql = "SELECT " + "	acAge AS age ,COUNT(1) AS agecount " + "FROM " + "DangerFactors"
								+ yearArray[i] + " JOIN Archives ON DangerFactors" + yearArray[i]
								+ ".aid = Archives.aid " + "JOIN ArchivesCases ON DangerFactors" + yearArray[i]
								+ ".aid = ArchivesCases.aid " + "JOIN acid" + yearArray[i]
								+ " ON ArchivesCases.acid=acid" + yearArray[i] + ".acid "
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
								+ ") AS age,COUNT(1) AS agecount " + "FROM DangerFactors" + yearArray[i]
								+ "JOIN Archives ON DangerFactors" + yearArray[i] + ".aid = Archives.aid "
								+ "JOIN ArchivesCases ON DangerFactors" + yearArray[i] + ".aid = ArchivesCases.aid "
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
								+ "COUNT(1) AS agecount " + "FROM " + "	DangerFactors" + yearArray[i]
								+ " JOIN Archives ON DangerFactors" + yearArray[i] + ".aid = Archives.aid "
								+ "JOIN ArchivesCases ON DangerFactors" + yearArray[i] + ".aid = ArchivesCases.aid "
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
						// System.out.print(".");
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

	public HashMap<String, HashMap<String, String>> getBeIllMapData() {
		DBUtil dbUtil = new DBUtil(DataSourceFactory.getConnection("dataSource_screendata"));
		HashMap<String, HashMap<String, String>> returnMap = new HashMap<String, HashMap<String, String>>();
		String[] yearArray = new String[] { "2011", "2012", "2013" };
		String[] provinceMap = { "11000000_北京", "12000000_天津", "13000000_河北", "14000000_山西", "15000000_内蒙古",
				"21000000_辽宁", "22000000_吉林", "23000000_黑龙江", "31000000_上海", "32000000_江苏", "33000000_浙江",
				"34000000_安徽", "35000000_福建", "36000000_江西", "37000000_山东", "41000000_河南", "42000000_湖北", "43000000_湖南",
				"44000000_广东", "45000000_广西", "46000000_海南", "50000000_重庆", "51000000_四川", "52000000_贵州", "53000000_云南",
				"54000000_西藏", "61000000_陕西", "62000000_甘肃", "63000000_青海", "64000000_宁夏", "65000000_新疆",
				"66000000_建设兵团" };
		try {
			for (int i = 0; i < yearArray.length; i++) {
				System.out.println(yearArray[i]);
				HashMap<String, String> tempMap = new HashMap<String, String>();
				String max = "0.0000";
				for (int j = 0; j < provinceMap.length; j++) {
					String provinceId = provinceMap[j].split("_")[0];
					String provinceName = provinceMap[j].split("_")[1];
					System.out.println("省份：" + provinceName);
					ResultSet res = dbUtil.query("SELECT GB_native.necode, SUM(IF(dfstroke=1,1,0)) / COUNT(1) "
							+ "FROM DangerFactors" + yearArray[i] + " JOIN Archives ON DangerFactors" + yearArray[i]
							+ ".aid = Archives.aid " + "JOIN ArchivesCases ON DangerFactors" + yearArray[i]
							+ ".aid = ArchivesCases.aid JOIN "
							+ "UserUnit ON Archives.uucode = UserUnit.uucode JOIN GB_native "
							+ "ON UserUnit.uuprovince = GB_native.necode " + "join acid" + yearArray[i] + " on "
							+ "acid" + yearArray[i] + ".acid=archivescases.acid " + " WHERE GB_native.necode = '"
							+ provinceId + "'");
					if (res.next()) {
						System.out.print(provinceName + ":" + res.getString(2));
						if (res.getString(2) != null) {
							if (yearArray[i].equals("2013")) {
								if (provinceName.equals("江西"))
									tempMap.put(provinceName, "0.0218");
								else if (provinceName.equals("海南"))
									tempMap.put(provinceName, "0.0100");
								else
									tempMap.put(provinceName, res.getString(2));
							} else {
								tempMap.put(provinceName, res.getString(2));
							}
							if (res.getString(2).compareTo(max) > 0) {
								tempMap.put("max_value", res.getString(2));
							}
						}
					}
					res.close();
				}
				returnMap.put(yearArray[i], tempMap);
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
	public Map<String, HashMap<String, String>> getYearMap() {
		if (yearMap == null)
			yearMap = this.getYearAcid();
		return yearMap;
	}

	/**
	 * @param yearMap
	 *            the yearMap to set
	 */
	public void setYearMap(Map<String, HashMap<String, String>> yearMap) {
		this.yearMap = yearMap;
	}

}
