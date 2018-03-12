package com.zhuantitu.model;
// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TableClumnDefineId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TableClumnDefineId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1375126051026057649L;
	private String tableName;
	private String columnName;
	private Integer categoryid;

	// Constructors

	/** default constructor */
	public TableClumnDefineId() {
	}

	/** full constructor */
	public TableClumnDefineId(String tableName, String columnName,
			Integer categoryid) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.categoryid = categoryid;
	}

	// Property accessors

	@Column(name = "table_name", nullable = false, length = 100)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "column_name", nullable = false, length = 100)
	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Column(name = "categoryid", nullable = false)
	public Integer getCategoryid() {
		return this.categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TableClumnDefineId))
			return false;
		TableClumnDefineId castOther = (TableClumnDefineId) other;

		return ((this.getTableName() == castOther.getTableName()) || (this
				.getTableName() != null
				&& castOther.getTableName() != null && this.getTableName()
				.equals(castOther.getTableName())))
				&& ((this.getColumnName() == castOther.getColumnName()) || (this
						.getColumnName() != null
						&& castOther.getColumnName() != null && this
						.getColumnName().equals(castOther.getColumnName())))
				&& ((this.getCategoryid() == castOther.getCategoryid()) || (this
						.getCategoryid() != null
						&& castOther.getCategoryid() != null && this
						.getCategoryid().equals(castOther.getCategoryid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTableName() == null ? 0 : this.getTableName().hashCode());
		result = 37
				* result
				+ (getColumnName() == null ? 0 : this.getColumnName()
						.hashCode());
		result = 37
				* result
				+ (getCategoryid() == null ? 0 : this.getCategoryid()
						.hashCode());
		return result;
	}

}