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
 * ThematicPointImage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_thematic_point_image", schema = "public")
@SequenceGenerator(name="generator",sequenceName="zt_thematic_point_image_imageid_seq",allocationSize=1)
public class ThematicPointImage implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7514484083102188424L;
	private Integer imageid;
	private ThematicPoint thematicPoint;
	private String name;
	private String path;
	private Integer orderid;
	private String memo;

	// Constructors

	/** default constructor */
	public ThematicPointImage() {
	}

	/** minimal constructor */
	public ThematicPointImage(Integer imageid) {
		this.imageid = imageid;
	}

	/** full constructor */
	public ThematicPointImage(Integer imageid, ThematicPoint thematicPoint,
			String name, String path, Integer orderid, String memo) {
		this.imageid = imageid;
		this.thematicPoint = thematicPoint;
		this.name = name;
		this.path = path;
		this.orderid = orderid;
		this.memo = memo;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@Column(name = "imageid", unique = true, nullable = false)
	public Integer getImageid() {
		return this.imageid;
	}

	public void setImageid(Integer imageid) {
		this.imageid = imageid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pointid")
	public ThematicPoint getThematicPoint() {
		return this.thematicPoint;
	}

	public void setThematicPoint(ThematicPoint thematicPoint) {
		this.thematicPoint = thematicPoint;
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