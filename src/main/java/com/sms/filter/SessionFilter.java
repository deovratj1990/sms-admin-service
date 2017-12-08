package com.sms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sms.security.auth.RequestAuthorizer;

@Component
public class SessionFilter implements Filter {

	@Autowired
	private RequestAuthorizer requestAuthorizer;
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		boolean isJwtValid = requestAuthorizer.authorize(httpRequest);
		
		System.out.println("SessionFilter.doFilter tokenType: " + requestAuthorizer.getTokenType());
		System.out.println("SessionFilter.doFilter token: " + requestAuthorizer.getToken());
		System.out.println("SessionFilter.doFilter tokenText: " + requestAuthorizer.getTokenText());
		System.out.println("SessionFilter.doFilter isJwtValid: " + isJwtValid);
		
		httpRequest.setAttribute("userEmail", requestAuthorizer.getTokenText());
		
		chain.doFilter(request, response);
	}
    
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}