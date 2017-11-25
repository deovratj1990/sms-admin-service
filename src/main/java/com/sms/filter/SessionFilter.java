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

import com.sms.security.AuthUtils;

@Component
public class SessionFilter implements Filter {

	@Autowired
	private AuthUtils authUtils; 
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		String authType = ((HttpServletRequest)req).getAuthType();
		String authHeader = ((HttpServletRequest)req).getHeader("Authorization");
		System.out.println("SessionFilter.doFilter authType:  " + authType);
		System.out.println("SessionFilter.doFilter authHeader:  " + authHeader);
		
		if(null != authHeader) {
			String jwt = authHeader.replace(authType + " ", "");
			boolean isJwtValid = authUtils.parseJWT(jwt); //to-do after this
			System.out.println("SessionFilter.doFilter isJwtValid: " + isJwtValid);
		}
			
		
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
    
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}