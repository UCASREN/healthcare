package otc.healthcare.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import otc.healthcare.pojo.BaseHospitalModel;
import otc.healthcare.pojo.CommunityModel;
import otc.healthcare.pojo.YearStatisticsModel;
import otc.healthcare.service.MySQLServiceScreen;

@Controller
public class ScreenController {
	@Autowired
	private MySQLServiceScreen mySQLServiceScreen;

	@RequestMapping(value = "cache_all")
	@ResponseBody
	public String cacheAll(HttpServletRequest request) {
		System.out.println("缓存强力加载中");
		joinBaseHospitalInfo(request);
		System.out.println("缓存joinBaseHospitalInfo成功");
		joinCommunityInfo(request);
		System.out.println("缓存joinCommunityInfo成功");
		provinceCityInfo(request);
		System.out.println("缓存provinceCityInfo成功");
		provinceInfo(request);
		System.out.println("缓存provinceInfo成功");
		yearInfo(request);
		System.out.println("缓存yearInfo成功");
		beIllMapData(request);
		System.out.println("缓存illmaldata成功");
		return "ok!";
	}

	@RequestMapping(value = "hospital_distribution_base")
	public String hospitalDistributionBase(HttpServletResponse response){
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return "hospital_distribution_base";
	}

	@RequestMapping(value = "hospital_distribution_community")
	public String hospitalDistributionCommunity(HttpServletResponse response){
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return "hospital_distribution_community";
	}

	@RequestMapping(value = "getjoinbasehospitalinfo")
	@ResponseBody
	public List<BaseHospitalModel> joinBaseHospitalInfo(HttpServletRequest request) {
		String check_year = getCheckYear(request);
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("joinbasehosiptalinfo") == null)
			servletContext.setAttribute("joinbasehosiptalinfo", getMySQLServiceScreen().getJoinBaseHosiptalInfo());
		List<BaseHospitalModel> list = ((Map<String, List<BaseHospitalModel>>) servletContext
				.getAttribute("joinbasehosiptalinfo")).get(check_year);
		return list;
	}

	@RequestMapping(value = "getjoinbasehospitalfromprovinceinfo")
	@ResponseBody
	public List<BaseHospitalModel> joinBaseHospitalFromProvinceInfo(
			@RequestParam(value = "provinceid", required = false) String id_province, HttpServletRequest request) {
		List<BaseHospitalModel> list = joinBaseHospitalInfo(request);
		List<BaseHospitalModel> returnList = new ArrayList<BaseHospitalModel>();
		for (BaseHospitalModel bhm : list) {
			if (bhm.getUuProvince() != null && bhm.getUuProvince().equals(id_province))
				returnList.add(bhm);
		}
		return returnList;
	}

	@RequestMapping(value = "reset_check_year")
	@ResponseBody
	public String resetCheckYear(@RequestParam(value = "check_year", required = false) String check_year,
			HttpServletRequest request) {
		System.out.println("设置check_year" + check_year);
		request.getSession().setAttribute("check_year", check_year);
		return "success";
	}

	private String getCheckYear(HttpServletRequest request) {
		if (request.getSession().getAttribute("check_year") == null)
			request.getSession().setAttribute("check_year", "2011");
		return (String) request.getSession().getAttribute("check_year");
	}

	@RequestMapping(value = "getjoincommunityinfo")
	@ResponseBody
	public List<CommunityModel> joinCommunityInfo(HttpServletRequest request) {
		String check_year = getCheckYear(request);
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("joincommunityinfo") == null)
			servletContext.setAttribute("joincommunityinfo", getMySQLServiceScreen().getJoinCommunityInfo());
		List<CommunityModel> list = ((Map<String, List<CommunityModel>>) servletContext
				.getAttribute("joincommunityinfo")).get(check_year);
		return list;
	}

	@RequestMapping(value = "getjoincommunityinfofromaccode")
	@ResponseBody
	public List<CommunityModel> joinCommunityInfoFromAcCode(
			@RequestParam(value = "accode", required = false) String accode, HttpServletRequest request) {
		List<CommunityModel> list = joinCommunityInfo(request);
		List<CommunityModel> returnList = new ArrayList<CommunityModel>();
		for (CommunityModel cm : list) {
			if (cm.getAcCodeUp().equals(accode))
				returnList.add(cm);
		}
		return returnList;
	}

	@RequestMapping(value = "getprovincecityinfo")
	@ResponseBody
	public Map<String, Object> provinceCityInfo(HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("provincecityinfo") == null)
			servletContext.setAttribute("provincecityinfo", getMySQLServiceScreen().getProvinceCityInfo());
		return (Map<String, Object>) servletContext.getAttribute("provincecityinfo");
	}

	@RequestMapping(value = "getprovinceoptions")
	@ResponseBody
	public Map<String, String> provinceOptions(HttpServletRequest request) {
		return (Map<String, String>) provinceCityInfo(request).get("province");
	}

	@RequestMapping(value = "getcityoptions")
	@ResponseBody
	public ArrayList<String> cityOptions(@RequestParam(value = "provinceid", required = false) String id_province,
			HttpServletRequest request) {
		HashMap<String, ArrayList<String>> pci = (HashMap<String, ArrayList<String>>) provinceCityInfo(request)
				.get("all");
		return pci.get(id_province);
	}

	@RequestMapping(value = "getprovinceinfo")
	@ResponseBody
	public Map<String, String> provinceInfo(HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("provinceinfo") == null)
			servletContext.setAttribute("provinceinfo", getMySQLServiceScreen().getProvinceInfo());
		return (Map<String, String>) servletContext.getAttribute("provinceinfo");
	}

	@RequestMapping(value = "getcityinfo")
	@ResponseBody
	public Map<String, String> cityInfo(@RequestParam(value = "provinceid", required = false) String provinceid,
			HttpServletRequest request) {
		return getMySQLServiceScreen().getCityInfo(provinceid);
	}

	@RequestMapping(value = "crowd_features")
	public String stroke_patient(HttpServletResponse response){
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return "crowd_features";
	}

	@RequestMapping(value = "getyearinfo")
	@ResponseBody
	public Map<String, YearStatisticsModel> yearInfo(HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("yearinfo") == null)
			servletContext.setAttribute("yearinfo", getMySQLServiceScreen().getYearInfo());
		return (Map<String, YearStatisticsModel>) servletContext.getAttribute("yearinfo");
	}

	@RequestMapping(value = "getgenderinfo")
	@ResponseBody
	public List<HashMap<String, String>> genderInfo(
			@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community, HttpServletRequest request) {
		String check_year = getCheckYear(request);
		return getMySQLServiceScreen().getGenderInfo(provinceid, acCodeUp, community).get(check_year);
	}

	@RequestMapping(value = "getageinfo")
	@ResponseBody
	public List<HashMap<String, String>> ageInfo(
			@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community, HttpServletRequest request) {
		String check_year = getCheckYear(request);
		return getMySQLServiceScreen().getAgeInfo(provinceid, acCodeUp, community).get(check_year);
	}

	@RequestMapping(value = "getregioninfo")
	@ResponseBody
	public List<HashMap<String, String>> regionInfo(
			@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community, HttpServletRequest request) {
		String check_year = getCheckYear(request);
		return getMySQLServiceScreen().getRegionInfo(provinceid, acCodeUp, community).get(check_year);
	}

	@RequestMapping(value = "getgenderstrokeinfo")
	@ResponseBody
	public List<HashMap<String, String>> genderStrokeInfo(
			@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community, HttpServletRequest request) {
		String check_year = getCheckYear(request);
		return getMySQLServiceScreen().getGenderWithStrokeInfo(provinceid, acCodeUp, community).get(check_year);
	}

	@RequestMapping(value = "getagestrokeinfo")
	@ResponseBody
	public List<HashMap<String, String>> ageStrokeInfo(
			@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community, HttpServletRequest request) {
		String check_year = getCheckYear(request);
		return getMySQLServiceScreen().getAgeWithStrokeInfo(provinceid, acCodeUp, community).get(check_year);
	}

	@RequestMapping(value = "getregionstrokeinfo")
	@ResponseBody
	public List<HashMap<String, String>> regionStrokeInfo(
			@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community, HttpServletRequest request) {
		String check_year = getCheckYear(request);
		return getMySQLServiceScreen().getRegionWithStrokeInfo(provinceid, acCodeUp, community).get(check_year);
	}

	@RequestMapping(value = "geteducationstrokeinfo")
	@ResponseBody
	public List<HashMap<String, String>> educationStrokeInfo(
			@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community, HttpServletRequest request) {
		String check_year = getCheckYear(request);
		return getMySQLServiceScreen().getEducationWithStrokeInfo(provinceid, acCodeUp, community).get(check_year);
	}

	@RequestMapping(value = "getgenderdangerfactorinfo")
	@ResponseBody
	public Map<String, List<HashMap<String, String>>> genderDangerFactorInfo(
			@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "dangertype", required = false) String dangertype,
			@RequestParam(value = "ageclassification", required = false) String ageclassification,
			HttpServletRequest request) {
		String check_year = getCheckYear(request);
//		ObjectMapper objectMapper=new ObjectMapper();
//		
//		String str="{\"all\":[{\"count\":\"4\",\"age\":\"40-44\"},{\"count\":\"1059\",\"age\":\"45-49\"},{\"count\":\"1381\",\"age\":\"50-54\"},{\"count\":\"1619\",\"age\":\"55-59\"},{\"count\":\"1527\",\"age\":\"60-64\"},{\"count\":\"1263\",\"age\":\"65-69\"},{\"count\":\"862\",\"age\":\"70-74\"},{\"count\":\"595\",\"age\":\"75-79\"},{\"count\":\"356\",\"age\":\"80+\"}],\"1\":[{\"count\":\"2\",\"age\":\"40-44\"},{\"count\":\"523\",\"age\":\"45-49\"},{\"count\":\"608\",\"age\":\"50-54\"},{\"count\":\"608\",\"age\":\"55-59\"},{\"count\":\"557\",\"age\":\"60-64\"},{\"count\":\"506\",\"age\":\"65-69\"},{\"count\":\"343\",\"age\":\"70-74\"},{\"count\":\"265\",\"age\":\"75-79\"},{\"count\":\"156\",\"age\":\"80+\"}],\"2\":[{\"count\":\"2\",\"age\":\"40-44\"},{\"count\":\"536\",\"age\":\"45-49\"},{\"count\":\"773\",\"age\":\"50-54\"},{\"count\":\"1011\",\"age\":\"55-59\"},{\"count\":\"970\",\"age\":\"60-64\"},{\"count\":\"757\",\"age\":\"65-69\"},{\"count\":\"519\",\"age\":\"70-74\"},{\"count\":\"330\",\"age\":\"75-79\"},{\"count\":\"200\",\"age\":\"80+\"}]}";
//		Map<String, List<HashMap<String, String>>> maps = null;
//		try {
//			maps = objectMapper.readValue(str, Map.class);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return maps;
		return getMySQLServiceScreen().getGenderDangerFactorInfo(provinceid, dangertype, ageclassification).get(check_year);
	  
	}

	@RequestMapping(value = "get_beillmap_data")
	@ResponseBody
	public Map<String, String> beIllMapData(HttpServletRequest request) {
		String check_year = getCheckYear(request);
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("beillmapdata") == null)
			servletContext.setAttribute("beillmapdata", getMySQLServiceScreen().getBeIllMapData());
		Map<String, String> map = ((HashMap<String, HashMap<String, String>>) servletContext
				.getAttribute("beillmapdata")).get(check_year);
		return map;
	}

	@RequestMapping(value = "disease_burden")
	public String diseaseBurden(HttpServletResponse response){
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return "disease_burden";
	}

	@RequestMapping(value = "year_info")
	public String yearInfo() {
		return "year_info";
	}

	@RequestMapping(value = "danger_factor")
	public String dangerFactor(HttpServletResponse response){
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return "danger_factor";
	}

	@RequestMapping(value = "be_ill_map")
	public String beIllMap(HttpServletResponse response){
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return "beillmap";
	}
	@RequestMapping(value = "topicanalysis_screen")
	public String topicanalysisScreen() {
		return "topicanalysis_screen";
	}

	/**
	 * @return the mySQLServiceScreen
	 */
	public MySQLServiceScreen getMySQLServiceScreen() {
		return mySQLServiceScreen;
	}

	/**
	 * @param mySQLServiceScreen the mySQLServiceScreen to set
	 */
	public void setMySQLServiceScreen(MySQLServiceScreen mySQLServiceScreen) {
		this.mySQLServiceScreen = mySQLServiceScreen;
	}



}
