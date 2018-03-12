package com.zhuantitu.model;
// default package

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * MeterDayStatistics entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_meter_day_statistics", schema = "public")
public class MeterDayStatistics implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5385356407963088291L;
	private MeterDayStatisticsId id;
	private Date finishTime;
	private Double energyValue;
	private String unit;
	private String floorid;
	private Integer locationid;
	private String metertype;

	// Constructors

	/** default constructor */
	public MeterDayStatistics() {
	}

	/** minimal constructor */
	public MeterDayStatistics(MeterDayStatisticsId id) {
		this.id = id;
	}

	/** full constructor */
	public MeterDayStatistics(MeterDayStatisticsId id, Date finishTime,
			Double energyValue, String unit) {
		this.id = id;
		this.finishTime = finishTime;
		this.energyValue = energyValue;
		this.unit = unit;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "meterid", column = @Column(name = "meterid", nullable = false)),
			@AttributeOverride(name = "startTime", column = @Column(name = "start_time", nullable = false, length = 29)) })
	public MeterDayStatisticsId getId() {
		return this.id;
	}

	public void setId(MeterDayStatisticsId id) {
		this.id = id;
	}

	@Column(name = "finish_time", length = 21)
	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	@Column(name = "energy_value", precision = 17, scale = 17)
	public Double getEnergyValue() {
		return this.energyValue;
	}

	public void setEnergyValue(Double energyValue) {
		this.energyValue = energyValue;
	}

	@Column(name = "unit", length = 100)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(name = "floorid")
	public String getFloorid() {
		return floorid;
	}

	public void setFloorid(String floorid) {
		this.floorid = floorid;
	}
	@Column(name = "locationid")
	public Integer getLocationid() {
		return locationid;
	}

	public void setLocationid(Integer locationid) {
		this.locationid = locationid;
	}
	@Column(name = "metertype")
	public String getMetertype() {
		return metertype;
	}

	public void setMetertype(String metertype) {
		this.metertype = metertype;
	}

}