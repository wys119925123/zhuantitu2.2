package com.system.utils;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	public static String get(String url){
		String content = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
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
}
