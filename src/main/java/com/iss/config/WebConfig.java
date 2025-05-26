package com.iss.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${cors.allowed-origins}")
	private String[] allowedOrigins; // automatically splits by comma

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(allowedOrigins)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*").allowCredentials(true)
				.exposedHeaders("accessToken", "refreshToken"); // Optional if you need them
	}

}
