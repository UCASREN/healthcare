package otc.healthcare.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import otc.healthcare.pojo.ClassificationInfo;
import otc.healthcare.pojo.DatabaseInfo;
import otc.healthcare.pojo.FieldInfo;
import otc.healthcare.pojo.TableInfo;
import otc.healthcare.pojo.TreeJson;
import otc.healthcare.service.MySQLServiceMetaData;
import otc.healthcare.util.ExcelUtil;

/**
 * @author xingkong
 */
@Controller
@RequestMapping("/dataresource")
public class MetadataController {

	@Autowired
	private MySQLServiceMetaData mySQLServiceMetaData;

	@RequestMapping(value = "/getdatabasetreeinfo", method = RequestMethod.GET)
	@ResponseBody
	public List<TreeJson> getDatabaseTreeInfo(@RequestParam(value = "parent", required = true) String parent) {
		System.out.println(parent);
		List<TreeJson> returnList = new ArrayList<TreeJson>();
		if (parent.equals("#")) {
			TreeJson tm = new TreeJson();
			tm.setId("all_" + 1);
			tm.setText("所有类别");
			tm.setChildren(true);
			tm.setIcon("fa fa-folder icon-lg icon-state-success");
			tm.setType("root");
			returnList.add(tm);
		}
		if (parent.indexOf("all_") != -1) {
			List<ClassificationInfo> list = this.getMySQLServiceMetaData().getAllClassificationDatabaseInfoWithClass();
			for (int i = 0; i < list.size(); i++) {
				TreeJson tm = new TreeJson();
				tm.setId("classification_" + list.get(i).getClassificationid());
				tm.setText(list.get(i).getName());
				tm.setChildren(true);
				tm.setIcon("fa fa-folder icon-lg icon-state-success");
				tm.setType("root");
				returnList.add(tm);
			}
		}
		if (parent.indexOf("classification_") != -1) {
			List<DatabaseInfo> list = this.getMySQLServiceMetaData()
					.getDatabaseInfoWithClass(parent.substring(parent.indexOf("_") + 1));
			for (int i = 0; i < list.size(); i++) {
				TreeJson tm = new TreeJson();
				tm.setId("alldatabase_" + list.get(i).getDatabaseid());
				tm.setText(list.get(i).getName());
				tm.setChildren(true);
				tm.setIcon("fa fa-folder icon-lg icon-state-success");
				tm.setType("default");
				returnList.add(tm);
			}
		}
		if (parent.indexOf("alldatabase_") != -1) {
			String databaseid = parent.substring(parent.indexOf("_") + 1);
			List<TableInfo> list = this.getMySQLServiceMetaData().getDatabaseInfo(databaseid);
			for (int i = 0; i < list.size(); i++) {
				TreeJson tm = new TreeJson();
				tm.setId("alltable_" + list.get(i).getTableid());
				tm.setText(list.get(i).getName());
				tm.setChildren(false);
				tm.setIcon("fa fa-folder icon-lg icon-state-danger");
				tm.setType("default");
				returnList.add(tm);
			}
		}
		return returnList;
	}

	// @RequestMapping(value = "/createhcDB")
	// public String createHealthCareDB() {
	// this.getMySQLServiceMetaData().createHcDB();
	// return "redirect:/";
	// }
	@RequestMapping(value = "/testremoteconnect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> TestRemoteConnect(@RequestParam(value = "url", required = true) String url,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {
		Map<String, String> result = new HashMap<String, String>();
		if (this.getMySQLServiceMetaData().testConnection(url, username, password)) {
			result.put("result", "数据库可用");
		} else
			result.put("result", "数据库不可用");
		return result;
	}

	@RequestMapping(value = "/getremotedatabasetreeinfo", method = RequestMethod.GET)
	@ResponseBody
	public List<TreeJson> getRemoteDatabaseTreeInfo(@RequestParam(value = "parent", required = true) String parent,
			@RequestParam(value = "url", required = false) String url,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password) {
		// System.out.println(parent);
		List<TreeJson> returnList = new ArrayList<TreeJson>();
		if (parent.equals("#")) {
			TreeJson tm = new TreeJson();
			tm.setId("all_" + 1);
			tm.setText("所有类别");
			tm.setChildren(true);
			tm.setIcon("fa fa-folder icon-lg icon-state-success");
			tm.setType("root");
			returnList.add(tm);
		}
		if (parent.indexOf("all_") != -1) {
			List<String> list = this.getMySQLServiceMetaData().getDataBaseList(url, username, password);
			for (int i = 0; i < list.size(); i++) {
				TreeJson tm = new TreeJson();
				tm.setId("alldatabase_" + list.get(i));
				tm.setText(list.get(i));
				tm.setChildren(true);
				tm.setIcon("fa fa-folder icon-lg icon-state-success");
				tm.setType("root");
				returnList.add(tm);
			}
		}
		if (parent.indexOf("alldatabase_") != -1) {
			String databaseid = parent.substring(parent.indexOf("_") + 1);
			List<TableInfo> list = this.getMySQLServiceMetaData().getTargetTableMap(url, username, password,
					databaseid);
			for (int i = 0; i < list.size(); i++) {
				TreeJson tm = new TreeJson();
				tm.setId(databaseid + ";" + list.get(i).getName());// 防止数据名字中有下划线，所以换成了";"
				tm.setText(list.get(i).getName());
				tm.setChildren(false);
				tm.setIcon("fa fa-folder icon-lg icon-state-danger");
				tm.setType("default");
				returnList.add(tm);
			}
		}
		return returnList;
	}

	@RequestMapping(value = "/addremotedatabase", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addRemoteDatabase(@RequestParam(value = "url", required = false) String url,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password, String selectedtables) {
		// System.out.println("传过来的数据"+selectedtables);
		// this.getMySQLServiceMetaData().insertRemoteDB(url,username,password,selectedtables);
		return null;
	}

	@RequestMapping(value = "/nodeoperation", method = RequestMethod.GET)
	@ResponseBody
	public String databaseNodeOperation(@RequestParam(value = "operation", required = true) String operation,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "parent", required = false) String parent,
			@RequestParam(value = "position", required = false) String position,
			@RequestParam(value = "text", required = false) String text,
			@RequestParam(value = "zhcnname", required = false) String zhcnname,
			@RequestParam(value = "comments", required = false) String comments,
			@RequestParam(value = "numrows", required = false) String numrows) {
		String operationResult = "";
		String operationType = id != null ? (id.contains("classification") ? "classification"
				: (id.contains("alldatabase") ? "database" : (id.contains("table") ? "table" : "all"))) : "";// detect
		// operation
		// type
		String operationId = id != null ? (id.substring(id.indexOf("_") + 1)) : "";
		switch (operation) {
		case "delete_node": {
			if (operationType.equals("database")) {
				operationResult = this.getMySQLServiceMetaData().deleteDatabase(operationId) ? "success" : "fail";
			}
			if (operationType.equals("table")) {
				operationResult = this.getMySQLServiceMetaData().deleteTable(parent.substring(parent.indexOf("_") + 1),
						operationId) ? "success" : "fail";
			}
			if (operationType.equals("classification")) {
				operationResult = this.getMySQLServiceMetaData().deleteClassification(operationId) ? "success"
						: "类别下有数据库，请清空后再删除";
			}
			// operationResult = (operationType.equals("database") ?
			// this.mySQLServiceMetaData.deleteDatabase(operationId)
			// :
			// this.mySQLServiceMetaData.deleteTable(parent.substring(parent.indexOf("_")
			// + 1), operationId)) ? "success"
			// : "fail";
		}
			break;
		case "create_node": {
			if (parent.indexOf("all_") != -1) {
				operationResult = "classification_" + this.getMySQLServiceMetaData().createClassification(text,
						comments == null ? "备注为空" : comments);
			}
			if (parent.indexOf("classification_") != -1) {
				operationResult = "alldatabase_" + this.getMySQLServiceMetaData().createDatabase(parent.substring(15),
						text, zhcnname, comments == null ? "备注为空" : comments);
			}
			if (parent.indexOf("alldatabase_") != -1) {
				operationResult = "alltable_"
						+ this.getMySQLServiceMetaData().createTable(parent.substring(parent.indexOf("_") + 1), text,
								comments == null ? "备注为空" : comments, numrows == null ? "0" : numrows);
			}
			// operationResult = parent.indexOf("all_") != -1
			// ? "alldatabase_" +
			// this.mySQLServiceMetaData.createDatabase(text,zhcnname, comments
			// == null ? "备注为空" : comments)
			// : "alltable_" +
			// this.mySQLServiceMetaData.createTable(parent.substring(parent.indexOf("_")
			// + 1), text,
			// comments == null ? "备注为空" : comments,numrows==null?"0":numrows);
		}
			break;
		case "rename_node": {
			if (operationType.equals("classification")) {
				this.getMySQLServiceMetaData().changeClassification(operationId, text, comments);
				operationResult = "success";
			}
			if (operationType.equals("database")) {
				this.getMySQLServiceMetaData().changeDatabase(operationId, text, comments, null);
				operationResult = "success";
			}
			if (operationType.equals("table")) {
				this.getMySQLServiceMetaData().changeTable(parent.substring(parent.indexOf("_") + 1), operationId, text,
						zhcnname, comments, numrows);
				operationResult = "success";
			}
			if (operationType.equals("all")) {
				this.getMySQLServiceMetaData().changeTable(parent.substring(parent.indexOf("_") + 1), operationId, text,
						zhcnname, comments, numrows);
				operationResult = "success";
			}
		}
			break;
		case "move_node": {
			this.getMySQLServiceMetaData().changeDatabase(operationId, null, null,
					parent.substring(parent.indexOf("_") + 1));
			operationResult = "success";
		}
		default:
			operationResult = "success";
		}
		return operationResult;
	}

	@RequestMapping(value = "/databaseupdate", method = RequestMethod.POST)
	@ResponseBody
	public boolean databaseUpdate(@ModelAttribute DatabaseInfo databaseinfo) {
		// System.out.println("前台传过来的"+databaseinfo);
		this.getMySQLServiceMetaData().changeDatabase(databaseinfo);
		return true;
	}

	@RequestMapping(value = "/updateclassificationdetail", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateclassificationdetail(@ModelAttribute ClassificationInfo classificationinfo) {
		this.getMySQLServiceMetaData().changeClassification(classificationinfo);
		return true;
	}

	@RequestMapping(value = "/getdatabaseInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> databaseInfo(@RequestParam(value = "databaseid", required = true) String databaseid) {
		return this.getMySQLServiceMetaData().getDatabaseSummary(databaseid);
	}

	@RequestMapping(value = "/getclassificationdetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getClassificationDetail(
			@RequestParam(value = "classificationid", required = true) String classificationid) {
		return this.getMySQLServiceMetaData().getClassificationSummary(classificationid);
	}

	@RequestMapping(value = "/fieldoperation", method = RequestMethod.GET)
	@ResponseBody
	public String fieldOperation(@RequestParam(value = "operation", required = true) String operation,
			@RequestParam(value = "databaseid", required = false) String databaseid,
			@RequestParam(value = "tableid", required = false) String tableid,
			@RequestParam(value = "fieldid", required = false) String fieldid,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "zhcnname", required = false) String zhcnname,
			@RequestParam(value = "comments", required = false) String comments,
			@RequestParam(value = "datadictionary", required = false) String datadictionary) {
		String operationResult = "";
		switch (operation) {
		case "delete": {
			this.getMySQLServiceMetaData().deleteField(databaseid, tableid, fieldid);
			operationResult = "success";
		}
			break;
		case "create": {
			// operationResult = "alltable_"
			// +
			// this.mySQLServiceMetaData.createTable(parent.substring(parent.indexOf("_")
			// + 1), text, comments==null?"备注为空":comments);
			this.getMySQLServiceMetaData().createField(databaseid, tableid, name, zhcnname, comments, datadictionary);
			operationResult = "success";
		}
			break;
		case "rename": {
			this.getMySQLServiceMetaData().changeField(fieldid, databaseid, tableid, name, zhcnname, comments,
					datadictionary);
			operationResult = "success";
		}
			break;
		default:
			operationResult = "fail";
		}
		return operationResult;
	}

	@RequestMapping(value = "/getalldatabaseinfo", method = RequestMethod.GET)
	@ResponseBody
	public List<DatabaseInfo> getAllDatabaseInfo(
			@RequestParam(value = "operation", required = false) String operation) {
		// System.out.println("get_all_database_info_list");
		List<DatabaseInfo> list = this.getMySQLServiceMetaData().getALLDatabaseInfo();
		for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i).getDatabaseid() + "::" +
			// list.get(i).getName() + "::" + list.get(i).getComments());
			if (operation != null && operation.equals("all"))
				list.get(i).setTablelist(this.getMySQLServiceMetaData().getDatabaseInfo(list.get(i).getDatabaseid()));
		}
		return list;
	}

	@RequestMapping(value = "/getalldatabaseinfowithclass", method = RequestMethod.GET)
	@ResponseBody
	public List<ClassificationInfo> getAllDatabaseInfoWithClass(
			@RequestParam(value = "operation", required = false) String operation) {
		// System.out.println("get_all_database_info_list_with_class");
		List<ClassificationInfo> list = this.getMySQLServiceMetaData().getAllClassificationDatabaseInfoWithClass();
		for (ClassificationInfo classification : list) {
			List<DatabaseInfo> databaseInfoList = classification.getDatabaseinfolist();
			for (int i = 0; i < databaseInfoList.size(); i++) {
				if (operation != null && operation.equals("all"))
					databaseInfoList.get(i).setTablelist(
							this.getMySQLServiceMetaData().getDatabaseInfo(databaseInfoList.get(i).getDatabaseid()));
			}
		}
		return list;
	}

	@RequestMapping(value = "/getdatabasesummary", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getDatabaseSummary(
			@RequestParam(value = "databaseid", required = true) String databaseid) {
		// System.out.println("get_database_info_summary");
		Map<String, String> resultMap = this.getMySQLServiceMetaData().getDatabaseSummary(databaseid);
		resultMap.put("length", this.getMySQLServiceMetaData().getDatabaseInfo(databaseid).size() + "");
		return resultMap;
	}

	@RequestMapping(value = "/getdatabaseinfo", method = RequestMethod.GET)
	@ResponseBody
	public List<TableInfo> getDatabaseInfo(@RequestParam(value = "databaseid", required = true) String databaseid) {
		// System.out.println("get_database_info_list");
		List<TableInfo> list = this.getMySQLServiceMetaData().getDatabaseInfo(databaseid);
		for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i).getTableid() + "::" +
			// list.get(i).getDatabaseid() + "::"
			// + list.get(i).getName() + "::" + list.get(i).getComments());
		}
		return list;
	}

	@RequestMapping(value = "/getdatabasecssinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDatabaseCssInfo(
			@RequestParam(value = "databaseid", required = false) String databaseid,
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "draw", required = false) Integer draw, HttpSession httpSession) {
		System.out.println("get_database_css_info_list");
		List<TableInfo> list = null;
		if (databaseid == null) {
			// list = this.mySQLServiceMetaData.getAllTableInfo();
			list = new ArrayList<TableInfo>();
		} else {
			list = this.getMySQLServiceMetaData().getDatabaseInfo(databaseid);
		}
		// 分页
		int totalRecords = list.size();
		int displayLength = length < 0 ? totalRecords : length;
		int displayStart = start;
		int end = displayStart + displayLength;
		end = end > totalRecords ? totalRecords : end;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ArrayList<String>> store = new ArrayList<ArrayList<String>>();
		for (int i = start; i < end; i++) {
			TableInfo tableInfo = list.get(i);
			ArrayList<String> tempStore = new ArrayList<String>();
			tempStore.add("<input type='checkbox' name='id" + tableInfo.getTableid() + "' value='"
					+ tableInfo.getDatabaseid() + "_" + tableInfo.getTableid() + "' "
					+ (getCheckedState(httpSession, tableInfo.getDatabaseid(), tableInfo.getTableid()) ? "checked" : "")
					+ ">");
			tempStore.add((i + 1) + "");
			tempStore.add(tableInfo.getTableid());
			tempStore.add(tableInfo.getName());
			tempStore.add(tableInfo.getZhcnname());
			tempStore.add(tableInfo.getComments());
			store.add(tempStore);
		}
		resultMap.put("draw", draw);
		resultMap.put("recordsTotal", totalRecords);
		resultMap.put("recordsFiltered", totalRecords);
		resultMap.put("data", store);
		return resultMap;
	}

	private boolean getCheckedState(HttpSession httpSession, String database, String table) {
		if (httpSession.getAttribute("shoppingcart") != null) {
			Map<String, HashSet<String>> shoppingcartMap = (Map<String, HashSet<String>>) httpSession
					.getAttribute("shoppingcart");
			if (shoppingcartMap.containsKey(database) && shoppingcartMap.get(database).contains(table))
				return true;
		}
		return false;
	}

	@RequestMapping(value = "/getalldatabasecssinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllDatabaseCssInfo(@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "draw", required = false) Integer draw) {
		// System.out.println("get_all_database_css_info_list");
		List<DatabaseInfo> list = this.getMySQLServiceMetaData().getALLDatabaseInfo();
		// for (int i = 0; i < list.size(); i++) {
		// System.out.println(list.get(i).getDatabaseid() + "::" +
		// list.get(i).getName() + "::" + list.get(i).getComments());
		// list.get(i).setTablelist(this.mySQLServiceMetaData.getDatabaseInfo(list.get(i).getDatabaseid()));
		// }

		// 分页
		int totalRecords = list.size();
		int displayLength = length < 0 ? totalRecords : length;
		int displayStart = start;
		int end = displayStart + displayLength;
		end = end > totalRecords ? totalRecords : end;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ArrayList<String>> store = new ArrayList<ArrayList<String>>();
		for (int i = start; i < end; i++) {
			DatabaseInfo databaseInfo = list.get(i);
			ArrayList<String> tempStore = new ArrayList<String>();
			tempStore.add((i + 1) + "");// 0
			tempStore.add(databaseInfo.getDatabaseid());// 1
			tempStore.add(databaseInfo.getName());// 2
			tempStore.add(databaseInfo.getZhcnname());// 3
			tempStore.add(databaseInfo.getComments());// 4
			tempStore.add(databaseInfo.getIdentifier());// 5
			tempStore.add(databaseInfo.getLanguage());// 6
			tempStore.add(databaseInfo.getCharset());// 7
			tempStore.add(databaseInfo.getSubjectclassification());// 8
			tempStore.add(databaseInfo.getKeywords());// 9
			tempStore.add(databaseInfo.getCredibility());// 10
			tempStore.add(databaseInfo.getResinstitution());// 11
			tempStore.add(databaseInfo.getResname());// 12
			tempStore.add(databaseInfo.getResaddress());// 13
			tempStore.add(databaseInfo.getRespostalcode());// 14
			tempStore.add(databaseInfo.getResphone());// 15
			tempStore.add(databaseInfo.getResemail());// 16
			tempStore.add(databaseInfo.getResourceurl());// 17
			tempStore.add("<a class='checkmore' href=''>查看详情</a>");// 18
			store.add(tempStore);
		}
		resultMap.put("draw", draw);
		resultMap.put("recordsTotal", totalRecords);
		resultMap.put("recordsFiltered", totalRecords);
		resultMap.put("data", store);
		return resultMap;
	}

	@RequestMapping(value = "/gettablesummary", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getTableSummary(@RequestParam(value = "databaseid", required = true) String databaseid,
			@RequestParam(value = "tableid", required = true) String tableid) {
		// System.out.println("get_table_info_summary");
		Map<String, String> resultMap = this.getMySQLServiceMetaData().getTableSummary(databaseid, tableid);
		resultMap.put("length", this.getMySQLServiceMetaData().getTableInfo(databaseid, tableid).size() + "");
		return resultMap;
	}

	@RequestMapping(value = "/gettableinfo", method = RequestMethod.GET)
	@ResponseBody
	public List<FieldInfo> getTableInfo(@RequestParam(value = "databaseid", required = true) String databaseid,
			@RequestParam(value = "tableid", required = true) String tableid) {
		// System.out.println("get_table_info_list");
		List<FieldInfo> list = this.getMySQLServiceMetaData().getTableInfo(databaseid, tableid);
		for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i).getFieldid() + "::" +
			// list.get(i).getTableid() + "::"
			// + list.get(i).getDatabaseid() + "::" + list.get(i).getName() +
			// "::" + list.get(i).getComments());
		}
		return list;
	}

	@RequestMapping(value = "/gettablecssinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTableCssInfo(@RequestParam(value = "databaseid", required = false) String databaseid,
			@RequestParam(value = "tableid", required = false) String tableid,
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "draw", required = false) Integer draw) {
		System.out.println("get_table_info_list");
		List<FieldInfo> list = null;
		if (databaseid == null || tableid == null) {
			// list = this.mySQLServiceMetaData.getAllTableInfo();
			list = new ArrayList<FieldInfo>();
		} else {
			list = this.getMySQLServiceMetaData().getTableInfo(databaseid, tableid);
		}
		// 分页
		int totalRecords = list.size();
		int displayLength = length < 0 ? totalRecords : length;
		int displayStart = start;
		int end = displayStart + displayLength;
		end = end > totalRecords ? totalRecords : end;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ArrayList<String>> store = new ArrayList<ArrayList<String>>();
		for (int i = start; i < end; i++) {
			FieldInfo fieldInfo = list.get(i);
			ArrayList<String> tempStore = new ArrayList<String>();
			tempStore.add("<input type='checkbox' name='id" + fieldInfo.getFieldid() + "' value='"
					+ fieldInfo.getFieldid() + "'>");
			tempStore.add((i + 1) + "");
			tempStore.add(fieldInfo.getFieldid());
			tempStore.add(fieldInfo.getName());
			tempStore.add(fieldInfo.getZhcnname());
			tempStore.add(fieldInfo.getComments());
			tempStore.add(fieldInfo.getDatadictionary());
			store.add(tempStore);
		}
		resultMap.put("draw", draw);
		resultMap.put("recordsTotal", totalRecords);
		resultMap.put("recordsFiltered", totalRecords);
		resultMap.put("data", store);
		return resultMap;
	}

	@RequestMapping(value = "/batchupload", method = RequestMethod.POST)
	@ResponseBody
	public String handleFileUpload(HttpServletRequest request) {
		System.out.println("database:" + request.getParameter("database"));
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		for (int i = 0; i < files.size(); ++i) {
			MultipartFile file = files.get(i);
			String name = file.getName();
			if (!file.isEmpty()) {
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				System.out.println(user.getAuthorities());
				System.out.println("用户：" + user);
				System.out.println("文件长度: " + file.getSize());
				System.out.println("文件类型: " + file.getContentType());
				System.out.println("文件名称: " + file.getName());
				System.out.println("文件原名: " + file.getOriginalFilename());
				System.out.println("========================================");
				String realPath = request.getSession().getServletContext()
						.getRealPath(File.separator + "WEB-INF" + File.separator + "upload");
				File exitfile = new File(realPath + File.separator + user.getUsername());
				// 如果文件夹不存在则创建
				if (!exitfile.exists() && !exitfile.isDirectory()) {
					System.out.println("目录 " + realPath + File.separator + user.getUsername() + " 不存在");
					exitfile.mkdir();
				} else {
					System.out.println("目录 " + realPath + File.separator + user.getUsername() + " 存在");
				}
				// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
				try {
					FileUtils.copyInputStreamToFile(file.getInputStream(),
							new File(realPath + File.separator + user.getUsername(), file.getOriginalFilename()));
					// 进行新建操作
					this.getMySQLServiceMetaData()
							.insertTableToDatabase(request.getParameter("database"), ExcelUtil.read(
									file.getInputStream(),
									file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1)));
				} catch (IOException e) {
					e.printStackTrace();
					return "You failed to upload " + name + " because internal error.";
				}
			} else {
				return "You failed to upload " + name + " because the file was empty.";
			}
		}
		return "upload successful";
	}

	/**
	 * @return the mySQLServiceMetaData
	 */
	public MySQLServiceMetaData getMySQLServiceMetaData() {
		return mySQLServiceMetaData;
	}

	/**
	 * @param mySQLServiceMetaData
	 *            the mySQLServiceMetaData to set
	 */
	public void setMySQLServiceMetaData(MySQLServiceMetaData mySQLServiceMetaData) {
		this.mySQLServiceMetaData = mySQLServiceMetaData;
	}

}