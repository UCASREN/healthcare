package otc.healthcare.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beyondsphere.database.JDBCUtil;

import otc.healthcare.dao.ConnectionFactory;
import otc.healthcare.pojo.VMUser;
import otc.healthcare.util.HealthcareConfiguration;
import otc.healthcare.util.OracleDBUtil;

@Component
public class VMService implements IService {

	@Autowired
	private HealthcareConfiguration hcConfiguration;

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public int checkStatus() {
		return 0;
	}

	public ArrayList<VMUser> listVMService() {
		String vm_ip = hcConfiguration.getProperty(HealthcareConfiguration.VM_IP);
		String vm_name = hcConfiguration.getProperty(HealthcareConfiguration.VM_USERNAME);
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_BASIC_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		
		//从云平台获得所有虚拟机id， name, 
		//再判断vmid是否在本地数据库表vmmanger中存在，如果存在表示已经分配。把虚拟机id， 名字，使用者显示到页面，不存在时只显示vmid， vmname
		JDBCUtil jdbc = new JDBCUtil();
		List<String> uuids = jdbc.getAllVMsByUserName(vm_ip, vm_name);
		
		ArrayList<VMUser>  vmUserList2 = new ArrayList<VMUser>(); //存放已经分配和未分配的
		ArrayList<VMUser>  vmUserList = new ArrayList<VMUser>();  //存放已经分配的
		  
		//查找id在表vmmanger中是否存在
		ResultSet rs = dbUtil.query("select * from SYSTEM.HC_VMMANAGER");
        try {
			while(rs.next()){
				 VMUser  vms = new VMUser();
				 vms.vmid = rs.getString(1);
				 vms.vmName =rs.getString(2);
				 vms.vmUser = rs.getString(3);
				 //将已经分配好的显示到页面，并保存好vmid
				 vmUserList.add(vms); //用于和所有的vmid比较，筛选出未分配的
				 vmUserList2.add(vms);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	         
	   //先前一个也没分配过
       if (vmUserList.size() ==0){
    	   for(int i = 0 ; i < uuids.size(); i++){//将vmid， vmName 显示到页面
    		   VMUser  vms2 = new VMUser();
    		   vms2.vmid = uuids.get(i);
    		   vms2.vmName = jdbc.getNameByUuid(vm_ip, uuids.get(i));
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
		        	sName = jdbc.getNameByUuid(vm_ip, sid);
		        	vms3.vmName = sName;
		        	vms3.vmUser = "";
		        	vmUserList2.add(vms3);
		        }
		     }//end for
	      }//end else
       dbUtil.close();
       return vmUserList2;
	}
	

	public void saveVMService(String vmid, String vmName, String applydataid) {
		String vm_ip = hcConfiguration.getProperty(HealthcareConfiguration.VM_IP);
		String vm_name = hcConfiguration.getProperty(HealthcareConfiguration.VM_USERNAME);
		String oracle_url = hcConfiguration.getProperty(HealthcareConfiguration.DB_BASIC_URL);
		String oracle_username = hcConfiguration.getProperty(HealthcareConfiguration.DB_USERNAME);
		String oracle_password = hcConfiguration.getProperty(HealthcareConfiguration.DB_PASSWORD);
		ConnectionFactory connectionFactory = new ConnectionFactory("oracle", oracle_url, oracle_username,
				oracle_password);
		OracleDBUtil dbUtil = new OracleDBUtil(connectionFactory.getInstance().getConnection());
		
		String sSQL = "";
		sSQL = String.format("insert into HC_VMMANAGER values( '%s','%s','%s') ",  vmid,vmName,applydataid);
		dbUtil.execute(sSQL);

  	  	//拼成可访问的虚拟机url
  	  	String uuid8 = vmid.substring(0, 7) ;  //截取vmid前8位
  	  	String vmurl = "http://" + vm_ip + ":8989/novnc/console.html?id=" + uuid8;
  	  	
  	  	String sql2 = "" ;
  	  	sql2 = String.format("update HC_APPLYENV set ENV_URL = '%s' where hc_username= '%s' ", vmurl,applydataid);
		dbUtil.execute(sql2);
	}
	
		
}//end-class
