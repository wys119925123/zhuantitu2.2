package com.zhuantitu.model;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MeterDayStatisticsId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class MeterDayStatisticsId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7255898173263174449L;
	private Integer meterid;
	private Date startTime;

	// Constructors

	/** default constructor */
	public MeterDayStatisticsId() {
	}

	/** full constructor */
	public MeterDayStatisticsId(Integer meterid, Date startTime) {
		this.meterid = meterid;
		this.startTime = startTime;
	}

	// Property accessors

	@Column(name = "meterid", nullable = false)
	public Integer getMeterid() {
		return this.meterid;
	}

	public void setMeterid(Integer meterid) {
		this.meterid = meterid;
	}

	@Column(name = "start_time", nullable = false, length = 29)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MeterDayStatisticsId))
			return false;
		MeterDayStatisticsId castOther = (MeterDayStatisticsId) other;

		return ((this.getMeterid() == castOther.getMeterid()) || (this
				.getMeterid() != null
				&& castOther.getMeterid() != null && this.getMeterid().equals(
				castOther.getMeterid())))
				&& ((this.getStartTime() == castOther.getStartTime()) || (this
						.getStartTime() != null
						&& castOther.getStartTime() != null && this
						.getStartTime().equals(castOther.getStartTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMeterid() == null ? 0 : this.getMeterid().hashCode());
		result = 37 * result
				+ (getStartTime() == null ? 0 : this.getStartTime().hashCode());
		return result;
	}

}