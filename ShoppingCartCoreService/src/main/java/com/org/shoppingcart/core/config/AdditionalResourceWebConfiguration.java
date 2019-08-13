package com.org.shoppingcart.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

	@Value("${image.save.path}")
	private String imagePath;

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("file:///" + imagePath);
	}

}