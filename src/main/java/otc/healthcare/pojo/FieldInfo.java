package otc.healthcare.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dataBaseInfoModel")
public class FieldInfo {
	private String fieldid;
	private String databaseid;
	private String tableid;
	private String name;
	private String zhcnname;
	private String datatype;
	private String datalength;
	private String nullable;
	private String comments;
	private String datadictionary;

	@XmlElement(name = "datalength")
	public String getDatalength() {
		return datalength;
	}

	public void setDatalength(String datalength) {
		this.datalength = datalength;
	}

	@XmlElement(name = "nullable")
	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	@XmlElement(name = "fieldid")
	public String getFieldid() {
		return fieldid;
	}

	public void setFieldid(String fieldid) {
		this.fieldid = fieldid;
	}

	@XmlElement(name = "databaseid")
	public String getDatabaseid() {
		return databaseid;
	}

	public void setDatabaseid(String databaseid) {
		this.databaseid = databaseid;
	}

	@XmlElement(name = "tableid")
	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "datatype")
	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
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
		return "FieldInfoModel [fieldid=" + fieldid + ", databaseid=" + databaseid + ", tableid=" + tableid + ", name="
				+ name + ", datatype=" + datatype + ", comments=" + comments + "]";
	}

	/**
	 * @return the datadictionary
	 */
	@XmlElement(name = "datadictionary")
	public String getDatadictionary() {
		return datadictionary;
	}

	/**
	 * @param datadictionary
	 *            the datadictionary to set
	 */
	public void setDatadictionary(String datadictionary) {
		this.datadictionary = datadictionary;
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
