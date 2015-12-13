package otc.healthcare.pojo;

public class BaseHospitalModel {
	private String uuCode;
	private String name;
	private Integer endCount;
	private Integer dangerCount;
	private String uull;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getEndCount() {
		return endCount;
	}
	public void setEndCount(Integer endCount) {
		this.endCount = endCount;
	}
	public Integer getDangerCount() {
		return dangerCount;
	}
	public void setDangerCount(Integer dangerCount) {
		this.dangerCount = dangerCount;
	}
	/**
	 * @return the uull
	 */
	public String getUull() {
		return uull;
	}
	/**
	 * @param uull the uull to set
	 */
	public void setUull(String uull) {
		this.uull = uull;
	}
	/**
	 * @return the uuCode
	 */
	public String getUuCode() {
		return uuCode;
	}
	/**
	 * @param uuCode the uuCode to set
	 */
	public void setUuCode(String uuCode) {
		this.uuCode = uuCode;
	}
}
