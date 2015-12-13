package otc.healthcare.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import otc.healthcare.service.SqlServerService;

@Controller
public class TopicAnalyController {
	@Autowired
	public SqlServerService SqlServerService;
	
	@RequestMapping(value="/topicanalysis")
	public String TopicAnalysis(){
		return "topicanalysis";
	}
	
	@RequestMapping(value="/inhospital_home")
	public String inhospital_home(){
		return "inhospital_home";
	}
	
	@RequestMapping(value="/inhospital_patientsNum")
	public String inhospital_patientsNum(){
		return "service_inhospital_patientsNum";
	}
	
	@RequestMapping(value="/getInhospitalNum")
	@ResponseBody
	public List<String> getInhospitalNum(@RequestParam(value = "timeType", required = true) String timeType){
		List<String> InhospitalNum_list = new ArrayList<>();
		String inHospital_num = this.SqlServerService.getInhospitalNum(timeType);
		String inHospital_rate = this.SqlServerService.getInhospitalRate(timeType);
		InhospitalNum_list.add(inHospital_num);
		InhospitalNum_list.add(inHospital_rate);
		return InhospitalNum_list;
	}
	

	@RequestMapping(value="/getInhospitalAverageDays")
	@ResponseBody
	public List<String> getInhospitalAverageDays(@RequestParam(value = "timeType", required = true) String timeType){
		List<String> inhospitalAverageDays_list = new ArrayList<>();
		String inhospitalAverageDays_num = this.SqlServerService.getInhospitalAverageDays_Num(timeType);
		String inhospitalAverageDays_rate = this.SqlServerService.getInhospitalAverageDays_Rate(timeType);
		inhospitalAverageDays_list.add(inhospitalAverageDays_num);
		inhospitalAverageDays_list.add(inhospitalAverageDays_rate);
		return inhospitalAverageDays_list;
	}
	
	@RequestMapping(value="/getTreatmentAverageCost")
	@ResponseBody
	public List<String> getTreatmentAverageCost(@RequestParam(value = "timeType", required = true) String timeType){
		List<String> TreatmentAverageCost_list = new ArrayList<>();
		String TreatmentAverageCost_Num = this.SqlServerService.getTreatmentAverageCost_Num(timeType);
		String TreatmentAverageCost_Rate = this.SqlServerService.getTreatmentAverageCost_Rate(timeType);
		TreatmentAverageCost_list.add(TreatmentAverageCost_Num);
		TreatmentAverageCost_list.add(TreatmentAverageCost_Rate);
		return TreatmentAverageCost_list;
	}
	
	@RequestMapping(value="/getAllRYKB")
	@ResponseBody
	public List<String> getAllRYKB(){
		List<String> RYKB_list = new ArrayList<>();
		RYKB_list = this.SqlServerService.getAll_RYKB();
		return RYKB_list;
	}
	
	public SqlServerService getSqlServerService() {
		return SqlServerService;
	}

	public void setSqlServerService(SqlServerService sqlServerService) {
		SqlServerService = sqlServerService;
	}
}
