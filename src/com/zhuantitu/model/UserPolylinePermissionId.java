package com.zhuantitu.model;
// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserPolylinePermissionId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class UserPolylinePermissionId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6955043338460699614L;
	private String userid;
	private Integer polylineid;

	// Constructors

	/** default constructor */
	public UserPolylinePermissionId() {
	}

	/** full constructor */
	public UserPolylinePermissionId(String userid, Integer polylineid) {
		this.userid = userid;
		this.polylineid = polylineid;
	}

	// Property accessors

	@Column(name = "userid", nullable = false, length = 100)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "polylineid", nullable = false)
	public Integer getPolylineid() {
		return this.polylineid;
	}

	public void setPolylineid(Integer polylineid) {
		this.polylineid = polylineid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserPolylinePermissionId))
			return false;
		UserPolylinePermissionId castOther = (UserPolylinePermissionId) other;

		return ((this.getUserid() == castOther.getUserid()) || (this
				.getUserid() != null
				&& castOther.getUserid() != null && this.getUserid().equals(
				castOther.getUserid())))
				&& ((this.getPolylineid() == castOther.getPolylineid()) || (this
						.getPolylineid() != null
						&& castOther.getPolylineid() != null && this
						.getPolylineid().equals(castOther.getPolylineid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserid() == null ? 0 : this.getUserid().hashCode());
		result = 37
				* result
				+ (getPolylineid() == null ? 0 : this.getPolylineid()
						.hashCode());
		return result;
	}

}