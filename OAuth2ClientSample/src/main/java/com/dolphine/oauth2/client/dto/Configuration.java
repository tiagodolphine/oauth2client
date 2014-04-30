package com.dolphine.oauth2.client.dto;

import java.util.Properties;

public class Configuration {

	public String authorizationURI;
	public String tokenURI;	
	public String clientId;
	public String scope;
	public String authorizationCallbackURI;
	public String clientSecret;
	public String profileUrl;
	
	public Configuration(Properties properties){
		this.authorizationURI=properties.getProperty("authorizationURI");
		this.tokenURI=properties.getProperty("tokenURI");
		this.clientId=properties.getProperty("clientId");
		this.authorizationCallbackURI=properties.getProperty("authorizationCallbackURI");
		this.clientSecret=properties.getProperty("clientSecret");
		this.profileUrl=properties.getProperty("profileUrl");
		this.scope=properties.getProperty("scope");
	}
	
	public String getAuthorizationURI() {
		return authorizationURI;
	}
	public void setAuthorizationURI(String authorizationURI) {
		this.authorizationURI = authorizationURI;
	}
	public String getTokenURI() {
		return tokenURI;
	}
	public void setTokenURI(String tokenURI) {
		this.tokenURI = tokenURI;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getAuthorizationCallbackURI() {
		return authorizationCallbackURI;
	}
	public void setAuthorizationCallbackURI(String authorizationCallbackURI) {
		this.authorizationCallbackURI = authorizationCallbackURI;
	}	
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	} 	
}
