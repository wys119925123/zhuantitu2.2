package com.zhuantitu.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * CameraIcon entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_camera_icon", schema = "public")
@SequenceGenerator(name="generator",sequenceName="zt_camera_icon_iconid_seq",allocationSize=1)

public class CameraIcon implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3803120934196766626L;
	private Integer iconid;
	private String name;
	private String path;
	private Integer orderid;
	private String memo;

	// Constructors

	/** default constructor */
	public CameraIcon() {
	}

	/** minimal constructor */
	public CameraIcon(Integer iconid) {
		this.iconid = iconid;
	}

	/** full constructor */
	public CameraIcon(Integer iconid, String name, String path,
			Integer orderid, String memo) {
		this.iconid = iconid;
		this.name = name;
		this.path = path;
		this.orderid = orderid;
		this.memo = memo;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@Column(name = "iconid", unique = true, nullable = false)
	public Integer getIconid() {
		return this.iconid;
	}

	public void setIconid(Integer iconid) {
		this.iconid = iconid;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "path")
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "orderid")
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}