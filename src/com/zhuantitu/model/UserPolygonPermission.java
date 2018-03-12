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
 * UserPolygonPermission entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_user_polygon_permission")
public class UserPolygonPermission implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4496013323744209794L;
	private UserPolygonPermissionId id;
	private ThematicPolygon thematicPolygon;
	private Integer editPermission;
	private Integer deletePermission;

	// Constructors

	/** default constructor */
	public UserPolygonPermission() {
	}

	/** minimal constructor */
	public UserPolygonPermission(UserPolygonPermissionId id,
			ThematicPolygon thematicPolygon) {
		this.id = id;
		this.thematicPolygon = thematicPolygon;
	}

	/** full constructor */
	public UserPolygonPermission(UserPolygonPermissionId id,
			ThematicPolygon thematicPolygon, Integer editPermission,
			Integer deletePermission) {
		this.id = id;
		this.thematicPolygon = thematicPolygon;
		this.editPermission = editPermission;
		this.deletePermission = deletePermission;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "userid", column = @Column(name = "userid", nullable = false, length = 100)),
			@AttributeOverride(name = "polygonid", column = @Column(name = "polygonid", nullable = false)) })
	public UserPolygonPermissionId getId() {
		return this.id;
	}

	public void setId(UserPolygonPermissionId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "polygonid", nullable = false, insertable = false, updatable = false)
	public ThematicPolygon getThematicPolygon() {
		return this.thematicPolygon;
	}

	public void setThematicPolygon(ThematicPolygon thematicPolygon) {
		this.thematicPolygon = thematicPolygon;
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