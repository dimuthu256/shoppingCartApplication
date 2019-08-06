package com.org.shoppingcart.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix="rabbitmq")
@Data
public class CongifDetails {
	private String queue;
	private String exchange;
	private String routingKey;
	
}
