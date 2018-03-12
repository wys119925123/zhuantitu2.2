package com.zhuantitu.energy.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.system.utils.Configuration;


public class EnergyClient {
	private static final String GET_ACCESS_TOKEN_URL = Configuration.getInstance().getValue("get_access_token_url");
	private static final String METERS_INFO_URL = Configuration.getInstance().getValue("meters_info_url");
	private static final String BUILD_ENERGY_URL = Configuration.getInstance().getValue("build_energy_url");
	private static final String ENERGY_COUNT_URL = Configuration.getInstance().getValue("energy_count_url");
	private static final String GRANT_TYPE = Configuration.getInstance().getValue("auth_grant_type");
	private static final String USERNAME = Configuration.getInstance().getValue("auth_username");
	private static final String PASSWORD = Configuration.getInstance().getValue("auth_password");
	private static AccessToken accessToken = new AccessToken();
//	private Integer energyCategoryId; //能源类型 90001电、90031水
//	private String startTime;//统计开始时间
//	private String finishTime;//统计结束时间
//	/**
//	 * 统计建筑能耗时50205水 、50107低压侧三相电表
//	 * 能耗统计时60019度、60023流量
//	 */
//	private String parameterTypeId;
//	/**
//	 * 建筑ID 1 ---水表（设备ID：35）
//	 * 建筑ID 2 ---水表（设备ID：36）
//	 * 建筑ID 2 ----网关（设备ID：16）
//	 * 建筑ID 2 ---电表（设备ID：17,18,19）
//	 * 建筑ID 3 ----网关（设备ID：1）
//	 * 建筑ID 3 ----电表（设备ID：2,3）
//	 * 建筑ID 4 ----电表（设备ID：8,9,10）
//	 * 建筑ID 5 ----电表（设备ID：4）
//	 * 建筑ID 6 ----电表（设备ID：5）
//	 * 建筑ID 7 ----电表（设备ID：6）
//	 * 建筑ID 8 ----电表（设备ID：7）
//	 * 建筑ID 10 ----电表（设备ID：11）
//	 * 建筑ID 11 ----电表（设备ID：12）
//	 * 建筑ID 12  ----电表（设备ID：13）
//	 * 建筑ID 13  ----电表（设备ID：14）
//	 * 建筑ID 14  ----电表（设备ID：15）
//	 * 建筑ID 15 ----网关（设备ID：20）
//	 * 建筑ID 15  ----电表（设备ID：21）
//	 * 建筑ID 16  ----网关（设备ID：28）
//	 * 建筑ID 16  ----电表（设备ID：29）
//	 * 建筑ID 17  ----电表（设备ID：22）
//	 * 建筑ID 18  ----电表（设备ID：23）
//	 * 建筑ID 19  ----电表（设备ID：24）
//	 * 建筑ID 20  ----电表（设备ID：25）
//	 * 建筑ID 21  ----电表（设备ID：26）
//	 * 建筑ID 22  ----电表（设备ID：27）
//	 * 建筑ID 23  ----电表（设备ID：30）
//	 * 建筑ID 24  ----电表（设备ID：31）
//	 * 建筑ID 25  ----电表（设备ID：32）
//	 * 建筑ID 26  ----电表（设备ID：33）
//	 * 建筑ID 27  ----电表（设备ID：34）
//	 */
//	private String targetId;//建筑ID;
//	private String statMode;//统计类型[Meter：设备、Building：建筑、Organization：部门]
//	private String timeUnit;//时间单位[Quarterly：季度、Monthly：月、Daily：天、Hourly：小时]
//	private String statWay;//统计模式[total：合计、Avg：平均、PerCapita、PerUnitArea]
	
	public static String getAuthorization(){
		if(accessToken.getNext_get_time() > System.currentTimeMillis()/1000){
			return accessToken.getToken_type() + " " + accessToken.getAccess_token();
		}else{
			accessToken.setNext_get_time(0);
			System.out.println("更新Token...");
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				HttpPost httpPost = new HttpPost(GET_ACCESS_TOKEN_URL);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("grant_type", GRANT_TYPE));
				nameValuePairs.add(new BasicNameValuePair("username", USERNAME));
				nameValuePairs.add(new BasicNameValuePair("password", PASSWORD));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				CloseableHttpResponse response = httpclient.execute(httpPost);
				try {
					if(response.getStatusLine().getStatusCode() == 200){
						String content = EntityUtils.toString(response.getEntity());
						JSONObject json = JSON.parseObject(content);
						String access_token = json.getString("access_token");
						String token_type = json.getString("token_type");
						long expires_in = json.getLongValue("expires_in");
						accessToken.setAccess_token(access_token);
						accessToken.setToken_type(token_type);
						accessToken.setNext_get_time(System.currentTimeMillis()/1000 + expires_in);
					}
				} catch (Exception e) {
					e.printStackTrace();
					response.close();
				}finally{
					response.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					httpclient.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}finally{
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(accessToken.getNext_get_time() != 0){
				return accessToken.getToken_type() + " " + accessToken.getAccess_token();
			}else{
				return null;
			}
		}
	}
	/**
	 * 设备详情
	 * @param id
	 * @return
	 */
	public static String getMetersDeatil(int id){
		String content = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(METERS_INFO_URL + "/" + id);
            httpget.setHeader("Authorization", getAuthorization());
            httpget.setHeader("Content-Type","application/json; charset=utf-8");
            CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if(response.getStatusLine().getStatusCode() == 200){
					content = EntityUtils.toString(response.getEntity());
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.close();
			}finally{
				response.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
			try {
				httpclient.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
	/**
	 * 设备列表
	 * @return
	 */
	public static String getMetersList(){
		String content = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(METERS_INFO_URL);
            httpget.setHeader("Authorization", getAuthorization());
            httpget.setHeader("Content-Type","application/json; charset=utf-8");
            CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if(response.getStatusLine().getStatusCode() == 200){
					content = EntityUtils.toString(response.getEntity());
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.close();
			}finally{
				response.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
			try {
				httpclient.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
	/**
	 * 建筑能耗查询
	 * @param params
	 * @return
	 */
	public static String getDataByBuildAndEnergyType(String params) {
		String content = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(BUILD_ENERGY_URL);
			httpPost.setHeader("Authorization", getAuthorization());
			httpPost.setHeader("Content-Type","application/json; charset=utf-8");
            StringEntity entity = new StringEntity(params,"UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				if(response.getStatusLine().getStatusCode() == 200){
					content = EntityUtils.toString(response.getEntity());
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.close();
			}finally{
				response.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
			try {
				httpclient.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
	/**
	 * 能耗统计查询
	 * @param params
	 * @return
	 */
	public static String getStatistics(String params) {
		String content = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(ENERGY_COUNT_URL);
			httpPost.setHeader("Authorization", getAuthorization());
			httpPost.setHeader("Content-Type","application/json; charset=utf-8");
            StringEntity entity = new StringEntity(params,"UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				if(response.getStatusLine().getStatusCode() == 200){
					content = EntityUtils.toString(response.getEntity());
					System.out.println(content);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.close();
			}finally{
				response.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
			try {
				httpclient.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
 	public static String statistics(String targetId,String statMode,String timeUnit,
			String statWay,String parameterTypeId,String energyCategoryId, String finishTime){
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
			String test =
			 EnergyClient.getStatistics(params.toString()).replaceAll("null", "\"\"");
			return test;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
 	
