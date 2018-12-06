package com.zhuantitu.model;

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
@Table(name = "zt_thematic_equipment_image")
@SequenceGenerator(name="generator",sequenceName="zt_thematic_equipment_image_imageid_seq",allocationSize=1)

public class ThematicPolylineEquipmentImage implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer imageid;
	private ThematicPolylineEquipment thematicPolylineEquipment;
	private String name;
	private String path;
	private Integer orderid;
	private String memo;
	@Id
	@Column(name = "imageid", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	public Integer getImageid() {
		return this.imageid;
	}

	public void setImageid(Integer imageid) {
		this.imageid = imageid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	public ThematicPolylineEquipment getThematicPolylineEquipment() {
		return thematicPolylineEquipment;
	}

	public void setThematicPolylineEquipment(
			ThematicPolylineEquipment thematicPolylineEquipment) {
		this.thematicPolylineEquipment = thematicPolylineEquipment;
	}

	
	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "path")
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "orderid")
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
