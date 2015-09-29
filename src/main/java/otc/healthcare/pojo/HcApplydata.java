package otc.healthcare.pojo;

import java.math.BigDecimal;

import javax.persistence.Entity;

/**
 * HcApplydata generated by hbm2java
 */
@Entity
public class HcApplydata implements java.io.Serializable {

	private BigDecimal idApplydata;
	private String hcUsername;
	private String docName;
	private String name;
	private String department;
	private String address;
	private String tel;
	private String email;
	private String demandtype;
	private String demand;
	private String proName;
	private String proChair;
	private String proSource;
	private String proUndertake;
	private String proRemark;
	private String proUsefield;
	private String flagApplydata;
	private String applyTime;
	private String applyRejectReason;
	private String applyData;
	
	public String getApplyRejectReason() {
		return applyRejectReason;
	}

	public void setApplyRejectReason(String applyRejectReason) {
		this.applyRejectReason = applyRejectReason;
	}

	public HcApplydata() {
	}

	public HcApplydata(BigDecimal idApplydata, String hcUsername, String docName) {
		this.idApplydata = idApplydata;
		this.hcUsername = hcUsername;
		this.docName = docName;
	}

	public HcApplydata(BigDecimal idApplydata, String hcUsername, String docName, String name, String department,
			String address, String tel, String email, String demandtype, String demand, String proName, String proChair,
			String proSource, String proUndertake, String proRemark, String proUsefield, String flagApplydata,
			String applyTime, String applyRejectReason,String applyData) {
		this.idApplydata = idApplydata;
		this.hcUsername = hcUsername;
		this.docName = docName;
		this.name = name;
		this.department = department;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.demandtype = demandtype;
		this.demand = demand;
		this.proName = proName;
		this.proChair = proChair;
		this.proSource = proSource;
		this.proUndertake = proUndertake;
		this.proRemark = proRemark;
		this.proUsefield = proUsefield;
		this.flagApplydata = flagApplydata;
		this.applyTime = applyTime;
		this.applyRejectReason = applyRejectReason;
		this.applyData = applyData;
	}

	public String getApplyData() {
		return applyData;
	}

	public void setApplyData(String applyData) {
		this.applyData = applyData;
	}

	public BigDecimal getIdApplydata() {
		return this.idApplydata;
	}

	public void setIdApplydata(BigDecimal idApplydata) {
		this.idApplydata = idApplydata;
	}

	public String getHcUsername() {
		return this.hcUsername;
	}

	public void setHcUsername(String hcUsername) {
		this.hcUsername = hcUsername;
	}

	public String getDocName() {
		return this.docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDemandtype() {
		return this.demandtype;
	}

	public void setDemandtype(String demandtype) {
		this.demandtype = demandtype;
	}

	public String getDemand() {
		return this.demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProChair() {
		return this.proChair;
	}

	public void setProChair(String proChair) {
		this.proChair = proChair;
	}

	public String getProSource() {
		return this.proSource;
	}

	public void setProSource(String proSource) {
		this.proSource = proSource;
	}

	public String getProUndertake() {
		return this.proUndertake;
	}

	public void setProUndertake(String proUndertake) {
		this.proUndertake = proUndertake;
	}

	public String getProRemark() {
		return this.proRemark;
	}

	public void setProRemark(String proRemark) {
		this.proRemark = proRemark;
	}

	public String getProUsefield() {
		return this.proUsefield;
	}

	public void setProUsefield(String proUsefield) {
		this.proUsefield = proUsefield;
	}

	public String getFlagApplydata() {
		return this.flagApplydata;
	}

	public void setFlagApplydata(String flagApplydata) {
		this.flagApplydata = flagApplydata;
	}

	public String getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

}
