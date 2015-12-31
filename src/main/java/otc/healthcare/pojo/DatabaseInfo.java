package otc.healthcare.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dataBaseInfoModel")
public class DatabaseInfo {
	private String databaseid;
	private String name;
	private String zhcnname;
	private String comments;
	private String identifier;
	private String language;
	private String charset;
	private String subjectclassification;
	private String keywords;
	private String credibility;
	private String resinstitution;
	private String resname;
	private String resaddress;
	private String respostalcode;
	private String resphone;
	private String resemail;
	private String resourceurl;
	private List<TableInfo> tablelist;

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
		return "DatabaseInfoModel [databaseid=" + databaseid + ", name=" + name + ", comments=" + comments
				+ ",identifier=" + identifier + ",language=" + language + ",charset=" + charset
				+ ",subjectclassification=" + subjectclassification + ",keywords=" + keywords + "]";
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSubjectclassification() {
		return subjectclassification;
	}

	public void setSubjectclassification(String subjectclassification) {
		this.subjectclassification = subjectclassification;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getCredibility() {
		return credibility;
	}

	public void setCredibility(String credibility) {
		this.credibility = credibility;
	}

	public String getResinstitution() {
		return resinstitution;
	}

	public void setResinstitution(String resinstitution) {
		this.resinstitution = resinstitution;
	}

	public String getResname() {
		return resname;
	}

	public void setResname(String resname) {
		this.resname = resname;
	}

	public String getResaddress() {
		return resaddress;
	}

	public void setResaddress(String resaddress) {
		this.resaddress = resaddress;
	}

	public String getRespostalcode() {
		return respostalcode;
	}

	public void setRespostalcode(String respostalcode) {
		this.respostalcode = respostalcode;
	}

	public String getResphone() {
		return resphone;
	}

	public void setResphone(String resphone) {
		this.resphone = resphone;
	}

	public String getResemail() {
		return resemail;
	}

	public void setResemail(String resemail) {
		this.resemail = resemail;
	}

	public String getResourceurl() {
		return resourceurl;
	}

	public void setResourceurl(String resourceurl) {
		this.resourceurl = resourceurl;
	}

	/**
	 * @return the tablelist
	 */
	public List<TableInfo> getTablelist() {
		return tablelist;
	}

	/**
	 * @param tablelist
	 *            the tablelist to set
	 */
	public void setTablelist(List<TableInfo> tablelist) {
		this.tablelist = tablelist;
	}

	/**
	 * @return the zhcnname
	 */
	@XmlElement(name = "zhcnname")
	public String getZhcnname() {
		return zhcnname;
	}

	/**
	 * @param zhcnname
	 *            the zhcnname to set
	 */
	public void setZhcnname(String zhcnname) {
		this.zhcnname = zhcnname;
	}

}
