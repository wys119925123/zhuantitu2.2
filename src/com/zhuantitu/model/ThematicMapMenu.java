package com.zhuantitu.model;
// default package

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
 * ThematicMapMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_thematic_map_menu", schema = "public")
@SequenceGenerator(name="generator",sequenceName="zt_thematic_map_menu_menuid_seq",allocationSize=1)
public class ThematicMapMenu implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2483729000507505137L;
	private Integer menuid;
	private ThematicMapMenu parentThematicMapMenu;
	private String name;
	private String icon;
	private String selecticon;
	private String theme;
	private String appIcon;
	private Integer isleaf;
	private Integer orderid;
	private Integer geomtype;
	private Date posttime;
	private String memo;
	private Integer enableDel;
	private Set<UserMenuPermission> userMenuPermissions = new HashSet<UserMenuPermission>(
			0);
	private Set<ThematicPointCategory> thematicPointCategories = new HashSet<ThematicPointCategory>(
			0);
	private List<ThematicMapMenu> thematicMapMenus = new ArrayList<ThematicMapMenu>(
			0);

	// Constructors

	/** default constructor */
	public ThematicMapMenu() {
	}

	/** minimal constructor */
	public ThematicMapMenu(Integer menuid) {
		this.menuid = menuid;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@Column(name = "menuid", unique = true, nullable = false)
	public Integer getMenuid() {
		return this.menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	


	@Column(name = "name", length = 100)
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

	@Column(name = "selecticon")
	public String getSelecticon() {
		return this.selecticon;
	}

	public void setSelecticon(String selecticon) {
		this.selecticon = selecticon;
	}

	@Column(name = "theme", length = 100)
	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "isleaf")
	public Integer getIsleaf() {
		return this.isleaf;
	}

	public void setIsleaf(Integer isleaf) {
		this.isleaf = isleaf;
	}

	@Column(name = "orderid")
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "geomtype")
	public Integer getGeomtype() {
		return this.geomtype;
	}

	public void setGeomtype(Integer geomtype) {
		this.geomtype = geomtype;
	}

	@Column(name = "posttime", length = 29)
	public Date getPosttime() {
		return this.posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	@Column(name = "memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "thematicMapMenu")
	public Set<UserMenuPermission> getUserMenuPermissions() {
		return this.userMenuPermissions;
	}

	public void setUserMenuPermissions(
			Set<UserMenuPermission> userMenuPermissions) {
		this.userMenuPermissions = userMenuPermissions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "thematicMapMenu")
	public Set<ThematicPointCategory> getThematicPointCategories() {
		return this.thematicPointCategories;
	}

	public void setThematicPointCategories(
			Set<ThematicPointCategory> thematicPointCategories) {
		this.thematicPointCategories = thematicPointCategories;
	}

	
	@Column(name = "app_icon")
	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}
	@Column(name = "enable_del")
	public Integer getEnableDel() {
		return enableDel;
	}

	public void setEnableDel(Integer enableDel) {
		this.enableDel = enableDel;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentThematicMapMenu")
	public List<ThematicMapMenu> getThematicMapMenus() {
		return thematicMapMenus;
	}

	public void setThematicMapMenus(List<ThematicMapMenu> thematicMapMenus) {
		this.thematicMapMenus = thematicMapMenus;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_menuid")
	public ThematicMapMenu getParentThematicMapMenu() {
		return parentThematicMapMenu;
	}

	public void setParentThematicMapMenu(ThematicMapMenu parentThematicMapMenu) {
		this.parentThematicMapMenu = parentThematicMapMenu;
	}

}