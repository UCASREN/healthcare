package otc.healthcare.pojo;

public class CommunityModel {
	private String uuCode;
	private String acCodeUp;
	private String name;
	private String upName;
	private Integer endCount;
	private String uull;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUpName() {
		return upName;
	}
	public void setUpName(String upName) {
		this.upName = upName;
	}
	public Integer getEndCount() {
		return endCount;
	}
	public void setEndCount(Integer endCount) {
		this.endCount = endCount;
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
	 * @return the acCodeUp
	 */
	public String getAcCodeUp() {
		return acCodeUp;
	}
	/**
	 * @param acCodeUp the acCodeUp to set
	 */
	public void setAcCodeUp(String acCodeUp) {
		this.acCodeUp = acCodeUp;
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
