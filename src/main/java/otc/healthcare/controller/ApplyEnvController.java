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
import otc.healthcare.service.OracleService;
import otc.healthcare.service.WordService;
import otc.healthcare.util.HealthcareConfiguration;

/**
 * @author Andy
 *
 */
@Controller
@RequestMapping("/applyenv")
public class ApplyEnvController {

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

	
	@RequestMapping(value = "/applyenv", method = RequestMethod.GET)
	public String getApplyData(@RequestParam(value = "docid", required = false) String docid,
			@RequestParam(value = "applydataid", required = false) String applydataid) {
		System.out.println("applyenvController--> " + docid + " : " + applydataid);
		return "applyenv";
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
		return "applytable_env_user";
		
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
		boolean sql_flag = this.oracleService.deleteApplyEnv(applydataid);
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
			String docPath = hcConfiguration.getProperty(HealthcareConfiguration.HC_DOCPATH);
			String f_name = UUID.randomUUID() + ".doc";
			String f_path_name = docPath + "/" + f_name;
			this.oracleService.insertApplyEnv(req, f_name);
			this.WordService.createWordFromFtl(req, resp, f_path_name);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:/applyenv/applytable";
	}
	
	@RequestMapping(value = "/getdocdatabydocid", method = RequestMethod.GET)
	@ResponseBody
	public HcApplyenv getDocDataByDocId(@RequestParam(value = "docid", required = false) String docid){
		HcApplyenv docData = this.oracleService.getEnvDocBydocID(docid);
		return docData;
	}
	
	@RequestMapping(value = "/getdocdatabyapplyid", method = RequestMethod.GET)
	@ResponseBody
	public HcApplyenv getDocDataByApplyID(@RequestParam(value = "applyid", required = false) String applyid){
		HcApplyenv docData = this.oracleService.getDocEnvByApplyDataID(applyid);
		return docData;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getdocenv_user", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDocEnv_user(@RequestParam(value = "hcUser", required = false) String hcUser,
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "draw", required = false) Integer draw){
		
		//check the authority
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUserName = user.getUsername();
		
		List<HcApplyenv> docEnvDataList = new ArrayList<>();
		docEnvDataList = this.oracleService.getEnvDocByHcUserName(currentUserName);
		
		// 分页
		int totalRecords = docEnvDataList.size();
		int displayLength = (length<0)? totalRecords : length;
		int displayStart = start;
		int end = displayStart + displayLength;
		end = end > totalRecords ? totalRecords : end;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ArrayList<String>> store = new ArrayList<ArrayList<String>>();
		
		for (int i=start; i<end; i++) {
			HcApplyenv docEnvData = docEnvDataList.get(i);
			ArrayList<String> tempStore = new ArrayList<String>();
			tempStore.add("<input type='checkbox' name='id" + docEnvData.getIdApplydata() + "' value='"
					+ docEnvData.getIdApplydata() + "'>");
			tempStore.add(String.valueOf(docEnvData.getIdApplydata()));
			tempStore.add(docEnvData.getName());
			tempStore.add(docEnvData.getProName());
			tempStore.add(docEnvData.getDemand());
			tempStore.add(docEnvData.getApplyTime());
			
			String applyStatus = getApplyStatus(docEnvData.getFlagApplydata());
			tempStore.add(applyStatus);
			
			String docID = docEnvData.getDocName();
			tempStore.add("<a href=\"/healthcare/applyenv/wordonline?docid="+docID+"\" id=\""+docEnvData.getIdApplydata()+"\" target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-search\"></i> word预览</a>"
					+"&nbsp;&nbsp;&nbsp;"+ "<a href=\"/healthcare/applyenv/applyenv?docid="+docID+"&applydataid="+String.valueOf(docEnvData.getIdApplydata())+"\" class=\"btn btn-xs default btn-editable\"><i class=\"fa fa-pencil\"></i> 编辑申请</a>");
			
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
			status= "<span class=\"label label-sm label-danger\">审核失败</span>";
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
