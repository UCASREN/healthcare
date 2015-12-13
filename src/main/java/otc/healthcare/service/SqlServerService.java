package otc.healthcare.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

	//统计科室---RYKB（入院科别）
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
			while (res.next()){
				String RYKBBM = res.getString(1);
				String RYKBMC = res.getString(2);
				RYKB_list.add(RYKBBM+","+RYKBMC);
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
				"SELECT	CAST (SUM (DATEDIFF(DAY, RYSJ, CYSJ)) AS DECIMAL) / COUNT (ZYH) FROM TB_Inpatient_FirstPage WHERE ('%s' <= RYSJ) AND (RYSJ <= '%s');",
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

		double tmp_rate = (Double.valueOf(InhospitalAverageDays_num_thisweek) - Double.valueOf(InhospitalAverageDays_num_lastweek))
				/ Double.valueOf(InhospitalAverageDays_num_lastweek);
		DecimalFormat df = new DecimalFormat("0.00%");
		String InhospitalAverageDaysRate = df.format(tmp_rate);
		return InhospitalAverageDaysRate;
	}

	//平均医疗费用 --- 数量
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
		
		DecimalFormat df = new DecimalFormat("0.00");
		TreatmentAverageCost_Num = df.format(Double.valueOf(TreatmentAverageCost_Num));
		return TreatmentAverageCost_Num;
	}

	//平均医疗费用 --- 环比增幅
	public String getTreatmentAverageCost_Rate(String timeType) {
		// this week
		String curDate = Calendar2String(getCurDate());
		String preDate = Calendar2String(getDateThisWeek(getCurDate(), timeType));

		// last week
		String curDate_1 = Calendar2String(getDateLastWeek(getCurDate(), timeType));
		String preDate_1 = Calendar2String(getDateThisWeek(getDateLastWeek(getCurDate(), timeType), timeType));

		String TreatmentAverageCost_Rate_lastweek = getInhospitalAverageDays_Num(preDate_1, curDate_1);
		String TreatmentAverageCost_Rate_thisweek = getInhospitalAverageDays_Num(preDate, curDate);

		double tmp_rate = (Double.valueOf(TreatmentAverageCost_Rate_thisweek) - Double.valueOf(TreatmentAverageCost_Rate_lastweek))
				/ Double.valueOf(TreatmentAverageCost_Rate_lastweek);
		DecimalFormat df = new DecimalFormat("0.00%");
		String InhospitalAverageDaysRate = df.format(tmp_rate);
		return InhospitalAverageDaysRate;
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
		now.add(Calendar.DAY_OF_YEAR, 25);
		return now;
	}

}
