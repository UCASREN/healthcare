package otc.healthcare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import otc.healthcare.pojo.BaseHospitalModel;
import otc.healthcare.pojo.CommunityModel;
import otc.healthcare.pojo.YearStatisticsModel;
import otc.healthcare.service.MySQLService;


@Controller
public class ScreenController {
	@Autowired
	private MySQLService mySQLService;
	
	@RequestMapping(value="hospital_distribution_base")
	public String hospitalDistributionBase(){
		return "hospital_distribution_base";
	}
	@RequestMapping(value="hospital_distribution_community")
	public String hospitalDistributionCommunity(){
		return "hospital_distribution_community";
	}
	@RequestMapping(value = "getjoinbasehospitalinfo")
	@ResponseBody
	public List<BaseHospitalModel> joinBaseHosiptalInfo(HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("joinbasehosiptalinfo") == null)
			servletContext.setAttribute("joinbasehosiptalinfo", getMySQLService().getJoinBaseHosiptalInfo());
		return (List<BaseHospitalModel>) servletContext.getAttribute("joinbasehosiptalinfo");
	}
	@RequestMapping(value = "getjoincommunityinfo")
	@ResponseBody
	public List<CommunityModel> joinCommunityInfo(HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("joincommunityinfo") == null)
			servletContext.setAttribute("joincommunityinfo", getMySQLService().getJoinCommunityInfo());
		return (List<CommunityModel>) servletContext.getAttribute("joincommunityinfo");
	}
	@RequestMapping(value = "getjoincommunityinfofromaccode")
	@ResponseBody
	public List<CommunityModel> joinCommunityInfoFromAcCode(@RequestParam(value = "accode", required = false) String accode,HttpServletRequest request) {
		List<CommunityModel> list=joinCommunityInfo(request);
		List<CommunityModel> returnList=new ArrayList<CommunityModel>();
		for(CommunityModel cm:list){
			if(cm.getAcCodeUp().equals(accode))
				returnList.add(cm);
		}
		return returnList;
	}
	@RequestMapping(value = "getprovincecityinfo")
	@ResponseBody
	public Map<String,Object> provinceCityInfo(HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("provincecityinfo") == null)
			servletContext.setAttribute("provincecityinfo", getMySQLService().getProvinceCityInfo());
		return (Map<String,Object>) servletContext.getAttribute("provincecityinfo");
	}
	@RequestMapping(value = "getprovinceoptions")
	@ResponseBody
	public Map<String,String> provinceOptions(HttpServletRequest request) {
		return (Map<String, String>) provinceCityInfo(request).get("province");
	}
	@RequestMapping(value = "getcityoptions")
	@ResponseBody
	public ArrayList<String> cityOptions(@RequestParam(value = "provinceid", required = false) String id_province,HttpServletRequest request) {
		HashMap<String,ArrayList<String>> pci=(HashMap<String, ArrayList<String>>) provinceCityInfo(request).get("all");
		return pci.get(id_province);
	}
	
	@RequestMapping(value = "getprovinceinfo")
	@ResponseBody
	public Map<String,String> provinceInfo(HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("provinceinfo") == null)
			servletContext.setAttribute("provinceinfo", getMySQLService().getProvinceInfo());
		return (Map<String,String>) servletContext.getAttribute("provinceinfo");
	}
	@RequestMapping(value = "getcityinfo")
	@ResponseBody
	public Map<String,String> cityInfo(@RequestParam(value = "provinceid", required = false) String provinceid,HttpServletRequest request) {
		return getMySQLService().getCityInfo(provinceid);
	}
	@RequestMapping(value="crowd_features")
	public String stroke_patient(){
		return "crowd_features";
	}
	@RequestMapping(value="getyearinfo")
	@ResponseBody
	public Map<String,YearStatisticsModel> yearInfo(HttpServletRequest request){
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("yearinfo") == null)
			servletContext.setAttribute("yearinfo", getMySQLService().getYearInfo());
		return (Map<String,YearStatisticsModel>) servletContext.getAttribute("yearinfo");
	}
	@RequestMapping(value="getgenderinfo")
	@ResponseBody
	public List<HashMap<String, String>> genderInfo(@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community,
			HttpServletRequest request){
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("genderinfo") == null)
			servletContext.setAttribute("genderinfo", getMySQLService().getGenderInfo(provinceid,acCodeUp,community));
		return (List<HashMap<String, String>>) servletContext.getAttribute("genderinfo");
	}
	@RequestMapping(value="getageinfo")
	@ResponseBody
	public Map<String,YearStatisticsModel> ageInfo(@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community,
			HttpServletRequest request){
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("ageinfo") == null)
			servletContext.setAttribute("ageinfo", getMySQLService().getAgeInfo(provinceid,acCodeUp,community));
		return (Map<String,YearStatisticsModel>) servletContext.getAttribute("ageinfo");
	}
	@RequestMapping(value="getregioninfo")
	@ResponseBody
	public Map<String,YearStatisticsModel> regionInfo(@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community,
			HttpServletRequest request){
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("regioninfo") == null)
			servletContext.setAttribute("regioninfo", getMySQLService().getRegionInfo(provinceid,acCodeUp,community));
		return (Map<String,YearStatisticsModel>) servletContext.getAttribute("regioninfo");
	}
	/**
	 * @return the mySQLService
	 */
	public MySQLService getMySQLService() {
		return mySQLService;
	}
	/**
	 * @param mySQLService the mySQLService to set
	 */
	public void setMySQLService(MySQLService mySQLService) {
		this.mySQLService = mySQLService;
	}

}
