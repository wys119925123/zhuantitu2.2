package com.map.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 地图基础信息
 */
@Entity
@Table(name = "map_zone")
public class MapZone implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4889687637229252373L;
	private String zoneid;
	private MapCampus mapCampus;
	private String name;
	private Double leftBottomLon;
	private Double leftBottomLat;
	private Double rightTopLon;
	private Double rightTopLat;
	private Integer minZoom;
	private Integer maxZoom;
	private Integer defaultZoom;
	private Double maxResolution;
	private String layerName;
	private String baseLayerName;
	private String navigationVersion;
	private Double centerLon;
	private Double centerLat;
	private String memo;
	private String maptype;
	private Integer openIndoorNavigation;


	/** default constructor */
	public MapZone() {
	}

	/** minimal constructor */
	public MapZone(String zoneid) {
		this.zoneid = zoneid;
	}

	/** full constructor */
	public MapZone(String zoneid, String name, Double leftBottomLon,
			Double leftBottomLat, Double rightTopLon, Double rightTopLat,
			Integer minZoom, Integer maxZoom, Integer defaultZoom,
			Double maxResolution, String layerName, String memo) {
		this.zoneid = zoneid;
		this.name = name;
		this.leftBottomLon = leftBottomLon;
		this.leftBottomLat = leftBottomLat;
		this.rightTopLon = rightTopLon;
		this.rightTopLat = rightTopLat;
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
		this.defaultZoom = defaultZoom;
		this.maxResolution = maxResolution;
		this.layerName = layerName;
		this.memo = memo;
	}

	@Id
	@Column(name = "zoneid", unique = true, nullable = false, length = 4)
	@GeneratedValue(generator = "myIdGenerator")     
	@GenericGenerator(name = "myIdGenerator", strategy = "assigned")  
	public String getZoneid() {
		return this.zoneid;
	}

	public void setZoneid(String zoneid) {
		this.zoneid = zoneid;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "left_bottom_lon", precision = 17, scale = 17)
	public Double getLeftBottomLon() {
		return this.leftBottomLon;
	}

	public void setLeftBottomLon(Double leftBottomLon) {
		this.leftBottomLon = leftBottomLon;
	}

	@Column(name = "left_bottom_lat", precision = 17, scale = 17)
	public Double getLeftBottomLat() {
		return this.leftBottomLat;
	}

	public void setLeftBottomLat(Double leftBottomLat) {
		this.leftBottomLat = leftBottomLat;
	}

	@Column(name = "right_top_lon", precision = 17, scale = 17)
	public Double getRightTopLon() {
		return this.rightTopLon;
	}

	public void setRightTopLon(Double rightTopLon) {
		this.rightTopLon = rightTopLon;
	}

	@Column(name = "right_top_lat", precision = 17, scale = 17)
	public Double getRightTopLat() {
		return this.rightTopLat;
	}

	public void setRightTopLat(Double rightTopLat) {
		this.rightTopLat = rightTopLat;
	}

	@Column(name = "min_zoom")
	public Integer getMinZoom() {
		return this.minZoom;
	}

	public void setMinZoom(Integer minZoom) {
		this.minZoom = minZoom;
	}

	@Column(name = "max_zoom")
	public Integer getMaxZoom() {
		return this.maxZoom;
	}

	public void setMaxZoom(Integer maxZoom) {
		this.maxZoom = maxZoom;
	}

	@Column(name = "default_zoom")
	public Integer getDefaultZoom() {
		return this.defaultZoom;
	}

	public void setDefaultZoom(Integer defaultZoom) {
		this.defaultZoom = defaultZoom;
	}

	@Column(name = "max_resolution", precision = 17, scale = 17)
	public Double getMaxResolution() {
		return this.maxResolution;
	}

	public void setMaxResolution(Double maxResolution) {
		this.maxResolution = maxResolution;
	}

	@Column(name = "layer_name", length = 100)
	public String getLayerName() {
		return this.layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	@Column(name = "memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name = "navigation_version", length = 200)
	public String getNavigationVersion() {
		return navigationVersion;
	}

	public void setNavigationVersion(String navigationVersion) {
		this.navigationVersion = navigationVersion;
	}
	@Column(name = "center_lon")
	public Double getCenterLon() {
		return centerLon;
	}

	public void setCenterLon(Double centerLon) {
		this.centerLon = centerLon;
	}
	@Column(name = "center_lat")
	public Double getCenterLat() {
		return centerLat;
	}

	public void setCenterLat(Double centerLat) {
		this.centerLat = centerLat;
	}
	@Column(name = "maptype")
	public String getMaptype() {
		return maptype;
	}

	public void setMaptype(String maptype) {
		this.maptype = maptype;
	}
	@Column(name = "base_layer_name")
	public String getBaseLayerName() {
		return baseLayerName;
	}

	public void setBaseLayerName(String baseLayerName) {
		this.baseLayerName = baseLayerName;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campusid")
	public MapCampus getMapCampus() {
		return mapCampus;
	}

	public void setMapCampus(MapCampus mapCampus) {
		this.mapCampus = mapCampus;
	}
	@Column(name="open_indoor_navigation")
	public Integer getOpenIndoorNavigation() {
		return openIndoorNavigation;
	}

	public void setOpenIndoorNavigation(Integer openIndoorNavigation) {
		this.openIndoorNavigation = openIndoorNavigation;
	}
}