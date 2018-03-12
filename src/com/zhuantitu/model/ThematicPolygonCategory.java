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
 * ThematicPolygonCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_thematic_polygon_category")
@SequenceGenerator(name="generator",sequenceName="zt_thematic_polygon_category_categoryid_seq",allocationSize=1)
public class ThematicPolygonCategory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1951150288555922694L;
	// Fields

	private Integer categoryid;
	private ThematicMapMenu thematicMapMenu;
	private String name;
	private String icon;
	private String strokecolor;
	private String bordercolor;
	private Integer orderid;
	private Date posttime;
	private Integer enableDel;
	private Integer minZoom;
	private Integer maxZoom;
	private Integer isClick;
	private Set<ThematicPolygon> thematicPolygons = new HashSet<ThematicPolygon>(
			0);

	// Constructors

	/** default constructor */
	public ThematicPolygonCategory() {
	}

	/** minimal constructor */
	public ThematicPolygonCategory(Integer categoryid) {
		this.categoryid = categoryid;
	}

	/** full constructor */
	public ThematicPolygonCategory(Integer categoryid,
			ThematicMapMenu thematicMapMenu, String name, String icon,
			String strokecolor, String bordercolor, Integer orderid,
			Date posttime, Set<ThematicPolygon> thematicPolygons) {
		this.categoryid = categoryid;
		this.thematicMapMenu = thematicMapMenu;
		this.name = name;
		this.icon = icon;
		this.strokecolor = strokecolor;
		this.bordercolor = bordercolor;
		this.orderid = orderid;
		this.posttime = posttime;
		this.thematicPolygons = thematicPolygons;
	}

	// Property accessors
	@Id
	@Column(name = "categoryid", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
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

	@Column(name = "name", length = 20)
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

	@Column(name = "strokecolor", length = 6)
	public String getStrokecolor() {
		return this.strokecolor;
	}

	public void setStrokecolor(String strokecolor) {
		this.strokecolor = strokecolor;
	}

	@Column(name = "bordercolor", length = 6)
	public String getBordercolor() {
		return this.bordercolor;
	}

	public void setBordercolor(String bordercolor) {
		this.bordercolor = bordercolor;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "thematicPolygonCategory")
	public Set<ThematicPolygon> getThematicPolygons() {
		return this.thematicPolygons;
	}

	public void setThematicPolygons(Set<ThematicPolygon> thematicPolygons) {
		this.thematicPolygons = thematicPolygons;
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