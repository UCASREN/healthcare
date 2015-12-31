package otc.healthcare.pojo;

public class BaseHospitalModel {
	private String uuCode;
	private String name;
	private Integer endCount;
	private Integer dangerCount;
	private String uull;
	private String uuProvince;

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
	 * @param uull
	 *            the uull to set
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
	 * @param uuCode
	 *            the uuCode to set
	 */
	public void setUuCode(String uuCode) {
		this.uuCode = uuCode;
	}

	/**
	 * @return the uuProvince
	 */
	public String getUuProvince() {
		return uuProvince;
	}

	/**
	 * @param uuProvince
	 *            the uuProvince to set
	 */
	public void setUuProvince(String uuProvince) {
		this.uuProvince = uuProvince;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dangerCount == null) ? 0 : dangerCount.hashCode());
		result = prime * result + ((endCount == null) ? 0 : endCount.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uuCode == null) ? 0 : uuCode.hashCode());
		result = prime * result + ((uuProvince == null) ? 0 : uuProvince.hashCode());
		result = prime * result + ((uull == null) ? 0 : uull.hashCode());
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
		BaseHospitalModel other = (BaseHospitalModel) obj;
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (uuCode == null) {
			if (other.uuCode != null)
				return false;
		} else if (!uuCode.equals(other.uuCode))
			return false;
		if (uuProvince == null) {
			if (other.uuProvince != null)
				return false;
		} else if (!uuProvince.equals(other.uuProvince))
			return false;
		if (uull == null) {
			if (other.uull != null)
				return false;
		} else if (!uull.equals(other.uull))
			return false;
		return true;
	}

}
