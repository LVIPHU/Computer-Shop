package com.example.servercomputer.security;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.example.servercomputer.jwt.JwtConfig;
import com.example.servercomputer.jwt.JwtFilter;
import com.example.servercomputer.jwt.JwtVerifier;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {
	private final SecretKey secretKey;
	private final JwtConfig jwtConfig;
	private static final String[] AUTH_WHITELIST = {
	        "/swagger-resources/**",
	        "/swagger-ui.html",
	        "/swagger-ui/**",
	        "/v2/api-docs",
	        "/webjars/**"
	};
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		AuthenticationConfiguration configuration = http.getSharedObject(AuthenticationConfiguration.class);
		http
			.cors().disable()
			.csrf().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(new JwtFilter(authenticationManager(configuration), jwtConfig, secretKey))
			.addFilterAfter(new JwtVerifier(secretKey, jwtConfig), JwtFilter.class)
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/product/*", "/api/category").permitAll()
				.antMatchers(HttpMethod.POST, "/api/user/").permitAll()
				.antMatchers("/api/user/resetToken/*", "/api/user/resetToken").permitAll()
				.antMatchers(AUTH_WHITELIST).permitAll()
			.anyRequest()
				.authenticated();
		
		return http.build();
	}
}
