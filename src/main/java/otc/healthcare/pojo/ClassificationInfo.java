package otc.healthcare.pojo;

import java.util.List;

public class ClassificationInfo {
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
	 * @param databaseinfolist the databaseinfolist to set
	 */
	public void setDatabaseinfolist(List<DatabaseInfo> databaseinfolist) {
		this.databaseinfolist = databaseinfolist;
	}

}
