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
@RequestMapping("/applydata")
public class ApplyDataController {

	private static final String DELETE_APPLYDATA_ERROR = "0";
	private static final String DELETE_APPLYDATA_SUCCESS = "1";
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
	
	
	@RequestMapping(value = "/applydata", method = RequestMethod.GET)
	public String getApplyData(@RequestParam(value = "docid", required = false) String docid,
			@RequestParam(value = "applydataid", required = false) String applydataid) {
		System.out.println("applydataController--> "+docid+" : "+applydataid);
		return "applydata";
	}
	
	@RequestMapping(value = "/applytable", method = RequestMethod.GET)
	public String getApplyTable() {
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Object[] userAuthory = user.getAuthorities().toArray();
//		String currentUserName = user.getUsername();
//	
//		if( "ROLE_ADMIN".equals(userAuthory[0].toString()) )
//			return "applytable_admin";
//		else if( "ROLE_USER".equals((String)userAuthory[0].toString()) )
		return "applytable_data_user";
		
	}
	
	@RequestMapping(value = "/deleteapplydata", method = RequestMethod.GET)
	@ResponseBody
	public List<String>  deleteApplyData(@RequestParam(value = "id[]", required = false) String[] applydataid) {
		List<String> rs = new ArrayList<>();
		if(applydataid == null || applydataid.length == 0){
			 rs.add(DELETE_APPLYDATA_ERROR);
			 return rs;
		}
		boolean word_flag = this.WordService.deleteDoc(applydataid);
		boolean sql_flag = this.oracleService.deleteApplyData(applydataid);
		if(sql_flag && word_flag){
			rs.add(DELETE_APPLYDATA_SUCCESS);
		}else{
			rs.add(DELETE_APPLYDATA_ERROR);
		}
		return rs;
	}
	
	@RequestMapping(value = "/createdataword", method = RequestMethod.POST)
	public String createDataApplyWordFromFtl(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req. setCharacterEncoding("UTF-8");
			String docid = req.getParameter("docid");
			if(docid==null || docid.equals("")){
				String docPath = hcConfiguration.getProperty(HealthcareConfiguration.HC_DOCPATH);
				String f_name = UUID.randomUUID() + ".doc";
				String f_path_name = docPath + "/" + f_name;
				this.oracleService.insertApplyData(req, f_name, false);
				this.WordService.createWordFromFtl(req, resp, f_path_name);
			}else{
				String docPath = hcConfiguration.getProperty(HealthcareConfiguration.HC_DOCPATH);
				String f_path_name = docPath + "/" + docid;
				this.oracleService.insertApplyData(req, docid, true);
				this.WordService.createWordFromFtl(req, resp, f_path_name);
			}
			
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:/applydata/applytable";
	}
	
	@RequestMapping(value = "/getdocdatabydocid", method = RequestMethod.GET)
	@ResponseBody
	public HcApplydata getDocDataByDocId(@RequestParam(value = "docid", required = false) String docid){
		HcApplydata docData = this.oracleService.getDocBydocID(docid);
		return docData;
	}
	
	@RequestMapping(value = "/getdocdatabyapplyid", method = RequestMethod.GET)
	@ResponseBody
	public HcApplydata getDocDataByApplyID(@RequestParam(value = "applyid", required = false) String applyid){
		HcApplydata docData = this.oracleService.getDocByApplyDataID(applyid);
		return docData;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getdocdata_user", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDocData_user(@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "applyData_id", required = false) String applyData_id,
			@RequestParam(value = "applyData_userName", required = false) String applyData_userName,
			@RequestParam(value = "applyData_projectName", required = false) String applyData_projectName,
			@RequestParam(value = "applyData_dataDemand", required = false) String applyData_dataDemand,
			@RequestParam(value = "product_created_from", required = false) String product_created_from,
			@RequestParam(value = "product_created_to", required = false) String product_created_to,
			@RequestParam(value = "product_status", required = false) String product_status,
			
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "draw", required = false) Integer draw){
		
		String applyData_userDepartment = "";
		//check the authority
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUserName = user.getUsername();
		
		List<HcApplydata> docDataList = new ArrayList<>();
		List<HcApplydata> userDataList = this.oracleService.getDocByHcUserName(currentUserName);
		
		//处理过滤逻辑
		if(action!=null && action.equals("filter"))
			docDataList = this.filterService.getFinalDataList(userDataList, applyData_id,applyData_userName,applyData_userDepartment
					,applyData_projectName,applyData_dataDemand,product_created_from,product_created_to,product_status);
		else
			docDataList = userDataList;
		
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
			tempStore.add(docData.getProName());
			tempStore.add(docData.getDemand());
			tempStore.add(docData.getApplyTime());
			
			String applyStatus = getApplyStatus(docData.getFlagApplydata(), String.valueOf(docData.getIdApplydata()), docData.getProName(),docData.getName());
			tempStore.add(applyStatus);
			
			String docID = docData.getDocName();
			tempStore.add("<a href=\"/healthcare/applydata/wordonline?docid="+docID+"\" id=\""+docData.getIdApplydata()+"\" target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-search\"></i> word预览</a>"
					+"&nbsp;&nbsp;&nbsp;"+ "<a href=\"/healthcare/applydata/applydata?docid="+docID+"&applydataid="+String.valueOf(docData.getIdApplydata())+"\" class=\"btn btn-xs default btn-editable\"><i class=\"fa fa-pencil\"></i> 编辑申请</a>");
			
			store.add(tempStore);
		}
		
		resultMap.put("draw", draw);
		resultMap.put("recordsTotal", totalRecords);
		resultMap.put("recordsFiltered", totalRecords);
		resultMap.put("data", store);
		return resultMap;
	}
	
	private String getApplyStatus(String flag_Apply, String ApplyID, String proName, String userName) {
		// TODO Auto-generated method stub
		String status = "<span id=\"a"+ApplyID+"\" class=\"label label-sm label-warning\">出错了</span>";
		switch (flag_Apply) {
		case "1":
			status = "<span id=\"a"+ApplyID+"\" class=\"label label-sm label-primary\">待审核</span>";
			break;
		case "2":
			status= "<span id=\"a"+ApplyID+"\" class=\"label label-sm label-default\">审核中</span>";
			break;
		case "3":
			status= "<span id=\"a"+ApplyID+"\" class=\"label label-sm label-success\">审核通过</span>";
			break;
		case "4":
			status= "<span id=\"a"+ApplyID+"\" class=\"label label-sm label-danger\">审核失败</span>";
			break;
		default:
			System.out.println("申请标志位"+flag_Apply);
			break;
		}
		return status;
	}

	
	@RequestMapping(value = "/wordonline", method = RequestMethod.GET)
	public String showDocWordOnline(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam(value = "docid", required = false) String docid) {
		try {
			req. setCharacterEncoding("UTF-8");
			this.WordService.docConvert(req, docid);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "documentView";
	}
	

}
