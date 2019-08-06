package com.org.shoppingcart.controller.util;

import java.io.Serializable;

import org.springframework.web.client.RestTemplate;

public class ServiceManager implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected RestTemplate restTemplate;
	protected String CONTEXT_PATH = "";
	
	public ServiceManager() {
		CONTEXT_PATH = ResourceUtil.getAppConfigValue("shopping.cart.rest.url");
	}
	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
