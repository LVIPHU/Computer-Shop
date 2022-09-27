package com.example.clientcomputer.config;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class VerifyJWT {
	private static String secretKey = "thisissecurethisissecurethisissecurethisissecurethisissecurethisissecure";
	private static String prefix = "Bearer";
	
	public static String checkRole(String token) {
		token = token.replace(prefix, "");
		Jws<Claims> claimsJws = Jwts.parser()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.parseClaimsJws(token);
		Claims body = claimsJws.getBody();
		
		var authorities = (List<Map<String, String>>)body.get("authorities");
		String role = "";
		for (Map<String, String> map : authorities) {
			role = map.get("authority");
		}
		return role;
	}
	
	public static void verify(String token, HttpSession session) {
		token = token.replace(prefix, "");
		Jws<Claims> claimsJws = Jwts.parser()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.parseClaimsJws(token);
		Claims body = claimsJws.getBody();
		
		var authorities = (List<Map<String, String>>)body.get("authorities");
		String role = "";
		for (Map<String, String> map : authorities) {
			role = map.get("authority");
		}
		
		session.setAttribute("role", role);
		session.setAttribute("userMail", body.getSubject());
	}
}
