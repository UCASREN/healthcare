 package otc.healthcare.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.beyondsphere.database.JDBCUtil;

import otc.healthcare.pojo.HcApplydata;
import otc.healthcare.pojo.HcApplyenv;
import otc.healthcare.pojo.VMUser;
import otc.healthcare.service.FilterService;
import otc.healthcare.service.OracleService;
import otc.healthcare.service.WordService;
import otc.healthcare.util.HealthcareConfiguration;

/**
 * @author Andy
 *
 */
@Controller
@RequestMapping("/adminpanel")
public class AdminController {

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

	@Autowired
	FilterService filterService;
	public FilterService getFilterService() {
		return filterService;
	}

	public void setFilterService(FilterService filterService) {
		this.filterService = filterService;
	}
	
	@RequestMapping(value = "/listVMService", method = RequestMethod.POST)
	public void listVMService(@RequestParam(value = "docid", required = false) String docid,
			@RequestParam(value = "applydataid", required = false) String applydataid,
			HttpServletRequest request, HttpServletResponse response) {
		
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		response.setContentType("text/html");
		try {
			PrintWriter out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//从云平台获得所有虚拟机id， name, 
		//再判断vmid是否在本地数据库表vmmanger中存在，如果存在表示已经分配。把虚拟机id， 名字，使用者显示到页面，不存在时只显示vmid， vmname
		JDBCUtil jdbc = new JDBCUtil();
		List<String> uuids = jdbc.getAllVMsByUserName("133.133.135.11","ditest");
		ArrayList<VMUser>  vmUserList = new ArrayList<VMUser>();  //存放已经分配的
		ArrayList<VMUser>  vmUserList2 = new ArrayList<VMUser>();  //存放已经分配和未分配的
//		String  vmNames = jdbc.getAllVMNameByID("133.133.135.11","ditest");
		  
		//查找id在表vmmanger中是否存在
		String sql = "select * from SYSTEM.HC_VMMANAGER";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
        	 Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
//             String url="jdbc:oracle:thin:@localhost:1522:orcl";
             String url="jdbc:oracle:thin:@localhost:1521:XE";
//             conn = DriverManager.getConnection(url,"scott","tiger");
             conn = DriverManager.getConnection(url,"system","123456");
             stmt = conn.createStatement();
	         rs = stmt.executeQuery(sql);
	         while(rs.next()){
            	 VMUser  vms = new VMUser();
            	 vms.vmid = rs.getString(1);
            	 vms.vmName =rs.getString(2);
            	 vms.vmUser = rs.getString(3);
            	 
            	 //将已经分配好的显示到页面，并保存好vmid
            	 vmUserList.add(vms); //用于和所有的vmid比较，筛选出未分配的
            	 vmUserList2.add(vms);
            }
	         
	        conn.close();
            stmt.close();
         } catch (SQLException e) {
	          e.printStackTrace();
         } catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    //先前一个也没分配过
       if ( vmUserList.size() ==0){
    	   //将vmid， vmName 显示到页面
    	   for(int i = 0 ; i < uuids.size(); i++){
    		   VMUser  vms2 = new VMUser();
    		   vms2.vmid = uuids.get(i);
    		   vms2.vmName = jdbc.getNameByUuid("133.133.135.11", uuids.get(i));
    		   vms2.vmUser = "";   //没分配过，使用者是“”
    		   vmUserList2.add(vms2);
    		   }
        }else{
        	//获得未分配的vmid, vmname, 显示到页面
        	for(int i = 0 ; i < uuids.size(); i++){
		    	 VMUser  vms3 = new VMUser();
	        	 String sid = uuids.get(i);
	        	 boolean bFlag = false; //未分配
	        	 for(int j = 0; j< vmUserList.size(); j++){
	        		 String idtmp = vmUserList.get(j).getVMid();
	        		 String str1 = sid.substring(0, 7);
	        		 String str2 = idtmp.substring(0, 7);
	        		 //说明已经分配过
	        		 if (str1.equals(str2)== true ){
	        			 bFlag = true;
	        			 break;	        
	        		 }
	        	 }
	        	 
	        	//未分配过	 
		        if (bFlag == false){
		        	//根据该id，向云平台获得名字
		        	vms3.vmid = sid;
		        	String sName = "";
		        	sName = jdbc.getNameByUuid("133.133.135.11", sid);
		        	vms3.vmName = sName;
		        	vms3.vmUser = "";
		        	vmUserList2.add(vms3);
		        }
		     }//end for
	      }//end else
	         
	       try {
	    	   //把变量vmUserList输出到页面
		       RequestDispatcher rd = request.getRequestDispatcher("applyenvalloc");
		       request.setAttribute("vmUserList", vmUserList2);
		       rd.forward(request,response);
	       } catch (ServletException e) {
	    	   e.printStackTrace();
	       } catch (IOException e) {
	    	   e.printStackTrace();
	       }
	}
	
	@RequestMapping(value = "/saveVMService", method = RequestMethod.POST)
	public void saveVMService(@RequestParam(value = "docid", required = false) String docid,
			@RequestParam(value = "applydataid", required = false) String applydataid,
			HttpServletRequest request, HttpServletResponse response) {
			try {
				response.setContentType("text/html");
				request.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//将选中的虚拟机id， 名字，使用者，插入到本地数据库vmManager中，并把拼接好的url，根据使用者名称插入到表HC_APPLYENV对应的字段ENV_URL中
			String vmid = request.getParameter("vmidSelect");
			String vmName = request.getParameter("vmNameSelect");
			String user = request.getParameter("vmuser");
			
			String sSQL = "";
			sSQL = String.format("insert into HC_VMMANAGER values( '%s','%s','%s') ",  vmid,vmName,user);
			java.sql.Connection conn = null;
			Statement stmt = null;
			Statement stmt2 = null;
		   
			try {
	      	  	Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
//	      	  	String url="jdbc:oracle:thin:@localhost:1522:orcl";
	            String url="jdbc:oracle:thin:@localhost:1521:XE";
	      	  	conn = DriverManager.getConnection(url,"system","123456");
	      	  	stmt = conn.createStatement();
	      	  	stmt2 = conn.createStatement();
	      	  	stmt.executeUpdate(sSQL);
	           
	      	  	//拼成可访问的虚拟机url
	      	  	String uuid8 = vmid.substring(0, 7) ;  //截取vmid前8位
	      	  	String vmurl;
	           
	      	  	vmurl = "http://133.133.135.11:8989/novnc/console.html?id=" + uuid8;
	      	  	String sql2 = "" ;
	      	  	sql2 = String.format("update HC_APPLYENV set ENV_URL = '%s' where hc_username= '%s' ", vmurl,user);
	      	  	stmt2.executeUpdate(sql2);
	      	  	
	      		conn.close();
                stmt.close();
                this.oracleService.changeApplyEnvStatus(applydataid,"4");//改变状态
		   }catch (Exception e) {
	           e.printStackTrace();
	       }
	}
	
	@RequestMapping(value = "/applydataalloc", method = RequestMethod.GET)
	public String applyDataAlloc(@RequestParam(value = "docid", required = false) String docid,
			@RequestParam(value = "applydataid", required = false) String applydataid) {
		return "applydataalloc";
	}
	
	
	@RequestMapping(value = "/applyenvalloc")
	public String applyEnvAlloc(@RequestParam(value = "docid", required = false) String docid,
			@RequestParam(value = "applydataid", required = false) String applydataid) {
		return "vm_envalloc";
	}
	
	
	@RequestMapping(value = "/applydatacheck", method = RequestMethod.GET)
	public String applyDataCheck(@RequestParam(value = "docid", required = false) String docid,
			@RequestParam(value = "applydataid", required = false) String applydataid) {
		return "applydatacheck";
	}
	
	@RequestMapping(value = "/applydatacheck_success", method = RequestMethod.GET)
	public String applyDataCheck_success(@RequestParam(value = "applyid", required = false) String applydataid) {
		Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authority = "";  
		if(principal instanceof UserDetails){
			Iterator it = ((UserDetails)principal).getAuthorities().iterator();  
			while(it.hasNext()) 
				authority = ((GrantedAuthority)it.next()).getAuthority();  
		}
		
		HcApplydata docData = this.oracleService.getDataDocByApplyDataID(applydataid);
		String curStatus = docData.getFlagApplydata();
		
		//中国卒中数据中心 --- 状态由1到2 --- 办公室
		if(authority.equals("ROLE_SU1") && curStatus.equals("1"))
			this.oracleService.changeApplyDataStatus(applydataid,"2");
		
		//国家卫生计生委脑卒中防治委员会办公室 --- 状态由2到3 --- 管理员分配
		if(authority.equals("ROLE_SU2") && curStatus.equals("2"))
			this.oracleService.changeApplyDataStatus(applydataid,"3");
		
		//系统管理员---状态变到4
		if(authority.equals("ROLE_ADMIN"))
			this.oracleService.changeApplyDataStatus(applydataid,"3");
		
		
		return "applydatacheck";
	}
	
	@RequestMapping(value = "/applydatacheck_reject", method = RequestMethod.GET)
	public String applyDataCheck_reject(@RequestParam(value = "applyid", required = false) String applydataid
			,@RequestParam(value = "rejectReason", required = false) String rejectReason) {
		//中国卒中数据中心、国家卫生计生委脑卒中防治委员会办公室
		String status = "5";
		this.oracleService.changeApplyDataStatus(applydataid,status);
		if(!rejectReason.equals("")){
			try {
				String reason = URLDecoder.decode(URLDecoder.decode(rejectReason,"UTF-8"),"UTF-8");
				this.oracleService.insertApplyDataFailReason(applydataid, reason);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		}
		return "applydatacheck";
	}
	
	@RequestMapping(value = "/applyenvcheck", method = RequestMethod.GET)
	public String applyEnvCheck(@RequestParam(value = "docid", required = false) String docid,
			@RequestParam(value = "applydataid", required = false) String applydataid) {
		return "applyenvcheck";
	}
	
	@RequestMapping(value = "/applyenvcheck_success", method = RequestMethod.GET)
	public String applyEnvCheck_success(@RequestParam(value = "applyid", required = false) String applydataid) {
		Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authority = "";  
		if(principal instanceof UserDetails){
			Iterator it = ((UserDetails)principal).getAuthorities().iterator();  
			while(it.hasNext()) 
				authority = ((GrantedAuthority)it.next()).getAuthority();  
		}
		
		HcApplyenv docEnv = this.oracleService.getDocEnvByApplyDataID(applydataid);
		String curStatus = docEnv.getFlagApplydata();
		
		//中国卒中数据中心 --- 状态由1到2 --- 办公室
		if(authority.equals("ROLE_SU1") && curStatus.equals("1"))
			this.oracleService.changeApplyEnvStatus(applydataid,"2");
		
		//国家卫生计生委脑卒中防治委员会办公室 --- 状态由2到3 --- 管理员分配
		if(authority.equals("ROLE_SU2") && curStatus.equals("2"))
			this.oracleService.changeApplyEnvStatus(applydataid,"3");
		
		//系统管理员 --- 状态变到4 --- 审核成功
		if(authority.equals("ROLE_ADMIN")){
			this.oracleService.changeApplyEnvStatus(applydataid,"3");
			//以下部分应该是新的界面中，分配按钮对应的内荣，这里仅仅是为了测试
			this.oracleService.updateEnvUrlByApplyID(applydataid,"http://133.133.135.11:8989/novnc/console.html?id=bda2af74");
			this.oracleService.changeApplyEnvStatus(applydataid,"4");
		}
		
		return "applyenvcheck";
	}
	
	@RequestMapping(value = "/applycheck_getauthority", method = RequestMethod.GET)
	@ResponseBody
	public String applyCheckGetAuthority(@RequestParam(value = "applyid", required = false) String applydataid) {
		Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authority = "";  

		if(principal instanceof UserDetails){
			Iterator it = ((UserDetails)principal).getAuthorities().iterator();  
			while(it.hasNext())
				authority = ((GrantedAuthority)it.next()).getAuthority();  
		}
		return authority;
	}
	
	@RequestMapping(value = "/applyenvcheck_reject", method = RequestMethod.GET)
	public String applyEnvCheck_reject(@RequestParam(value = "applyid", required = false) String applydataid
			,@RequestParam(value = "rejectReason", required = false) String rejectReason) {
		
		//中国卒中数据中心、国家卫生计生委脑卒中防治委员会办公室----拒绝申请
		String status = "5";
		this.oracleService.changeApplyEnvStatus(applydataid,status);
		if(!rejectReason.equals("")){
			try {
				String reason = URLDecoder.decode(URLDecoder.decode(rejectReason,"UTF-8"),"UTF-8");
				this.oracleService.insertApplyEnvFailReason(applydataid,reason);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		}
		return "applyenvcheck";
	}
	
	@RequestMapping(value = "/applydatatable", method = RequestMethod.GET)
	public String getApplyDataTable() {
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Object[] userAuthory = user.getAuthorities().toArray();
//		String currentUserName = user.getUsername();
//		if( "ROLE_ADMIN".equals(userAuthory[0].toString()) )
		return "applytable_data_admin";
		
	}
	
	@RequestMapping(value = "/applyenvtable", method = RequestMethod.GET)
	public String getApplyEnvTable() {
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Object[] userAuthory = user.getAuthorities().toArray();
//		String currentUserName = user.getUsername();
//		if( "ROLE_ADMIN".equals(userAuthory[0].toString()) )
		return "applytable_env_admin";
		
	}
	
	@RequestMapping(value = "/getdocdatabyapplyid", method = RequestMethod.GET)
	@ResponseBody
	public HcApplydata getDocDataByApplyID(@RequestParam(value = "applyid", required = false) String applyid){
		HcApplydata docData = this.oracleService.getDataDocByApplyDataID(applyid);
		return docData;
	}
	
	@RequestMapping(value = "/getdocenvbyapplyid", method = RequestMethod.GET)
	@ResponseBody
	public HcApplyenv getDocEnvByApplyID(@RequestParam(value = "applyid", required = false) String applyid){
		HcApplyenv docEnv = this.oracleService.getDocEnvByApplyDataID(applyid);
		return docEnv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getdocenv_admin", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDocEnv_admin(@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "applyData_id", required = false) String applyData_id,
			@RequestParam(value = "applyData_userName", required = false) String applyData_userName,
			@RequestParam(value = "applyData_userDepartment", required = false) String applyData_userDepartment,
			@RequestParam(value = "applyData_projectName", required = false) String applyData_projectName,
			@RequestParam(value = "applyData_dataDemand", required = false) String applyData_dataDemand,
			@RequestParam(value = "product_created_from", required = false) String product_created_from,
			@RequestParam(value = "product_created_to", required = false) String product_created_to,
			@RequestParam(value = "product_status", required = false) String product_status,
			
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "draw", required = false) Integer draw){
		
		//check the authority
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Object[] userAuthory = user.getAuthorities().toArray();
//		String currentUserName = user.getUsername();
		
		List<HcApplyenv> docEnvList = new ArrayList<>();
		List<HcApplyenv> ALLEnvList = this.oracleService.getAllDocEnv();
		
		//处理过滤逻辑
		if(action!=null && action.equals("filter"))
			docEnvList = this.filterService.getFinalEnvList(ALLEnvList,applyData_id,applyData_userName,applyData_userDepartment
					,applyData_projectName,applyData_dataDemand,product_created_from,product_created_to,product_status);
		else
			docEnvList = ALLEnvList;
		
		// 分页
		int totalRecords = docEnvList.size();
		int displayLength = (length<0)? totalRecords : length;
		int displayStart = start;
		int end = displayStart + displayLength;
		end = end > totalRecords ? totalRecords : end;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ArrayList<String>> store = new ArrayList<ArrayList<String>>();
		
		for (int i=start; i<end; i++) {
			HcApplyenv docEnv = docEnvList.get(i);
			ArrayList<String> tempStore = new ArrayList<String>();
			tempStore.add("<input type='checkbox' name='id" + docEnv.getIdApplydata() + "' value='"
					+ docEnv.getIdApplydata() + "'>");
			tempStore.add(String.valueOf(docEnv.getIdApplydata()));
			tempStore.add(docEnv.getName());
			tempStore.add(docEnv.getDepartment());
			tempStore.add(docEnv.getProName());
			tempStore.add(docEnv.getDemand());
			tempStore.add(docEnv.getApplyTime());
			
			String applyStatus = getEnvApplyStatus(docEnv.getFlagApplydata(), String.valueOf(docEnv.getIdApplydata()), docEnv.getProName(), docEnv.getName());
			tempStore.add(applyStatus);
			
			//处理按钮--根据statuts添加按钮
			String envStatusFlag = docEnv.getFlagApplydata();
			String docID = docEnv.getDocName();
			
			String blank = "&nbsp;&nbsp;&nbsp;";
			String wordPreview = "<a href=\"/healthcare/applyenv/wordonline?docid="+docID+"\" id=\""+docEnv.getIdApplydata()+"\" "
					+ "target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-search\"></i> word预览</a>";
			
			String envCheck = "<a href=\"/healthcare/adminpanel/applyenvcheck?applydataid="+docEnv.getIdApplydata()+"\" "
					+ "target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-lock\"></i> 环境审核</a>";
			
			String envAlloc = "<a href=\"/healthcare/adminpanel/applyenvalloc?applydataid="+docEnv.getIdApplydata()+"\" "
					+ "target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-cogs\"></i> 环境分配</a>";;
			
			String envCheck_All = "<a href=\"/healthcare/adminpanel/applyenvcheck?applydataid="+docEnv.getIdApplydata()+"\" "
							+ "target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-lock\"></i> 一键审核</a>";
			String button = wordPreview;
			Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String authority = "";  

			if(principal instanceof UserDetails){
				Iterator it = ((UserDetails)principal).getAuthorities().iterator();  
				while(it.hasNext()){  
					authority = ((GrantedAuthority)it.next()).getAuthority();  
//					System.out.println("Authority:"+authority);  
				}  
			}
			
			if(envStatusFlag.equals("1") && authority.equals("ROLE_SU1"))
				button += envCheck;
			if(envStatusFlag.equals("2") && authority.equals("ROLE_SU2"))
				button += envCheck+envAlloc;
			if(authority.equals("ROLE_ADMIN"))
				button += envCheck_All + envAlloc;
			
			tempStore.add(button);
			store.add(tempStore);
		}
		
		resultMap.put("draw", draw);
		resultMap.put("recordsTotal", totalRecords);
		resultMap.put("recordsFiltered", totalRecords);
		resultMap.put("data", store);
		return resultMap;
	}
	
	private String getEnvApplyStatus(String flag_Apply, String ApplyID, String proName, String userName) {
		String status = "<button id=\"a"+ApplyID+"\" class=\"btn btn-xs btn-default env-no\">出错了</button>";
		switch (flag_Apply) {
		case "1":
			status = "<button id=\"a"+ApplyID+"\" title=\"点击查看申请进度\" class=\"btn btn-xs btn-primary env-no\">待审核</button>";
			break;
		case "2"://卒中中心---审核ok
			status= "<button id=\"a"+ApplyID+"\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-info env-no\">审核中</button>";
			break;
		case "3"://卒中办公室---审核ok
			status= "<button id=\"a"+ApplyID+"\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-warning env-no\">环境分配中</button>";
			break;
		case "4"://分配虚拟环境---ok
			status= "<button id=\"a"+ApplyID+"\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-success env-success\">审核通过</button>";
			break;
		case "5"://审核失败
			status= "<button id=\"a"+ApplyID+"\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-danger env-no\">审核失败</button>";
			break;
		default:
			System.out.println("申请标志位"+flag_Apply);
			break;
		}
		return status;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getdocdata_admin", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDocData_admin(@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "applyData_id", required = false) String applyData_id,
			@RequestParam(value = "applyData_userName", required = false) String applyData_userName,
			@RequestParam(value = "applyData_userDepartment", required = false) String applyData_userDepartment,
			@RequestParam(value = "applyData_projectName", required = false) String applyData_projectName,
			@RequestParam(value = "applyData_dataDemand", required = false) String applyData_dataDemand,
			@RequestParam(value = "product_created_from", required = false) String product_created_from,
			@RequestParam(value = "product_created_to", required = false) String product_created_to,
			@RequestParam(value = "product_status", required = false) String product_status,
			
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "draw", required = false) Integer draw){
		
		//check the authority
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Object[] userAuthory = user.getAuthorities().toArray();
//		String currentUserName = user.getUsername();
		
		List<HcApplydata> docDataList = new ArrayList<>();
		List<HcApplydata> ALLDataList = this.oracleService.getAllDocData();
		
		//处理过滤逻辑
		if(action!=null && action.equals("filter"))
			docDataList = this.filterService.getFinalDataList(ALLDataList,applyData_id,applyData_userName,applyData_userDepartment
					,applyData_projectName,applyData_dataDemand,product_created_from,product_created_to,product_status);
		else
			docDataList = ALLDataList;
			
		// 分页
		int totalRecords = docDataList.size();
		int displayLength = (length<0)? totalRecords : length;
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
			tempStore.add(String.valueOf(docData.getIdApplydata()));
			tempStore.add(docData.getName());
			tempStore.add(docData.getDepartment());
			tempStore.add(docData.getProName());
			tempStore.add(docData.getDemand());
			tempStore.add(docData.getApplyTime());
			
			String applyStatus = getDataApplyStatus(docData.getFlagApplydata(), String.valueOf(docData.getIdApplydata()), docData.getProName(),docData.getName());
			tempStore.add(applyStatus);
			
			//处理按钮--根据statuts添加按钮
			String dataStatusFlag = docData.getFlagApplydata();
			String docID = docData.getDocName();
			
			String blank = "&nbsp;&nbsp;&nbsp;";
			String wordPreview = "<a href=\"/healthcare/applydata/wordonline?docid="+docID+"\" id=\""+docData.getIdApplydata()+"\" "
					+ "target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-search\"></i> word预览</a>";
			
			String dataCheck = "<a href=\"/healthcare/adminpanel/applydatacheck?applydataid="+docData.getIdApplydata()+"\" target=\"_blank\" "
					+ "class=\"btn btn-xs default\"><i class=\"fa fa-lock\"></i> 数据审核</a>";
			
			String dataCheck_All = "<a href=\"/healthcare/adminpanel/applydatacheck?applydataid="+docData.getIdApplydata()+"\" "
					+ "target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-lock\"></i> 一键审核</a>";
			
			String dataAlloc = "<a href=\"/healthcare/adminpanel/applydataalloc?applydataid="+docData.getIdApplydata()+"\" "
					+ "target=\"_blank\" class=\"btn btn-xs default\"><i class=\"fa fa-cogs\"></i> 数据分配</a>";;
			
			String button = wordPreview;
			
			Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String authority = "";  
			if(principal instanceof UserDetails){
				Iterator it = ((UserDetails)principal).getAuthorities().iterator();  
				while(it.hasNext()){  
					authority = ((GrantedAuthority)it.next()).getAuthority();  
//					System.out.println("Authority:"+authority);  
				}  
			}
			
			if(dataStatusFlag.equals("1") && authority.equals("ROLE_SU1"))
				button += dataCheck;
			if(dataStatusFlag.equals("2") && authority.equals("ROLE_SU2"))
				button += dataCheck + dataAlloc;
			if(authority.equals("ROLE_ADMIN"))
				button += dataCheck_All + dataAlloc;
			
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
		String status = "<button id=\"a"+ApplyID+"\" class=\"btn btn-xs btn-default env-no\">出错了</button>";
		switch (flag_Apply) {
		case "1":
			status = "<button id=\"a"+ApplyID+"\" title=\"点击查看申请进度\" class=\"btn btn-xs btn-primary env-no\">待审核</button>";
			break;
		case "2"://卒中中心---审核ok
			status= "<button id=\"a"+ApplyID+"\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-info env-no\">审核中</button>";
			break;
		case "3"://卒中办公室---审核ok
			status= "<button id=\"a"+ApplyID+"\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-info env-no\">数据分配中</button>";
			break;
		case "4"://管理员---审核ok---审核通过
			status= "<button id=\"a"+ApplyID+"\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-success env-success\">审核通过</button>";
			break;
		case "5"://审核失败
			status= "<button id=\"a"+ApplyID+"\"  title=\"点击查看申请进度\" class=\"btn btn-xs btn-danger env-no\">审核失败</button>";
			break;
		default:
			System.out.println("申请标志位"+flag_Apply);
			break;
		}
		return status;
	}
	


	@RequestMapping(value = "/starttool", method = RequestMethod.GET)
	public void startWordOnlineTools(HttpServletRequest req, HttpServletResponse resp) {
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext(); 
			ServletContext servletContext = webApplicationContext.getServletContext(); 
			String batPath = servletContext.getRealPath("/WEB-INF/spring/startWordonLineTools.bat");
			Runtime r = Runtime.getRuntime();
			try {
				Process p = r.exec(batPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("startwordOnlinetools success!");
			try {
				PrintWriter out = resp.getWriter();
				resp.setContentType("text/html;charset=UTF-8");
				out.println("在线预览插件----启动成功！");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}
