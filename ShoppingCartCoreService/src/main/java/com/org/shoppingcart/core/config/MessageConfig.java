package com.org.shoppingcart.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@Data
@PropertySource("classpath:message_config.properties")
public class MessageConfig {

	@Value("${shoppingcart.validation.failed.message}")
	private String validationFailed;

	@Value("${shoppingcart.methodnotsupported.message}")
	private String httpRequestMethodNotSupported;

	@Value("${shoppingcart.product.details.notfound}")
	private String productDetailsNotFound;

	@Value("${shoppingcart.status.success}")
	private String success;

	@Value("${shoppingcart.status.failed}")
	private String failed;

	@Value("${shoppingcart.product.details.add.filed}")
	private String productDetailsAddingFailed;

}
