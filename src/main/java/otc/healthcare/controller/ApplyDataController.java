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
import otc.healthcare.service.MySQLServiceApply;
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
	MySQLServiceApply mySQLServiceApply;

	public MySQLServiceApply getMySQLServiceApply() {
		return mySQLServiceApply;
	}

	public void setMySQLServiceApply(MySQLServiceApply mySQLServiceApply) {
		this.mySQLServiceApply = mySQLServiceApply;
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
		System.out.println("applydataController--> " + docid + " : " + applydataid);
		return "applydata";
	}

	@RequestMapping(value = "/applytable", method = RequestMethod.GET)
	public String getApplyTable() {
		// User user = (User)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// Object[] userAuthory = user.getAuthorities().toArray();
		// String currentUserName = user.getUsername();
		//
		// if( "ROLE_ADMIN".equals(userAuthory[0].toString()) )
		// return "applytable_admin";
		// else if( "ROLE_USER".equals((String)userAuthory[0].toString()) )
		return "applytable_data_user";

	}

	@RequestMapping(value = "/deleteapplydata", method = RequestMethod.GET)
	@ResponseBody
	public List<String> deleteApplyData(@RequestParam(value = "id[]", required = false) String[] applydataid,
			@RequestParam(value = "deleteType", required = true) String deleteType) {
		List<String> rs = new ArrayList<>();
		if (applydataid == null || applydataid.length == 0) {
			rs.add(DELETE_APPLYDATA_ERROR);
			return rs;
		}
		boolean word_flag = this.WordService.deleteDoc(applydataid, deleteType);
		boolean sql_flag = this.mySQLServiceApply.deleteApplyData(applydataid);
		if (sql_flag && word_flag) {
			rs.add(DELETE_APPLYDATA_SUCCESS);
		} else {
			rs.add(DELETE_APPLYDATA_ERROR);
		}
		return rs;
	}

	@RequestMapping(value = "/createdataword", method = RequestMethod.POST)
	public String createDataApplyWordFromFtl(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setCharacterEncoding("UTF-8");
			String docid = req.getParameter("docid");
			if (docid == null || docid.equals("")) {
				String docPath = hcConfiguration.getProperty(HealthcareConfiguration.HC_DOCPATH);
				String f_name = UUID.randomUUID() + ".doc";
				String f_path_name = docPath + "/" + f_name;
				this.mySQLServiceApply.insertApplyData(req, f_name, false);
				this.WordService.createWordFromFtl(req, resp, f_path_name, "data");
			} else {// 编辑
				String docPath = hcConfiguration.getProperty(HealthcareConfiguration.HC_DOCPATH);
				String f_path_name = docPath + "/" + docid;
				this.mySQLServiceApply.insertApplyData(req, docid, true);
				this.WordService.createWordFromFtl(req, resp, f_path_name, "data");
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:/applydata/applytable";
	}

	@RequestMapping(value = "/getdocdatabydocid", method = RequestMethod.GET)
	@ResponseBody
	public HcApplydata getDocDataByDocId(@RequestParam(value = "docid", required = false) String docid) {
		HcApplydata docData = this.mySQLServiceApply.getDocBydocID(docid);
		return docData;
	}

	@RequestMapping(value = "/getdocdatabyapplyid", method = RequestMethod.GET)
	@ResponseBody
	public HcApplydata getDocDataByApplyID(@RequestParam(value = "applyid", required = false) String applyid) {
		HcApplydata docData = this.mySQLServiceApply.getDataDocByApplyDataID(applyid);
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
			@RequestParam(value = "draw", required = false) Integer draw) {

		String applyData_userDepartment = "";
		// check the authority
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUserName = user.getUsername();

		List<HcApplydata> docDataList = new ArrayList<>();
		List<HcApplydata> userDataList = this.mySQLServiceApply.getDocByHcUserName(currentUserName);

		// 处理过滤逻辑
		if (action != null && action.equals("filter"))
			docDataList = this.filterService.getFinalDataList(userDataList, applyData_id, applyData_userName,
					applyData_userDepartment, applyData_projectName, applyData_dataDemand, product_created_from,
					product_created_to, product_status);
		else
			docDataList = userDataList;

		// 分页
		int totalRecords = docDataList.size();
		int displayLength = (length < 0) ? totalRecords : length;
		int displayStart = start;
		int end = displayStart + displayLength;
		end = end > totalRecords ? totalRecords : end;

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ArrayList<String>> store = new ArrayList<ArrayList<String>>();

		for (int i = start; i < end; i++) {
			HcApplydata docData = docDataList.get(i);
			ArrayList<String> tempStore = new ArrayList<String>();
			tempStore.add("<input type='checkbox' name='id" + docData.getIdApplydata() + "' value='"
					+ docData.getIdApplydata() + "'>");
			tempStore.add(String.valueOf(docData.getIdApplydata()));
			tempStore.add(docData.getName());
			tempStore.add(docData.getProName());
			tempStore.add(docData.getDemand());
			tempStore.add(docData.getApplyTime());

			String applyStatus = getDataApplyStatus(docData.getFlagApplydata(),
					String.valueOf(docData.getIdApplydata()), docData.getProName(), docData.getName());
			tempStore.add(applyStatus);

			// 处理按钮--根据statuts添加按钮
			String dataStatusFlag = docData.getFlagApplydata();
			String docID = docData.getDocName();

			String blank = "&nbsp;&nbsp;&nbsp;";
			String wordPreview = "<a href=\"/healthcare/applydata/wordonline?docid=" + docID + "\" id=\""
					+ docData.getIdApplydata() + "\" "
					+ "target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-search\"></i> word预览</a>";

			String EditApply = "<a href=\"/healthcare/applydata/applydata?docid=" + docID + "&applydataid="
					+ String.valueOf(docData.getIdApplydata()) + "\" "
					+ "class=\"btn btn-xs default btn-editable\"><i class=\"fa fa-pencil\"></i> 编辑申请</a>";

			String button = wordPreview + blank;
			if (dataStatusFlag.equals("1"))
				button += EditApply;

			tempStore.add(button);
			store.add(tempStore);
		}

		resultMap.put("draw", draw);
		resultMap.put("recordsTotal", totalRecords);
		resultMap.put("recordsFiltered", totalRecords);
		resultMap.put("data", store);
		return resultMap;
	}

	private String getDataApplyStatus(String flag_Apply, String ApplyID, String proName, String userName) {
		String status = "<button id=\"a" + ApplyID + "\" class=\"btn btn-xs btn-default motalButton\">出错了</button>";
		switch (flag_Apply) {
		case "1":
			status = "<button id=\"a" + ApplyID
					+ "\" title=\"点击查看申请进度\" class=\"btn btn-xs btn-primary motalButton\">待审核</button>";
			break;
		case "2":// 卒中中心---审核ok
			status = "<button id=\"a" + ApplyID
					+ "\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-info motalButton\">审核中</button>";
			break;
		case "3":// 卒中办公室---审核ok
			status = "<button id=\"a" + ApplyID
					+ "\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-info motalButton\">数据分配中</button>";
			break;
		case "4":// 管理员---数据集发布ok---审核通过
			HcApplydata hcApplydata = this.mySQLServiceApply.getDataDocByApplyDataID(ApplyID);
			String filePath = hcApplydata.getApplyDataDir();
			if (filePath == null || filePath.equals("")) {
				status = "<button id=\"a" + ApplyID
						+ "\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-success motalButton\">审核通过</button>";
				break;
			}
			status = "<button id=\"a" + ApplyID
					+ "\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-success motalButton\">审核通过</button>"
					+ "&nbsp;<button id=\"a1" + ApplyID
					+ "\"  title=\"点击下载数据资源\" onclick=\"window.location.href='/healthcare/applymanager/downLoadApplyData?ApplyID="
					+ ApplyID + "'\" class" + "=\"btn btn-xs btn-success data-download\">下载</button>";
			break;
		case "5":// 审核失败
			status = "<button id=\"a" + ApplyID
					+ "\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-danger motalButton\">审核失败</button>";
			break;
		default:
			System.out.println("申请标志位" + flag_Apply);
			break;
		}
		return status;
	}

	@RequestMapping(value = "/wordonline", method = RequestMethod.GET)
	public String showDocWordOnline(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam(value = "docid", required = false) String docid) {
		try {
			req.setCharacterEncoding("UTF-8");
			this.WordService.docConvert(req, docid);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "documentView";
	}

	@RequestMapping(value = "/firstwordonline", method = RequestMethod.POST)
	@ResponseBody
	public String firstShowDocWordOnline(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setCharacterEncoding("UTF-8");
			String docPath = hcConfiguration.getProperty(HealthcareConfiguration.HC_DOCPATH);
			String f_name = UUID.randomUUID() + ".doc";
			String f_path_name = docPath + "/" + f_name;
			this.WordService.createWordFromFtl(req, resp, f_path_name, "data");

			this.WordService.docConvert(req, f_name);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "firstwordonline_success";
	}

	@RequestMapping(value = "/first_documentView", method = RequestMethod.GET)
	public String showDocWordOnline(HttpServletRequest req, HttpServletResponse resp) {
		return "documentView";
	}
}
