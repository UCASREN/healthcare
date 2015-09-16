 package otc.healthcare.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import otc.healthcare.pojo.HcApplydata;
import otc.healthcare.pojo.HcApplyenv;
import otc.healthcare.service.FilterService;
import otc.healthcare.service.OracleService;
import otc.healthcare.service.WordService;
import otc.healthcare.util.HealthcareConfiguration;

/**
 * @author Andy
 *
 */
@Controller
@RequestMapping("/adminpanel")
public class AdminController {

	@Autowired
	private HealthcareConfiguration hcConfiguration;
	
	@Autowired
	WordService WordService;
	public WordService getWordService() {
		return WordService;
	}

	public void setWordService(WordService wordService) {
		WordService = wordService;
	}
	
	@Autowired
	OracleService oracleService;
	public OracleService getOracleService() {
		return oracleService;
	}

	public void setOracleService(OracleService oracleService) {
		this.oracleService = oracleService;
	}

	@Autowired
	FilterService filterService;
	public FilterService getFilterService() {
		return filterService;
	}

	public void setFilterService(FilterService filterService) {
		this.filterService = filterService;
	}
	

	@RequestMapping(value = "/applydatacheck", method = RequestMethod.GET)
	public String applyDataCheck(@RequestParam(value = "docid", required = false) String docid,
			@RequestParam(value = "applydataid", required = false) String applydataid) {
		return "applydatacheck";
	}
	
	@RequestMapping(value = "/applydatacheck_success", method = RequestMethod.GET)
	public String applyDataCheck_success(@RequestParam(value = "applyid", required = false) String applydataid) {
		//中国卒中数据中心、国家卫生计生委脑卒中防治委员会办公室
		String status = "3";
		this.oracleService.changeApplyDataStatus(applydataid,status);
		return "applydatacheck";
	}
	
	@RequestMapping(value = "/applydatacheck_reject", method = RequestMethod.GET)
	public String applyDataCheck_reject(@RequestParam(value = "applyid", required = false) String applydataid
			,@RequestParam(value = "rejectReason", required = false) String rejectReason) {
		//中国卒中数据中心、国家卫生计生委脑卒中防治委员会办公室
		String status = "4";
		this.oracleService.changeApplyDataStatus(applydataid,status);
		if(!rejectReason.equals("")){
			try {
				String reason = URLDecoder.decode(URLDecoder.decode(rejectReason,"UTF-8"),"UTF-8");
				this.oracleService.insertApplyDataFailReason(applydataid,reason);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		}
		return "applydatacheck";
	}
	
	@RequestMapping(value = "/applyenvcheck", method = RequestMethod.GET)
	public String applyEnvCheck(@RequestParam(value = "docid", required = false) String docid,
			@RequestParam(value = "applydataid", required = false) String applydataid) {
		return "applyenvcheck";
	}
	
	@RequestMapping(value = "/applyenvcheck_success", method = RequestMethod.GET)
	public String applyEnvCheck_success(@RequestParam(value = "applyid", required = false) String applydataid) {
		//中国卒中数据中心、国家卫生计生委脑卒中防治委员会办公室
		String status = "3";
		this.oracleService.changeApplyEnvStatus(applydataid,status);
		return "applyenvcheck";
	}
	
	@RequestMapping(value = "/applyenvcheck_reject", method = RequestMethod.GET)
	public String applyEnvCheck_reject(@RequestParam(value = "applyid", required = false) String applydataid
			,@RequestParam(value = "rejectReason", required = false) String rejectReason) {
		//中国卒中数据中心、国家卫生计生委脑卒中防治委员会办公室
		String status = "4";
		this.oracleService.changeApplyEnvStatus(applydataid,status);
		if(!rejectReason.equals("")){
			try {
				String reason = URLDecoder.decode(URLDecoder.decode(rejectReason,"UTF-8"),"UTF-8");
				this.oracleService.insertApplyEnvFailReason(applydataid,reason);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		}
		return "applyenvcheck";
	}
	
	@RequestMapping(value = "/applydatatable", method = RequestMethod.GET)
	public String getApplyDataTable() {
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Object[] userAuthory = user.getAuthorities().toArray();
//		String currentUserName = user.getUsername();
//		if( "ROLE_ADMIN".equals(userAuthory[0].toString()) )
		return "applytable_data_admin";
		
	}
	
	@RequestMapping(value = "/applyenvtable", method = RequestMethod.GET)
	public String getApplyEnvTable() {
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Object[] userAuthory = user.getAuthorities().toArray();
//		String currentUserName = user.getUsername();
//		if( "ROLE_ADMIN".equals(userAuthory[0].toString()) )
		return "applytable_env_admin";
		
	}
	
	@RequestMapping(value = "/getdocdatabyapplyid", method = RequestMethod.GET)
	@ResponseBody
	public HcApplydata getDocDataByApplyID(@RequestParam(value = "applyid", required = false) String applyid){
		HcApplydata docData = this.oracleService.getDocByApplyDataID(applyid);
		return docData;
	}
	
	@RequestMapping(value = "/getdocenvbyapplyid", method = RequestMethod.GET)
	@ResponseBody
	public HcApplyenv getDocEnvByApplyID(@RequestParam(value = "applyid", required = false) String applyid){
		HcApplyenv docEnv = this.oracleService.getDocEnvByApplyDataID(applyid);
		return docEnv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getdocenv_admin", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDocEnv_admin(@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "applyData_id", required = false) String applyData_id,
			@RequestParam(value = "applyData_userName", required = false) String applyData_userName,
			@RequestParam(value = "applyData_userDepartment", required = false) String applyData_userDepartment,
			@RequestParam(value = "applyData_projectName", required = false) String applyData_projectName,
			@RequestParam(value = "applyData_dataDemand", required = false) String applyData_dataDemand,
			@RequestParam(value = "product_created_from", required = false) String product_created_from,
			@RequestParam(value = "product_created_to", required = false) String product_created_to,
			@RequestParam(value = "product_status", required = false) String product_status,
			
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "draw", required = false) Integer draw){
		
		//check the authority
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Object[] userAuthory = user.getAuthorities().toArray();
//		String currentUserName = user.getUsername();
		
		List<HcApplyenv> docEnvList = new ArrayList<>();
		List<HcApplyenv> ALLEnvList = this.oracleService.getAllDocEnv();
		
		//处理过滤逻辑
		if(action!=null && action.equals("filter"))
			docEnvList = this.filterService.getFinalEnvList(ALLEnvList,applyData_id,applyData_userName,applyData_userDepartment
					,applyData_projectName,applyData_dataDemand,product_created_from,product_created_to,product_status);
		else
			docEnvList = ALLEnvList;
		
		// 分页
		int totalRecords = docEnvList.size();
		int displayLength = (length<0)? totalRecords : length;
		int displayStart = start;
		int end = displayStart + displayLength;
		end = end > totalRecords ? totalRecords : end;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ArrayList<String>> store = new ArrayList<ArrayList<String>>();
		
		for (int i=start; i<end; i++) {
			HcApplyenv docEnv = docEnvList.get(i);
			ArrayList<String> tempStore = new ArrayList<String>();
			tempStore.add("<input type='checkbox' name='id" + docEnv.getIdApplydata() + "' value='"
					+ docEnv.getIdApplydata() + "'>");
			tempStore.add(String.valueOf(docEnv.getIdApplydata()));
			tempStore.add(docEnv.getName());
			tempStore.add(docEnv.getDepartment());
			tempStore.add(docEnv.getProName());
			tempStore.add(docEnv.getDemand());
			tempStore.add(docEnv.getApplyTime());
			
			String applyStatus = getApplyStatus(docEnv.getFlagApplydata());
			tempStore.add(applyStatus);
			
			String docID = docEnv.getDocName();
			tempStore.add("<a href=\"/healthcare/applyenv/wordonline?docid="+docID+"\" id=\""+docEnv.getIdApplydata()+"\" target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-search\"></i> word预览</a>"
					+"<a href=\"/healthcare/adminpanel/applyenvcheck?applydataid="+docEnv.getIdApplydata()+"\" target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-lock\"></i> 数据审核</a>");
			store.add(tempStore);
		}
		
		resultMap.put("draw", draw);
		resultMap.put("recordsTotal", totalRecords);
		resultMap.put("recordsFiltered", totalRecords);
		resultMap.put("data", store);
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getdocdata_admin", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDocData_admin(@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "applyData_id", required = false) String applyData_id,
			@RequestParam(value = "applyData_userName", required = false) String applyData_userName,
			@RequestParam(value = "applyData_userDepartment", required = false) String applyData_userDepartment,
			@RequestParam(value = "applyData_projectName", required = false) String applyData_projectName,
			@RequestParam(value = "applyData_dataDemand", required = false) String applyData_dataDemand,
			@RequestParam(value = "product_created_from", required = false) String product_created_from,
			@RequestParam(value = "product_created_to", required = false) String product_created_to,
			@RequestParam(value = "product_status", required = false) String product_status,
			
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "draw", required = false) Integer draw){
		
		//check the authority
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Object[] userAuthory = user.getAuthorities().toArray();
//		String currentUserName = user.getUsername();
		
		List<HcApplydata> docDataList = new ArrayList<>();
		List<HcApplydata> ALLDataList = this.oracleService.getAllDocData();
		
		//处理过滤逻辑
		if(action!=null && action.equals("filter"))
			docDataList = this.filterService.getFinalDataList(ALLDataList,applyData_id,applyData_userName,applyData_userDepartment
					,applyData_projectName,applyData_dataDemand,product_created_from,product_created_to,product_status);
		else
			docDataList = ALLDataList;
			
		// 分页
		int totalRecords = docDataList.size();
		int displayLength = (length<0)? totalRecords : length;
		int displayStart = start;
		int end = displayStart + displayLength;
		end = end > totalRecords ? totalRecords : end;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ArrayList<String>> store = new ArrayList<ArrayList<String>>();
		
		for (int i=start; i<end; i++) {
			HcApplydata docData = docDataList.get(i);
			ArrayList<String> tempStore = new ArrayList<String>();
			tempStore.add("<input type='checkbox' name='id" + docData.getIdApplydata() + "' value='"
					+ docData.getIdApplydata() + "'>");
			tempStore.add(String.valueOf(docData.getIdApplydata()));
			tempStore.add(docData.getName());
			tempStore.add(docData.getDepartment());
			tempStore.add(docData.getProName());
			tempStore.add(docData.getDemand());
			tempStore.add(docData.getApplyTime());
			
			String applyStatus = getApplyStatus(docData.getFlagApplydata());
			tempStore.add(applyStatus);
			
			String docID = docData.getDocName();
			tempStore.add("<a href=\"/healthcare/applydata/wordonline?docid="+docID+"\" id=\""+docData.getIdApplydata()+"\" target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-search\"></i> word预览</a>"
					+"<a href=\"/healthcare/adminpanel/applydatacheck?applydataid="+docData.getIdApplydata()+"\" target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-lock\"></i> 数据审核</a>");
			store.add(tempStore);
		}
		
		resultMap.put("draw", draw);
		resultMap.put("recordsTotal", totalRecords);
		resultMap.put("recordsFiltered", totalRecords);
		resultMap.put("data", store);
		return resultMap;
	}

	private String getApplyStatus(String flag_Apply) {
		// TODO Auto-generated method stub
		String status = "<span class=\"label label-sm label-warning\">出错了</span>";
		switch (flag_Apply) {
		case "1":
			status = "<span class=\"label label-sm label-primary\">待审核</span>";
			break;
		case "2":
			status= "<span class=\"label label-sm label-default\">审核中</span>";
			break;
		case "3":
			status= "<span class=\"label label-sm label-success\">审核通过</span>";
			break;
		case "4":
			status= "<span class=\"label label-sm label-danger\">审核未通过</span>";
			break;
		default:
			System.out.println("申请标志位"+flag_Apply);
			break;
		}
		return status;
	}

	@RequestMapping(value = "/startwordOnlinetools", method = RequestMethod.GET)
	public void startWordOnlineTools(HttpServletRequest req, HttpServletResponse resp) {
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext(); 
			ServletContext servletContext = webApplicationContext.getServletContext(); 
			String batPath = servletContext.getRealPath("/WEB-INF/spring/startWordonLineTools.bat");
			Runtime r = Runtime.getRuntime();
			try {
				Process p = r.exec(batPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("startwordOnlinetools success!");
			try {
				PrintWriter out = resp.getWriter();
				resp.setContentType("text/html;charset=UTF-8");
				out.println("在线预览插件----启动成功！");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}
