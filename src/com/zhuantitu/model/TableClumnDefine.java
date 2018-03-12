package com.zhuantitu.model;
// default package

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TableClumnDefine entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_table_clumn_define", schema = "public")
public class TableClumnDefine implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6189052012423392069L;
	private TableClumnDefineId id;
	private String columnCnname;
	private Integer columnType;
	private Integer isRequired;
	private Integer orderid;
	private Integer isShow;
	private String memo;

	// Constructors

	/** default constructor */
	public TableClumnDefine() {
	}

	/** minimal constructor */
	public TableClumnDefine(TableClumnDefineId id) {
		this.id = id;
	}

	/** full constructor */
	public TableClumnDefine(TableClumnDefineId id, String columnCnname,
			Integer columnType, Integer isRequired, Integer orderid,
			Integer isShow, String memo) {
		this.id = id;
		this.columnCnname = columnCnname;
		this.columnType = columnType;
		this.isRequired = isRequired;
		this.orderid = orderid;
		this.isShow = isShow;
		this.memo = memo;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "tableName", column = @Column(name = "table_name", nullable = false, length = 100)),
			@AttributeOverride(name = "columnName", column = @Column(name = "column_name", nullable = false, length = 100)),
			@AttributeOverride(name = "categoryid", column = @Column(name = "categoryid", nullable = false)) })
	public TableClumnDefineId getId() {
		return this.id;
	}

	public void setId(TableClumnDefineId id) {
		this.id = id;
	}

	@Column(name = "column_cnname", length = 100)
	public String getColumnCnname() {
		return this.columnCnname;
	}

	public void setColumnCnname(String columnCnname) {
		this.columnCnname = columnCnname;
	}

	@Column(name = "column_type")
	public Integer getColumnType() {
		return this.columnType;
	}

	public void setColumnType(Integer columnType) {
		this.columnType = columnType;
	}

	@Column(name = "is_required")
	public Integer getIsRequired() {
		return this.isRequired;
	}

	public void setIsRequired(Integer isRequired) {
		this.isRequired = isRequired;
	}

	@Column(name = "orderid")
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "is_show")
	public Integer getIsShow() {
		return this.isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	@Column(name = "memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}