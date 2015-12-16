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
	@RequestMapping(value="cache_all")
	@ResponseBody
	public String cacheAll(HttpServletRequest request){
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
		return "ok!";
	}
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
	public List<BaseHospitalModel> joinBaseHospitalInfo(HttpServletRequest request) {
		String check_year=getCheckYear(request);
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("joinbasehosiptalinfo") == null)
			servletContext.setAttribute("joinbasehosiptalinfo", getMySQLService().getJoinBaseHosiptalInfo());
		List<BaseHospitalModel> list=((Map<String, List<BaseHospitalModel>>)servletContext.getAttribute("joinbasehosiptalinfo")).get(check_year);
		return list;
	}
	@RequestMapping(value = "getjoinbasehospitalfromprovinceinfo")
	@ResponseBody
	public List<BaseHospitalModel> joinBaseHospitalFromProvinceInfo(@RequestParam(value = "provinceid", required = false) String id_province,
			HttpServletRequest request) {
		List<BaseHospitalModel> list=joinBaseHospitalInfo(request);
		List<BaseHospitalModel> returnList=new ArrayList<BaseHospitalModel>();
		for(BaseHospitalModel bhm:list){
			if(bhm.getUuProvince()!=null&&bhm.getUuProvince().equals(id_province))
				returnList.add(bhm);
		}
		return returnList;
	}
	@RequestMapping(value = "reset_check_year")
	@ResponseBody
	public String resetCheckYear(@RequestParam(value = "check_year", required = false) String check_year,
			HttpServletRequest request) {
		System.out.println("设置check_year"+check_year);
		request.getSession().setAttribute("check_year", check_year);
		return "success";
	}
	private String getCheckYear(HttpServletRequest request){
		if(request.getSession().getAttribute("check_year")==null)
			request.getSession().setAttribute("check_year","2011");
		return (String) request.getSession().getAttribute("check_year");
	}
	@RequestMapping(value = "getjoincommunityinfo")
	@ResponseBody
	public List<CommunityModel> joinCommunityInfo(HttpServletRequest request) {
		String check_year=getCheckYear(request);
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute("joincommunityinfo") == null)
			servletContext.setAttribute("joincommunityinfo", getMySQLService().getJoinCommunityInfo()); 
		List<CommunityModel> list=((Map<String, List<CommunityModel>>)servletContext.getAttribute("joinbasehosiptalinfo")).get(check_year);
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
			@RequestParam(value = "community", required = false) String community,HttpServletRequest request){
		String check_year=getCheckYear(request);
		return getMySQLService().getGenderInfo(provinceid,acCodeUp,community).get(check_year);
	}
	@RequestMapping(value="getageinfo")
	@ResponseBody
	public List<HashMap<String, String>> ageInfo(@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community,HttpServletRequest request){
		String check_year=getCheckYear(request);
		return getMySQLService().getAgeInfo(provinceid,acCodeUp,community).get(check_year);
	}
	@RequestMapping(value="getregioninfo")
	@ResponseBody
	public List<HashMap<String, String>> regionInfo(@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community,HttpServletRequest request){
		String check_year=getCheckYear(request);
		return getMySQLService().getRegionInfo(provinceid,acCodeUp,community).get(check_year);
	}
	@RequestMapping(value="getgenderstrokeinfo")
	@ResponseBody
	public List<HashMap<String, String>> genderStrokeInfo(@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community,HttpServletRequest request){
		String check_year=getCheckYear(request);	 
		return getMySQLService().getGenderWithStrokeInfo(provinceid,acCodeUp,community).get(check_year);
	}
	@RequestMapping(value="getagestrokeinfo")
	@ResponseBody
	public List<HashMap<String, String>> ageStrokeInfo(@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community,HttpServletRequest request){
		String check_year=getCheckYear(request); 
		return getMySQLService().getAgeWithStrokeInfo(provinceid, acCodeUp, community).get(check_year);
	}
	@RequestMapping(value="getregionstrokeinfo")
	@ResponseBody
	public List<HashMap<String, String>> regionStrokeInfo(@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community,HttpServletRequest request){
		String check_year=getCheckYear(request);	 
		return getMySQLService().getRegionWithStrokeInfo(provinceid, acCodeUp, community).get(check_year);
	}
	@RequestMapping(value="geteducationstrokeinfo")
	@ResponseBody
	public List<HashMap<String, String>> educationStrokeInfo(@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "accodeup", required = false) String acCodeUp,
			@RequestParam(value = "community", required = false) String community,HttpServletRequest request){
		String check_year=getCheckYear(request);
		return getMySQLService().getEducationWithStrokeInfo(provinceid, acCodeUp, community).get(check_year);
	}
	@RequestMapping(value="getgenderdangerfactorinfo")
	@ResponseBody
	public Map<String, List<HashMap<String, String>>> genderDangerFactorInfo(@RequestParam(value = "provinceid", required = false) String provinceid,
			@RequestParam(value = "dangertype", required = false) String dangertype,
			@RequestParam(value = "ageclassification", required = false) String ageclassification,HttpServletRequest request){
		String check_year=getCheckYear(request);
		return getMySQLService().getGenderDangerFactorInfo(provinceid, dangertype, ageclassification).get(check_year);
	}
	@RequestMapping(value="disease_burden")
	public String diseaseBurden(){
		return "disease_burden";
	}
	@RequestMapping(value="year_info")
	public String yearInfo(){
		return "year_info";
	}
	@RequestMapping(value="danger_factor")
	public String dangerFactor(){
		return "danger_factor";
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
