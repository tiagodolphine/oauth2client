package com.dolphine.oauth2.client.dto;


public class AccessToken {
	
	public final static String ACCESS_TOKEN="access_token";	
	public final static String REFRESH_TOKEN="refresh_token";	
	public final static String EXPIRES_IN="expires_in";	
	public final static String TOKEN_TYPE="token_type";
		
	private String accessToken;
	private String refreshToken;
	private String expiresIn;
	private String tokenType;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	@Override
	public String toString() {	
		return accessToken;
	}	
}
