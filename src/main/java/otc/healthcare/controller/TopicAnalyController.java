package otc.healthcare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import otc.healthcare.service.MySQLServiceHospital;

@Controller
public class TopicAnalyController {
	@Autowired
	private MySQLServiceHospital mySQLServiceHospital;

	@RequestMapping(value = "/topicanalysis")
	public String TopicAnalysis() {
		return "topicanalysis_home";
	}

	@RequestMapping(value = "/topicanalysis_hospital")
	public String topicanalysis_hospital() {
		return "topicanalysis_hospital";
	}

	// 住院数据---首页
	@RequestMapping(value = "/topic_inhospital_home")
	public String inhospital_home(HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return "topic_inhospital_home";
	}

	// 入院情况---入院人次
	@RequestMapping(value = "/topic_inhospital_patientsNum")
	public String inhospital_patientsNum(HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return "topic_inhospital_patientsNum";
	}

	// 入院情况---入院途径、病情
	@RequestMapping(value = "/topic_inhospital_approachAndillstate")
	public String inhospital_approachAndillstate(HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return "topic_inhospital_approachAndillstate";
	}

	// 出院情况---出院方式、病情
	@RequestMapping(value = "/topic_outhospital_approachAndillstate")
	public String outhospital_approachAndillstate(HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return "topic_outhospital_approachAndillstate";
	}

	// 住院情况---住院费用
	@RequestMapping(value = "/topic_beInhospital_costs")
	public String topic_beInhospital_costs(HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return "topic_beInhospital_costs";
	}

	@RequestMapping(value = "/getInhospitalNum")
	@ResponseBody
	public List<String> getInhospitalNum(@RequestParam(value = "timeType", required = true) String timeType) {
		List<String> InhospitalNum_list = new ArrayList<>();
		String inHospital_num = this.getMySQLServiceHospital().getInhospitalNum(timeType);
		String inHospital_rate = this.getMySQLServiceHospital().getInhospitalRate(timeType);
		if (inHospital_num.equals("Infinity"))
			inHospital_num = "--";
		InhospitalNum_list.add(inHospital_num);
		InhospitalNum_list.add(inHospital_rate);
		return InhospitalNum_list;
	}

	@RequestMapping(value = "/getInhospitalAverageDays")
	@ResponseBody
	public List<String> getInhospitalAverageDays(@RequestParam(value = "timeType", required = true) String timeType) {
		List<String> inhospitalAverageDays_list = new ArrayList<>();
		String inhospitalAverageDays_num = this.getMySQLServiceHospital().getInhospitalAverageDays_Num(timeType);
		String inhospitalAverageDays_rate = this.getMySQLServiceHospital().getInhospitalAverageDays_Rate(timeType);
		if (inhospitalAverageDays_num.equals("Infinity"))
			inhospitalAverageDays_num = "--";
		inhospitalAverageDays_list.add(inhospitalAverageDays_num);
		inhospitalAverageDays_list.add(inhospitalAverageDays_rate);
		return inhospitalAverageDays_list;
	}

	@RequestMapping(value = "/getTreatmentAverageCost")
	@ResponseBody
	public List<String> getTreatmentAverageCost(@RequestParam(value = "timeType", required = true) String timeType) {
		List<String> TreatmentAverageCost_list = new ArrayList<>();
		String TreatmentAverageCost_Num = this.getMySQLServiceHospital().getTreatmentAverageCost_Num(timeType);
		String TreatmentAverageCost_Rate = this.getMySQLServiceHospital().getTreatmentAverageCost_Rate(timeType);
		if (TreatmentAverageCost_Num.equals("Infinity"))
			TreatmentAverageCost_Num = "--";
		TreatmentAverageCost_list.add(TreatmentAverageCost_Num);
		TreatmentAverageCost_list.add(TreatmentAverageCost_Rate);
		return TreatmentAverageCost_list;
	}

	@RequestMapping(value = "/getAllRYKB")
	@ResponseBody
	public List<String> getAllRYKB() {
		List<String> RYKB_list = new ArrayList<>();
		RYKB_list = this.getMySQLServiceHospital().getAll_RYKB();
		return RYKB_list;
	}

	// 入院患者病种构成---筛选项：性别、年龄
	@RequestMapping(value = "/inhospitalPatienConsist")
	@ResponseBody
	public Map<String, String> getInhospitalPatienConsist(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String[]> requestMap = request.getParameterMap();
		Map<String, String> paramMap = new HashMap<String, String>();
		Set<String> keys = requestMap.keySet();
		for (String key : keys)
			paramMap.put(key, requestMap.get(key)[0]);
		map = this.getMySQLServiceHospital().getInhospitalPatienConsist(paramMap);
		return map;
	}

	// 性别年龄构成---病种
	@RequestMapping(value = "/inhospitalPatien_SexAgeConsist")
	@ResponseBody
	public List<Map<String, String>> getInhospitalPatienSexAgeConsist(
			@RequestParam(value = "bingZhong", required = true) String bingZhong,
			@RequestParam(value = "timeType", required = true) String timeType,
			@RequestParam(value = "hospitalDeps", required = true) String hospitalDeps) {

		List<Map<String, String>> rs_list = this.getMySQLServiceHospital().getInhospitalPatienSexAgeConsist(bingZhong,
				timeType, hospitalDeps);

		// 将男和女的数量相加---得到总数
		Map<String, String> map = new TreeMap<>();
		for (String key : rs_list.get(0).keySet())
			map.put(key,
					String.valueOf(Double.valueOf(rs_list.get(0).get(key)) + Double.valueOf(rs_list.get(1).get(key))));
		rs_list.add(map);
		return rs_list;
	}

	// 入院患者时间变化
	@RequestMapping(value = "/inhospitalPatienNum_bytime")
	@ResponseBody
	public Map<String, String> getInhospitalPatienNum_bytime(
			@RequestParam(value = "showSize", required = true) String showSize,
			@RequestParam(value = "timeType", required = true) String timeType,
			@RequestParam(value = "hospitalDeps", required = true) String hospitalDeps) {

		Map<String, String> map = this.getMySQLServiceHospital().getInhospitalPatienNum_bytime(showSize, timeType,
				hospitalDeps);
		return map;
	}

	// 入院途径 --- 筛选项:科室、性别、年龄、病种、时间范围
	@RequestMapping(value = "/inhospital_approach")
	@ResponseBody
	public Map<String, String> getInhospital_approach(
			@RequestParam(value = "bingZhong", required = true) String bingZhong,
			@RequestParam(value = "timeType", required = true) String timeType,
			@RequestParam(value = "hospitalDeps", required = true) String hospitalDeps,
			@RequestParam(value = "sex", required = true) String sex,
			@RequestParam(value = "age", required = true) String age) {

		Map<String, String> map = this.getMySQLServiceHospital().getInhospital_approach(bingZhong, timeType,
				hospitalDeps, sex, age);
		return map;
	}

	// 入院病情 --- 筛选项：科室、性别、年龄、病种、时间范围
	@RequestMapping(value = "/inhospital_illstatus")
	@ResponseBody
	public Map<String, String> getInhospital_illstatus(
			@RequestParam(value = "bingZhong", required = true) String bingZhong,
			@RequestParam(value = "timeType", required = true) String timeType,
			@RequestParam(value = "hospitalDeps", required = true) String hospitalDeps,
			@RequestParam(value = "sex", required = true) String sex,
			@RequestParam(value = "age", required = true) String age) {

		Map<String, String> map = this.getMySQLServiceHospital().getInhospital_illstatus(bingZhong, timeType,
				hospitalDeps, sex, age);
		return map;
	}

	// 离院方式 --- 筛选项:科室、性别、年龄、病种、时间范围
	@RequestMapping(value = "/outhospital_approach")
	@ResponseBody
	public Map<String, String> getOuthospital_approach(
			@RequestParam(value = "bingZhong", required = true) String bingZhong,
			@RequestParam(value = "timeType", required = true) String timeType,
			@RequestParam(value = "hospitalDeps", required = true) String hospitalDeps,
			@RequestParam(value = "sex", required = true) String sex,
			@RequestParam(value = "age", required = true) String age) {

		Map<String, String> map = this.getMySQLServiceHospital().getOuthospital_approach(bingZhong, timeType,
				hospitalDeps, sex, age);
		return map;
	}

	// 离院病情 --- 筛选项：科室、性别、年龄、病种、时间范围
	@RequestMapping(value = "/outhospital_illstatus")
	@ResponseBody
	public Map<String, String> getOuthospital_illstatus(
			@RequestParam(value = "bingZhong", required = true) String bingZhong,
			@RequestParam(value = "timeType", required = true) String timeType,
			@RequestParam(value = "hospitalDeps", required = true) String hospitalDeps,
			@RequestParam(value = "sex", required = true) String sex,
			@RequestParam(value = "age", required = true) String age) {

		Map<String, String> map = this.getMySQLServiceHospital().getOuthospital_illstatus(bingZhong, timeType,
				hospitalDeps, sex, age);
		return map;
	}

	// 住院费用 ---付费方式--- 筛选项：科室、性别、年龄、病种、时间范围
	@RequestMapping(value = "/beInhospital_treatmentPayWay")
	@ResponseBody
	public Map<String, String> getbeInhospital_treatmentPayWay(
			@RequestParam(value = "bingZhong", required = true) String bingZhong,
			@RequestParam(value = "timeType", required = true) String timeType,
			@RequestParam(value = "hospitalDeps", required = true) String hospitalDeps,
			@RequestParam(value = "sex", required = true) String sex,
			@RequestParam(value = "age", required = true) String age) {

		Map<String, String> map = this.getMySQLServiceHospital().getbeInhospital_treatmentPayWay(bingZhong, timeType,
				hospitalDeps, sex, age);
		return map;
	}

	// 住院费用---平均费用--- X轴：病种 Y轴：平均费用
	@RequestMapping(value = "/beInhospital_averageCost")
	@ResponseBody
	public Map<String, String> getbeInhospital_averageCost(
			@RequestParam(value = "timeType", required = true) String timeType,
			@RequestParam(value = "hospitalDeps", required = true) String hospitalDeps,
			@RequestParam(value = "sex", required = true) String sex,
			@RequestParam(value = "age", required = true) String age) {

		Map<String, String> map = this.getMySQLServiceHospital().getbeInhospital_averageCost(timeType, hospitalDeps,
				sex, age);
		return map;
	}

	// 费用构成（药费、手术费、检查检验费用、其他费用）--- 筛选项：科室、性别、年龄、病种、时间范围（饼图）
	@RequestMapping(value = "/beInhospital_costConsist")
	@ResponseBody
	public Map<String, String> getbeInhospital_costConsist(
			@RequestParam(value = "bingZhong", required = true) String bingZhong,
			@RequestParam(value = "timeType", required = true) String timeType,
			@RequestParam(value = "hospitalDeps", required = true) String hospitalDeps,
			@RequestParam(value = "sex", required = true) String sex,
			@RequestParam(value = "age", required = true) String age) {

		Map<String, String> map = this.getMySQLServiceHospital().getbeInhospital_costConsist(bingZhong, timeType,
				hospitalDeps, sex, age);
		return map;
	}

	// 住院情况---住院费用---每床日费用 X轴：病种 Y轴：每床日费用
	@RequestMapping(value = "/beInhospital_sickbedCostByDay")
	@ResponseBody
	public Map<String, String> getbeInhospital_sickbedCostByDay(
			@RequestParam(value = "timeType", required = true) String timeType,
			@RequestParam(value = "hospitalDeps", required = true) String hospitalDeps,
			@RequestParam(value = "sex", required = true) String sex,
			@RequestParam(value = "age", required = true) String age) {

		Map<String, String> map = this.getMySQLServiceHospital().getbeInhospital_sickbedCostByDay(timeType,
				hospitalDeps, sex, age);
		return map;
	}

	/**
	 * @return the mySQLServiceHospital
	 */
	public MySQLServiceHospital getMySQLServiceHospital() {
		return mySQLServiceHospital;
	}

	/**
	 * @param mySQLServiceHospital
	 *            the mySQLServiceHospital to set
	 */
	public void setMySQLServiceHospital(MySQLServiceHospital mySQLServiceHospital) {
		this.mySQLServiceHospital = mySQLServiceHospital;
	}

}