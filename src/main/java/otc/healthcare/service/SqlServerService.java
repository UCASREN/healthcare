package otc.healthcare.service;

import java.math.BigDecimal;
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

import javax.activation.MailcapCommandMap;
import javax.servlet.http.HttpServletRequest;

import org.apache.xmlbeans.impl.jam.mutable.MMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import otc.healthcare.dao.ConnectionFactory;
import otc.healthcare.dao.HcApplydataDao;
import otc.healthcare.dao.HcApplyenvDao;
import otc.healthcare.dao.OracleDBUtil;
import otc.healthcare.dao.SQLServerDBUtil;
import otc.healthcare.pojo.ClassificationInfo;
import otc.healthcare.pojo.DatabaseInfo;
import otc.healthcare.pojo.FieldInfo;
import otc.healthcare.pojo.HcApplydata;
import otc.healthcare.pojo.HcApplyenv;
import otc.healthcare.pojo.TableInfo;
import otc.healthcare.util.DBUtil;
import otc.healthcare.util.HealthcareConfiguration;

@Component
public class SqlServerService implements IService {

	@Autowired
	private HealthcareConfiguration hcConfiguration;

	public boolean testConnection(String sqlserver_url, String sqlserver_username, String sqlserver_password) {
		ConnectionFactory connectionFactory = new ConnectionFactory("sqlserver", sqlserver_url, sqlserver_username,
				sqlserver_password);
		if (connectionFactory.getInstance().getConnection() != null)
			return true;
		return false;
	}

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
		String sqlserver_url = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_URL);
		String sqlserver_username = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_USERNAME);
		String sqlserver_password = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("sqlserver", sqlserver_url, sqlserver_username,
				sqlserver_password);
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
		String sqlserver_url = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_URL);
		String sqlserver_username = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_USERNAME);
		String sqlserver_password = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("sqlserver", sqlserver_url, sqlserver_username,
				sqlserver_password);
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
		String sqlserver_url = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_URL);
		String sqlserver_username = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_USERNAME);
		String sqlserver_password = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("sqlserver", sqlserver_url, sqlserver_username,
				sqlserver_password);
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
		String sqlserver_url = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_URL);
		String sqlserver_username = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_USERNAME);
		String sqlserver_password = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("sqlserver", sqlserver_url, sqlserver_username,
				sqlserver_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());

		String sql = "";
		sql = String.format(
				"SELECT SUM (CAST(ZFY AS DECIMAL)) / COUNT (ZYH) FROM TB_Inpatient_FirstPage WHERE	('%s'<=RYSJ) AND (RYSJ<'%s');",
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
		String sqlserver_url = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_URL);
		String sqlserver_username = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_USERNAME);
		String sqlserver_password = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("sqlserver", sqlserver_url, sqlserver_username,
				sqlserver_password);
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
				"SELECT SUM(CASE WHEN CYZDBM = 'G45-G46.8'	OR CYZDBM = 'I63-I63.9'	OR CYZDBM = 'I65-I66.9'	OR CYZDBM = 'I67.2' OR CYZDBM = 'I67.3'	OR CYZDBM = 'I67.5'	OR CYZDBM = 'I67.6'	OR CYZDBM = 'I69.3-I69.398' THEN 1 ELSE	0	END) AS '缺血性卒中',"
				+ "SUM(CASE WHEN CYZDBM = 'I60-I61.9' OR CYZDBM = 'I62.0-I62.03' OR CYZDBM = 'I67.0' OR CYZDBM = 'I67.1' OR CYZDBM = 'I67.7' OR CYZDBM = 'I69.0' OR CYZDBM = 'I69.198'	OR CYZDBM = 'I69.2-I69.298' THEN 1 ELSE	0	END ) AS '出血性卒中',"
				+ "SUM (CASE	WHEN CYZDBM != 'G45-G46.8' AND CYZDBM != 'I63-I63.9' AND CYZDBM != 'I65-I66.9' AND CYZDBM != 'I67.2' AND CYZDBM != 'I67.3'	AND CYZDBM != 'I67.5' AND CYZDBM != 'I67.6'	AND CYZDBM != 'I69.3-I69.398' AND CYZDBM != 'I60-I61.9' "
				+ "AND CYZDBM != 'I62.0-I62.03' AND CYZDBM != 'I67.0' AND CYZDBM != 'I67.1' AND CYZDBM != 'I67.7' AND CYZDBM != 'I69.0' AND CYZDBM != 'I69.198'	AND CYZDBM != 'I69.2-I69.298' THEN 1 ELSE 0 END) AS '其他'"
				+ "FROM (SELECT * FROM TB_Inpatient_FirstPage WHERE('%s'<=RYSJ) AND (RYSJ<='%s') AND ('%s'='0' OR RYKBBM = '%s') ) AS a"
				+ " WHERE (XB='%s' OR '%s'='0') AND NL BETWEEN '%s' AND '%s';",
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
		
		String sqlserver_url = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_URL);
		String sqlserver_username = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_USERNAME);
		String sqlserver_password = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("sqlserver", sqlserver_url, sqlserver_username,
				sqlserver_password);
		SQLServerDBUtil dbUtil = new SQLServerDBUtil(connectionFactory.getInstance().getConnection());
		
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));
		
		if(bingZhong.equals("1")){//缺血卒中
			String sql = "";
			sql = String.format(
				"SELECT	XB,	SUM (CASE WHEN NL BETWEEN 0	AND 20 THEN	1 ELSE 0 END ) AS '[0-20]',	"
				+ "SUM (CASE WHEN NL BETWEEN 21 AND 40 THEN 1 ELSE 0 END ) AS '[21-40]', "
				+ "SUM ( CASE WHEN NL BETWEEN 41 AND 60 THEN 1 ELSE 0 END ) AS '[41-60]',	"
				+ "SUM ( CASE WHEN NL BETWEEN 61 AND 80 THEN 1 ELSE	0 END ) AS '[61-80]',	"
				+ "SUM ( CASE WHEN NL BETWEEN 81 AND 100 THEN 1	ELSE 0 END ) AS '[81-100]',	"
				+ "SUM ( CASE WHEN NL > 101 THEN 1 ELSE 0 END	) AS '[>101]' "
				+ "FROM	(SELECT * FROM TB_Inpatient_FirstPage WHERE ('%s'<= RYSJ) AND (RYSJ <= '%s')"
				+ " AND (RYKBBM = '%s' or '%s'='0') ) AS a WHERE CYZDBM = 'G45-G46.8'OR CYZDBM = 'I63-I63.9'OR CYZDBM = 'I65-I66.9'OR "
				+ "CYZDBM = 'I67.2'OR CYZDBM = 'I67.3'OR CYZDBM = 'I67.5'OR CYZDBM = 'I67.6'OR CYZDBM = 'I69.3-I69.398' "
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
			
		}else if(bingZhong.equals("2")){
			String sql = "";
			sql = String.format(
				"SELECT	XB,	SUM ( CASE WHEN NL BETWEEN 0 AND 20 THEN 1 ELSE 0 END ) AS '[0-20]', "
				+ "SUM (CASE WHEN NL BETWEEN 21 AND 40 THEN 1 ELSE 0 END ) AS '[21-40]', "
				+ "SUM (CASE WHEN NL BETWEEN 41 AND 60 THEN 1 ELSE 0 END ) AS '[41-60]', "
				+ "SUM (CASE WHEN NL BETWEEN 61 AND 80 THEN 1 ELSE 0 END ) AS '[61-80]', "
				+ "SUM (CASE WHEN NL BETWEEN 81 AND 100 THEN 1 ELSE 0 END	) AS '[81-100]',"
				+ "SUM (CASE WHEN NL > 101 THEN 1 ELSE 0 END) AS '[>101]' "
				+ "FROM	(SELECT * FROM TB_Inpatient_FirstPage WHERE ('%s' <= RYSJ) AND (RYSJ <= '%s') "
				+ "AND (RYKBBM = '%s' OR '%s' = '0') ) AS a WHERE	CYZDBM = 'I60-I61.9'OR CYZDBM = 'I62.0-I62.03'OR "
				+ "CYZDBM = 'I67.0'OR CYZDBM = 'I67.1'OR CYZDBM = 'I67.7'OR CYZDBM = 'I69.0'OR CYZDBM = 'I69.198'OR "
				+ "CYZDBM = 'I69.2-I69.298' GROUP BY	XB;",			 
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
		}
		
		//无数据时候，模拟相应数据
		if(rs_list.size() != 2){
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
		String sqlserver_url = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_URL);
		String sqlserver_username = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_USERNAME);
		String sqlserver_password = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("sqlserver", sqlserver_url, sqlserver_username,
				sqlserver_password);
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
		String sqlserver_url = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_URL);
		String sqlserver_username = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_USERNAME);
		String sqlserver_password = hcConfiguration.getProperty(HealthcareConfiguration.SQLSERVER_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("sqlserver", sqlserver_url, sqlserver_username,
				sqlserver_password);
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
		
		//对map中的key排序，方便前台show
		return map;
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
			System.out.println("sqlserver-service-日期选择出错！");
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
			System.out.println("sqlserver-service-日期选择出错！");
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
