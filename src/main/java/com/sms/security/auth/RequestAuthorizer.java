package com.sms.security.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestAuthorizer {
	@Autowired
	private TokenManager tokenManager;
	
	private String tokenType;
	
	private String token;
	
	private String tokenText;
	
	public boolean authorize(HttpServletRequest request) {
		tokenType = "";
		token = "";
		tokenText = "";
		
		String authorizationHeaderValue = request.getHeader("Authorization");
		
		if(authorizationHeaderValue != null && authorizationHeaderValue.length() > 0) {
			String[] authorizationHeaderValueParts = authorizationHeaderValue.trim().split(" ");
			
			if(authorizationHeaderValueParts.length == 2) {
				tokenType = authorizationHeaderValueParts[0];
				token = authorizationHeaderValueParts[1].trim();
				
				if(tokenType == "Bearer" && token.length() != 0) {
					try {
						tokenText = tokenManager.parseToken(token);
					} catch(Exception ex) {
						return false;
					}
				}
			}
		}
		
		return false;
	}
	
	public String getTokenType() {
		return tokenType;
	}
	
	public String getToken() {
		return token;
	}
	
	public String getTokenText() {
		return tokenText;
	}
}
