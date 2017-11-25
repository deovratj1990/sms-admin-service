package com.sms.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class AuthUtils {

	private boolean checkAuthHeader(String authHeader) {
		if(null == authHeader) {
			return false;
		}
		
		return true;
	}

	//to-do check if token present and valid
    private boolean checkPermission(String authHeader, HttpServletRequest request,
            HttpServletResponse response) {
		if(null == authHeader) {
			String token = createJWT("username", 1511070533916l);
			Cookie cookie = new Cookie("auth", token);
			response.addCookie(cookie);
			parseJWT(token);
			return true;
		}
			
		return true;
	}

    
    public String createJWT(String id, long ttlMillis) {
    	 
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
     
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
     
        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("password"); //to-do
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
     
        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                                    .setIssuedAt(now)
                                    .signWith(signatureAlgorithm, signingKey);
     
        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
        long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
     
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
    
    public boolean parseJWT(String jwt) { 
    	
    	try {
    		//This line will throw an exception if it is not a signed JWS (as expected)
            Claims claims = Jwts.parser()         
               .setSigningKey(DatatypeConverter.parseBase64Binary("password")) //to-do
               .parseClaimsJws(jwt).getBody();
            System.out.println("ID: " + claims.getId());
            System.out.println("Expiration: " + claims.getExpiration());
    		return true;
    	} catch(Exception e) {
    		return false;
    	}
        
    }
    
}
