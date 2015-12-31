package otc.healthcare.service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import otc.healthcare.dao.ConnectionFactory;
import otc.healthcare.dao.SQLServerDBUtil;
import otc.healthcare.util.HealthcareConfiguration;

@Component
public class MySQLServiceHospital implements IService {

	@Autowired
	private HealthcareConfiguration hcConfiguration;

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public int checkStatus() {
		return 0;
	}

	// 统计科室---RYKB（入院科别）
	public List<String> getAll_RYKB() {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());

		String sql = "select DISTINCT RYKBBM,RYKBMC FROM TB_Inpatient_FirstPage;";

		List<String> RYKB_list = new ArrayList<>();
		ResultSet res = dbUtil.query(sql);
		try {
			while (res.next()) {
				String RYKBBM = res.getString(1);
				String RYKBMC = res.getString(2);
				RYKB_list.add(RYKBBM + "," + RYKBMC);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.close();
		return RYKB_list;
	}

	// 住院人次
	public String getInhospitalNum(String timeType) {
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));

		String InhospitalNum = getInhospitalNum(preDate, curDate);
		return InhospitalNum;
	}

	public String getInhospitalNum(String t1, String t2) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());

		String sql = "";
		sql = String.format("SELECT COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE (RYSJ >= '%s') AND (RYSJ <= '%s');",
				t1, t2);

		String InhospitalNum = "";
		ResultSet res = dbUtil.query(sql);
		try {
			while (res.next())
				InhospitalNum = res.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.close();
		DecimalFormat df = new DecimalFormat("0.00");
		InhospitalNum = df.format(Double.valueOf(InhospitalNum));
		return InhospitalNum;
	}

	// 住院人数---环比增幅
	public String getInhospitalRate(String timeType) {
		// this week
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));

		// last week
		String curDate_1 = Calendar2String(getDateLastWeek(getCurDate(), timeType));
		String preDate_1 = Calendar2String(getDateThisWeek(getDateLastWeek(getCurDate(), timeType), timeType));

		String Inhospital_num_lastweek = getInhospitalNum(preDate_1, curDate_1);
		String Inhospital_num_thisweek = getInhospitalNum(preDate, curDate);

		double tmp_rate = (Double.valueOf(Inhospital_num_thisweek) - Double.valueOf(Inhospital_num_lastweek))
				/ Double.valueOf(Inhospital_num_lastweek);
		if (Double.isNaN(tmp_rate))
			return "∞%";
		DecimalFormat df = new DecimalFormat("0.00%");
		String InhospitalRate = df.format(tmp_rate);
		return InhospitalRate;
	}

	// 平均住院天数
	public String getInhospitalAverageDays_Num(String timeType) {
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));

		String InhospitalAverageDays_Num = getInhospitalAverageDays_Num(preDate, curDate);
		return InhospitalAverageDays_Num;
	}

	public String getInhospitalAverageDays_Num(String t1, String t2) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());

		String sql = "";
		sql = String.format(
				"SELECT	CAST (SUM (DATEDIFF(DAY, RYSJ, CYSJ)) AS DECIMAL) / COUNT (ZYH) "
				+ "FROM TB_Inpatient_FirstPage WHERE ('%s' <= RYSJ) AND (RYSJ <= '%s');",
				t1, t2);

		String InhospitalAverageDays_Num = "";
		ResultSet res = dbUtil.query(sql);
		try {
			while (res.next())
				InhospitalAverageDays_Num = res.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.close();

		if (InhospitalAverageDays_Num == null || InhospitalAverageDays_Num.equals(""))
			return String.valueOf(Double.POSITIVE_INFINITY);
		DecimalFormat df = new DecimalFormat("0.00");
		InhospitalAverageDays_Num = df.format(Double.valueOf(InhospitalAverageDays_Num));
		return InhospitalAverageDays_Num;
	}

	// 平均住院天数---环比增幅
	public String getInhospitalAverageDays_Rate(String timeType) {
		// this week
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));

		// last week
		String curDate_1 = Calendar2String(getDateLastWeek(getCurDate(), timeType));
		String preDate_1 = Calendar2String(getDateThisWeek(getDateLastWeek(getCurDate(), timeType), timeType));

		String InhospitalAverageDays_num_lastweek = getInhospitalAverageDays_Num(preDate_1, curDate_1);
		String InhospitalAverageDays_num_thisweek = getInhospitalAverageDays_Num(preDate, curDate);

		double tmp_rate = (Double.valueOf(InhospitalAverageDays_num_thisweek)
				- Double.valueOf(InhospitalAverageDays_num_lastweek))
				/ Double.valueOf(InhospitalAverageDays_num_lastweek);
		if (Double.isNaN(tmp_rate))
			return "∞%";
		DecimalFormat df = new DecimalFormat("0.00%");
		String InhospitalAverageDaysRate = df.format(tmp_rate);
		return InhospitalAverageDaysRate;
	}

	// 平均医疗费用 --- 数量
	public String getTreatmentAverageCost_Num(String timeType) {
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));

		String TreatmentAverageCost_Num = getTreatmentAverageCost_Num(preDate, curDate);
		return TreatmentAverageCost_Num;
	}

	private String getTreatmentAverageCost_Num(String t1, String t2) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());

		String sql = "";
		sql = String.format(
				"SELECT SUM (case when ZFY is not null and ZFY!='' then CAST(ZFY AS DECIMAL) else 0 END) / COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<'%s');",
				t1, t2);

		String TreatmentAverageCost_Num = "";
		ResultSet res = dbUtil.query(sql);
		try {
			while (res.next())
				TreatmentAverageCost_Num = res.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.close();

		if (TreatmentAverageCost_Num == null || TreatmentAverageCost_Num.equals(""))
			return String.valueOf(Double.POSITIVE_INFINITY);
		DecimalFormat df = new DecimalFormat("0.00");
		TreatmentAverageCost_Num = df.format(Double.valueOf(TreatmentAverageCost_Num));
		return TreatmentAverageCost_Num;
	}

	// 平均医疗费用 --- 环比增幅
	public String getTreatmentAverageCost_Rate(String timeType) {
		// this week
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));

		// last week
		String curDate_1 = Calendar2String(getDateLastWeek(getCurDate(), timeType));
		String preDate_1 = Calendar2String(getDateThisWeek(getDateLastWeek(getCurDate(), timeType), timeType));

		String TreatmentAverageCost_Rate_lastweek = getInhospitalAverageDays_Num(preDate_1, curDate_1);
		String TreatmentAverageCost_Rate_thisweek = getInhospitalAverageDays_Num(preDate, curDate);

		double tmp_rate = (Double.valueOf(TreatmentAverageCost_Rate_thisweek)
				- Double.valueOf(TreatmentAverageCost_Rate_lastweek))
				/ Double.valueOf(TreatmentAverageCost_Rate_lastweek);
		if (Double.isNaN(tmp_rate))
			return "∞%";
		DecimalFormat df = new DecimalFormat("0.00%");
		String InhospitalAverageDaysRate = df.format(tmp_rate);
		return InhospitalAverageDaysRate;
	}

	// 入院患者病种构成
	public Map<String, String> getInhospitalPatienConsist(Map<String, String> paramMap) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		String curDate = "";
		String preDate = "";
		String age1 = "";
		String age2 = "";
		String RYKBBM = "";
		String XB = "";
		
		Iterator<String> keys = paramMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			String value = paramMap.get(key);
			switch (key) {
			case "timeType":
				String timeType = value;
				curDate = Calendar2String(getCurDate());
				preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));
				break;
			case "age":
				String[] tmp = value.substring(1, value.length()-1).split(",");
				age1 = tmp[0];
				age2 = tmp[1];
				break;
			case "hospitalDeps":
				RYKBBM = value;
				break;	
			case "sex":
				XB = value;
				break;	
			default:
				System.out.println("读取参数出错！");
				break;
			}
		}
		String sql = "";
		sql = String.format(
				"SELECT "
				+ "SUM(CASE WHEN ('G45'<=UPPER(CYZDBM) AND UPPER(CYZDBM)<'G46.9') THEN 1 ELSE	0	END) AS '短暂性脑缺血发作',"
				+ "SUM(CASE WHEN (('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
				+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
				+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
				+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
				+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
				+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
				+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')) THEN 1 ELSE 0 END ) AS '脑出血',"
				+ "SUM(CASE WHEN (('I63' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I64') OR "
				+ "('I65' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I67') OR "
				+ "('I67.2' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.4') OR "
				+ "('I67.5' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.7') OR "
				+ "('I69.3' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I69.399')) THEN 1 ELSE 0 END) AS '脑梗死',"
				+ "SUM(CASE WHEN ('I60' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I61') THEN 1 ELSE 0	END) AS '蛛网膜下腔出血'"
				+ "FROM (SELECT * FROM TB_Inpatient_FirstPage WHERE('%s'<=RYSJ) AND (RYSJ<='%s') AND ('%s'='0' OR RYKBBM = '%s') ) AS a"
				+ " WHERE (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s';",
				preDate, curDate, RYKBBM, RYKBBM, XB,XB, age1, age2);
		
		Map<String,String> map = new HashMap<String,String>();
		double sum = 0.0;
		try {
			ResultSet res = dbUtil.query(sql);
			ResultSetMetaData rsmd = res.getMetaData() ;
			while (res.next())
				for(int i=1; i<=rsmd.getColumnCount(); i++){
					if(res.getString(i)==null || res.getString(i).equals(""))
						map.put(rsmd.getColumnName(i), "1");
					else
						map.put(rsmd.getColumnName(i), res.getString(i));
					sum += Double.valueOf(map.get(rsmd.getColumnName(i)));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.close();
		
		//饼状图---整理成百分比
		for(Entry<String, String> tmp : map.entrySet()){
			String key = tmp.getKey();
			double cur_value = Double.valueOf(tmp.getValue()) / sum;
			map.put(key, String.valueOf(cur_value));
		}
		return map;
	}
	
	//性别年龄构成---入院人次
	public List<Map<String, String>> getInhospitalPatienSexAgeConsist(String bingZhong, String timeType,
			String hospitalDeps) {
		List<Map<String,String>> rs_list = new ArrayList<Map<String,String>>();
		
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));
		
		if(bingZhong.equals("1")){//短暂性脑缺血发作
			String sql = "";
			sql = String.format(
				"SELECT	XB,	SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 0 AND 20 THEN	1 ELSE 0 END ) AS '[0-20]',	"
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 21 AND 40 THEN 1 ELSE 0 END ) AS '[21-40]', "
				+ "SUM ( CASE WHEN CAST(NL AS DECIMAL) BETWEEN 41 AND 60 THEN 1 ELSE 0 END ) AS '[41-60]',	"
				+ "SUM ( CASE WHEN CAST(NL AS DECIMAL) BETWEEN 61 AND 80 THEN 1 ELSE	0 END ) AS '[61-80]',	"
				+ "SUM ( CASE WHEN CAST(NL AS DECIMAL) BETWEEN 81 AND 100 THEN 1	ELSE 0 END ) AS '[81-100]',	"
				+ "SUM ( CASE WHEN CAST(NL AS DECIMAL) > 101 THEN 1 ELSE 0 END	) AS '[>101]' "
				+ "FROM	(SELECT * FROM TB_Inpatient_FirstPage WHERE ('%s'<= RYSJ) AND (RYSJ <= '%s')"
				+ " AND (RYKBBM = '%s' or '%s'='0')) AS a WHERE"
				+ "("
				+ "'G45'<=UPPER(CYZDBM) AND UPPER(CYZDBM)<'G46.9'"
				+ ")"
				+ "GROUP BY	XB;",				
				preDate, curDate, hospitalDeps, hospitalDeps);
			
			try {
				ResultSet res = dbUtil.query(sql);
				ResultSetMetaData rsmd = res.getMetaData() ;
				while (res.next()){
					Map<String,String> map = new TreeMap<String,String>();
					for(int i=1; i<=rsmd.getColumnCount(); i++){
						if(res.getString(i)==null || res.getString(i).equals(""))
							map.put(rsmd.getColumnName(i), "10");
						else
							map.put(rsmd.getColumnName(i), res.getString(i));
					}
					rs_list.add(map);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("2")){//脑出血
			String sql = "";
			sql = String.format(
				"SELECT	XB,	SUM ( CASE WHEN CAST(NL AS DECIMAL) BETWEEN 0 AND 20 THEN 1 ELSE 0 END ) AS '[0-20]', "
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 21 AND 40 THEN 1 ELSE 0 END ) AS '[21-40]', "
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 41 AND 60 THEN 1 ELSE 0 END ) AS '[41-60]', "
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 61 AND 80 THEN 1 ELSE 0 END ) AS '[61-80]', "
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 81 AND 100 THEN 1 ELSE 0 END	) AS '[81-100]',"
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) > 101 THEN 1 ELSE 0 END) AS '[>101]' "
				+ "FROM	(SELECT * FROM TB_Inpatient_FirstPage WHERE ('%s' <= RYSJ) AND (RYSJ <= '%s') "
				+ "AND (RYKBBM = '%s' OR '%s' = '0') ) AS a WHERE"
				+ "("
				+ "('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
				+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
				+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
				+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
				+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
				+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
				+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')"
				+ ") "				
				+ "GROUP BY XB;",			 
				preDate, curDate, hospitalDeps, hospitalDeps);
			
			try {
				ResultSet res = dbUtil.query(sql);
				ResultSetMetaData rsmd = res.getMetaData() ;
				while (res.next()){
					Map<String,String> map = new TreeMap<String,String>();
					for(int i=1; i<=rsmd.getColumnCount(); i++){
						if(res.getString(i)==null || res.getString(i).equals(""))
							map.put(rsmd.getColumnName(i), "10");
						else
							map.put(rsmd.getColumnName(i), res.getString(i));
					}
					rs_list.add(map);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
		}else if(bingZhong.equals("3")){//脑梗死
			String sql = "";
			sql = String.format(
				"SELECT	XB,	SUM ( CASE WHEN CAST(NL AS DECIMAL) BETWEEN 0 AND 20 THEN 1 ELSE 0 END ) AS '[0-20]', "
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 21 AND 40 THEN 1 ELSE 0 END ) AS '[21-40]', "
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 41 AND 60 THEN 1 ELSE 0 END ) AS '[41-60]', "
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 61 AND 80 THEN 1 ELSE 0 END ) AS '[61-80]', "
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 81 AND 100 THEN 1 ELSE 0 END	) AS '[81-100]',"
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) > 101 THEN 1 ELSE 0 END) AS '[>101]' "
				+ "FROM	(SELECT * FROM TB_Inpatient_FirstPage WHERE ('%s' <= RYSJ) AND (RYSJ <= '%s') "
				+ "AND (RYKBBM = '%s' OR '%s' = '0') ) AS a WHERE "
				+ "("
				+ "('I63' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I64') OR "
				+ "('I65' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I67') OR "
				+ "('I67.2' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.4') OR "
				+ "('I67.5' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.7') OR "
				+ "('I69.3' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I69.399')"
				+ ")"
				+ "GROUP BY XB;",		 
				preDate, curDate, hospitalDeps, hospitalDeps);
			
			try {
				ResultSet res = dbUtil.query(sql);
				ResultSetMetaData rsmd = res.getMetaData() ;
				while (res.next()){
					Map<String,String> map = new TreeMap<String,String>();
					for(int i=1; i<=rsmd.getColumnCount(); i++){
						if(res.getString(i)==null || res.getString(i).equals(""))
							map.put(rsmd.getColumnName(i), "10");
						else
							map.put(rsmd.getColumnName(i), res.getString(i));
					}
					rs_list.add(map);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
		}else if(bingZhong.equals("4")){//蛛网膜下腔出血
			String sql = "";
			sql = String.format(
				"SELECT	XB,	SUM ( CASE WHEN CAST(NL AS DECIMAL) BETWEEN 0 AND 20 THEN 1 ELSE 0 END ) AS '[0-20]', "
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 21 AND 40 THEN 1 ELSE 0 END ) AS '[21-40]', "
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 41 AND 60 THEN 1 ELSE 0 END ) AS '[41-60]', "
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 61 AND 80 THEN 1 ELSE 0 END ) AS '[61-80]', "
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) BETWEEN 81 AND 100 THEN 1 ELSE 0 END	) AS '[81-100]',"
				+ "SUM (CASE WHEN CAST(NL AS DECIMAL) > 101 THEN 1 ELSE 0 END) AS '[>101]' "
				+ "FROM	(SELECT * FROM TB_Inpatient_FirstPage WHERE ('%s' <= RYSJ) AND (RYSJ <= '%s') "
				+ "AND (RYKBBM = '%s' OR '%s' = '0') ) AS a WHERE "
				+ "("
				+ "'I60' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I61'"
				+ ")"
				+ "GROUP BY XB;",		 
				preDate, curDate, hospitalDeps, hospitalDeps);
			
			try {
				ResultSet res = dbUtil.query(sql);
				ResultSetMetaData rsmd = res.getMetaData() ;
				while (res.next()){
					Map<String,String> map = new TreeMap<String,String>();
					for(int i=1; i<=rsmd.getColumnCount(); i++){
						if(res.getString(i)==null || res.getString(i).equals(""))
							map.put(rsmd.getColumnName(i), "10");
						else
							map.put(rsmd.getColumnName(i), res.getString(i));
					}
					rs_list.add(map);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
		}//end ifelse
		
		//无数据时候，模拟相应数据
		if(rs_list.size() == 0){
			rs_list = new ArrayList<Map<String,String>>();
			for(int i=2; i<4; i++){
				Map<String,String> map = new TreeMap<String,String>();
				map.put("[0-20]", String.valueOf(i*2));
				map.put("[21-40]", String.valueOf(i*3));
				map.put("[41-60]", String.valueOf(i*2.1));
				map.put("[61-80]", String.valueOf(i*2.3));
				map.put("[81-100]", String.valueOf(i*5.3));
				map.put("[>101]", String.valueOf(i*2.3));
				rs_list.add(map);
			}
		}
		return rs_list;
	}
	
	//入院患者时间变化
	public Map<String, String> getInhospitalPatienNum_bytime(String showSize, String timeType,
			String hospitalDeps) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		Map<String,String> map = new HashMap<String,String>(); 
		switch (showSize) {
		case "day":
			map = getInhospitalPatienNum_bytime_day(timeType, hospitalDeps);
			break;
		case "week":
			break;
		default:
			System.out.println("筛选粒度出错！");
			break;
		}
		
		return map;
	}
	
	public Map<String, String> getInhospitalPatienNum_bytime_day(String timeType, String hospitalDeps) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		List<String> dayList = new ArrayList<>();
		String day_str = "(";
		for(int i=0; i<7; i++)
			dayList.add(Calendar2String(getDateByDays(getCurDate(), -i)));
		for(String day : dayList)
			day_str += "'" + day+ "',";
		day_str = day_str.substring(0, day_str.length()-1);
		day_str += ")";
		
		String sql = "";
		sql = String.format(
			"SELECT RYSJ,COUNT (ZYH) FROM TB_Inpatient_FirstPage WHERE RYSJ in %s AND (RYKBBM = '%s' OR %s='0') GROUP BY RYSJ;",			
			day_str, hospitalDeps, hospitalDeps);
		
		Map<String,String> map = new TreeMap<String,String>(); 
		
		try {
			ResultSet res = dbUtil.query(sql);
			while (res.next())
				map.put(res.getString(1), res.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.close();
		
		//如果相应日期无值，这里进行填充0
		for(String day : dayList){
			if(!map.containsKey(day))
				map.put(day, "0");
		}
		
		//treeMap的key排序，方便前台show
		return map;
	}
	
	
	//入院途径       ---   1.急诊 2.门诊 3.其他医疗机构转入 9.其他
	public Map<String, String> getInhospital_approach(String bingZhong, String timeType, String hospitalDeps,
			String sex, String age) {
		
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));
		
		Map<String, String> inhospital_approach_map = new HashMap<>();
		inhospital_approach_map.put("1", "急诊 ");
		inhospital_approach_map.put("2", "门诊 ");
		inhospital_approach_map.put("3", "其他医疗机构转入 ");
		inhospital_approach_map.put("9", "其他 ");
		inhospital_approach_map.put("－", "未知 1");
		inhospital_approach_map.put("null", "未知2");
		
		Map<String, String> map = getInhospital_approach(bingZhong,hospitalDeps,sex,age,preDate,curDate);
		Map<String, String> rs_map = new HashMap<>();
		double sum = 0.0;
		for(String key : map.keySet()){
			String new_key = inhospital_approach_map.get(key);
			sum += Double.valueOf(map.get(key));
			rs_map.put(new_key, map.get(key));
		}
		
		for(String key : rs_map.keySet())
			rs_map.put(key, String.valueOf(Double.valueOf(rs_map.get(key))/sum));
		
		return rs_map;
	}
	
	private Map<String, String> getInhospital_approach(String bingZhong, String hospitalDeps, String sex, String age,
			String preDate, String curDate) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		Map<String,String> rs_map = new HashMap();
		String[] tmp = age.substring(1, age.length()-1).split(",");
		String age1 = tmp[0];
		String age2 = tmp[1];
		
		if(bingZhong.equals("1")){//短暂性脑缺血发作
			String sql = "";
			sql = String.format(
				"SELECT	RYTJBM, COUNT (ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "'G45'<=UPPER(CYZDBM) AND UPPER(CYZDBM)<'G46.9'"
				+ ")"
				+ "GROUP BY RYTJBM;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("2")){//脑出血
			String sql = "";
			sql = String.format(
				"SELECT	RYTJBM, COUNT (ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
				+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
				+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
				+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
				+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
				+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
				+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')"
				+ ") "
				+ "GROUP BY RYTJBM;",		
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("3")){//脑梗死
			String sql = "";
			sql = String.format(
				"SELECT	RYTJBM, COUNT (ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "('I63' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I64') OR "
				+ "('I65' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I67') OR "
				+ "('I67.2' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.4') OR "
				+ "('I67.5' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.7') OR "
				+ "('I69.3' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I69.399')"
				+ ") "
				+ "GROUP BY RYTJBM;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("4")){//蛛网膜下腔出血
			String sql = "";
			sql = String.format(
				"SELECT	RYTJBM, COUNT (ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "'I60' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I61'"
				+ ") "
				+ "GROUP BY RYTJBM;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
		}//end ifelse
		
		//无法查出数据，使用样本数据
		if(rs_map.size() == 0){
			rs_map.put("1", "35");
			rs_map.put("2", "23");
			rs_map.put("3", "34");
			rs_map.put("9", "56");
		}
		return rs_map;
	}
	
	//入院病情   ---  1.危重 2.急诊 3.一般 9.其他
	public Map<String, String> getInhospital_illstatus(String bingZhong, String timeType, String hospitalDeps,
			String sex, String age) {
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));
		
		Map<String, String> inhospital_illstatus_map = new HashMap<>();
		inhospital_illstatus_map.put("1", "危重 ");
		inhospital_illstatus_map.put("2", "急诊 ");
		inhospital_illstatus_map.put("3", "一般 ");
		inhospital_illstatus_map.put("9", "其他 ");
		inhospital_illstatus_map.put("null", "未知");

		
		Map<String, String> map = getInhospital_illstatus(bingZhong,hospitalDeps,sex,age,preDate,curDate);
		Map<String, String> rs_map = new HashMap<>();
		double sum = 0.0;
		for(String key : map.keySet()){
			String new_key = inhospital_illstatus_map.get(key);
			sum += Double.valueOf(map.get(key));
			rs_map.put(new_key, map.get(key));
		}
		
		for(String key : rs_map.keySet())
			rs_map.put(key, String.valueOf(Double.valueOf(rs_map.get(key))/sum));
		
		return rs_map;
	}
	
	private Map<String, String> getInhospital_illstatus(String bingZhong, String hospitalDeps, String sex, String age,
			String preDate, String curDate) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		Map<String,String> rs_map = new HashMap();
		String[] tmp = age.substring(1, age.length()-1).split(",");
		String age1 = tmp[0];
		String age2 = tmp[1];
		
		if(bingZhong.equals("1")){//短暂性脑缺血发作
			String sql = "";
			sql = String.format(
				"SELECT	RYQKBM, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "'G45'<=UPPER(CYZDBM) AND UPPER(CYZDBM)<'G46.9'"
				+ ")"
				+ "GROUP BY RYQKBM;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("2")){//脑出血
			String sql = "";
			sql = String.format(
				"SELECT	RYQKBM, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
				+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
				+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
				+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
				+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
				+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
				+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')"
				+ ") "
				+ "GROUP BY RYQKBM;",		
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("3")){//脑梗死
			String sql = "";
			sql = String.format(
				"SELECT	RYQKBM, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "('I63' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I64') OR "
				+ "('I65' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I67') OR "
				+ "('I67.2' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.4') OR "
				+ "('I67.5' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.7') OR "
				+ "('I69.3' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I69.399')"
				+ ") "
				+ "GROUP BY RYQKBM;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("4")){//蛛网膜下腔出血
			String sql = "";
			sql = String.format(
				"SELECT	RYQKBM, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "'I60' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I61'"
				+ ") "
				+ "GROUP BY RYQKBM;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}//end else
		
		//无法查出数据，使用样本数据
		if(rs_map.size() == 0){
			rs_map.put("1", "12");
			rs_map.put("2", "23");
			rs_map.put("3", "45");
			rs_map.put("9", "89");
		}
		return rs_map;
	}

	//离院方式       ---  1.医嘱离院 2.医嘱转院 3.医嘱转社区卫生服务机构/乡镇卫生院  4.非医嘱离院 5.死亡 6.其他
	public Map<String, String> getOuthospital_approach(String bingZhong, String timeType, String hospitalDeps,
			String sex, String age) {
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));
		
		Map<String, String> outhospital_approach_map = new HashMap<>();
		outhospital_approach_map.put("1", "医嘱离院 ");
		outhospital_approach_map.put("2", "医嘱转院 ");
		outhospital_approach_map.put("3", "医嘱转社区卫生服务机构/乡镇卫生院 ");
		outhospital_approach_map.put("4", "非医嘱离院 ");
		outhospital_approach_map.put("5", "死亡 ");
		outhospital_approach_map.put("6", "其他 ");
		outhospital_approach_map.put("null", "未知1");
		outhospital_approach_map.put("0", "未知2");

		Map<String, String> map = getOuthospital_approach(bingZhong,hospitalDeps,sex,age,preDate,curDate);
		Map<String, String> rs_map = new HashMap<>();
		double sum = 0.0;
		for(String key : map.keySet()){
			String new_key = outhospital_approach_map.get(key);
			sum += Double.valueOf(map.get(key));
			rs_map.put(new_key, map.get(key));
		}
		
		for(String key : rs_map.keySet())
			rs_map.put(key, String.valueOf(Double.valueOf(rs_map.get(key))/sum));
		
		return rs_map;
	}

	private Map<String, String> getOuthospital_approach(String bingZhong, String hospitalDeps, String sex, String age,
			String preDate, String curDate) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		Map<String,String> rs_map = new HashMap();
		String[] tmp = age.substring(1, age.length()-1).split(",");
		String age1 = tmp[0];
		String age2 = tmp[1];
		
		if(bingZhong.equals("1")){//短暂性脑缺血发作
			String sql = "";
			sql = String.format(
				"SELECT	LYFSBM, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "'G45'<=UPPER(CYZDBM) AND UPPER(CYZDBM)<'G46.9'"
				+ ") GROUP BY LYFSBM;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("2")){//脑出血
			String sql = "";
			sql = String.format(
				"SELECT	LYFSBM, COUNT (ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
				+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
				+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
				+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
				+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
				+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
				+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')"
				+ ") "
				+ "GROUP BY LYFSBM;",		
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("3")){//脑梗死
			String sql = "";
			sql = String.format(
				"SELECT	LYFSBM, COUNT (ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "('I63' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I64') OR "
				+ "('I65' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I67') OR "
				+ "('I67.2' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.4') OR "
				+ "('I67.5' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.7') OR "
				+ "('I69.3' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I69.399')"
				+ ") "
				+ "GROUP BY LYFSBM;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("4")){//蛛网膜下腔出血
			String sql = "";
			sql = String.format(
				"SELECT	LYFSBM, COUNT (ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "'I60' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I61'"
				+ ") "
				+ "GROUP BY LYFSBM;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}//end else
		
		//无法查出数据，使用样本数据
		if(rs_map.size() == 0){
			rs_map.put("1", "35");
			rs_map.put("2", "23");
			rs_map.put("3", "34");
			rs_map.put("4", "12");
			rs_map.put("5", "23");
			rs_map.put("6", "17");
		}
		
		return rs_map;
	}

	//离院病情   ---  1.治愈 2.好转 3.稳定 4.恶化 5.死亡 6.其他
	public Map<String, String> getOuthospital_illstatus(String bingZhong, String timeType, String hospitalDeps,
			String sex, String age) {
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));
		
		Map<String, String> outhospital_illstatus_map = new HashMap<>();
		outhospital_illstatus_map.put("1", "治愈");
		outhospital_illstatus_map.put("2", "好转 ");
		outhospital_illstatus_map.put("3", "稳定 ");
		outhospital_illstatus_map.put("4", "恶化");
		outhospital_illstatus_map.put("5", "死亡");
		outhospital_illstatus_map.put("6", "其他 ");
		outhospital_illstatus_map.put("null", "未知");
		
		Map<String, String> map = getOuthospital_illstatus(bingZhong,hospitalDeps,sex,age,preDate,curDate);
		Map<String, String> rs_map = new HashMap<>();
		double sum = 0.0;
		for(String key : map.keySet()){
			String new_key = outhospital_illstatus_map.get(key);
			sum += Double.valueOf(map.get(key));
			rs_map.put(new_key, map.get(key));
		}
		
		for(String key : rs_map.keySet())
			rs_map.put(key, String.valueOf(Double.valueOf(rs_map.get(key))/sum));
		
		return rs_map;
	}
	
	private Map<String, String> getOuthospital_illstatus(String bingZhong, String hospitalDeps, String sex, String age,
			String preDate, String curDate) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		Map<String,String> rs_map = new HashMap();
		String[] tmp = age.substring(1, age.length()-1).split(",");
		String age1 = tmp[0];
		String age2 = tmp[1];
		
		if(bingZhong.equals("1")){//短暂性脑缺血发作
			String sql = "";
			sql = String.format(
				"SELECT	CYBQ, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "'G45'<=UPPER(CYZDBM) AND UPPER(CYZDBM)<'G46.9'"
				+ ") "
				+ "GROUP BY CYBQ;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("2")){//脑出血
			String sql = "";
			sql = String.format(
				"SELECT	CYBQ, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
				+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
				+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
				+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
				+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
				+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
				+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')"
				+ ") "
				+ "GROUP BY CYBQ;",		
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("3")){//脑梗死
			String sql = "";
			sql = String.format(
				"SELECT	CYBQ, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
				+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
				+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
				+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
				+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
				+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
				+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')"
				+ ") "
				+ "GROUP BY CYBQ;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("4")){//蛛网膜下腔出血
			String sql = "";
			sql = String.format(
				"SELECT	CYBQ, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "'I60' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I61'"
				+ ") "
				+ "GROUP BY CYBQ;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next()){
					if(res.getString(1)==null || res.getString(1).equals(""))
						rs_map.put("null", res.getString(2));
					else
						rs_map.put(res.getString(1), res.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}//end else
		
		//无法查出数据，使用样本数据
		if(rs_map.size() == 0){
			rs_map.put("1", "56");
			rs_map.put("2", "54");
			rs_map.put("3", "74");
			rs_map.put("4", "23");
			rs_map.put("5", "68");
			rs_map.put("6", "54");
		}
		
		return rs_map;
	}

	//住院情况 --- 医疗付费方式 --- 01城镇职工基本医疗保险;02城镇居民基本医疗保险;03新型农村合作医疗;04贫困救助;05商业医疗保险06全公费07全自费08其他社会保险99其他
	public Map<String, String> getbeInhospital_treatmentPayWay(String bingZhong, String timeType, String hospitalDeps,
			String sex, String age) {
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));
		
		Map<String, String> beinhospital_treatmentPayWay_map = new HashMap<>();
		beinhospital_treatmentPayWay_map.put("01", "城镇职工基本医疗保险");
		beinhospital_treatmentPayWay_map.put("02", "城镇居民基本医疗保险 ");
		beinhospital_treatmentPayWay_map.put("03", "新型农村合作医疗 ");
		beinhospital_treatmentPayWay_map.put("04", "贫困救助 ");
		beinhospital_treatmentPayWay_map.put("05", "商业医疗保险");
		beinhospital_treatmentPayWay_map.put("06", "全公费");
		beinhospital_treatmentPayWay_map.put("07", "全自费 ");
		beinhospital_treatmentPayWay_map.put("08", "其他社会保险 ");
		beinhospital_treatmentPayWay_map.put("99", "其他 ");

		
		Map<String, String> map = getbeInhospital_treatmentPayWay(bingZhong,hospitalDeps,sex,age,preDate,curDate);
		Map<String, String> rs_map = new HashMap<>();
		double sum = 0.0;
		for(String key : map.keySet()){
			String new_key = beinhospital_treatmentPayWay_map.get(key);
			sum += Double.valueOf(map.get(key));
			rs_map.put(new_key, map.get(key));
		}
		
		for(String key : rs_map.keySet())
			rs_map.put(key, String.valueOf(Double.valueOf(rs_map.get(key))/sum));
		
		return rs_map;
	}
	
	private Map<String, String> getbeInhospital_treatmentPayWay(String bingZhong, String hospitalDeps, String sex,
			String age, String preDate, String curDate) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		Map<String,String> rs_map = new HashMap();
		String[] tmp = age.substring(1, age.length()-1).split(",");
		String age1 = tmp[0];
		String age2 = tmp[1];
		
		if(bingZhong.equals("1")){//短暂性脑缺血发作
			String sql = "";
			sql = String.format(
				"SELECT	YLFKFS, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "'G45'<=UPPER(CYZDBM) AND UPPER(CYZDBM)<'G46.9'"
				+ ")"
				+ "GROUP BY YLFKFS;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next())
					rs_map.put(res.getString(1), res.getString(2));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("2")){//脑出血
			String sql = "";
			sql = String.format(
				"SELECT	YLFKFS, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
				+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
				+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
				+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
				+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
				+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
				+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')"
				+ ") "
				+ "GROUP BY YLFKFS;",		
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next())
					rs_map.put(res.getString(1), res.getString(2));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("3")){//脑梗死
			String sql = "";
			sql = String.format(
				"SELECT	YLFKFS, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "('I63' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I64') OR "
				+ "('I65' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I67') OR "
				+ "('I67.2' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.4') OR "
				+ "('I67.5' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.7') OR "
				+ "('I69.3' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I69.399')"
				+ ") "
				+ "GROUP BY YLFKFS;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next())
					rs_map.put(res.getString(1), res.getString(2));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}else if(bingZhong.equals("4")){//蛛网膜下腔出血
			String sql = "";
			sql = String.format(
				"SELECT	YLFKFS, COUNT(ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND "
				+ "("
				+ "'I60' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I61'"
				+ ")"
				+ "GROUP BY YLFKFS;",			
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);
			
			try {
				ResultSet res = dbUtil.query(sql);
				while (res.next())
					rs_map.put(res.getString(1), res.getString(2));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
			
		}
		
		//无法查出数据，使用样本数据
		if(rs_map.size() == 0){
			rs_map.put("01", "56");
			rs_map.put("02", "54");
			rs_map.put("03", "74");
			rs_map.put("04", "23");
			rs_map.put("05", "68");
			rs_map.put("06", "54");
			rs_map.put("07", "74");
			rs_map.put("08", "23");
			rs_map.put("99", "68");
		}
		
		return rs_map;
	}
	
	//住院情况 --- 平均费用--- X轴：病种---Y轴：平均费用
	public Map<String, String> getbeInhospital_averageCost(String timeType, String hospitalDeps, String sex,
			String age) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));
		
		String[] tmp = age.substring(1, age.length()-1).split(",");
		String age1 = tmp[0];
		String age2 = tmp[1];
		
		String sql = "";
		sql = String.format(
			"SELECT SUM (case when ZFY is not null and ZFY!='' then CAST(ZFY AS DECIMAL) else 0 END ) / COUNT (ZYH) AS '平均费用',"
			+ "CASE WHEN ("
			+ "'G45'<=UPPER(CYZDBM) AND UPPER(CYZDBM)<'G46.9'"
			+ ") THEN '短暂性脑缺血发作' "
			+ "WHEN ("
			+ "('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
			+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
			+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
			+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
			+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
			+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
			+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')"
			+ ") THEN '脑出血' "
			+ "WHEN ("
			+ "('I63' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I64') OR "
			+ "('I65' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I67') OR "
			+ "('I67.2' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.4') OR "
			+ "('I67.5' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.7') OR "
			+ "('I69.3' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I69.399')"
			+ ") THEN '脑梗死' "
			+ "WHEN ("
			+ "'I60' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I61'"
			+ ") THEN '蛛网膜下腔出血' "
			+ "ELSE '其他' "
			+ "END AS '病种' "
			+ "FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "			
			+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s'"			
			+ "GROUP BY "
			+ "CASE WHEN ("
			+ "'G45'<=UPPER(CYZDBM) AND UPPER(CYZDBM)<'G46.9'"
			+ ") THEN '短暂性脑缺血发作' "
			+ "WHEN ("
			+ "('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
			+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
			+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
			+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
			+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
			+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
			+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')"
			+ ") THEN '脑出血' "
			+ "WHEN ("
			+ "('I63' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I64') OR "
			+ "('I65' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I67') OR "
			+ "('I67.2' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.4') OR "
			+ "('I67.5' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.7') OR "
			+ "('I69.3' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I69.399')"
			+ ") THEN '脑梗死' "
			+ "WHEN ("
			+ "'I60' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I61'"
			+ ") THEN '蛛网膜下腔出血' "
			+ "ELSE '其他' "
			+ "END;",					
			preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);	
		
		Map<String,String> rs_map = new HashMap();
		try {
			ResultSet res = dbUtil.query(sql);
			while (res.next())
				rs_map.put(res.getString(2), res.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.close();
		
		//无法查出数据，使用样本数据
		if(rs_map.size() == 0){
			rs_map.put("短暂性脑缺血发作", "56");
			rs_map.put("脑出血", "56");
			rs_map.put("脑梗死", "56");
			rs_map.put("蛛网膜下腔出血", "54");
			rs_map.put("其他", "74");
		}
		
		return rs_map;
	}
	
	
	//住院情况 --- 费用构成---（药费、手术费、检查检验费用、其他费用） --- 饼状图---（目前针对全部病种）
	public Map<String, String> getbeInhospital_costConsist(String bingZhong, String timeType, String hospitalDeps, String sex,
			String age) {
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));
		Map<String,String> rs_map = getbeInhospital_costConsist(bingZhong, curDate, preDate, hospitalDeps, sex, age);
		
		double sum = 0.0;
		for(Entry<String, String> tmp : rs_map.entrySet()){
			String key = tmp.getKey();
			sum += Double.valueOf(tmp.getValue());
		}
		if(sum == 0.0){
			rs_map.put("药费", "56");
			rs_map.put("手术费", "54");
			rs_map.put("检查检验费", "89");
			rs_map.put("其他费用", "74");
			sum = 56 + 54 + 89 + 74;
		}
		
		for(Entry<String, String> tmp : rs_map.entrySet()){
			String key = tmp.getKey();
			rs_map.put(key, String.valueOf(Double.valueOf(tmp.getValue())/sum) );
		}
		return rs_map;
	}
	//费用构成---饼状图
	public Map<String, String> getbeInhospital_costConsist(String bingZhong, String curDate, String preDate, String hospitalDeps, String sex,
			String age) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		String[] tmp = age.substring(1, age.length()-1).split(",");
		String age1 = tmp[0];
		String age2 = tmp[1];
		
		Map<String,String> rs_map = new HashMap();
		if(bingZhong.equals("1")){//短暂性脑缺血发作
			String sql = "";
			sql = String.format(
				"SELECT	*, 总费用 - 药费 - 手术费 - 检查检验费  AS '其他费用' FROM (SELECT "
				+ "SUM (case when ZFY is not null and ZFY!='' then CAST(ZFY AS DECIMAL) else 0 END ) AS '总费用',"
				+ "SUM ((case when XYF is not null and XYF!='' then CAST(XYF AS DECIMAL) else 0 END) + (case when KJYWF is not null and KJYWF!='' then CAST(KJYWF AS DECIMAL) else 0 END) "
				+ "+ (case when ZCYF is not null and ZCYF!='' then CAST(ZCYF AS DECIMAL) else 0 END) + (case when ZCYF1 is not null and ZCYF1!='' then CAST(ZCYF1 AS DECIMAL) else 0 END) "
				+ "+ (case when BDBLZPF is not null and BDBLZPF!='' then CAST(BDBLZPF AS DECIMAL) else 0 END) + (case when QDBLZPF is not null and QDBLZPF!='' then CAST(QDBLZPF AS DECIMAL) else 0 END) "
				+ "+ (case when NXYZLZPF is not null and NXYZLZPF!='' then CAST(NXYZLZPF AS DECIMAL) else 0 END) + (case when XBYZLZPF is not null and XBYZLZPF!='' then CAST(XBYZLZPF AS DECIMAL) else 0 END)) AS '药费',"
				+ "SUM ((case when SSF is not null and SSF!='' then CAST(SSF AS DECIMAL) else 0 END) + (case when MAF is not null and MAF!='' then CAST(MAF AS DECIMAL) else 0 END) "
				+ "+ (case when SSZLF is not null and SSZLF!='' then CAST(SSZLF AS DECIMAL) else 0 END) + (case when YCXYYCLF is not null and YCXYYCLF!='' then CAST(YCXYYCLF AS DECIMAL) else 0 END)) AS '手术费',"
				+ "SUM ((case when SYSZDF is not null and SYSZDF!='' then CAST(SYSZDF AS DECIMAL) else 0 END) + (case when YXXZDF is not null and YXXZDF!='' then CAST(YXXZDF AS DECIMAL) else 0 END) "
				+ "+ (case when LCZDXMF is not null and LCZDXMF!='' then CAST(LCZDXMF AS DECIMAL) else 0 END)) AS '检查检验费' "
				+ "FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "			
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND"			
				+ "("
				+ "'G45'<=UPPER(CYZDBM) AND UPPER(CYZDBM)<'G46.9'"
				+ ")"
				+ ") a",
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);	
			
			try {
				ResultSet res = dbUtil.query(sql);
				ResultSetMetaData rsmd = res.getMetaData() ;
				while (res.next()){
					for(int i=1; i<=rsmd.getColumnCount(); i++){
						if(rsmd.getColumnName(i).equals("总费用"))
							continue;
						if(res.getString(i) == null || res.getString(i).equals("") || Double.valueOf(res.getString(i)) < 0)
							rs_map.put(rsmd.getColumnName(i), "0");
						else
							rs_map.put(rsmd.getColumnName(i), res.getString(i));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
		}else if(bingZhong.equals("2")){//脑出血
			String sql = "";
			sql = String.format(
				"SELECT	*, 总费用 - 药费 - 手术费 - 检查检验费  AS '其他费用' FROM (SELECT "
				+ "SUM (case when ZFY is not null and ZFY!='' then CAST(ZFY AS DECIMAL) else 0 END ) AS '总费用',"
				+ "SUM ((case when XYF is not null and XYF!='' then CAST(XYF AS DECIMAL) else 0 END) + (case when KJYWF is not null and KJYWF!='' then CAST(KJYWF AS DECIMAL) else 0 END) "
				+ "+ (case when ZCYF is not null and ZCYF!='' then CAST(ZCYF AS DECIMAL) else 0 END) + (case when ZCYF1 is not null and ZCYF1!='' then CAST(ZCYF1 AS DECIMAL) else 0 END) "
				+ "+ (case when BDBLZPF is not null and BDBLZPF!='' then CAST(BDBLZPF AS DECIMAL) else 0 END) + (case when QDBLZPF is not null and QDBLZPF!='' then CAST(QDBLZPF AS DECIMAL) else 0 END) "
				+ "+ (case when NXYZLZPF is not null and NXYZLZPF!='' then CAST(NXYZLZPF AS DECIMAL) else 0 END) + (case when XBYZLZPF is not null and XBYZLZPF!='' then CAST(XBYZLZPF AS DECIMAL) else 0 END)) AS '药费',"
				+ "SUM ((case when SSF is not null and SSF!='' then CAST(SSF AS DECIMAL) else 0 END) + (case when MAF is not null and MAF!='' then CAST(MAF AS DECIMAL) else 0 END) "
				+ "+ (case when SSZLF is not null and SSZLF!='' then CAST(SSZLF AS DECIMAL) else 0 END) + (case when YCXYYCLF is not null and YCXYYCLF!='' then CAST(YCXYYCLF AS DECIMAL) else 0 END)) AS '手术费',"
				+ "SUM ((case when SYSZDF is not null and SYSZDF!='' then CAST(SYSZDF AS DECIMAL) else 0 END) + (case when YXXZDF is not null and YXXZDF!='' then CAST(YXXZDF AS DECIMAL) else 0 END) "
				+ "+ (case when LCZDXMF is not null and LCZDXMF!='' then CAST(LCZDXMF AS DECIMAL) else 0 END)) AS '检查检验费' "
				+ "FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "			
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND"			
				+ "("
				+ "('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
				+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
				+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
				+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
				+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
				+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
				+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')"
				+ ") "
				+ ") a",
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);	
			
			try {
				ResultSet res = dbUtil.query(sql);
				ResultSetMetaData rsmd = res.getMetaData() ;
				while (res.next()){
					for(int i=1; i<=rsmd.getColumnCount(); i++){
						if(rsmd.getColumnName(i).equals("总费用"))
							continue;
						if(res.getString(i) == null || res.getString(i).equals("") || Double.valueOf(res.getString(i)) < 0)
							rs_map.put(rsmd.getColumnName(i), "0");
						else
							rs_map.put(rsmd.getColumnName(i), res.getString(i));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
		}else if(bingZhong.equals("3")){//脑梗死
			String sql = "";
			sql = String.format(
				"SELECT	*, 总费用 - 药费 - 手术费 - 检查检验费  AS '其他费用' FROM (SELECT "
				+ "SUM (case when ZFY is not null and ZFY!='' then CAST(ZFY AS DECIMAL) else 0 END ) AS '总费用',"
				+ "SUM ((case when XYF is not null and XYF!='' then CAST(XYF AS DECIMAL) else 0 END) + (case when KJYWF is not null and KJYWF!='' then CAST(KJYWF AS DECIMAL) else 0 END) "
				+ "+ (case when ZCYF is not null and ZCYF!='' then CAST(ZCYF AS DECIMAL) else 0 END) + (case when ZCYF1 is not null and ZCYF1!='' then CAST(ZCYF1 AS DECIMAL) else 0 END) "
				+ "+ (case when BDBLZPF is not null and BDBLZPF!='' then CAST(BDBLZPF AS DECIMAL) else 0 END) + (case when QDBLZPF is not null and QDBLZPF!='' then CAST(QDBLZPF AS DECIMAL) else 0 END) "
				+ "+ (case when NXYZLZPF is not null and NXYZLZPF!='' then CAST(NXYZLZPF AS DECIMAL) else 0 END) + (case when XBYZLZPF is not null and XBYZLZPF!='' then CAST(XBYZLZPF AS DECIMAL) else 0 END)) AS '药费',"
				+ "SUM ((case when SSF is not null and SSF!='' then CAST(SSF AS DECIMAL) else 0 END) + (case when MAF is not null and MAF!='' then CAST(MAF AS DECIMAL) else 0 END) "
				+ "+ (case when SSZLF is not null and SSZLF!='' then CAST(SSZLF AS DECIMAL) else 0 END) + (case when YCXYYCLF is not null and YCXYYCLF!='' then CAST(YCXYYCLF AS DECIMAL) else 0 END)) AS '手术费',"
				+ "SUM ((case when SYSZDF is not null and SYSZDF!='' then CAST(SYSZDF AS DECIMAL) else 0 END) + (case when YXXZDF is not null and YXXZDF!='' then CAST(YXXZDF AS DECIMAL) else 0 END) "
				+ "+ (case when LCZDXMF is not null and LCZDXMF!='' then CAST(LCZDXMF AS DECIMAL) else 0 END)) AS '检查检验费' "
				+ "FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "			
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND"			
				+ "("
				+ "('I63' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I64') OR "
				+ "('I65' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I67') OR "
				+ "('I67.2' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.4') OR "
				+ "('I67.5' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.7') OR "
				+ "('I69.3' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I69.399')"
				+ ") "
				+ ") a",
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);	
			
			try {
				ResultSet res = dbUtil.query(sql);
				ResultSetMetaData rsmd = res.getMetaData() ;
				while (res.next()){
					for(int i=1; i<=rsmd.getColumnCount(); i++){
						if(rsmd.getColumnName(i).equals("总费用"))
							continue;
						if(res.getString(i) == null || res.getString(i).equals("") || Double.valueOf(res.getString(i)) < 0)
							rs_map.put(rsmd.getColumnName(i), "0");
						else
							rs_map.put(rsmd.getColumnName(i), res.getString(i));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
		}else if(bingZhong.equals("4")){//蛛网膜下腔出血
			String sql = "";
			sql = String.format(
				"SELECT	*, 总费用 - 药费 - 手术费 - 检查检验费  AS '其他费用' FROM (SELECT "
				+ "SUM (case when ZFY is not null and ZFY!='' then CAST(ZFY AS DECIMAL) else 0 END ) AS '总费用',"
				+ "SUM ((case when XYF is not null and XYF!='' then CAST(XYF AS DECIMAL) else 0 END) + (case when KJYWF is not null and KJYWF!='' then CAST(KJYWF AS DECIMAL) else 0 END) "
				+ "+ (case when ZCYF is not null and ZCYF!='' then CAST(ZCYF AS DECIMAL) else 0 END) + (case when ZCYF1 is not null and ZCYF1!='' then CAST(ZCYF1 AS DECIMAL) else 0 END) "
				+ "+ (case when BDBLZPF is not null and BDBLZPF!='' then CAST(BDBLZPF AS DECIMAL) else 0 END) + (case when QDBLZPF is not null and QDBLZPF!='' then CAST(QDBLZPF AS DECIMAL) else 0 END) "
				+ "+ (case when NXYZLZPF is not null and NXYZLZPF!='' then CAST(NXYZLZPF AS DECIMAL) else 0 END) + (case when XBYZLZPF is not null and XBYZLZPF!='' then CAST(XBYZLZPF AS DECIMAL) else 0 END)) AS '药费',"
				+ "SUM ((case when SSF is not null and SSF!='' then CAST(SSF AS DECIMAL) else 0 END) + (case when MAF is not null and MAF!='' then CAST(MAF AS DECIMAL) else 0 END) "
				+ "+ (case when SSZLF is not null and SSZLF!='' then CAST(SSZLF AS DECIMAL) else 0 END) + (case when YCXYYCLF is not null and YCXYYCLF!='' then CAST(YCXYYCLF AS DECIMAL) else 0 END)) AS '手术费',"
				+ "SUM ((case when SYSZDF is not null and SYSZDF!='' then CAST(SYSZDF AS DECIMAL) else 0 END) + (case when YXXZDF is not null and YXXZDF!='' then CAST(YXXZDF AS DECIMAL) else 0 END) "
				+ "+ (case when LCZDXMF is not null and LCZDXMF!='' then CAST(LCZDXMF AS DECIMAL) else 0 END)) AS '检查检验费' "
				+ "FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "			
				+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s' AND"			
				+ "("
				+ "'I60' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I61'"
				+ ")"
				+ ") a",
				preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);	
			
			try {
				ResultSet res = dbUtil.query(sql);
				ResultSetMetaData rsmd = res.getMetaData() ;
				while (res.next()){
					for(int i=1; i<=rsmd.getColumnCount(); i++){
						if(rsmd.getColumnName(i).equals("总费用"))
							continue;
						if(res.getString(i) == null || res.getString(i).equals("") || Double.valueOf(res.getString(i)) < 0)
							rs_map.put(rsmd.getColumnName(i), "0");
						else
							rs_map.put(rsmd.getColumnName(i), res.getString(i));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbUtil.close();
		}
		return rs_map;
	}
	
	//住院情况---住院费用---每床日费用 X轴：病种    Y轴：每床日费用
	public Map<String, String> getbeInhospital_sickbedCostByDay(String timeType, String hospitalDeps, String sex,
			String age) {
		String mysql_url = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_URL);
		String mysql_username = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_USERNAME);
		String mysql_password = hcConfiguration.getProperty(HealthcareConfiguration.MYSQL_HOSPITAL_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("mysql", mysql_url, mysql_username,
				mysql_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));
		
		String[] tmp = age.substring(1, age.length()-1).split(",");
		String age1 = tmp[0];
		String age2 = tmp[1];
		
		String sql = "";
		sql = String.format(
			"SELECT SUM (case when ZFY is not null and ZFY!='' then CAST(ZFY AS DECIMAL) else 0 END ) / sum(cast(SJZYTS as decimal)) AS '每床日费用', "
			+ "CASE WHEN ("
			+ "'G45'<=UPPER(CYZDBM) AND UPPER(CYZDBM)<'G46.9'"
			+ ") THEN '短暂性脑缺血发作' "
			+ "WHEN ("
			+ "('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
			+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
			+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
			+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
			+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
			+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
			+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')"
			+ ") THEN '脑出血' "
			+ "WHEN ("
			+ "('I63' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I64') OR "
			+ "('I65' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I67') OR "
			+ "('I67.2' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.4') OR "
			+ "('I67.5' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.7') OR "
			+ "('I69.3' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I69.399')"
			+ ") THEN '脑梗死' "
			+ "WHEN ("
			+ "'I60' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I61'"
			+ ") THEN '蛛网膜下腔出血' "
			+ "ELSE '其他' "
			+ "END AS '病种' "
			+ " FROM TB_Inpatient_FirstPage WHERE ('%s'<=RYSJ) AND (RYSJ<='%s') "			
			+ "AND (RYKBBM='%s' OR '%s'='0') AND (XB='%s' OR '%s'='0') AND CAST(NL AS DECIMAL) BETWEEN '%s' AND '%s'"			
			+ "GROUP BY "
			+ "CASE WHEN ("
			+ "'G45'<=UPPER(CYZDBM) AND UPPER(CYZDBM)<'G46.9'"
			+ ") THEN '短暂性脑缺血发作' "
			+ "WHEN ("
			+ "('I61' <= UPPER (CYZDBM)	AND UPPER (cyzdbm) < 'I62') OR "
			+ "('I62.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I62.04') OR "
			+ "('I67.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.2') OR "
			+ "('I67.7' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I67.8') OR "
			+ "('I69.0' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.1') OR "
			+ "('I69.198' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.199') OR "
			+ "('I69.2' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I69.299')"
			+ ") THEN '脑出血' "
			+ "WHEN ("
			+ "('I63' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I64') OR "
			+ "('I65' <= UPPER (CYZDBM)	AND UPPER (CYZDBM) < 'I67') OR "
			+ "('I67.2' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.4') OR "
			+ "('I67.5' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I67.7') OR "
			+ "('I69.3' <= UPPER (CYZDBM) AND UPPER (CYZDBM) < 'I69.399')"
			+ ") THEN '脑梗死' "
			+ "WHEN ("
			+ "'I60' <= UPPER (CYZDBM) AND UPPER (cyzdbm) < 'I61'"
			+ ") THEN '蛛网膜下腔出血' "
			+ "ELSE '其他' "
			+ "END;",						
			preDate, curDate, hospitalDeps, hospitalDeps, sex, sex, age1, age2);	
		
		Map<String,String> rs_map = new HashMap();
		try {
			ResultSet res = dbUtil.query(sql);
			while (res.next())
				rs_map.put(res.getString(2), res.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbUtil.close();
		
		//无法查出数据，使用样本数据
		if(rs_map.size() == 0){
			rs_map.put("短暂性脑缺血发作", "56");
			rs_map.put("脑出血", "56");
			rs_map.put("脑梗死", "56");
			rs_map.put("蛛网膜下腔出血", "54");
			rs_map.put("其他", "74");
		}
		
		return rs_map;
	}
	
	private String Calendar2String(Calendar date) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date_str = f.format(date.getTime());
		return date_str;
	}

	private Calendar getDateByDays(Calendar date, int i) {
		date.add(Calendar.DAY_OF_YEAR, i);
		return date;
	}

	private Calendar getDateLastWeek(Calendar date, String timeType) {
		switch (timeType) {
		case "week":
			date.add(Calendar.DAY_OF_YEAR, -7);
			break;
		case "month":
			date.add(Calendar.MONTH, -1);
			date.add(Calendar.DAY_OF_YEAR, 1);
			break;
		case "year":
			date.add(Calendar.YEAR, -1);
			date.add(Calendar.DAY_OF_YEAR, 1);
			break;
		default:
			System.out.println("mysql-service-日期选择出错！");
			break;
		}
		return date;
	}

	private Calendar getDateThisWeek(Calendar date, String timeType) {
		switch (timeType) {
		case "week":
			date.add(Calendar.DAY_OF_YEAR, -6);
			break;
		case "month":
			date.add(Calendar.MONTH, -1);
			break;
		case "year":
			date.add(Calendar.YEAR, -1);
			break;
		default:
			System.out.println("mysql-service-日期选择出错！");
			break;
		}
		return date;
	}

	private Calendar getCurDate() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, -1);
		now.add(Calendar.DAY_OF_YEAR, 10);
		return now;
	}



}
