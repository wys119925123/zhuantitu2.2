package com.zhuantitu.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.system.utils.Configuration;
import com.system.utils.DateUtil;
import com.system.utils.StringUtil;
import com.zhuantitu.energy.client.EnergyClient;
import com.zhuantitu.model.MeterDayStatistics;
import com.zhuantitu.model.MeterDayStatisticsId;
import com.zhuantitu.model.ThematicPoint;
import com.zhuantitu.service.MeterDayStatisticsService;
import com.zhuantitu.service.ThematicPointService;

public class YesterdayEnergyTask {
	private static final String menuid = Configuration.getInstance().getValue("energyMenuid");
	private static final String startTask = Configuration.getInstance().getValue("startTask");
	@Resource
	private ThematicPointService thematicPointService;
	@Resource
	private MeterDayStatisticsService meterDayStatisticsService;
	public void startJob(){
		if(startTask.equals("true")){
			getData();
		}
	}
	private void getData(){
		String yesterdayTime = DateUtil.formatDate(System.currentTimeMillis() - (1000 * 60 * 60 * 24)) + " 23:59:59";
		List<ThematicPoint> thematicPoints = this.thematicPointService
				.getByHql("from ThematicPoint as t where t.thematicPointCategory.thematicMapMenu.parentThematicMapMenu.menuid =  " + menuid + " ");
		StringBuffer electricTargetId = new StringBuffer();
		StringBuffer waterTargetId = new StringBuffer();
		Map<String, Integer> locationMap = new HashMap<String, Integer>();
		Map<String, String> floorMap = new HashMap<String,String>();
		for (ThematicPoint thematicPoint : thematicPoints) {
			if(StringUtil.isNotEmpty(thematicPoint.getColumn28()) && StringUtil.isNotEmpty(thematicPoint.getColumn25())){
				if(thematicPoint.getColumn28().equals("50107")){//用电设备
					electricTargetId.append(thematicPoint.getColumn25() + ",");
					locationMap.put(thematicPoint.getColumn25(), thematicPoint.getThematicLocation().getLocationid());
					floorMap.put(thematicPoint.getColumn25(), thematicPoint.getFloorid());
				}else if(thematicPoint.getColumn28().equals("50205")){//用水设备
					waterTargetId.append(thematicPoint.getColumn25() + ",");
					locationMap.put(thematicPoint.getColumn25(), thematicPoint.getThematicLocation().getLocationid());
					floorMap.put(thematicPoint.getColumn25(), thematicPoint.getFloorid());
				}else{}
			}
		}
		if(electricTargetId.length() > 0){
			String yesterdayResultData = EnergyClient.statistics(StringUtil.deleteLastStr(electricTargetId.toString()),
					"Meter","Daily","total","60019","90001",yesterdayTime);
			updateData(yesterdayResultData,locationMap,floorMap,"50107");
			
		}
		if(waterTargetId.length() > 0){
			String yesterdayResultData = EnergyClient.statistics(StringUtil.deleteLastStr(waterTargetId.toString()),
					"Meter","Daily","total","60023","90031",yesterdayTime);
			updateData(yesterdayResultData,locationMap,floorMap,"50205");
		}
	}
	private void updateData(String result,Map<String, Integer> locationMap, Map<String, String> floorMap,String metertype){
		JSONArray resultDataToJsonArr = JSON.parseArray(result);
		for (Object meterData : resultDataToJsonArr) {
			JSONObject meterDataToJson = JSON.parseObject(meterData.toString());
			JSONArray meterDayResult = meterDataToJson.getJSONArray("result");
			
			if(meterDayResult.size() > 0){
				JSONObject json = JSON.parseObject(meterDayResult.get(meterDayResult.size() -1).toString());
				int meterid = meterDataToJson.getInteger("statisticalId");
				String unit = meterDataToJson.getString("unit");
				double todayValue = json.getDoubleValue("value");
				String startTime = json.getString("startTime");
				String finishTime = json.getString("finishTime");
				MeterDayStatisticsId id = new MeterDayStatisticsId();
				MeterDayStatistics meterDayStatistics = new MeterDayStatistics();
				id.setMeterid(meterid);
				id.setStartTime(DateUtil.parseDatetime(startTime));
				meterDayStatistics.setId(id);
				meterDayStatistics.setEnergyValue(todayValue);
				meterDayStatistics.setFinishTime(DateUtil.parseDatetime(finishTime));
				meterDayStatistics.setUnit(unit);
				meterDayStatistics.setFloorid(floorMap.get(meterid + ""));
				meterDayStatistics.setLocationid(locationMap.get(meterid + ""));
				meterDayStatistics.setMetertype(metertype);
				this.meterDayStatisticsService.merge(meterDayStatistics);
				this.thematicPointService.updateTimeByMeterid(meterid,finishTime);
			}
		}
	}
}
