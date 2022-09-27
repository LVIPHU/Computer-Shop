package com.example.servercomputer.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.google.common.net.HttpHeaders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtConfig {
	private String secretKey;
	private String tokenPrefix;
	private Integer tokenExpirationAfterDays;
	
	public String getAuthorizationHeader() {
		return HttpHeaders.AUTHORIZATION;
	}
}
