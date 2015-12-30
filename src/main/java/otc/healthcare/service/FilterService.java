package otc.healthcare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import otc.healthcare.dao.HcApplydataDao;
import otc.healthcare.dao.HcApplyenvDao;
import otc.healthcare.pojo.HcApplydata;
import otc.healthcare.pojo.HcApplyenv;
import otc.healthcare.util.HealthcareConfiguration;

@Component
public class FilterService implements IService {

	@Autowired
	private HealthcareConfiguration hcConfiguration;
	@Autowired
	private HcApplydataDao hcApplydataDao;
	@Autowired
	private HcApplyenvDao hcApplyenvDao;

	public HcApplydataDao getHcApplydataDao() {
		return hcApplydataDao;
	}

	public void setHcApplydataDao(HcApplydataDao hcApplydataDao) {
		this.hcApplydataDao = hcApplydataDao;
	}

	public HcApplyenvDao getHcApplyenvDao() {
		return hcApplyenvDao;
	}

	public void setHcApplyenvDao(HcApplyenvDao hcApplyenvDao) {
		this.hcApplyenvDao = hcApplyenvDao;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int checkStatus() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//过滤函数
	public List<HcApplydata> getFinalDataList(List<HcApplydata> aLLDataList, String applyData_id, 
			String applyData_userName, String applyData_userDepartment, String applyData_projectName, String applyData_dataDemand, 
			String from, String to, String product_status) {
		if(aLLDataList == null || aLLDataList.size()==0)
			return aLLDataList;
		
		List<HcApplydata> rsList = new ArrayList<>();
		for(HcApplydata h : aLLDataList){
			//(1)---id
			if(notEmpty(applyData_id))
				if(String.valueOf(h.getIdApplydata()).equals(applyData_id)){
					rsList.add(h);
					continue;
				}
			
			//(2)---username
			if(notEmpty(applyData_userName))
				if(h.getName().contains(applyData_userName)){
					rsList.add(h);
					continue;
				}
			
			//(3)---department
			if(notEmpty(applyData_userDepartment))
				if(h.getDepartment().contains(applyData_userDepartment)){
					rsList.add(h);
					continue;
				}
			
			//(4)---projectName
			if(notEmpty(applyData_projectName))
				if(h.getProName().contains(applyData_projectName)){
					rsList.add(h);
					continue;
				}
			
			//(5)---dataDemand
			if(notEmpty(applyData_dataDemand))
				if(h.getDemand().contains(applyData_dataDemand)){
					rsList.add(h);
					continue;
				}
			
			//(6)---time
			String time = h.getApplyTime();
			if(notEmpty(to) || notEmpty(from))
				if(timeCompare(time, from ,to)){
					rsList.add(h);
					continue;
				}
			
			//(7)---status
			if(notEmpty(product_status)){
				int flag = getApplyStatusFlag(product_status);
				if(Integer.valueOf(h.getFlagApplydata()) == flag){
					rsList.add(h);
					continue;
				}
			}
		}
		
//		if(rsList.size()==0)
//			return aLLDataList;
		return rsList;
	}
	
	
	public List<HcApplyenv> getFinalEnvList(List<HcApplyenv> aLLEnvList, String applyData_id, String applyData_userName,
			String applyData_userDepartment, String applyData_projectName, String applyData_dataDemand,
			String from, String to, String product_status) {
		
		if(aLLEnvList == null || aLLEnvList.size()==0)
			return aLLEnvList;
		
		List<HcApplyenv> rsList = new ArrayList<>();
		for(HcApplyenv h : aLLEnvList){
			//(1)---id
			if(notEmpty(applyData_id))
				if(String.valueOf(h.getIdApplydata()).equals(applyData_id)){
					rsList.add(h);
					continue;
				}
			
			//(2)---username
			if(notEmpty(applyData_userName))
				if(h.getName().contains(applyData_userName)){
					rsList.add(h);
					continue;
				}
			
			//(3)---department
			if(notEmpty(applyData_userDepartment))
				if(h.getDepartment().contains(applyData_userDepartment)){
					rsList.add(h);
					continue;
				}
			
			//(4)---projectName
			if(notEmpty(applyData_projectName))
				if(h.getProName().contains(applyData_projectName)){
					rsList.add(h);
					continue;
				}
			
			//(5)---dataDemand
			if(notEmpty(applyData_dataDemand))
				if(h.getDemand().contains(applyData_dataDemand)){
					rsList.add(h);
					continue;
				}
			
			//(6)---time
			String time = h.getApplyTime();
			if(notEmpty(to) || notEmpty(from))
				if(timeCompare(time, from ,to)){
					rsList.add(h);
					continue;
				}
			
			//(7)---status
			if(notEmpty(product_status)){
				int flag = getApplyStatusFlag(product_status);
				if(Integer.valueOf(h.getFlagApplydata()) == flag){
					rsList.add(h);
					continue;
				}
			}
		}
		
//		if(rsList.size()==0)
//			return aLLEnvList;
		return rsList;
	}
	
	
	private static int getApplyStatusFlag(String product_status) {
		int flag = 0;
		switch (product_status) {
		case "notCheck":
			flag = 1;
			break;
		case "Checking":
			flag = 2;
			break;
		case "Checked":
			flag = 3;
			break;
		case "unChecked":
			flag = 4;
			break;
		default:
			System.out.println("filtering 出错！");
			break;
		}
		return flag;
	}

	private static boolean notEmpty(String s){
		return !s.equals("");
	}
	
	private static boolean timeCompare(String time, String from, String to){
		if(notEmpty(to) && notEmpty(from)){
			if(timeMorethan(time, from) && timeLessthan(time, to))	return true; 
			
		}else if(notEmpty(from)){
			if(timeMorethan(time, from)) 	return true;
		}else if(notEmpty(to)){
			if(timeMorethan(time, from))	return true;
		}
		return false;
	}

	private static boolean timeLessthan(String time, String to) {
		long time1 = Long.valueOf(time.replace("/",""));
		long to1 = Long.valueOf(to.replace("/", ""));
		if(time1 < to1)
			return true;
		return false;
	}

	private static boolean timeMorethan(String time, String from) {
		long time1 = Long.valueOf(time.replace("/",""));
		long from1 = Long.valueOf(from.replace("/", ""));
		if(time1 > from1)
			return true;
		return false;
	}


}//end-class
