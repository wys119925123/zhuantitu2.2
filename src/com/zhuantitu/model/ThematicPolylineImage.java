package com.zhuantitu.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ThematicPolylineImage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_thematic_polyline_image")
@SequenceGenerator(name="generator",sequenceName="zt_thematic_polyline_image_imageid_seq",allocationSize=1)

public class ThematicPolylineImage implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5630988545071120838L;
	private Integer imageid;
	private ThematicPolyline thematicPolyline;
	private String name;
	private String path;
	private Integer orderid;
	private String memo;

	// Constructors

	/** default constructor */
	public ThematicPolylineImage() {
	}

	/** minimal constructor */
	public ThematicPolylineImage(Integer imageid) {
		this.imageid = imageid;
	}

	/** full constructor */
	public ThematicPolylineImage(Integer imageid,
			ThematicPolyline thematicPolyline, String name, String path,
			Integer orderid, String memo) {
		this.imageid = imageid;
		this.thematicPolyline = thematicPolyline;
		this.name = name;
		this.path = path;
		this.orderid = orderid;
		this.memo = memo;
	}

	// Property accessors
	@Id
	@Column(name = "imageid", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	public Integer getImageid() {
		return this.imageid;
	}

	public void setImageid(Integer imageid) {
		this.imageid = imageid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "polylineid")
	public ThematicPolyline getThematicPolyline() {
		return this.thematicPolyline;
	}

	public void setThematicPolyline(ThematicPolyline thematicPolyline) {
		this.thematicPolyline = thematicPolyline;
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