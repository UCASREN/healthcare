package otc.healthcare.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "dataBaseInfoModel")
public class DatabaseInfo {
	private String databaseid;
	private String name;
	private String comments;
	
	@XmlElement(name = "databaseid")
	public String getDatabaseid() {
		return databaseid;
	}
	public void setDatabaseid(String databaseid) {
		this.databaseid = databaseid;
	}
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "comments")
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "DatabaseInfoModel [databaseid=" + databaseid + ", name=" + name
				+ ", comments=" + comments + "]";
	}
	
}
