package com.zhuantitu.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.system.utils.StringUtil;
import com.zhuantitu.energy.client.EnergyClient;
@Controller("thematicMapEnergyAction")
@Scope("prototype")
public class ThematicMapEnergyAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1608270390742944105L;
	private Integer energyCategoryId; //能源类型 90001电、90031水
	private String startTime;//统计开始时间
	private String finishTime;//统计结束时间
	/**
	 * 统计建筑能耗时50205水 、50107低压侧三相电表
	 * 能耗统计时60019度、60023流量
	 */
	private String parameterTypeId;
	/**
	 * 建筑ID 1 ---水表（设备ID：35）
	 * 建筑ID 2 ---水表（设备ID：36）
	 * 建筑ID 2 ----网关（设备ID：16）
	 * 建筑ID 2 ---电表（设备ID：17,18,19）
	 * 建筑ID 3 ----网关（设备ID：1）
	 * 建筑ID 3 ----电表（设备ID：2,3）
	 * 建筑ID 4 ----电表（设备ID：8,9,10）
	 * 建筑ID 5 ----电表（设备ID：4）
	 * 建筑ID 6 ----电表（设备ID：5）
	 * 建筑ID 7 ----电表（设备ID：6）
	 * 建筑ID 8 ----电表（设备ID：7）
	 * 建筑ID 10 ----电表（设备ID：11）
	 * 建筑ID 11 ----电表（设备ID：12）
	 * 建筑ID 12  ----电表（设备ID：13）
	 * 建筑ID 13  ----电表（设备ID：14）
	 * 建筑ID 14  ----电表（设备ID：15）
	 * 建筑ID 15 ----网关（设备ID：20）
	 * 建筑ID 15  ----电表（设备ID：21）
	 * 建筑ID 16  ----网关（设备ID：28）
	 * 建筑ID 16  ----电表（设备ID：29）
	 * 建筑ID 17  ----电表（设备ID：22）
	 * 建筑ID 18  ----电表（设备ID：23）
	 * 建筑ID 19  ----电表（设备ID：24）
	 * 建筑ID 20  ----电表（设备ID：25）
	 * 建筑ID 21  ----电表（设备ID：26）
	 * 建筑ID 22  ----电表（设备ID：27）
	 * 建筑ID 23  ----电表（设备ID：30）
	 * 建筑ID 24  ----电表（设备ID：31）
	 * 建筑ID 25  ----电表（设备ID：32）
	 * 建筑ID 26  ----电表（设备ID：33）
	 * 建筑ID 27  ----电表（设备ID：34）
	 */
	private String targetId;//建筑ID;
	private String statMode;//统计类型[Meter：设备、Building：建筑、Organization：部门]
	private String timeUnit;//时间单位[Quarterly：季度、Monthly：月、Daily：天、Hourly：小时]
	private String statWay;//统计模式[total：合计、Avg：平均、PerCapita、PerUnitArea]
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
	/**
	 * 建筑能耗
	 * @return
	 */
	public String getDataByBuildAndEnergyType(){
		try {
			StringBuffer params = new StringBuffer();
			params.append("{")
				.append("\"energyCategoryId\":" + energyCategoryId + ",")
				.append("\"startTime\":\"" + startTime + "\",")
				.append("\"finishTime\":\"" + finishTime + "\",")
				.append("\"parameterTypeId\":[")
				.append(parameterTypeId)
				.append("],")
				.append("\"targetId\":[")
				.append(targetId)
				.append("]}");
			responseResult(EnergyClient.getDataByBuildAndEnergyType(params.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			writeError(203, "获取信息失败");
		}
		return NONE;
	}
	/**
	 * 能耗统计
	 * @return
	 */
	public String getStatistics(){
		try {
			StringBuffer params = new StringBuffer();
			params.append("{")
				.append("\"targetId\":[")
				.append(targetId)
				.append("],")
				.append("\"statMode\":\"" + statMode + "\",")
				.append("\"timeUnit\":\"" + timeUnit + "\",")
				.append("\"statWay\":\"" + statWay + "\",")
				.append("\"parameterTypeId\":[")
				.append(parameterTypeId)
				.append("],")
				.append("\"energyCategoryId\":" +energyCategoryId+ ",")
				.append("\"finishTime\":\"" + finishTime + "\"")
				.append("}");
			responseResult(EnergyClient.getStatistics(params.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			writeError(203, "获取信息失败");
		}
		return NONE;
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
            writer.write(json.replaceAll("null", "\"\"")); 
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	public Integer getEnergyCategoryId() {
		return energyCategoryId;
	}
	public void setEnergyCategoryId(Integer energyCategoryId) {
		this.energyCategoryId = energyCategoryId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getParameterTypeId() {
		return parameterTypeId;
	}
	public void setParameterTypeId(String parameterTypeId) {
		this.parameterTypeId = parameterTypeId;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getStatMode() {
		return statMode;
	}
	public void setStatMode(String statMode) {
		this.statMode = statMode;
	}
	public String getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	public String getStatWay() {
		return statWay;
	}
	public void setStatWay(String statWay) {
		this.statWay = statWay;
	}
}
