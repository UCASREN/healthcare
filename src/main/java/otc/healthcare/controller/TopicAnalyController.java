package otc.healthcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import otc.healthcare.service.OracleService;

@Controller
@RequestMapping("/topicanalysis")
public class TopicAnalyController {
	@Autowired
	private OracleService oracleSerive;
	
	@RequestMapping(value="")
	public String TopicAnalysis(){
		return "topicanalysis";
	}
	
	@RequestMapping(value="/inhospital_patientsNum")
	public String inhospital_patientsNum(){
		return "service_inhospital_patientsNum";
	}
}
