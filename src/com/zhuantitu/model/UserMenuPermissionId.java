package com.zhuantitu.model;
// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserMenuPermissionId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class UserMenuPermissionId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3787555326348014299L;
	private Integer menuid;
	private String userid;

	// Constructors

	/** default constructor */
	public UserMenuPermissionId() {
	}

	/** full constructor */
	public UserMenuPermissionId(Integer menuid, String userid) {
		this.menuid = menuid;
		this.userid = userid;
	}

	// Property accessors

	@Column(name = "menuid", nullable = false)
	public Integer getMenuid() {
		return this.menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	@Column(name = "userid", nullable = false)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserMenuPermissionId))
			return false;
		UserMenuPermissionId castOther = (UserMenuPermissionId) other;

		return ((this.getMenuid() == castOther.getMenuid()) || (this
				.getMenuid() != null
				&& castOther.getMenuid() != null && this.getMenuid().equals(
				castOther.getMenuid())))
				&& ((this.getUserid() == castOther.getUserid()) || (this
						.getUserid() != null
						&& castOther.getUserid() != null && this.getUserid()
						.equals(castOther.getUserid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMenuid() == null ? 0 : this.getMenuid().hashCode());
		result = 37 * result
				+ (getUserid() == null ? 0 : this.getUserid().hashCode());
		return result;
	}

}