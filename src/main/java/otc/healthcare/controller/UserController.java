package otc.healthcare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import otc.healthcare.pojo.TableInfo;
import otc.healthcare.service.OracleService;

/**
 * @author xingkong
 */
@Controller
public class UserController {
	@Autowired
	private OracleService oracleSerive;
	@RequestMapping("/login")
	public String login() {
		return "home";
	}
	@RequestMapping("/updateshoppingcart")
	@ResponseBody
	public Map<String,HashSet<String>> updateShoppingCart(@RequestParam(value = "addinfolist", required = true) String addInfolist,
			@RequestParam(value = "deleteinfolist", required = true) String deleteInfolist,HttpSession httpSession){
//		httpSession.removeAttribute("shoppingcart");
//		if(httpSession.getAttribute("shoppingcart")==null) httpSession.setAttribute("shoppingcart", new HashMap<String,HashSet<String>>());
//		Map<String,HashSet<String>> shoppingcartMap=(Map<String, HashSet<String>>) httpSession.getAttribute("shoppingcart");
//		String[] infoArray=infolist.split(",");
//		for(String info:infoArray){
//			String database=info.split("_")[0];
//			String table=info.split("_")[1];
//			if(shoppingcartMap.get(database)==null) shoppingcartMap.put(database, new HashSet<String>());
//			shoppingcartMap.get(database).add(table);
//		}
		deleteShoppingCart(deleteInfolist,httpSession);
		addToShoppingCart(addInfolist,httpSession);
		Map<String,HashSet<String>> shoppingcartMap=(Map<String, HashSet<String>>) httpSession.getAttribute("shoppingcart");
		return shoppingcartMap;
	}
	@RequestMapping("/addtoshoppingcart")
	@ResponseBody
	public Map<String,HashSet<String>> addToShoppingCart(@RequestParam(value = "infolist", required = true) String infolist,HttpSession httpSession){
		if(httpSession.getAttribute("shoppingcart")==null) httpSession.setAttribute("shoppingcart", new HashMap<String,HashSet<String>>());
		Map<String,HashSet<String>> shoppingcartMap=(Map<String, HashSet<String>>) httpSession.getAttribute("shoppingcart");
		String[] infoArray=infolist.split(",");
		for(String info:infoArray){
			if(info!=null&&!info.equals("")){
				String database=info.split("_")[0];
				String table=info.split("_")[1];
				if(shoppingcartMap.get(database)==null) shoppingcartMap.put(database, new HashSet<String>());
				shoppingcartMap.get(database).add(table);
			}
		}
		return shoppingcartMap;
	}
	@RequestMapping("/getshoppingcart")
	@ResponseBody
	public Map<String,HashSet<String>> getShoppingCart(HttpSession httpSession){
		if(httpSession.getAttribute("shoppingcart")==null) httpSession.setAttribute("shoppingcart", new HashMap<String,HashSet<String>>());
		Map<String,HashSet<String>> shoppingcartMap=(Map<String, HashSet<String>>) httpSession.getAttribute("shoppingcart");
		return shoppingcartMap;
	}
	@RequestMapping("/getshoppingcartdetail")
	@ResponseBody
	public Map<String,ArrayList<String>> getShoppingCartDetail(HttpSession httpSession){
		Map<String,HashSet<String>> shoppingcartMap=getShoppingCart(httpSession);
		Map<String,ArrayList<String>> shoppingcartDetail=new HashMap<String,ArrayList<String>>();
		for(String databaseid:shoppingcartMap.keySet()){
			ArrayList<String> tableinfoList=new ArrayList<String>();
			for(String tableid:shoppingcartMap.get(databaseid)){
				tableinfoList.add(this.oracleSerive.getTableSummary(databaseid, tableid).get("name"));
			}
			shoppingcartDetail.put(this.oracleSerive.getDatabaseSummary(databaseid).get("name"), tableinfoList);
		}
		return shoppingcartDetail;
	}
	@RequestMapping("/getcssshoppingcartdetail")
	@ResponseBody
	public Map<String,ArrayList<String>> getCssShoppingCartDetail(HttpSession httpSession){
		Map<String,HashSet<String>> shoppingcartMap=getShoppingCart(httpSession);
		Map<String,ArrayList<String>> shoppingcartDetail=new HashMap<String,ArrayList<String>>();
		for(String databaseid:shoppingcartMap.keySet()){
			ArrayList<String> tableinfoList=new ArrayList<String>();
			for(String tableid:shoppingcartMap.get(databaseid)){
				tableinfoList.add(databaseid+"_"+tableid+"_"+this.oracleSerive.getTableSummary(databaseid, tableid).get("name"));
			}
			shoppingcartDetail.put(databaseid+"_"+this.oracleSerive.getDatabaseSummary(databaseid).get("name"), tableinfoList);
		}
		return shoppingcartDetail;
	}
	@RequestMapping("/deleteshoppingcart")
	@ResponseBody
	public Map<String,HashSet<String>> deleteShoppingCart(@RequestParam(value = "infolist", required = true) String infolist,HttpSession httpSession){
		if(httpSession.getAttribute("shoppingcart")==null) httpSession.setAttribute("shoppingcart", new HashMap<String,HashSet<String>>());
		Map<String,HashSet<String>> shoppingcartMap=(Map<String, HashSet<String>>) httpSession.getAttribute("shoppingcart");
		String[] infoArray=infolist.split(",");
		for(String info:infoArray){
			if(info!=null&&!info.equals("")){
				String database=info.split("_")[0];
				String table=info.split("_")[1];
				if(shoppingcartMap.containsKey(database)&&shoppingcartMap.get(database).contains(table)) 
						shoppingcartMap.get(database).remove(table);
				if(shoppingcartMap.containsKey(database)&&shoppingcartMap.get(database).size()==0) shoppingcartMap.remove(database);
			}
		}
		return shoppingcartMap;
	}
	@RequestMapping("/deletedatabaseshoppingcart")
	@ResponseBody
	public Map<String,HashSet<String>> deleteDatabaseShoppingCart(@RequestParam(value = "database", required = true) String database,HttpSession httpSession){
		if(httpSession.getAttribute("shoppingcart")==null) httpSession.setAttribute("shoppingcart", new HashMap<String,HashSet<String>>());
		Map<String,HashSet<String>> shoppingcartMap=(Map<String, HashSet<String>>) httpSession.getAttribute("shoppingcart");
		if(shoppingcartMap.containsKey(database)) shoppingcartMap.remove(database);
		return shoppingcartMap;
	}
	@RequestMapping("/deletedatabasetableshoppingcart")
	@ResponseBody
	public Map<String,HashSet<String>> deleteDatabaseShoppingCart(@RequestParam(value = "database", required = true) String database,
			@RequestParam(value = "table", required = true) String table,HttpSession httpSession){
		if(httpSession.getAttribute("shoppingcart")==null) httpSession.setAttribute("shoppingcart", new HashMap<String,HashSet<String>>());
		Map<String,HashSet<String>> shoppingcartMap=(Map<String, HashSet<String>>) httpSession.getAttribute("shoppingcart");
		if(shoppingcartMap.containsKey(database)&&shoppingcartMap.get(database).contains(table)) 
			shoppingcartMap.get(database).remove(table);
		if(shoppingcartMap.containsKey(database)&&shoppingcartMap.get(database).size()==0) 
			shoppingcartMap.remove(database);
		return shoppingcartMap;
	}
	/**
	 * @return the oracleSerive
	 */
	public OracleService getOracleSerive() {
		return oracleSerive;
	}
	/**
	 * @param oracleSerive the oracleSerive to set
	 */
	public void setOracleSerive(OracleService oracleSerive) {
		this.oracleSerive = oracleSerive;
	}
}