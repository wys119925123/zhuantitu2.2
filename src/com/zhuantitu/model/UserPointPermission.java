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
 * UserPointPermission entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_user_point_permission", schema = "public")
public class UserPointPermission implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1953542466200309854L;
	private UserPointPermissionId id;
	private ThematicPoint thematicPoint;
	private Integer editPermission;
	private Integer deletePermission;

	// Constructors

	/** default constructor */
	public UserPointPermission() {
	}

	/** minimal constructor */
	public UserPointPermission(UserPointPermissionId id,
			ThematicPoint thematicPoint) {
		this.id = id;
		this.thematicPoint = thematicPoint;
	}

	/** full constructor */
	public UserPointPermission(UserPointPermissionId id,
			ThematicPoint thematicPoint, Integer editPermission,
			Integer deletePermission) {
		this.id = id;
		this.thematicPoint = thematicPoint;
		this.editPermission = editPermission;
		this.deletePermission = deletePermission;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "userid", column = @Column(name = "userid", nullable = false)),
			@AttributeOverride(name = "pointid", column = @Column(name = "pointid", nullable = false)) })
	public UserPointPermissionId getId() {
		return this.id;
	}

	public void setId(UserPointPermissionId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pointid", nullable = false, insertable = false, updatable = false)
	public ThematicPoint getThematicPoint() {
		return this.thematicPoint;
	}

	public void setThematicPoint(ThematicPoint thematicPoint) {
		this.thematicPoint = thematicPoint;
	}

	@Column(name = "edit_permission")
	public Integer getEditPermission() {
		return this.editPermission;
	}

	public void setEditPermission(Integer editPermission) {
		this.editPermission = editPermission;
	}

	@Column(name = "delete_permission")
	public Integer getDeletePermission() {
		return this.deletePermission;
	}

	public void setDeletePermission(Integer deletePermission) {
		this.deletePermission = deletePermission;
	}

}