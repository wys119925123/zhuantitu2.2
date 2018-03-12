package com.zhuantitu.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "zt_thematic_location", schema = "public")
@SequenceGenerator(name="generator",sequenceName="zt_thematic_location_locationid_seq",allocationSize=1)
public class ThematicLocation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8841961313643405417L;
	private Integer locationid;
	private ThematicLocation parentThematicLocation;
	private String name;
	private Integer orderid;
	private String zoneid;
	public ThematicLocation(){}
	public ThematicLocation(Integer locationid){
		this.locationid = locationid;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@Column(name = "locationid", unique = true, nullable = false)
	public Integer getLocationid() {
		return locationid;
	}
	public void setLocationid(Integer locationid) {
		this.locationid = locationid;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="orderid")
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	@Column(name="zoneid")
	public String getZoneid() {
		return zoneid;
	}
	public void setZoneid(String zoneid) {
		this.zoneid = zoneid;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_locationid")
	public ThematicLocation getParentThematicLocation() {
		return parentThematicLocation;
	}
	public void setParentThematicLocation(ThematicLocation parentThematicLocation) {
		this.parentThematicLocation = parentThematicLocation;
	}
	
}
