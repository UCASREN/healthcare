package otc.healthcare.pojo;

public class YearStatisticsModel {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cityCount == null) ? 0 : cityCount.hashCode());
		result = prime * result + ((dangerCount == null) ? 0 : dangerCount.hashCode());
		result = prime * result + ((endCount == null) ? 0 : endCount.hashCode());
		result = prime * result + ((joinBaseHospitalCount == null) ? 0 : joinBaseHospitalCount.hashCode());
		result = prime * result + ((joinCommunityCount == null) ? 0 : joinCommunityCount.hashCode());
		result = prime * result + ((provinceCount == null) ? 0 : provinceCount.hashCode());
		result = prime * result + ((strokeCount == null) ? 0 : strokeCount.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		YearStatisticsModel other = (YearStatisticsModel) obj;
		if (cityCount == null) {
			if (other.cityCount != null)
				return false;
		} else if (!cityCount.equals(other.cityCount))
			return false;
		if (dangerCount == null) {
			if (other.dangerCount != null)
				return false;
		} else if (!dangerCount.equals(other.dangerCount))
			return false;
		if (endCount == null) {
			if (other.endCount != null)
				return false;
		} else if (!endCount.equals(other.endCount))
			return false;
		if (joinBaseHospitalCount == null) {
			if (other.joinBaseHospitalCount != null)
				return false;
		} else if (!joinBaseHospitalCount.equals(other.joinBaseHospitalCount))
			return false;
		if (joinCommunityCount == null) {
			if (other.joinCommunityCount != null)
				return false;
		} else if (!joinCommunityCount.equals(other.joinCommunityCount))
			return false;
		if (provinceCount == null) {
			if (other.provinceCount != null)
				return false;
		} else if (!provinceCount.equals(other.provinceCount))
			return false;
		if (strokeCount == null) {
			if (other.strokeCount != null)
				return false;
		} else if (!strokeCount.equals(other.strokeCount))
			return false;
		return true;
	}
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
