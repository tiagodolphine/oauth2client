package com.dolphine.oauth2.client.service;

import com.dolphine.oauth2.client.dto.AccessToken;
import com.dolphine.oauth2.client.dto.Configuration;

public class OAuth2Context {

	private AccessToken oauth2Token;
	private String userName;
	private String userId;
	private String authorizationServer;
	private Configuration configuration;

	public OAuth2Context() {

	}

	public OAuth2Context(AccessToken oauth2Token, String userName, String userId) {
		super();
		this.oauth2Token = oauth2Token;
		this.userName = userName;
		this.userId = userId;
	}

	public AccessToken getOauth2Token() {
		return oauth2Token;
	}

	public void setOauth2Token(AccessToken oauth2Token) {
		this.oauth2Token = oauth2Token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAuthorizationServer() {
		return authorizationServer;
	}

	public void setAuthorizationServer(String authorizationServer) {
		this.authorizationServer = authorizationServer;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

}
