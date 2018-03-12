package com.map.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "map_campus")
@SequenceGenerator(name="generator",sequenceName="map_campus_campusid_seq",allocationSize=1)

public class MapCampus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8825408482151818702L;
	private Integer campusid;
	private String name;
	private String campusImg;
	private Integer isDefault;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@Column(name = "campusid", unique = true, nullable = false) 
	public Integer getCampusid() {
		return campusid;
	}
	public void setCampusid(Integer campusid) {
		this.campusid = campusid;
	}
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "campus_img")
	public String getCampusImg() {
		return campusImg;
	}
	public void setCampusImg(String campusImg) {
		this.campusImg = campusImg;
	}
	@Column(name = "is_default")
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
}
