package otc.healthcare.pojo;

import java.util.List;

public class ClassificationInfo {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classificationid == null) ? 0 : classificationid.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((databaseinfolist == null) ? 0 : databaseinfolist.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ClassificationInfo other = (ClassificationInfo) obj;
		if (classificationid == null) {
			if (other.classificationid != null)
				return false;
		} else if (!classificationid.equals(other.classificationid))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (databaseinfolist == null) {
			if (other.databaseinfolist != null)
				return false;
		} else if (!databaseinfolist.equals(other.databaseinfolist))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	private String classificationid;
	private String name;
	private String comments;
	private List<DatabaseInfo> databaseinfolist;

	public String getClassificationid() {
		return classificationid;
	}

	public void setClassificationid(String classificationid) {
		this.classificationid = classificationid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the databaseinfolist
	 */
	public List<DatabaseInfo> getDatabaseinfolist() {
		return databaseinfolist;
	}

	/**
	 * @param databaseinfolist
	 *            the databaseinfolist to set
	 */
	public void setDatabaseinfolist(List<DatabaseInfo> databaseinfolist) {
		this.databaseinfolist = databaseinfolist;
	}

}
