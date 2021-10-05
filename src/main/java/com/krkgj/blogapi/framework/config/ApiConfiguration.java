package com.krkgj.blogapi.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfiguration implements WebMvcConfigurer 
{
	@Override
	public void addCorsMappings(CorsRegistry registry) 
	{
		// TODO Auto-generated method stub
		registry.addMapping("/**").allowedOrigins("http://localhost:8080", "http://172.25.200.72:8080/");
	}
	
	
}
