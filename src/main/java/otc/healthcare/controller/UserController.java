package otc.healthcare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import otc.healthcare.service.MySQLServiceApply;
import otc.healthcare.service.MySQLServiceMetaData;

/**
 * @author xingkong
 */
@Controller
public class UserController {
	@Autowired
	private MySQLServiceMetaData mySQLServiceMetaData;
	@Autowired
	private MySQLServiceApply mySQLServiceApply;
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/updateshoppingcart")
	@ResponseBody
	public Map<String, HashSet<String>> updateShoppingCart(
			@RequestParam(value = "addinfolist", required = true) String addInfolist,
			@RequestParam(value = "deleteinfolist", required = true) String deleteInfolist, HttpSession httpSession) {
		deleteShoppingCart(deleteInfolist, httpSession);
		addToShoppingCart(addInfolist, httpSession);
		Map<String, HashSet<String>> shoppingcartMap = (Map<String, HashSet<String>>) httpSession
				.getAttribute("shoppingcart");
		return shoppingcartMap;
	}

	@RequestMapping("/addtoshoppingcart")
	@ResponseBody
	public Map<String, HashSet<String>> addToShoppingCart(
			@RequestParam(value = "infolist", required = true) String infolist, HttpSession httpSession) {
		if (httpSession.getAttribute("shoppingcart") == null)
			httpSession.setAttribute("shoppingcart", new HashMap<String, HashSet<String>>());
		Map<String, HashSet<String>> shoppingcartMap = (Map<String, HashSet<String>>) httpSession
				.getAttribute("shoppingcart");
		String[] infoArray = infolist.split(",");
		for (String info : infoArray) {
			if (info != null && !info.equals("")) {
				String database = info.split("_")[0];
				String table = info.split("_")[1];
				if (shoppingcartMap.get(database) == null)
					shoppingcartMap.put(database, new HashSet<String>());
				shoppingcartMap.get(database).add(table);
			}
		}
		return shoppingcartMap;
	}

	@RequestMapping("/getshoppingcart")
	@ResponseBody
	public Map<String, HashSet<String>> getShoppingCart(HttpSession httpSession) {
		if (httpSession.getAttribute("shoppingcart") == null)
			httpSession.setAttribute("shoppingcart", new HashMap<String, HashSet<String>>());
		Map<String, HashSet<String>> shoppingcartMap = (Map<String, HashSet<String>>) httpSession
				.getAttribute("shoppingcart");
		return shoppingcartMap;
	}

	@RequestMapping("/getshoppingcartdetail")
	@ResponseBody
	public Map<String, ArrayList<String>> getShoppingCartDetail(HttpSession httpSession) {
		Map<String, HashSet<String>> shoppingcartMap = getShoppingCart(httpSession);
		Map<String, ArrayList<String>> shoppingcartDetail = new HashMap<String, ArrayList<String>>();
		for (String databaseid : shoppingcartMap.keySet()) {
			ArrayList<String> tableinfoList = new ArrayList<String>();
			for (String tableid : shoppingcartMap.get(databaseid)) {
				tableinfoList.add(this.mySQLServiceMetaData.getTableSummary(databaseid, tableid).get("name"));
			}
			shoppingcartDetail.put(this.mySQLServiceMetaData.getDatabaseSummary(databaseid).get("name"), tableinfoList);
		}
		return shoppingcartDetail;
	}

	@RequestMapping("/getshoppingcartAlldetail")
	@ResponseBody
	public Map<String, ArrayList<String>> getShoppingCartAllDetail(HttpSession httpSession) {
		Map<String, HashSet<String>> shoppingcartMap = getShoppingCart(httpSession);
		Map<String, ArrayList<String>> AllShoppingcartDetail = new HashMap<String, ArrayList<String>>();

		for (String databaseid : shoppingcartMap.keySet()) {
			ArrayList<String> tableinfoList = new ArrayList<String>();
			String dbName = this.mySQLServiceMetaData.getDatabaseSummary(databaseid).get("name");
			for (String tableid : shoppingcartMap.get(databaseid)) {
				tableinfoList.add(databaseid + "_" + dbName + "_" + tableid + "_"
						+ this.mySQLServiceMetaData.getTableSummary(databaseid, tableid).get("name") + "_"
						+ this.mySQLServiceMetaData.getTableSummary(databaseid, tableid).get("comments"));
			}
			AllShoppingcartDetail.put(databaseid + "_" + dbName, tableinfoList);
		}
		return AllShoppingcartDetail;
	}

	@RequestMapping(value = "/putshopdetailIntoSessionfromdb", method = RequestMethod.GET)
	@ResponseBody
	public boolean putShopDetailIntoSessionfromdb(HttpSession httpSession,
			@RequestParam(value = "docid", required = false) String docid,
			@RequestParam(value = "applyType", required = false) String applyType) {

		this.deleteAllShoppingCart(httpSession);
		Map<String, HashSet<String>> shoppingcartMap = getShoppingCart(httpSession);

		String shopInfo = new String();
		if (applyType.equals("data"))
			shopInfo = this.mySQLServiceApply.getApplyDataByDocId(docid);
		else if (applyType.equals("env"))
			shopInfo = this.mySQLServiceApply.getApplyDataByEnvDocId(docid);

		if (shopInfo == null || shopInfo.equals(""))
			return false;

		String[] shops = shopInfo.split(",");
		for (int i = 1; i < shops.length; i++) {
			String db_id = shops[i].split("_")[0];
			String t_id = shops[i].split("_")[2];
			if (shoppingcartMap.containsKey(db_id)) {
				shoppingcartMap.get(db_id).add(t_id);
			} else {
				HashSet<String> set = new HashSet<>();
				set.add(t_id);
				shoppingcartMap.put(db_id, set);
			}
		}
		httpSession.setAttribute("shoppingcart", shoppingcartMap);
		return true;
	}

	@RequestMapping("/getcssshoppingcartdetail")
	@ResponseBody
	public Map<String, ArrayList<String>> getCssShoppingCartDetail(HttpSession httpSession) {
		Map<String, HashSet<String>> shoppingcartMap = getShoppingCart(httpSession);
		Map<String, ArrayList<String>> shoppingcartDetail = new HashMap<String, ArrayList<String>>();
		for (String databaseid : shoppingcartMap.keySet()) {
			ArrayList<String> tableinfoList = new ArrayList<String>();
			for (String tableid : shoppingcartMap.get(databaseid)) {
				tableinfoList.add(databaseid + "_" + tableid + "_"
						+ this.mySQLServiceMetaData.getTableSummary(databaseid, tableid).get("name"));
			}
			shoppingcartDetail.put(databaseid + "_" + this.mySQLServiceMetaData.getDatabaseSummary(databaseid).get("name"),
					tableinfoList);
		}
		return shoppingcartDetail;
	}

	@RequestMapping("/deleteallshoppingcart")
	@ResponseBody
	public String deleteAllShoppingCart(HttpSession httpSession) {
		httpSession.removeAttribute("shoppingcart");
		return "deleteSuccess--njz";
	}

	@RequestMapping("/deleteshoppingcart")
	@ResponseBody
	public Map<String, HashSet<String>> deleteShoppingCart(
			@RequestParam(value = "infolist", required = true) String infolist, HttpSession httpSession) {
		if (httpSession.getAttribute("shoppingcart") == null)
			httpSession.setAttribute("shoppingcart", new HashMap<String, HashSet<String>>());
		Map<String, HashSet<String>> shoppingcartMap = (Map<String, HashSet<String>>) httpSession
				.getAttribute("shoppingcart");
		String[] infoArray = infolist.split(",");
		for (String info : infoArray) {
			if (info != null && !info.equals("")) {
				String database = info.split("_")[0];
				String table = info.split("_")[1];
				if (shoppingcartMap.containsKey(database) && shoppingcartMap.get(database).contains(table))
					shoppingcartMap.get(database).remove(table);
				if (shoppingcartMap.containsKey(database) && shoppingcartMap.get(database).size() == 0)
					shoppingcartMap.remove(database);
			}
		}
		return shoppingcartMap;
	}

	@RequestMapping("/deletedatabaseshoppingcart")
	@ResponseBody
	public Map<String, HashSet<String>> deleteDatabaseShoppingCart(
			@RequestParam(value = "database", required = true) String database, HttpSession httpSession) {
		if (httpSession.getAttribute("shoppingcart") == null)
			httpSession.setAttribute("shoppingcart", new HashMap<String, HashSet<String>>());
		Map<String, HashSet<String>> shoppingcartMap = (Map<String, HashSet<String>>) httpSession
				.getAttribute("shoppingcart");
		if (shoppingcartMap.containsKey(database))
			shoppingcartMap.remove(database);
		return shoppingcartMap;
	}

	@RequestMapping("/deletedatabasetableshoppingcart")
	@ResponseBody
	public Map<String, HashSet<String>> deleteDatabaseShoppingCart(
			@RequestParam(value = "database", required = true) String database,
			@RequestParam(value = "table", required = true) String table, HttpSession httpSession) {
		if (httpSession.getAttribute("shoppingcart") == null)
			httpSession.setAttribute("shoppingcart", new HashMap<String, HashSet<String>>());
		Map<String, HashSet<String>> shoppingcartMap = (Map<String, HashSet<String>>) httpSession
				.getAttribute("shoppingcart");
		if (shoppingcartMap.containsKey(database) && shoppingcartMap.get(database).contains(table))
			shoppingcartMap.get(database).remove(table);
		if (shoppingcartMap.containsKey(database) && shoppingcartMap.get(database).size() == 0)
			shoppingcartMap.remove(database);
		return shoppingcartMap;
	}

	/**
	 * @return the mySQLServiceMetaData
	 */
	public MySQLServiceMetaData getMySQLServiceMetaData() {
		return mySQLServiceMetaData;
	}

	/**
	 * @param mySQLServiceMetaData the mySQLServiceMetaData to set
	 */
	public void setMySQLServiceMetaData(MySQLServiceMetaData mySQLServiceMetaData) {
		this.mySQLServiceMetaData = mySQLServiceMetaData;
	}


	/**
	 * @return the mySQLServiceApply
	 */
	public MySQLServiceApply getMySQLServiceApply() {
		return mySQLServiceApply;
	}

	/**
	 * @param mySQLServiceApply the mySQLServiceApply to set
	 */
	public void setMySQLServiceApply(MySQLServiceApply mySQLServiceApply) {
		this.mySQLServiceApply = mySQLServiceApply;
	}


}