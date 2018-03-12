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
 * UserPolylinePermission entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_user_polyline_permission")
public class UserPolylinePermission implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -9211313595795803127L;
	private UserPolylinePermissionId id;
	private ThematicPolyline thematicPolyline;
	private Integer editPermission;
	private Integer deletePermission;

	// Constructors

	/** default constructor */
	public UserPolylinePermission() {
	}

	/** minimal constructor */
	public UserPolylinePermission(UserPolylinePermissionId id,
			ThematicPolyline thematicPolyline) {
		this.id = id;
		this.thematicPolyline = thematicPolyline;
	}

	/** full constructor */
	public UserPolylinePermission(UserPolylinePermissionId id,
			ThematicPolyline thematicPolyline, Integer editPermission,
			Integer deletePermission) {
		this.id = id;
		this.thematicPolyline = thematicPolyline;
		this.editPermission = editPermission;
		this.deletePermission = deletePermission;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "userid", column = @Column(name = "userid", nullable = false, length = 100)),
			@AttributeOverride(name = "polylineid", column = @Column(name = "polylineid", nullable = false)) })
	public UserPolylinePermissionId getId() {
		return this.id;
	}

	public void setId(UserPolylinePermissionId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "polylineid", nullable = false, insertable = false, updatable = false)
	public ThematicPolyline getThematicPolyline() {
		return this.thematicPolyline;
	}

	public void setThematicPolyline(ThematicPolyline thematicPolyline) {
		this.thematicPolyline = thematicPolyline;
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