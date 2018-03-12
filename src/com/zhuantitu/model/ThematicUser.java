package com.zhuantitu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "zt_thematic_user")
public class ThematicUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 364779702577185201L;
	private String userid;
	private String name;
	private String deptname;
	private Date posttime;
	private String password;
	private String avatar;
	private List<UserMenuPermission> userMenuPermissions = new ArrayList<UserMenuPermission>(0);
	public ThematicUser(){
	}
	public ThematicUser(String userid){
		this.userid = userid;
	}
	@Id
	@Column(name = "userid", unique = true, nullable = false, length = 100)
	@GeneratedValue(generator = "myIdGenerator")     
	@GenericGenerator(name = "myIdGenerator", strategy = "assigned")
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="deptname")
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	@Column(name="posttime")
	public Date getPosttime() {
		return posttime;
	}
	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "thematicUser")
	public List<UserMenuPermission> getUserMenuPermissions() {
		return userMenuPermissions;
	}
	public void setUserMenuPermissions(List<UserMenuPermission> userMenuPermissions) {
		this.userMenuPermissions = userMenuPermissions;
	}
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="avatar")
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
