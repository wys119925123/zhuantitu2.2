package com.zhuantitu.model;
// default package

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserMenuPermission entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_user_menu_permission", schema = "public")
public class UserMenuPermission implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2076656687781637473L;
	private UserMenuPermissionId id;
	private ThematicMapMenu thematicMapMenu;
	private Integer addcodePermission;
	private Integer viewPermission;
	private ThematicUser thematicUser;

	// Constructors

	/** default constructor */
	public UserMenuPermission() {
	}

	/** minimal constructor */
	public UserMenuPermission(UserMenuPermissionId id,
			ThematicMapMenu thematicMapMenu) {
		this.id = id;
		this.thematicMapMenu = thematicMapMenu;
	}

	/** full constructor */
	public UserMenuPermission(UserMenuPermissionId id,
			ThematicMapMenu thematicMapMenu, Integer addcodePermission,
			Integer viewPermission) {
		this.id = id;
		this.thematicMapMenu = thematicMapMenu;
		this.addcodePermission = addcodePermission;
		this.viewPermission = viewPermission;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "menuid", column = @Column(name = "menuid", nullable = false)),
			@AttributeOverride(name = "userid", column = @Column(name = "userid", nullable = false)) })
	public UserMenuPermissionId getId() {
		return this.id;
	}

	public void setId(UserMenuPermissionId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menuid", nullable = false, insertable = false, updatable = false)
	public ThematicMapMenu getThematicMapMenu() {
		return this.thematicMapMenu;
	}

	public void setThematicMapMenu(ThematicMapMenu thematicMapMenu) {
		this.thematicMapMenu = thematicMapMenu;
	}

	@Column(name = "addcode_permission")
	public Integer getAddcodePermission() {
		return this.addcodePermission;
	}

	public void setAddcodePermission(Integer addcodePermission) {
		this.addcodePermission = addcodePermission;
	}

	@Column(name = "view_permission")
	public Integer getViewPermission() {
		return this.viewPermission;
	}

	public void setViewPermission(Integer viewPermission) {
		this.viewPermission = viewPermission;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid",nullable = false, insertable = false, updatable = false)
	public ThematicUser getThematicUser() {
		return thematicUser;
	}

	public void setThematicUser(ThematicUser thematicUser) {
		this.thematicUser = thematicUser;
	}

}