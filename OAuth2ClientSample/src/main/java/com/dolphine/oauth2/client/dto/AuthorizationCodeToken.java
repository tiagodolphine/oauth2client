package com.dolphine.oauth2.client.dto;

public class AuthorizationCodeToken {
	//request
	private String scope;
	private String state;
	private String redirect_uri;//Determines where the response is sent
	private String response_type="code";//Indicates Authorization code
	private String client_id;
	private String approval_prompt;
	private String client_secret;
	private String grant_type="authorization_code";
	
	//response
	private String code;
}
