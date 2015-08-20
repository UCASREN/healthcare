package otc.healthcare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		} else {
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
	public boolean createHealthCareDB() {
		return this.oracleSerive.createHcDB();
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
		String operationType = id != null ? (id.contains("alldatabase") ? "database" : "table") : "";// detect  operation type
		String operationId = id != null ? (id.substring(id.indexOf("_") + 1)) : "";
		switch (operation) {
		case "delete_node": {
			operationResult = (operationType.equals("database") ? this.oracleSerive.deleteDatabase(operationId)
					: this.oracleSerive.deleteTable(parent.substring(parent.indexOf("_") + 1), operationId)) ? "success"
							: "fail";
		}
			break;
		case "create_node": {
			operationResult = "alltable_"
					+ this.oracleSerive.createTable(parent.substring(parent.indexOf("_") + 1), text, comments==null?"备注为空":comments);
		}
			break;
		case "rename_node": {
			operationResult = (operationType.equals("database")
					? this.oracleSerive.changeDatabase(operationId, text, null)
					: this.oracleSerive.changeTable(parent.substring(parent.indexOf("_") + 1), operationId, text, comments))
							? "success" : "fail";
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
		String operationResult="";
		switch (operation) {
		case "delete": {
			this.oracleSerive.deleteField(databaseid, tableid, fieldid);
			operationResult="success";
		}
			break;
		case "create": {
//			operationResult = "alltable_"
//					+ this.oracleSerive.createTable(parent.substring(parent.indexOf("_") + 1), text, comments==null?"备注为空":comments);
			this.oracleSerive.createField(databaseid, tableid, name, comments);
			operationResult="success";
		}
			break;
		case "rename": {
			this.oracleSerive.changeField(fieldid, databaseid, tableid, name, comments);
			operationResult="success";
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
	@RequestMapping(value = "/gettableinfo", method = RequestMethod.GET)
	@ResponseBody
	public List<FieldInfo> getTableInfo(@RequestParam(value = "databaseid", required = true) String databaseid,@RequestParam(value = "tableid", required = true) String tableid) {
		System.out.println("get_table_info_list");
		List<FieldInfo> list = this.oracleSerive.getTableInfo(databaseid,tableid);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getFieldid() + "::" + list.get(i).getTableid() + "::" + list.get(i).getDatabaseid() + "::"
					+ list.get(i).getName() + "::" + list.get(i).getComments());
		}
		return list;
	}
	public OracleService getOracleSerive() {
		return oracleSerive;
	}

	public void setOracleSerive(OracleService oracleSerive) {
		this.oracleSerive = oracleSerive;
	}
}