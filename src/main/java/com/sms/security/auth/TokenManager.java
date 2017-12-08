package com.sms.security.auth;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenManager {
	@Value("${com.sms.auth.token.secret:password}")
	private String tokenSecret;
	
	@Value("${com.sms.auth.token.ttl:24}")
	private int tokenTtl;
	
	public String generateToken(String text) {
		//The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
     
        long nowMillis = System.currentTimeMillis();
        long ttlMillis = tokenTtl * 60 * 60 * 1000;
        
        Date now = new Date(nowMillis);
        Date expiration = new Date(nowMillis + ttlMillis);
     
        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(tokenSecret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
     
        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(text).setIssuedAt(now).signWith(signatureAlgorithm, signingKey).setExpiration(expiration);
     
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
	}
	
	public String parseToken(String token) throws Exception {
		//This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(tokenSecret)).parseClaimsJws(token).getBody();
        
        System.out.println("ID: " + claims.getId());
        System.out.println("Expiration: " + claims.getExpiration());
        
		return claims.getId();
	}

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
			String token = createToken("username", 1511070533916l);
			Cookie cookie = new Cookie("auth", token);
			response.addCookie(cookie);
			authorizeToken(token);
			return true;
		}
			
		return true;
	}

    
    public String createToken(String id, long ttlMillis) {
    	 
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
    
    public boolean authorizeToken(String jwt) { 
    	
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
