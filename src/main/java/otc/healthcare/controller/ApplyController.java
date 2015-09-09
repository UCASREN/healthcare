package otc.healthcare.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	OracleService oracleService;
	
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
//			this.oracleService.insertApplyData(req, f_name);
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
	
	public WordService getWordService() {
		return WordService;
	}

	public void setWordService(WordService wordService) {
		WordService = wordService;
	}
	
}
