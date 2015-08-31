package otc.healthcare.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import otc.healthcare.pojo.DatabaseInfo;
import otc.healthcare.pojo.FieldInfo;
import otc.healthcare.pojo.TableInfo;
import otc.healthcare.pojo.TreeJson;
import otc.healthcare.service.OracleService;

/**
 * @author xingkong
 */
@Controller
@RequestMapping("/dataresource")
public class MetadataController {

	@Autowired
	private OracleService oracleSerive;

	@RequestMapping(value = "/getdatabasetreeinfo", method = RequestMethod.GET)
	@ResponseBody
	public List<TreeJson> getDatabaseTreeInfo(@RequestParam(value = "parent", required = true) String parent) {
		System.out.println(parent);
		List<TreeJson> returnList = new ArrayList<TreeJson>();
		if (parent.equals("#")) {
			TreeJson tm = new TreeJson();
			tm.setId("all_" + 1);
			tm.setText("所有数据库");
			tm.setChildren(true);
			tm.setIcon("fa fa-folder icon-lg icon-state-success");
			tm.setType("root");
			returnList.add(tm);
		}
		if (parent.indexOf("all_") != -1) {
			List<DatabaseInfo> list = this.oracleSerive.getALLDatabaseInfo();
			for (int i = 0; i < list.size(); i++) {
				TreeJson tm = new TreeJson();
				tm.setId("alldatabase_" + list.get(i).getDatabaseid());
				tm.setText(list.get(i).getName());
				tm.setChildren(true);
				tm.setIcon("fa fa-folder icon-lg icon-state-success");
				tm.setType("root");
				returnList.add(tm);
			}
		}
		if (parent.indexOf("alldatabase_") != -1) {
			String databaseid = parent.substring(parent.indexOf("_") + 1);
			List<TableInfo> list = this.oracleSerive.getDatabaseInfo(databaseid);
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

	@RequestMapping(value = "/createhcDB")
	public String createHealthCareDB() {
		this.oracleSerive.createHcDB();
		return "redirect:/";
	}

	@RequestMapping(value = "/nodeoperation", method = RequestMethod.GET)
	@ResponseBody
	public String databaseOperation(@RequestParam(value = "operation", required = true) String operation,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "parent", required = false) String parent,
			@RequestParam(value = "position", required = false) String position,
			@RequestParam(value = "text", required = false) String text,
			@RequestParam(value = "comments", required = false) String comments) {
		String operationResult = "";
		String operationType = id != null
				? (id.contains("alldatabase") ? "database" : (id.contains("table") ? "table" : "all")) : "";// detect
																											// operation
																											// type
		String operationId = id != null ? (id.substring(id.indexOf("_") + 1)) : "";
		switch (operation) {
		case "delete_node": {
			operationResult = (operationType.equals("database") ? this.oracleSerive.deleteDatabase(operationId)
					: this.oracleSerive.deleteTable(parent.substring(parent.indexOf("_") + 1), operationId)) ? "success"
							: "fail";
		}
			break;
		case "create_node": {
			operationResult = parent.indexOf("all_") != -1
					? "alldatabase_" + this.oracleSerive.createDatabase(text, comments == null ? "备注为空" : comments)
					: "alltable_" + this.oracleSerive.createTable(parent.substring(parent.indexOf("_") + 1), text,
							comments == null ? "备注为空" : comments);
		}
			break;
		case "rename_node": {
			if (operationType.equals("database")) {
				this.oracleSerive.changeDatabase(operationId, text, null);
				operationResult = "success";
			}
			if (operationType.equals("table")) {
				this.oracleSerive.changeTable(parent.substring(parent.indexOf("_") + 1), operationId, text, comments);
				operationResult = "success";
			}
			if (operationType.equals("all")) {
				operationResult = "fail";
			}
			/*
			 * operationResult = (operationType.equals("database") ?
			 * this.oracleSerive.changeDatabase(operationId, text, null) :
			 * this.oracleSerive.changeTable(parent.substring(parent.indexOf(
			 * "_") + 1), operationId, text, comments)) ? "success" : "fail";
			 */
		}
			break;
		default:
			operationResult = "sucess";
		}
		return operationResult;
	}

	@RequestMapping(value = "/fieldoperation", method = RequestMethod.GET)
	@ResponseBody
	public String fieldOperation(@RequestParam(value = "operation", required = true) String operation,
			@RequestParam(value = "databaseid", required = false) String databaseid,
			@RequestParam(value = "tableid", required = false) String tableid,
			@RequestParam(value = "fieldid", required = false) String fieldid,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "comments", required = false) String comments) {
		String operationResult = "";
		switch (operation) {
		case "delete": {
			this.oracleSerive.deleteField(databaseid, tableid, fieldid);
			operationResult = "success";
		}
			break;
		case "create": {
			// operationResult = "alltable_"
			// +
			// this.oracleSerive.createTable(parent.substring(parent.indexOf("_")
			// + 1), text, comments==null?"备注为空":comments);
			this.oracleSerive.createField(databaseid, tableid, name, comments);
			operationResult = "success";
		}
			break;
		case "rename": {
			this.oracleSerive.changeField(fieldid, databaseid, tableid, name, comments);
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
	public List<DatabaseInfo> getAllDatabaseInfo() {
		System.out.println("get_all_database_info_list");
		List<DatabaseInfo> list = this.oracleSerive.getALLDatabaseInfo();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(
					list.get(i).getDatabaseid() + "::" + list.get(i).getName() + "::" + list.get(i).getComments());
		}
		return list;
	}
	@RequestMapping(value = "/getdatabasesummary", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> getDatabaseSummary(@RequestParam(value = "databaseid", required = true) String databaseid) {
		System.out.println("get_database_info_summary");
		Map<String,String> resultMap=this.oracleSerive.getDatabaseSummary(databaseid);
		resultMap.put("length",this.oracleSerive.getDatabaseInfo(databaseid).size()+"");
		return resultMap;
	}
	@RequestMapping(value = "/getdatabaseinfo", method = RequestMethod.GET)
	@ResponseBody
	public List<TableInfo> getDatabaseInfo(@RequestParam(value = "databaseid", required = true) String databaseid) {
		System.out.println("get_database_info_list");
		List<TableInfo> list = this.oracleSerive.getDatabaseInfo(databaseid);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getTableid() + "::" + list.get(i).getDatabaseid() + "::"
					+ list.get(i).getName() + "::" + list.get(i).getComments());
		}
		return list;
	}
	@RequestMapping(value = "/getdatabasecssinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDatabaseCssInfo(@RequestParam(value = "databaseid", required = false) String databaseid,
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "draw", required = false) Integer draw) {
		System.out.println("get_database_css_info_list");
		List<TableInfo> list = null;
		if (databaseid == null) {
			//list = this.oracleSerive.getAllTableInfo();
			list=new ArrayList<TableInfo>();
		} else {
			list =  this.oracleSerive.getDatabaseInfo(databaseid);
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
					+ tableInfo.getDatabaseid() + "'>");
			tempStore.add(tableInfo.getDatabaseid());
			tempStore.add(tableInfo.getName());
			tempStore.add(tableInfo.getComments());
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
	public Map<String,String> getTableSummary(@RequestParam(value = "databaseid", required = true) String databaseid,
			@RequestParam(value = "tableid", required = true) String tableid) {
		System.out.println("get_table_info_summary");
		Map<String,String> resultMap=this.oracleSerive.getTableSummary(databaseid, tableid);
		resultMap.put("length", this.oracleSerive.getTableInfo(databaseid, tableid).size()+"");
		return resultMap;
	}
	@RequestMapping(value = "/gettableinfo", method = RequestMethod.GET)
	@ResponseBody
	public List<FieldInfo> getTableInfo(@RequestParam(value = "databaseid", required = true) String databaseid,
			@RequestParam(value = "tableid", required = true) String tableid) {
		System.out.println("get_table_info_list");
		List<FieldInfo> list = this.oracleSerive.getTableInfo(databaseid, tableid);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getFieldid() + "::" + list.get(i).getTableid() + "::"
					+ list.get(i).getDatabaseid() + "::" + list.get(i).getName() + "::" + list.get(i).getComments());
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
			//list = this.oracleSerive.getAllTableInfo();
			list=new ArrayList<FieldInfo>();
		} else {
			list = this.oracleSerive.getTableInfo(databaseid, tableid);
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
			tempStore.add(fieldInfo.getFieldid());
			tempStore.add(fieldInfo.getName());
			tempStore.add(fieldInfo.getComments());
			tempStore.add("0");
			tempStore.add("100");
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
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		for (int i = 0; i < files.size(); ++i) {
			MultipartFile file = files.get(i);
			String name = file.getName();
			if (!file.isEmpty()) {
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				System.out.println("用户：" + user);
				System.out.println("文件长度: " + file.getSize());
				System.out.println("文件类型: " + file.getContentType());
				System.out.println("文件名称: " + file.getName());
				System.out.println("文件原名: " + file.getOriginalFilename());
				System.out.println("========================================");
				// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
				File exitfile = new File(realPath + "/"+ user.getUsername() );
				// 如果文件夹不存在则创建
				if (!exitfile.exists() && !exitfile.isDirectory()) {
					System.out.println("目录 "+realPath + "/"+ user.getUsername()+" 不存在");
					exitfile.mkdir();
				} else {
					System.out.println("目录 "+realPath + "/"+ user.getUsername()+" 存在");
				}
				// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
				try {
					FileUtils.copyInputStreamToFile(file.getInputStream(),
							new File(realPath + "/"+ user.getUsername(), file.getOriginalFilename()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "You failed to upload " + name + " because internal error.";
				}
			} else {
				return "You failed to upload " + name + " because the file was empty.";
			}
		}
		return "upload successful";
	}

	public OracleService getOracleSerive() {
		return oracleSerive;
	}

	public void setOracleSerive(OracleService oracleSerive) {
		this.oracleSerive = oracleSerive;
	}
}