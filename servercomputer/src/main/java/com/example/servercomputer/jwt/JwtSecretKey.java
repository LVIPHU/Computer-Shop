package com.example.servercomputer.jwt;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class JwtSecretKey {
	@Autowired
	private final JwtConfig jwtConfig;
	
	@Bean 
	public SecretKey secretKey() {
		return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
	}
}
