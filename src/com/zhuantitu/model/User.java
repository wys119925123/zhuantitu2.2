package com.zhuantitu.model;

public class User {
	
	private String userid;
	private String name;
	private String nickname;
	private String avatar;
	private String rolename;
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public User(){}
	public User(String userid){
		this.userid = userid;
	}
	public User(String userid, String name, String nickname, String avatar,String rolename) {
		this.userid = userid;
		this.name = name;
		this.nickname = nickname;
		this.avatar = avatar;
		this.rolename = rolename;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
