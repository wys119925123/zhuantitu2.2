package com.zhuantitu.mobile.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.system.utils.StringUtil;
import com.zhuantitu.energy.client.EnergyClient;
@Controller("mapEnergyAction")
@Scope("prototype")
public class ThematicMapEnergyAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1976820430513358378L;
	private Integer energyCategoryId; //能源类型 90001电、90031、水
	private String startTime;//统计开始时间
	private String finishTime;//统计结束时间
	private String parameterTypeId;//设备参数类型ID
	private String targetId;//建筑ID;
	private String statMode;//统计类型[Meter、Building、Organization]
	private String timeUnit;//时间单位[Quarterly、Monthly、Daily、Hourly]
	private String statWay;//统计模式[total、Avg、PerCapita、PerUnitArea]
	/**
	 * 设备列表
	 * @return
	 */
	public String metersList(){
		String userid = validateUser();
		if(userid != null){
			try {
				responseResult(EnergyClient.getMetersList());
			} catch (Exception e) {
				e.printStackTrace();
				writeError(203, "获取信息失败");
			}
		}
		return NONE;
	}
	/**
	 * 设备信息详情
	 * id:设备ID
	 * @return
	 */
	public String metersDetatil(){
		String userid = validateUser();
		if(userid != null){
			try {
				responseResult(EnergyClient.getMetersDeatil(getId()));
			} catch (Exception e) {
				e.printStackTrace();
				writeError(203, "获取信息失败");
			}
		}
		return NONE;
	}
	/**
	 * 建筑能耗
	 * @return
	 */
	public String getDataByBuildAndEnergyType(){
		String userid = validateUser();
		if(userid != null){
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
		}
		return NONE;
	}
	/**
	 * 能耗统计
	 * @return
	 */
	public String getStatistics(){
		String userid = validateUser();
		if(userid != null){
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
