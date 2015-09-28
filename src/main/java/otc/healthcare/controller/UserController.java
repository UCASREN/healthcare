package otc.healthcare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xingkong
 */
@Controller
public class UserController {
	
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
			}
		}
		return shoppingcartMap;
	}
}