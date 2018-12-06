package com.zhuantitu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.system.utils.GisUtils;
import com.system.utils.StringUtil;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.WKTWriter;

@Entity
@Table(name = "zt_thematic_polyline_equipment")
@SequenceGenerator(name="generator",sequenceName="zt_thematic_polyline_equipment_id_seq",allocationSize=1)

public class ThematicPolylineEquipment implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3842064662355686224L;
	private Integer id;
	private ThematicMapMenu thematicMapMenu;
	private String name;
	private String floorid;
	private MultiPolygon geom;
	private String coordinates;
	private String coordinate;
	private Date posttime;
	private String fillcolor;
	private String strokecolor;
	private Integer strokewidth;
	private String column1;
	private String column2;
	private String column3;
	private String column4;
	private String column5;
	private String column6;
	private String column7;
	private String column8;
	private String column9;
	private String column10;
	private String column11;
	private String column12;
	private Integer equipmenttype;//0：井盖，1：链路设备
	private List<ThematicPolylineEquipmentImage> thematicPolylineEquipmentImages = new ArrayList<ThematicPolylineEquipmentImage>(
			0);
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menuid")
	public ThematicMapMenu getThematicMapMenu() {
		return thematicMapMenu;
	}
	public void setThematicMapMenu(ThematicMapMenu thematicMapMenu) {
		this.thematicMapMenu = thematicMapMenu;
	}
	
	
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "floorid")
	public String getFloorid() {
		return floorid;
	}
	public void setFloorid(String floorid) {
		this.floorid = floorid;
	}
	@Column(name = "geom")
	@Type(type="org.hibernatespatial.GeometryUserType",parameters ={
    		@Parameter(name="dialect",value="postgis")
    })
	public MultiPolygon getGeom() {
		return geom;
	}
	public void setGeom(MultiPolygon geom) {
		this.geom = geom;
	}
	
	
	@Column(name = "posttime", length = 29)
	public Date getPosttime() {
		return posttime;
	}
	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}
	@Column(name = "fillcolor")
	public String getFillcolor() {
		return fillcolor;
	}
	public void setFillcolor(String fillcolor) {
		this.fillcolor = fillcolor;
	}
	@Column(name = "strokecolor")
	public String getStrokecolor() {
		return strokecolor;
	}
	public void setStrokecolor(String strokecolor) {
		this.strokecolor = strokecolor;
	}
	@Column(name = "strokewidth")
	public Integer getStrokewidth() {
		return strokewidth;
	}
	public void setStrokewidth(Integer strokewidth) {
		this.strokewidth = strokewidth;
	}
	@Column(name = "column1")
	public String getColumn1() {
		return column1;
	}
	public void setColumn1(String column1) {
		this.column1 = column1;
	}
	@Column(name = "column2")
	public String getColumn2() {
		return column2;
	}
	public void setColumn2(String column2) {
		this.column2 = column2;
	}
	@Column(name = "column3")
	public String getColumn3() {
		return column3;
	}
	public void setColumn3(String column3) {
		this.column3 = column3;
	}
	@Column(name = "column4")
	public String getColumn4() {
		return column4;
	}
	public void setColumn4(String column4) {
		this.column4 = column4;
	}
	@Column(name = "column5")
	public String getColumn5() {
		return column5;
	}
	public void setColumn5(String column5) {
		this.column5 = column5;
	}
	@Column(name = "column6")
	public String getColumn6() {
		return column6;
	}
	public void setColumn6(String column6) {
		this.column6 = column6;
	}
	@Column(name = "column7")
	public String getColumn7() {
		return column7;
	}
	public void setColumn7(String column7) {
		this.column7 = column7;
	}
	@Column(name = "column8")
	public String getColumn8() {
		return column8;
	}
	public void setColumn8(String column8) {
		this.column8 = column8;
	}
	@Column(name = "column9")
	public String getColumn9() {
		return column9;
	}
	public void setColumn9(String column9) {
		this.column9 = column9;
	}
	@Column(name = "column10")
	public String getColumn10() {
		return column10;
	}
	public void setColumn10(String column10) {
		this.column10 = column10;
	}
	@Column(name = "column11")
	public String getColumn11() {
		return column11;
	}
	public void setColumn11(String column11) {
		this.column11 = column11;
	}
	@Column(name = "column12")
	public String getColumn12() {
		return column12;
	}
	public void setColumn12(String column12) {
		this.column12 = column12;
	}
	
	@Column(name = "equipmenttype")
	public Integer getEquipmenttype() {
		return equipmenttype;
	}
	public void setEquipmenttype(Integer equipmenttype) {
		this.equipmenttype = equipmenttype;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "thematicPolylineEquipment")
	public List<ThematicPolylineEquipmentImage> getThematicPolylineEquipmentImages() {
		return thematicPolylineEquipmentImages;
	}
	public void setThematicPolylineEquipmentImages(
			List<ThematicPolylineEquipmentImage> thematicPolylineEquipmentImages) {
		this.thematicPolylineEquipmentImages = thematicPolylineEquipmentImages;
	}
	@Transient
	public String getCoordinates() {
		try {
			WKTWriter wr = new WKTWriter();
			coordinates = wr.write(this.geom);
			if (coordinates.length() > 0) {
				coordinates = GisUtils.formatPolygon(coordinates);
			}
			return coordinates;
		} catch (Exception e) {
			System.out.println("未知面图元数据->" + this.id);
			return "";
		}
	}
	@Transient
	public String getCoordinate() {
		WKTWriter wr = new WKTWriter();
		coordinate = wr.write(geom.getCentroid());
		coordinate = "[" + coordinate.substring(coordinate.indexOf("(")+1, coordinate.indexOf(")")).replace(" ", ",") + "]";
		return coordinate;
	}
	@Transient
	public String getGeometry() {
		try {
			WKTWriter wr = new WKTWriter();
			String geometry =  wr.write(this.geom);
			if(geometry.length() > 0)
			{
				geometry = geometry.replaceAll(", ", ",");
			}
			return geometry;
		} catch (Exception e) {
			System.out.println("未知面图元数据->" + this.id);
			return "";
		}
	}
	
	@Transient
	public String getFancyBoxImages(String domain){
		StringBuffer temp = new StringBuffer();
		for(ThematicPolylineEquipmentImage img : thematicPolylineEquipmentImages){
			if(img.getPath().startsWith("http://")){
				temp.append("{\"href\":\"" + img.getPath() + "\",");
			}else{
				temp.append("{\"href\":\"" + domain + img.getPath() + "\",");
			}
			temp.append("\"title\":\"" + img.getName() + "\"},");
		}
		return StringUtil.deleteLastStr(temp.toString());
	}

}
