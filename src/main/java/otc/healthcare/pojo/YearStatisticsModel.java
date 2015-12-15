package otc.healthcare.pojo;

public class YearStatisticsModel {
	private Integer provinceCount;
	private Integer cityCount;
	private Integer joinBaseHospitalCount;
	private Integer joinCommunityCount;
	private Integer endCount;
	private Integer dangerCount;
	private Integer strokeCount;
	public Integer getProvinceCount() {
		return provinceCount;
	}
	public void setProvinceCount(Integer provinceCount) {
		this.provinceCount = provinceCount;
	}
	public Integer getCityCount() {
		return cityCount;
	}
	public void setCityCount(Integer cityCount) {
		this.cityCount = cityCount;
	}
	public Integer getJoinCommunityCount() {
		return joinCommunityCount;
	}
	public void setJoinCommunityCount(Integer joinCommunityCount) {
		this.joinCommunityCount = joinCommunityCount;
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
	public Integer getStrokeCount() {
		return strokeCount;
	}
	public void setStrokeCount(Integer strokeCount) {
		this.strokeCount = strokeCount;
	}
	/**
	 * @return the joinBaseHospitalCount
	 */
	public Integer getJoinBaseHospitalCount() {
		return joinBaseHospitalCount;
	}
	/**
	 * @param joinBaseHospitalCount the joinBaseHospitalCount to set
	 */
	public void setJoinBaseHospitalCount(Integer joinBaseHospitalCount) {
		this.joinBaseHospitalCount = joinBaseHospitalCount;
	}
}
