package com.zhuantitu.energy.client;

public class AccessToken {
	private  String access_token;
	private  String token_type;
	private  long next_get_time;
	public  String getAccess_token() {
		return access_token;
	}
	public  void setAccess_token(String accessToken) {
		access_token = accessToken;
	}
	public  String getToken_type() {
		return token_type;
	}
	public  void setToken_type(String tokenType) {
		token_type = tokenType;
	}
	public  long getNext_get_time() {
		return next_get_time;
	}
	public  void setNext_get_time(long nextGetTime) {
		next_get_time = nextGetTime;
	}
}
