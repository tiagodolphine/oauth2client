package com.dolphine.oauth2.client.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.dolphine.oauth2.client.dto.AccessToken;
import com.dolphine.oauth2.client.dto.Configuration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class OAuth2ClientService {

	public final static String CLIENT_ID = "client_id";
	public final static String REDIRECT_URI = "redirect_uri";
	public final static String STATE = "state";
	public final static String SCOPE = "scope";
	public final static String RESPONSE_TYPE = "response_type";
	public final static String CODE = "code";
	public final static String GRANT_TYPE = "grant_type";
	public final static String CLIENT_SECRET = "client_secret";
	public final static String AUTHORIZATION_HEADER = "Authorization";
	
	public Client client;
	public Client authenticatedClient;

	public OAuth2ClientService() {
		if (client == null) {
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			client = Client.create(clientConfig);
		}
	}

	public static URL buildAuthorizationServiceURI(
			String authorizationServiceURI, String clientId,
			String redirectUri, String state, String scope)
			throws MalformedURLException, UnsupportedEncodingException {

		StringBuilder sb = new StringBuilder(authorizationServiceURI);
		sb.append("?").append(CLIENT_ID).append("=").append(clientId);
		sb.append("&").append(REDIRECT_URI).append("=").append(redirectUri);
		sb.append("&").append(RESPONSE_TYPE).append("=").append("code");
		sb.append("&").append(SCOPE).append("=").append(scope);
		sb.append("&").append(STATE).append("=").append(state);
		
		return new URL(sb.toString());
	}

	public AccessToken getAccessToken(String authorizationCode,Configuration configuration) {
		
			// Client client = Client.create();

			WebResource webResource = client.resource(configuration.getTokenURI());
	
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add(CLIENT_ID, configuration.getClientId());
			formData.add(CLIENT_SECRET, configuration.getClientSecret());
			formData.add(REDIRECT_URI, configuration.getAuthorizationCallbackURI());//this is the URL that the request was coming before
			formData.add(CODE, authorizationCode);			
			formData.add(GRANT_TYPE, "authorization_code");

			ClientResponse response = webResource.type(
					MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
					ClientResponse.class, formData);
			
			String output; 
			AccessToken accessToken=null;
		
			boolean isJsonAccessToken = response.getType()!=null && response.getType().isCompatible(MediaType.APPLICATION_JSON_TYPE);
			//response is JSON
			if(isJsonAccessToken){
				try{	
					accessToken = response.getEntity(AccessToken.class);
				}catch(ClientHandlerException e){
					System.err.println("Json is not an AccessToken");
					isJsonAccessToken=false;
				}					
			}
			//response is Query parameter
			if(!isJsonAccessToken){
				output = response.getEntity(String.class);
				accessToken =new AccessToken();				
				accessToken.setAccessToken(getQueryMap(output).get("access_token"));
			}
			System.out.println("Received Access Token:");
			System.out.println(accessToken);					
			return accessToken;
	}
	
	public static Map<String, String> getQueryMap(String query)  
	{  
	    String[] params = query.split("&");  
	    Map<String, String> map = new HashMap<String, String>();  
	    for (String param : params)  
	    {  
	        String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        map.put(name, value);  
	    }  
	    return map;  
	}  

	public String performGet(String url, AccessToken accessToken) {

		if (authenticatedClient == null) {
			authenticatedClient = Client.create();
			authenticatedClient.addFilter(new OAuth2RequestFilter(accessToken.getAccessToken()));
		}

		WebResource webResource = authenticatedClient.resource(url);
		ClientResponse response = webResource.get(ClientResponse.class);
		String output = response.getEntity(String.class);
		return output;
	}

	private class OAuth2RequestFilter extends ClientFilter {

		private String oauth2TokenHeader;

		public OAuth2RequestFilter(String oauth2Token) {
			this.oauth2TokenHeader = "Bearer " + oauth2Token;
		}

		@Override
		public ClientResponse handle(ClientRequest request)
				throws ClientHandlerException {
			request.getHeaders().putSingle(AUTHORIZATION_HEADER, oauth2TokenHeader);
			return getNext().handle(request);
		}
		
	}
}
