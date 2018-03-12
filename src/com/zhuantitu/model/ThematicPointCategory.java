package com.zhuantitu.model;
// default package

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ThematicPointCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_thematic_point_category", schema = "public")
@SequenceGenerator(name="generator",sequenceName="zt_thematic_point_category_categoryid_seq",allocationSize=1)

public class ThematicPointCategory implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1142893009717938459L;
	private Integer categoryid;
	private ThematicMapMenu thematicMapMenu;
	private String name;
	private String icon;
	private Integer orderid;
	private Date posttime;
	private Integer enableDel;
	private Integer minZoom;
	private Integer maxZoom;
	private Integer isClick;
	private Set<ThematicPoint> thematicPoints = new HashSet<ThematicPoint>(0);

	// Constructors

	/** default constructor */
	public ThematicPointCategory() {
	}

	/** minimal constructor */
	public ThematicPointCategory(Integer categoryid) {
		this.categoryid = categoryid;
	}

	/** full constructor */
	public ThematicPointCategory(Integer categoryid,
			ThematicMapMenu thematicMapMenu, String name, String icon,
			Integer orderid, Date posttime,
			Set<ThematicPoint> thematicPoints) {
		this.categoryid = categoryid;
		this.thematicMapMenu = thematicMapMenu;
		this.name = name;
		this.icon = icon;
		this.orderid = orderid;
		this.posttime = posttime;
		this.thematicPoints = thematicPoints;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@Column(name = "categoryid", unique = true, nullable = false)
	public Integer getCategoryid() {
		return this.categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menuid")
	public ThematicMapMenu getThematicMapMenu() {
		return this.thematicMapMenu;
	}

	public void setThematicMapMenu(ThematicMapMenu thematicMapMenu) {
		this.thematicMapMenu = thematicMapMenu;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "icon")
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "orderid")
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "posttime", length = 29)
	public Date getPosttime() {
		return this.posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "thematicPointCategory")
	public Set<ThematicPoint> getThematicPoints() {
		return this.thematicPoints;
	}

	public void setThematicPoints(Set<ThematicPoint> thematicPoints) {
		this.thematicPoints = thematicPoints;
	}
	@Column(name="enable_del")
	public Integer getEnableDel() {
		return enableDel;
	}

	public void setEnableDel(Integer enableDel) {
		this.enableDel = enableDel;
	}
	@Column(name = "min_zoom")
	public Integer getMinZoom() {
		return minZoom;
	}

	public void setMinZoom(Integer minZoom) {
		this.minZoom = minZoom;
	}
	@Column(name = "max_zoom")
	public Integer getMaxZoom() {
		return maxZoom;
	}

	public void setMaxZoom(Integer maxZoom) {
		this.maxZoom = maxZoom;
	}
	@Column(name = "is_click")
	public Integer getIsClick() {
		return isClick;
	}

	public void setIsClick(Integer isClick) {
		this.isClick = isClick;
	}

}