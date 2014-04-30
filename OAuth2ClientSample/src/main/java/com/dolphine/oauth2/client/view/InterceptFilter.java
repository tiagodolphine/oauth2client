package com.dolphine.oauth2.client.view;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dolphine.oauth2.client.dto.Configuration;
import com.dolphine.oauth2.client.service.ConfigurationService;
import com.dolphine.oauth2.client.service.Constants;
import com.dolphine.oauth2.client.service.OAuth2ClientService;
import com.dolphine.oauth2.client.service.OAuth2Context;

public class InterceptFilter implements Filter {
	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if(request instanceof HttpServletRequest && response instanceof HttpServletResponse){
			HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			HttpServletResponse httpServletResponse = (HttpServletResponse)response;
						
			String authorizationServer=httpServletRequest.getParameter(Constants.AUTHORIZATION_SERVER_PARAMETER);
			OAuth2Context context = (OAuth2Context)httpServletRequest.getSession().getAttribute(Constants.OAUTH2_CONTEXT_PARAMETER);
			
			if(context==null || (authorizationServer!=null && !context.getAuthorizationServer().equals(authorizationServer))){
				context = new OAuth2Context();
				context.setAuthorizationServer(authorizationServer!=null?authorizationServer:"google");
				httpServletRequest.getSession().setAttribute(Constants.OAUTH2_CONTEXT_PARAMETER, context);
							
				//load OAuth2 Configuration for the authorization server
				Configuration configuration = ConfigurationService.getInstance().loadConfiguration(context.getAuthorizationServer());
				context.setConfiguration(configuration);
				
				String originalURL = httpServletRequest.getRequestURL().append(httpServletRequest.getQueryString()!=null?"?"+httpServletRequest.getQueryString():"").toString();
				String state = URLEncoder.encode(originalURL,"UTF-8");
				
				//get authorization code				
				URL authorizationURL = OAuth2ClientService.buildAuthorizationServiceURI(configuration.getAuthorizationURI(), configuration.getClientId(), configuration.getAuthorizationCallbackURI(), state, configuration.getScope());
				//redirect to authorization server				
				httpServletResponse.sendRedirect(authorizationURL.toString());
				return;
			}
			//already authorized
			chain.doFilter(request, response);
			return;
		}		
	}

	public void init(FilterConfig config) throws ServletException {
	}

}
