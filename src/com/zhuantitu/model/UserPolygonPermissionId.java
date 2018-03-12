package com.zhuantitu.model;
// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserPolygonPermissionId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class UserPolygonPermissionId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -157713726461297623L;
	private String userid;
	private Integer polygonid;

	// Constructors

	/** default constructor */
	public UserPolygonPermissionId() {
	}

	/** full constructor */
	public UserPolygonPermissionId(String userid, Integer polygonid) {
		this.userid = userid;
		this.polygonid = polygonid;
	}

	// Property accessors

	@Column(name = "userid", nullable = false, length = 100)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "polygonid", nullable = false)
	public Integer getPolygonid() {
		return this.polygonid;
	}

	public void setPolygonid(Integer polygonid) {
		this.polygonid = polygonid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserPolygonPermissionId))
			return false;
		UserPolygonPermissionId castOther = (UserPolygonPermissionId) other;

		return ((this.getUserid() == castOther.getUserid()) || (this
				.getUserid() != null
				&& castOther.getUserid() != null && this.getUserid().equals(
				castOther.getUserid())))
				&& ((this.getPolygonid() == castOther.getPolygonid()) || (this
						.getPolygonid() != null
						&& castOther.getPolygonid() != null && this
						.getPolygonid().equals(castOther.getPolygonid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserid() == null ? 0 : this.getUserid().hashCode());
		result = 37 * result
				+ (getPolygonid() == null ? 0 : this.getPolygonid().hashCode());
		return result;
	}

}