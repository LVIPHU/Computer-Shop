package com.example.clientcomputer.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class Config {
	@Bean
	public MessageSource messageSource() {
	  ReloadableResourceBundleMessageSource messageSource = new 
	  ReloadableResourceBundleMessageSource();
	  messageSource.setBasename("classpath:AppResources");
	  messageSource.setDefaultEncoding("UTF-8");
	  return messageSource;
	}

	/*
	 * Create LocaleResolver Bean
	 */
	@Bean
	public LocaleResolver localeResolver(){
	  CookieLocaleResolver resolver = new CookieLocaleResolver();
	  resolver.setDefaultLocale(new Locale("vi")); // your default locale
	  return resolver;
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// TODO Auto-generated method stub
				registry.addMapping("/**")
				.allowedOrigins("http://localhost:8080")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*");
			}
		};
	}
}
