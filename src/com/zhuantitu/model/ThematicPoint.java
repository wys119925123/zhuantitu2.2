package com.zhuantitu.model;
// default package

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTWriter;

/**
 * ThematicPoint entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zt_thematic_point", schema = "public")
@SequenceGenerator(name="generator",sequenceName="zt_thematic_point_pointid_seq",allocationSize=1)

public class ThematicPoint implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3836664240288772563L;
	private Integer pointid;
	private ThematicPointCategory thematicPointCategory;
	private ThematicLocation thematicLocation;
	private String name;
	private String floorid;
	private Point geom;
	private String coordinate;
	private String icon;
	private String ip;
	private String nvr;
	private String channel;
	private String videousername;
	private String videopassword;
	private Date posttime;
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
	private String column13;
	private String column14;
	private String column15;
	private String column16;
	private String column17;
	private String column18;
	private String column19;
	private String column20;
	private String column21;
	private String column22;
	private String column23;
	private String column24;
	private String column25;
	private String column26;
	private String column27;
	private String column28;
	private String column29;
	private String column30;
	private String type;
	private Set<UserPointPermission> userPointPermissions = new HashSet<UserPointPermission>(
			0);
	private List<ThematicPointImage> thematicPointImages = new ArrayList<ThematicPointImage>(
			0);

	// Constructors

	/** default constructor */
	public ThematicPoint() {
	}

	/** minimal constructor */
	public ThematicPoint(Integer pointid) {
		this.pointid = pointid;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@Column(name = "pointid", unique = true, nullable = false)
	public Integer getPointid() {
		return this.pointid;
	}

	public void setPointid(Integer pointid) {
		this.pointid = pointid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryid")
	public ThematicPointCategory getThematicPointCategory() {
		return this.thematicPointCategory;
	}

	public void setThematicPointCategory(
			ThematicPointCategory thematicPointCategory) {
		this.thematicPointCategory = thematicPointCategory;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "floorid", length = 30)
	public String getFloorid() {
		return this.floorid;
	}

	public void setFloorid(String floorid) {
		this.floorid = floorid;
	}

	@Column(name = "geom")
    @Type(type="org.hibernatespatial.GeometryUserType",parameters ={
    		@Parameter(name="dialect",value="postgis")
    })
	public Point getGeom() {
		return this.geom;
	}

	public void setGeom(Point geom) {
		this.geom = geom;
	}
	@Transient
	public String getCoordinate() {
		try {
			WKTWriter wr = new WKTWriter();
			coordinate = wr.write(this.geom);
			if (coordinate.length() > 0) {
				coordinate = GisUtils.formatPoint(coordinate);
			}
			return coordinate;
		} catch (Exception e) {
			System.out.println("未知点图元数据->" + this.pointid);
			return "";
		}
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
			System.out.println("未知点图元数据->" + this.pointid);
			return "";
		}
	}
	@Column(name = "icon")
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "ip")
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "nvr")
	public String getNvr() {
		return this.nvr;
	}

	public void setNvr(String nvr) {
		this.nvr = nvr;
	}

	@Column(name = "posttime", length = 29)
	public Date getPosttime() {
		return this.posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	@Column(name = "column1")
	public String getColumn1() {
		return this.column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	@Column(name = "column2")
	public String getColumn2() {
		return this.column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	@Column(name = "column3")
	public String getColumn3() {
		return this.column3;
	}

	public void setColumn3(String column3) {
		this.column3 = column3;
	}

	@Column(name = "column4")
	public String getColumn4() {
		return this.column4;
	}

	public void setColumn4(String column4) {
		this.column4 = column4;
	}

	@Column(name = "column5")
	public String getColumn5() {
		return this.column5;
	}

	public void setColumn5(String column5) {
		this.column5 = column5;
	}

	@Column(name = "column6")
	public String getColumn6() {
		return this.column6;
	}

	public void setColumn6(String column6) {
		this.column6 = column6;
	}

	@Column(name = "column7")
	public String getColumn7() {
		return this.column7;
	}

	public void setColumn7(String column7) {
		this.column7 = column7;
	}

	@Column(name = "column8")
	public String getColumn8() {
		return this.column8;
	}

	public void setColumn8(String column8) {
		this.column8 = column8;
	}

	@Column(name = "column9")
	public String getColumn9() {
		return this.column9;
	}

	public void setColumn9(String column9) {
		this.column9 = column9;
	}

	@Column(name = "column10")
	public String getColumn10() {
		return this.column10;
	}

	public void setColumn10(String column10) {
		this.column10 = column10;
	}

	@Column(name = "column11")
	public String getColumn11() {
		return this.column11;
	}

	public void setColumn11(String column11) {
		this.column11 = column11;
	}

	@Column(name = "column12")
	public String getColumn12() {
		return this.column12;
	}

	public void setColumn12(String column12) {
		this.column12 = column12;
	}

	@Column(name = "column13")
	public String getColumn13() {
		return this.column13;
	}

	public void setColumn13(String column13) {
		this.column13 = column13;
	}

	@Column(name = "column14")
	public String getColumn14() {
		return this.column14;
	}

	public void setColumn14(String column14) {
		this.column14 = column14;
	}

	@Column(name = "column15")
	public String getColumn15() {
		return this.column15;
	}

	public void setColumn15(String column15) {
		this.column15 = column15;
	}

	@Column(name = "column16")
	public String getColumn16() {
		return this.column16;
	}

	public void setColumn16(String column16) {
		this.column16 = column16;
	}

	@Column(name = "column17")
	public String getColumn17() {
		return this.column17;
	}

	public void setColumn17(String column17) {
		this.column17 = column17;
	}

	@Column(name = "column18")
	public String getColumn18() {
		return this.column18;
	}

	public void setColumn18(String column18) {
		this.column18 = column18;
	}

	@Column(name = "column19")
	public String getColumn19() {
		return this.column19;
	}

	public void setColumn19(String column19) {
		this.column19 = column19;
	}

	@Column(name = "column20")
	public String getColumn20() {
		return this.column20;
	}

	public void setColumn20(String column20) {
		this.column20 = column20;
	}

	@Column(name = "column21")
	public String getColumn21() {
		return this.column21;
	}

	public void setColumn21(String column21) {
		this.column21 = column21;
	}

	@Column(name = "column22")
	public String getColumn22() {
		return this.column22;
	}

	public void setColumn22(String column22) {
		this.column22 = column22;
	}

	@Column(name = "column23")
	public String getColumn23() {
		return this.column23;
	}

	public void setColumn23(String column23) {
		this.column23 = column23;
	}

	@Column(name = "column24")
	public String getColumn24() {
		return this.column24;
	}

	public void setColumn24(String column24) {
		this.column24 = column24;
	}

	@Column(name = "column25")
	public String getColumn25() {
		return this.column25;
	}

	public void setColumn25(String column25) {
		this.column25 = column25;
	}

	@Column(name = "column26")
	public String getColumn26() {
		return this.column26;
	}

	public void setColumn26(String column26) {
		this.column26 = column26;
	}

	@Column(name = "column27")
	public String getColumn27() {
		return this.column27;
	}

	public void setColumn27(String column27) {
		this.column27 = column27;
	}

	@Column(name = "column28")
	public String getColumn28() {
		return this.column28;
	}

	public void setColumn28(String column28) {
		this.column28 = column28;
	}

	@Column(name = "column29")
	public String getColumn29() {
		return this.column29;
	}

	public void setColumn29(String column29) {
		this.column29 = column29;
	}

	@Column(name = "column30")
	public String getColumn30() {
		return this.column30;
	}

	public void setColumn30(String column30) {
		this.column30 = column30;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "thematicPoint")
	public Set<UserPointPermission> getUserPointPermissions() {
		return this.userPointPermissions;
	}

	public void setUserPointPermissions(
			Set<UserPointPermission> userPointPermissions) {
		this.userPointPermissions = userPointPermissions;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "locationid")
	public ThematicLocation getThematicLocation() {
		return thematicLocation;
	}

	public void setThematicLocation(ThematicLocation thematicLocation) {
		this.thematicLocation = thematicLocation;
	}
	@Column(name = "videousername")
	public String getVideousername() {
		return videousername;
	}

	public void setVideousername(String videousername) {
		this.videousername = videousername;
	}
	@Column(name = "videopassword")
	public String getVideopassword() {
		return videopassword;
	}

	public void setVideopassword(String videopassword) {
		this.videopassword = videopassword;
	}
	
	@Column(name = "channel")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "thematicPoint")
	public List<ThematicPointImage> getThematicPointImages() {
		return thematicPointImages;
	}

	public void setThematicPointImages(List<ThematicPointImage> thematicPointImages) {
		this.thematicPointImages = thematicPointImages;
	}
	@Transient
	public String getImages(){
		StringBuffer temp = new StringBuffer();
		for(ThematicPointImage img : thematicPointImages){
			temp.append("{\"path\":\"" + img.getPath() + "\",")
				.append("\"name\":\"" + img.getName() + "\"},");
		}
		return StringUtil.deleteLastStr(temp.toString());
	}
	@Transient
	public String getFancyBoxImages(String domain){
		StringBuffer temp = new StringBuffer();
		for(ThematicPointImage img : thematicPointImages){
			if(img.getPath().startsWith("http://")){
				temp.append("{\"href\":\"" + img.getPath() + "\",");
			}else{
				temp.append("{\"href\":\"" + domain + img.getPath() + "\",");
			}
			temp.append("\"title\":\"" + img.getName() + "\"},");
		}
		return StringUtil.deleteLastStr(temp.toString());
	}
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}