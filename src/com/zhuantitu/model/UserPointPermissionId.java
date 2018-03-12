package com.zhuantitu.model;
// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserPointPermissionId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class UserPointPermissionId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 814770268532816030L;
	private String userid;
	private Integer pointid;

	// Constructors

	/** default constructor */
	public UserPointPermissionId() {
	}

	/** full constructor */
	public UserPointPermissionId(String userid, Integer pointid) {
		this.userid = userid;
		this.pointid = pointid;
	}

	// Property accessors

	@Column(name = "userid", nullable = false)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "pointid", nullable = false)
	public Integer getPointid() {
		return this.pointid;
	}

	public void setPointid(Integer pointid) {
		this.pointid = pointid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserPointPermissionId))
			return false;
		UserPointPermissionId castOther = (UserPointPermissionId) other;

		return ((this.getUserid() == castOther.getUserid()) || (this
				.getUserid() != null
				&& castOther.getUserid() != null && this.getUserid().equals(
				castOther.getUserid())))
				&& ((this.getPointid() == castOther.getPointid()) || (this
						.getPointid() != null
						&& castOther.getPointid() != null && this.getPointid()
						.equals(castOther.getPointid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserid() == null ? 0 : this.getUserid().hashCode());
		result = 37 * result
				+ (getPointid() == null ? 0 : this.getPointid().hashCode());
		return result;
	}

}