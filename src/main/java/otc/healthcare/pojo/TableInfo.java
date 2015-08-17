package otc.healthcare.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "tableInfoModel")
public class TableInfo {
	private String tableid;
	private String databaseid;
	private String name;
	private String comments;
	@XmlElement(name = "tableid")
	public String getTableid() {
		return tableid;
	}
	public void setTableid(String tableid) {
		this.tableid = tableid;
	}
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
		return "TableInfoModel [tableid="+tableid+", databaseid=" + databaseid + ", name=" + name
				+ ", comments=" + comments + "]";
	}
}
