package com.example.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	@Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 registry.addResourceHandler("/gallery/**")
	 .addResourceLocations("file:/Users/zoomohe/Desktop/gallery/");
	 }
	
}
