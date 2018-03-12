package com.zhuantitu.mobile.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.system.utils.Configuration;
import com.system.utils.DateUtil;
import com.system.utils.PageBean;
import com.system.utils.StringUtil;
import com.zhuantitu.energy.client.EnergyClient;
import com.zhuantitu.model.MeterDayStatistics;
import com.zhuantitu.model.ThematicPoint;
import com.zhuantitu.service.MeterDayStatisticsService;
import com.zhuantitu.service.ThematicPointService;
@Controller("energyAction")
@Scope("prototype")
public class EnergyMapAction extends BaseAction {
	private static final long serialVersionUID = 6205901444078276736L;
	private static final String electricPrice = Configuration.getInstance().getValue("electricPrice");
	private static final String waterPrice = Configuration.getInstance().getValue("waterPrice");
	private String floorids;
	private String startTime;
	private String flishTime;
	private String buildid;
	private String metertype;
	private String datetime;
	
	private static Map<String, Integer> buildMap = new HashMap<String, Integer>();
	
	@Resource
	private ThematicPointService thematicPointService;
	@Resource
	private MeterDayStatisticsService meterDayStatisticsService;
	public String meterDetail(){
		String userid = validateUser();
		if(userid != null){
			try {
				ThematicPoint thematicPoint = this.thematicPointService.getById(getId());
				if(StringUtil.isNotEmpty(thematicPoint.getColumn28())){
					StringBuffer json = new StringBuffer();
					String metertype = thematicPoint.getColumn28();
					String monitorTime = thematicPoint.getColumn22();
					String metertypeName = "",meterstatus="";
					if(metertype.equals("50205")){
						metertypeName = " 供水站进水侧水表";
					}else if(metertype.equals("50107")){
						metertypeName = "低压侧三相电表";
					}else{
						metertypeName ="网关";
					}
					if(StringUtil.isNotEmpty(monitorTime) && !metertype.equals("50101")){
						Date lastUpData = DateUtil.parseDatetime(monitorTime);
						if((lastUpData.getTime() + (40 * 60 * 1000)) >= System.currentTimeMillis()){
							meterstatus = "设备正常";
						}else{
							meterstatus = "设备掉线";
						}
					}
					json.append("{")
						.append("\"status\":true,")
						.append("\"name\":\"" + thematicPoint.getName() + "\",")
						.append("\"buildName\":\"" + thematicPoint.getThematicLocation().getName() + "\",")
						.append("\"monitorTime\":\"" + (monitorTime == null?"":monitorTime) + "\",")
						.append("\"ip\":\"" + thematicPoint.getColumn29() + "\",")
						.append("\"meterstatus\":\"" + meterstatus + "\",")
						.append("\"address\":\"" + thematicPoint.getColumn1() + "\",")
						.append("\"rate\":\"" + thematicPoint.getColumn23() + "\",")
						.append("\"getInterval\":\"" + thematicPoint.getColumn24() + "\",")
						.append("\"metertype\":\"" + thematicPoint.getColumn28() + "\",")
						.append("\"metertypeName\":\"" + metertypeName + "\"")
						.append("}");
					writeJSON(json.toString());
				}else{
					writeError(203, "查询失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				writeError(203, "查询失败");
			}
		}
		return NONE;
	}
	public String meterStatistics(){
		String userid = validateUser();
		if(userid != null){
			try {
				ThematicPoint thematicPoint = this.thematicPointService.getById(getId());
				if(StringUtil.isNotEmpty(thematicPoint.getColumn25())){
					PageBean<MeterDayStatistics> page = this.meterDayStatisticsService.sumByMeteridAndBetwenTime(Integer.parseInt(thematicPoint.getColumn25()), startTime, flishTime, getPageSize(), getPage());
					List<MeterDayStatistics> list = page.getList();
					StringBuffer json = new StringBuffer();
					StringBuffer temp = new StringBuffer();
					double unit = 0;
					if(thematicPoint.getColumn28().equals("50107")){
						unit = Double.parseDouble(electricPrice);
					}else{
						unit = Double.parseDouble(waterPrice);
					}
					for (MeterDayStatistics meterDayStatistics : list) {
						temp.append("{")
							.append("\"datetime\":\"" + DateUtil.formatDate(meterDayStatistics.getId().getStartTime().getTime())+ "\",")
							.append("\"value\":" + formatDouble(meterDayStatistics.getEnergyValue()) + ",")
							.append("\"totalPrice\":" + formatDouble(meterDayStatistics.getEnergyValue() * unit) + "")
							.append("},");
					}
					json.append("{")
						.append("\"status\":true,")
						.append("\"totalPage\":" + page.getTotalPage() + ",")
						.append("\"currentPage\":" + page.getCurrentPage() + ",")
						.append("\"type\":" + thematicPoint.getColumn28() + ",")
						.append("\"unit\":\"" + (thematicPoint.getColumn28().equals("50107")?"kWh":"立方")+ "\",")
						.append("\"data\":[")
						.append(StringUtil.deleteLastStr(temp.toString()))
						.append("]")
						.append("}");
					writeJSON(json.toString());
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				writeError(203, "查询统计失败");
			}
		}
		return NONE;
	}
	/**
	 * 大楼总能耗统计
	 * @return
	 */
	public String bulidTotalStatistics(){
		String userid = validateUser();
		if(userid != null){
			long timestamp = System.currentTimeMillis();
			String today = DateUtil.formatDate(timestamp) + " 00:00:00";
			String yesterday = DateUtil.formatDate(timestamp - 1000 * 60 * 60 * 24) + " 00:00:00";
			String monthStart = DateUtil.getMonthFirstDay() + " 00:00:00";
			String yearStart = DateUtil.getYearFirstDay() + " 00:00:00";
			double todayElectric = 0,yesterdayElectric = 0,monthElectric = 0,yearElectric = 0,
			todayWater = 0,yesterdayWater = 0,monthWater = 0,yearWater = 0;
			int locationid = getLocationId();
			if(locationid == -1){
				writeError(403, "当前建筑暂无能耗设备");
				return NONE;
			}
			try {
				todayElectric = this.meterDayStatisticsService.sumDay(locationid, today,"50107");
				yesterdayElectric = this.meterDayStatisticsService.sumDay(locationid, yesterday,"50107");
				monthElectric = this.meterDayStatisticsService.sumBetweenDay(locationid, monthStart, today,"50107");
				yearElectric = this.meterDayStatisticsService.sumBetweenDay(locationid, yearStart, today,"50107");
				todayWater = this.meterDayStatisticsService.sumDay(locationid, today,"50205");
				yesterdayWater = this.meterDayStatisticsService.sumDay(locationid, yesterday,"50205");
				monthWater = this.meterDayStatisticsService.sumBetweenDay(locationid, monthStart, today,"50205");
				yearWater = this.meterDayStatisticsService.sumBetweenDay(locationid, yearStart, today,"50205");
				StringBuffer json = new StringBuffer();
				json.append("{")
				.append("\"status\":true,")
				.append("\"electricUnit\":\"kWh\",")
				.append("\"waterUnit\":\"立方\",")
				.append("\"today\":[")
				.append("{\"electricValue\":" + todayElectric + ",\"waterValue\":" + todayWater + "}")
				.append("],")
				.append("\"yesterday\":[")
				.append("{\"electricValue\":" + yesterdayElectric + ",\"waterValue\":" + yesterdayWater + "}")
				.append("],")
				.append("\"month\":[")
				.append("{\"electricValue\":" + monthElectric + ",\"waterValue\":" + monthWater + "}")
				.append("],")
				.append("\"year\":[")
				.append("{\"electricValue\":" + yearElectric + ",\"waterValue\":" + yearWater + "}")
				.append("]")
				.append("}");
				writeJSON(json.toString());
			} catch (Exception e) {
				e.printStackTrace();
				writeError(203, "查询统计失败");
			}
		}
		return NONE;
	}
	public String bulidStatisticsDetail(){
		String userid = validateUser();
		if(userid != null){
			try {
				StringBuffer json = new StringBuffer();
				StringBuffer temp = new StringBuffer();
				int locationid = getLocationId();
				if(locationid == -1){
					writeError(403, "当前建筑暂无能耗设备");
					return NONE;
				}
				List<ThematicPoint> thematicPoints = this.thematicPointService
				.getByHql("from ThematicPoint as t where t.thematicLocation.locationid =  " + locationid 
						+ " and t.floorid = '" + getFloorid() + "' and t.column28 = '" + metertype + "' order by t.column28");
				for (ThematicPoint thematicPoint : thematicPoints) {
					if(StringUtil.isNotEmpty(thematicPoint.getColumn28()) && StringUtil.isNotEmpty(thematicPoint.getColumn25())){
						if(thematicPoint.getColumn28().equals("50107")){//用电设备
							double value = this.meterDayStatisticsService.sumByMeteridAndTime(Integer.parseInt(thematicPoint.getColumn25()),datetime + " 00:00:00");
							temp.append("{")
							.append("\"name\":\"" + StringUtil.toJsonText(thematicPoint.getName() )+ "\",")
							.append("\"value\":" + value + ",")
							.append("\"totalPrice\":" + formatDouble(value * Double.parseDouble(electricPrice)) + ",")
							.append("\"unit\":\"kWh\"")
							.append("},");
						}else if(thematicPoint.getColumn28().equals("50205")){//用水设备
							double value = this.meterDayStatisticsService.sumByMeteridAndTime(Integer.parseInt(thematicPoint.getColumn25()),datetime + " 00:00:00");
							temp.append("{")
							.append("\"name\":\"" + StringUtil.toJsonText(thematicPoint.getName() )+ "\",")
							.append("\"value\":" + value + ",")
							.append("\"totalPrice\":" + formatDouble(value * Double.parseDouble(waterPrice)) + ",")
							.append("\"unit\":\"立方\"")
							.append("},");
						}
					}
				}
				json.append("{")
				.append("\"status\":true,")
				.append("\"data\":[")
				.append(StringUtil.deleteLastStr(temp.toString()))
				.append("]}");
				writeJSON(json.toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
				writeError(203, "查询失败");
			}
		}
		return NONE;
	}
	@SuppressWarnings("unchecked")
	public String bulidStatistics(){
		String userid = validateUser();
		if(userid != null){
			try {
				int locationid = getLocationId();
				if(locationid == -1){
					writeError(403, "当前建筑暂无能耗设备");
					return NONE;
				}
				String [] floors = floorids.split(",");
				StringBuffer floorids = new StringBuffer();
				for (String str : floors) {
					floorids.append("'" + str +"',");
				}
				
				PageBean<?> page = this.meterDayStatisticsService
					.pageByHql(StringUtil.deleteLastStr(floorids.toString()), locationid, metertype, startTime, flishTime,getPageSize(), getPage());
				List<Object[]> list = (List<Object[]>) page.getList();
				StringBuffer json = new StringBuffer();
				StringBuffer temp = new StringBuffer();
				
				double unit = 0;
				if(metertype.equals("50107")){
					unit = Double.parseDouble(electricPrice);
				}else{
					unit = Double.parseDouble(waterPrice);
				}
				for (Object[] objects : list) {
					temp.append("{")
					.append("\"datetime\":\"" + DateUtil.formatDate(DateUtil.parseDatetime(objects[1].toString()).getTime())+ "\",")
					.append("\"value\":" + formatDouble(Double.parseDouble(objects[0].toString())) + ",")
					.append("\"totalPrice\":" + formatDouble(Double.parseDouble(objects[0].toString()) * unit) + "")
					.append("},");
				}
				json.append("{")
					.append("\"status\":true,")
					.append("\"totalPage\":" + page.getTotalPage() + ",")
					.append("\"currentPage\":" + page.getCurrentPage() + ",")
					.append("\"unit\":\"" + (metertype.equals("50107")?"kWh":"立方")+ "\",")
					.append("\"data\":[")
					.append(StringUtil.deleteLastStr(temp.toString()))
					.append("]")
					.append("}");
				writeJSON(json.toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
				writeError(203, "查询失败");
			}
		}
		return NONE;
	}
	
	public Integer getLocationId(){
		if(buildMap.size() == 0){
			buildMap.put("100000101", 4);
			buildMap.put("100900702", 4);
			buildMap.put("100000114", 10);
			buildMap.put("100900711", 10);
		}
		if(buildMap.containsKey(buildid)){
			return buildMap.get(buildid);
		}else{
			return  -1;
		}
	}
	/**
	 * 设备列表
	 * @return
	 */
	public String metersList(){
		try {
			responseResult(EnergyClient.getMetersList());
		} catch (Exception e) {
			e.printStackTrace();
			writeError(203, "获取信息失败");
		}
		return NONE;
	}
	/**
	 * 设备信息详情
	 * id:设备ID
	 * @return
	 */
	public String metersDetatil(){
		try {
			responseResult(EnergyClient.getMetersDeatil(getId()));
		} catch (Exception e) {
			e.printStackTrace();
			writeError(203, "获取信息失败");
		}
		return NONE;
	}
	private double formatDouble(double v){
		BigDecimal b = new BigDecimal(v);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}
	public void responseResult(String result){
		if(StringUtil.isNotEmpty(result)){
			this.writeJSON(result);
		}else{
			writeError(203, "获取信息失败");
		}
	}
	public void writeJSON(String json)
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
            // 设置响应头
            response.setContentType("application/json"); // 指定内容类型为 JSON 格式
            response.setCharacterEncoding("UTF-8"); // 防止中文乱码
            // 向响应中写入数据
            PrintWriter writer = response.getWriter();
            writer.write(json.replaceAll("null", "")); 
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	public String getBuildid() {
		return buildid;
	}

	public void setBuildid(String buildid) {
		this.buildid = buildid;
	}
	public String getFloorids() {
		return floorids;
	}
	public void setFloorids(String floorids) {
		this.floorids = floorids;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getFlishTime() {
		return flishTime;
	}
	public void setFlishTime(String flishTime) {
		this.flishTime = flishTime;
	}
	public String getMetertype() {
		return metertype;
	}
	public void setMetertype(String metertype) {
		this.metertype = metertype;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
}
