package otc.healthcare.controller;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import otc.healthcare.pojo.HcApplydata;
import otc.healthcare.service.OracleService;
import otc.healthcare.service.WordService;

/**
 * @author Andy
 *
 */
@Controller
@RequestMapping("/apply")
public class ApplyController {

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

	
	
	@RequestMapping(value = "/applydata", method = RequestMethod.GET)
	public String getDataResource() {
		return "applydata";
	}
	
	@RequestMapping(value = "/applytable", method = RequestMethod.GET)
	public String getApplyTable() {
		return "applytable";
	}
	
	@RequestMapping(value = "/createdataword", method = RequestMethod.POST)
	public String createDataApplyWordFromFtl(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req. setCharacterEncoding("UTF-8");
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext(); 
		    ServletContext servletContext = webApplicationContext.getServletContext(); 
		    String docPath = servletContext.getRealPath("/WEB-INF/hc_doc");
			String f_name = UUID.randomUUID() + ".doc";
			String f_path_name = docPath + "/" + f_name;
			
			HttpSession session = req.getSession();
			session.setAttribute("docName", f_name);
			this.oracleService.insertApplyData(req, f_name);
			this.WordService.createWordFromFtl(req, resp, f_path_name);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:/apply/applydata";
	}
	
	
	@RequestMapping(value = "/getdocdatabydocid", method = RequestMethod.GET)
	@ResponseBody
	public HcApplydata getDocDataByDocId(@RequestParam(value = "docid", required = false) String docid){
		HcApplydata docData = this.oracleService.getDocBydocID(docid);
		return docData;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getdocdatabyhcuser", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDocDataByHcUser(@RequestParam(value = "hcUser", required = false) String hcUser,
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "draw", required = false) Integer draw){
		
		List<HcApplydata> docDataList = new ArrayList<>();
		//check the authority
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Object[] userAuthory = user.getAuthorities().toArray();
		String currentUserName = user.getUsername();
	
		if( "ROLE_ADMIN".equals(userAuthory[0].toString()) ){
			if(hcUser.equals(""))
				docDataList = this.oracleService.getAllDoc();
			else
				docDataList = this.oracleService.getDocByHcUserName(hcUser);
		}else if( "ROLE_USER".equals((String)userAuthory[0].toString()) )
			docDataList = this.oracleService.getDocByHcUserName(currentUserName);
		
		// 分页
		int totalRecords = docDataList.size();
		int displayLength = length < 0 ? totalRecords : length;
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
			tempStore.add(docData.getProName());
			tempStore.add(docData.getDemand());
			tempStore.add(docData.getFlagApplydata());
			tempStore.add(docData.getApplyTime());
			store.add(tempStore);
		}
		
		resultMap.put("draw", draw);
		resultMap.put("recordsTotal", totalRecords);
		resultMap.put("recordsFiltered", totalRecords);
		resultMap.put("data", store);
		return resultMap;
	}
	
	
	@RequestMapping(value = "/wordonline", method = RequestMethod.GET)
	public String showDocWordOnline(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req. setCharacterEncoding("UTF-8");
			this.WordService.docConvert(req, resp);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "documentView";
	}
	
}
