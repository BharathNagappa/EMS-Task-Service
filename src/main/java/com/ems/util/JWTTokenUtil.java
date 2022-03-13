package com.ems.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ems.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenUtil implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Value("${jwt.secret}")
	private String secret;
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	@Autowired
	UserService userService;
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().
				setSigningKey(secret).
				parseClaimsJws(token).
				getBody();
	}
	
	public String getUsernameFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	private boolean isTokenExpired(String token)
	{
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(String key)
	{
		
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, key);
	}
	
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().
				setClaims(claims).
				setSubject(subject).
				setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public boolean validateToken(String token) {
		
		String userName = getUsernameFromToken(token);
		String unInDB = userService.getUserByName(userName).getUserId();
		return userName.equalsIgnoreCase(unInDB) && !isTokenExpired(token);
	}
}
