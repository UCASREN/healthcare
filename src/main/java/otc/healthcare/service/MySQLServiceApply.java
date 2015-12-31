package otc.healthcare.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import otc.healthcare.dao.HcApplydataDao;
import otc.healthcare.dao.HcApplyenvDao;
import otc.healthcare.pojo.HcApplydata;
import otc.healthcare.pojo.HcApplyenv;
import otc.healthcare.util.HealthcareConfiguration;

@Component
public class MySQLServiceApply implements IService {

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
		return null;
	}

	@Override
	public int checkStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * 数据申请
	 */
	@Transactional
	public void insertApplyData(HttpServletRequest req, String f_name, boolean update) {

		if (update) {
			// begin update
			HcApplydata hc_applydata = hcApplydataDao.findByDocName(f_name);

			String curStatus = hc_applydata.getFlagApplydata();
			if (!curStatus.equals("2")) {
				System.out.println("applyData 状态不为2，此时已经无法更新申请！");
				return;
			}

			String hc_userName = req.getParameter("userName");
			String hc_userDepartment = req.getParameter("userDepartment");
			String hc_userAddress = req.getParameter("userAddress");
			String hc_userTel = req.getParameter("userTel");
			String hc_userEmail = req.getParameter("userEmail");

			String hc_userDemandType = req.getParameter("userDemandType");
			String hc_userDemand = req.getParameter("userDemand");
			String hc_dataExportType = req.getParameter("dataExportType");

			String hc_useFields = req.getParameter("allUseField");//
			String hc_projectName = req.getParameter("projectName");
			String hc_projectChairman = req.getParameter("projectChairman");
			String hc_projectSource = req.getParameter("projectSource");
			String hc_projectUndertaking = req.getParameter("projectUndertaking");
			String hc_applyDate = req.getParameter("applyDate");
			String hc_projectRemarks = req.getParameter("projectRemarks");

			String applydata = req.getParameter("applydata");

			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			hc_applydata.setHcUsername(user.getUsername());// hc系统用户名
			// hc_applydata.setDocName(f_name);// 主键

			hc_applydata.setName(hc_userName);// 申请表填写用户
			hc_applydata.setDepartment(hc_userDepartment);
			hc_applydata.setAddress(hc_userAddress);
			hc_applydata.setTel(hc_userTel);
			hc_applydata.setEmail(hc_userEmail);

			hc_applydata.setDemandtype(hc_userDemandType);
			hc_applydata.setDemand(hc_userDemand);
			hc_applydata.setApplyDataExportType(hc_dataExportType);

			hc_applydata.setProUsefield(hc_useFields);
			hc_applydata.setProName(hc_projectName);
			hc_applydata.setProChair(hc_projectChairman);
			hc_applydata.setProSource(hc_projectSource);
			hc_applydata.setProUndertake(hc_projectUndertaking);
			hc_applydata.setApplyTime(hc_applyDate);
			hc_applydata.setProRemark(hc_projectRemarks);

			hc_applydata.setApplyData(applydata);

			// 只有在未进行审核情况下才可以update，apply标志为1 --- status=1（待审核状态）
			hc_applydata.setFlagApplydata("1");

			hcApplydataDao.attachDirty(hc_applydata);
			System.out.println("update hc_applydata ok");
			return;
		}

		// begin insert
		HcApplydata hc_applydata = new HcApplydata();

		String hc_userName = req.getParameter("userName");
		String hc_userDepartment = req.getParameter("userDepartment");
		String hc_userAddress = req.getParameter("userAddress");
		String hc_userTel = req.getParameter("userTel");
		String hc_userEmail = req.getParameter("userEmail");

		String hc_userDemandType = req.getParameter("userDemandType");
		String hc_userDemand = req.getParameter("userDemand");
		String hc_dataExportType = req.getParameter("dataExportType");

		String hc_useFields = req.getParameter("allUseField");//
		String hc_projectName = req.getParameter("projectName");
		String hc_projectChairman = req.getParameter("projectChairman");
		String hc_projectSource = req.getParameter("projectSource");
		String hc_projectUndertaking = req.getParameter("projectUndertaking");
		String hc_applyDate = req.getParameter("applyDate");
		String hc_projectRemarks = req.getParameter("projectRemarks");

		String applydata = req.getParameter("applydata");

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		hc_applydata.setHcUsername(user.getUsername());// hc系统用户名
		hc_applydata.setDocName(f_name);// 主键

		hc_applydata.setName(hc_userName);// 申请表填写用户
		hc_applydata.setDepartment(hc_userDepartment);
		hc_applydata.setAddress(hc_userAddress);
		hc_applydata.setTel(hc_userTel);
		hc_applydata.setEmail(hc_userEmail);

		hc_applydata.setDemandtype(hc_userDemandType);
		hc_applydata.setDemand(hc_userDemand);
		hc_applydata.setApplyDataExportType(hc_dataExportType);

		hc_applydata.setProUsefield(hc_useFields);
		hc_applydata.setProName(hc_projectName);
		hc_applydata.setProChair(hc_projectChairman);
		hc_applydata.setProSource(hc_projectSource);
		hc_applydata.setProUndertake(hc_projectUndertaking);
		hc_applydata.setApplyTime(hc_applyDate);
		hc_applydata.setProRemark(hc_projectRemarks);
		hc_applydata.setApplyData(applydata);

		// 提交后，apply标志为1 --- status=1（待审核状态）
		hc_applydata.setFlagApplydata("1");

		hcApplydataDao.attachDirty(hc_applydata);
		System.out.println("insert hc_applydata ok");
	}

	/*
	 * get the apply docdata from db by docName
	 */
	@Transactional
	public HcApplydata getDocBydocID(String docid) {
		HcApplydata docData = hcApplydataDao.findByDocName(docid);
		return docData;
	}

	@Transactional
	public HcApplydata getDataDocByApplyDataID(String applydataId) {
		HcApplydata docData = hcApplydataDao.findByApplyID(applydataId);
		return docData;
	}

	/*
	 * get the apply docdata from db by hc_userName(系统用户)
	 */
	@Transactional
	public List getDocByHcUserName(String hcUserName) {
		List<HcApplydata> docDataList = hcApplydataDao.findByHcUserName(hcUserName);
		return docDataList;
	}

	/*
	 * get all the apply docdata from db(ROLE_ADMIN管理员用)
	 */
	@Transactional
	public List<HcApplydata> getAllDocData() {
		List ALLdocDataList = hcApplydataDao.findAll();
		return ALLdocDataList;
	}

	@Transactional
	public String getApplyDataByDocId(String docid) {
		HcApplydata hcapplydata = hcApplydataDao.findByDocName(docid);
		return hcapplydata.getApplyData();
	}

	/*
	 * 删除applydata信息
	 */
	@Transactional
	public boolean deleteApplyData(String[] applydataid) {
		try {
			for (int i = 0; i < applydataid.length; i++) {
				HcApplydata tmp = hcApplydataDao.findByApplyID(applydataid[i]);
				hcApplydataDao.delete(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Transactional
	public void changeApplyDataStatus(String applyid, String status) {
		long apply_id = Long.valueOf(applyid);
		hcApplydataDao.changeApplyStatus(apply_id, status);
	}

	@Transactional
	public void insertApplyDataFailReason(String applyid, String rejectReason) {
		long apply_id = Long.valueOf(applyid);
		hcApplydataDao.setApplyFailReason(apply_id, rejectReason);
	}

	/*
	 * 虚拟环境申请
	 */
	@Transactional
	public boolean deleteApplyEnv(String[] applydataid) {
		try {
			for (int i = 0; i < applydataid.length; i++) {
				HcApplyenv tmp = hcApplyenvDao.findByApplyID(applydataid[i]);
				hcApplyenvDao.delete(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Transactional
	public String getApplyDataByEnvDocId(String docid) {
		HcApplyenv hcApplyenv = hcApplyenvDao.findByDocName(docid);
		return hcApplyenv.getApplyData();
	}

	@Transactional
	public void insertApplyEnv(HttpServletRequest req, String f_name, boolean update) {

		if (update) {
			// begin update envApply
			HcApplyenv hc_applyenv = hcApplyenvDao.findByDocName(f_name);

			String hc_userName = req.getParameter("userName");
			String hc_userDepartment = req.getParameter("userDepartment");
			String hc_userAddress = req.getParameter("userAddress");
			String hc_userTel = req.getParameter("userTel");
			String hc_userEmail = req.getParameter("userEmail");

			String hc_userDemandType = req.getParameter("userDemandType");
			String hc_userDemand = req.getParameter("userDemand");
			String hc_dataExportType = req.getParameter("dataExportType");

			String hc_useFields = req.getParameter("allUseField");//
			String hc_projectName = req.getParameter("projectName");
			String hc_projectChairman = req.getParameter("projectChairman");
			String hc_projectSource = req.getParameter("projectSource");
			String hc_projectUndertaking = req.getParameter("projectUndertaking");
			String hc_applyDate = req.getParameter("applyDate");
			String hc_projectRemarks = req.getParameter("projectRemarks");
			String applydata = req.getParameter("applydata");

			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			hc_applyenv.setHcUsername(user.getUsername());// hc系统用户名
			// hc_applyenv.setDocName(f_name);// 主键

			hc_applyenv.setName(hc_userName);// 申请表填写用户
			hc_applyenv.setDepartment(hc_userDepartment);
			hc_applyenv.setAddress(hc_userAddress);
			hc_applyenv.setTel(hc_userTel);
			hc_applyenv.setEmail(hc_userEmail);

			hc_applyenv.setDemandtype(hc_userDemandType);
			hc_applyenv.setDemand(hc_userDemand);
			hc_applyenv.setApplyDataExportType(hc_dataExportType);

			hc_applyenv.setProUsefield(hc_useFields);
			hc_applyenv.setProName(hc_projectName);
			hc_applyenv.setProChair(hc_projectChairman);
			hc_applyenv.setProSource(hc_projectSource);
			hc_applyenv.setProUndertake(hc_projectUndertaking);
			hc_applyenv.setApplyTime(hc_applyDate);
			hc_applyenv.setProRemark(hc_projectRemarks);
			hc_applyenv.setApplyData(applydata);
			// 提交后，apply标志为1
			hc_applyenv.setFlagApplydata("1");

			hcApplyenvDao.attachDirty(hc_applyenv);
			System.out.println("update hc_applyenv ok");
			return;
		}

		HcApplyenv hc_applyenv = new HcApplyenv();

		String hc_userName = req.getParameter("userName");
		String hc_userDepartment = req.getParameter("userDepartment");
		String hc_userAddress = req.getParameter("userAddress");
		String hc_userTel = req.getParameter("userTel");
		String hc_userEmail = req.getParameter("userEmail");

		String hc_userDemandType = req.getParameter("userDemandType");
		String hc_userDemand = req.getParameter("userDemand");
		String hc_dataExportType = req.getParameter("dataExportType");

		String hc_useFields = req.getParameter("allUseField");
		String hc_projectName = req.getParameter("projectName");
		String hc_projectChairman = req.getParameter("projectChairman");
		String hc_projectSource = req.getParameter("projectSource");
		String hc_projectUndertaking = req.getParameter("projectUndertaking");
		String hc_applyDate = req.getParameter("applyDate");
		String hc_projectRemarks = req.getParameter("projectRemarks");
		String applydata = req.getParameter("applydata");

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		hc_applyenv.setHcUsername(user.getUsername());// hc系统用户名
		hc_applyenv.setDocName(f_name);// 主键

		hc_applyenv.setName(hc_userName);// 申请表填写用户
		hc_applyenv.setDepartment(hc_userDepartment);
		hc_applyenv.setAddress(hc_userAddress);
		hc_applyenv.setTel(hc_userTel);
		hc_applyenv.setEmail(hc_userEmail);

		hc_applyenv.setDemandtype(hc_userDemandType);
		hc_applyenv.setDemand(hc_userDemand);
		hc_applyenv.setApplyDataExportType(hc_dataExportType);

		hc_applyenv.setProUsefield(hc_useFields);
		hc_applyenv.setProName(hc_projectName);
		hc_applyenv.setProChair(hc_projectChairman);
		hc_applyenv.setProSource(hc_projectSource);
		hc_applyenv.setProUndertake(hc_projectUndertaking);
		hc_applyenv.setApplyTime(hc_applyDate);
		hc_applyenv.setProRemark(hc_projectRemarks);
		hc_applyenv.setApplyData(applydata);
		// 提交后，apply标志为1
		hc_applyenv.setFlagApplydata("1");

		hcApplyenvDao.attachDirty(hc_applyenv);
		System.out.println("insert hc_applyenv ok");
	}

	public HcApplyenv getEnvDocBydocID(String docid) {
		HcApplyenv docData = hcApplyenvDao.findByDocName(docid);
		return docData;
	}

	public HcApplyenv getDocEnvByApplyDataID(String applyid) {
		HcApplyenv docEnv = hcApplyenvDao.findByApplyID(applyid);
		return docEnv;
	}

	public List<HcApplyenv> getEnvDocByHcUserName(String hcUserName) {
		List docEnvDataList = hcApplyenvDao.findByHcUserName(hcUserName);
		return docEnvDataList;
	}

	public List<HcApplyenv> getAllDocEnv() {
		List ALLdocEnvList = hcApplyenvDao.findAll();
		return ALLdocEnvList;
	}

	@Transactional
	public void changeApplyEnvStatus(String applyid, String status) {
		long apply_id = Long.valueOf(applyid);
		hcApplyenvDao.changeApplyStatus(apply_id, status);
	}

	@Transactional
	public void insertApplyEnvFailReason(String applyid, String rejectReason) {
		long apply_id = Long.valueOf(applyid);
		hcApplyenvDao.setApplyFailReason(apply_id, rejectReason);
	}

	@Transactional
	public void updateEnvUrlByApplyID(String applyid, String envUrl) {
		long apply_id = Long.valueOf(applyid);
		hcApplyenvDao.setApplyEnvUrl(apply_id, envUrl);
		System.out.println("update EnvUrl ok");
	}

}
