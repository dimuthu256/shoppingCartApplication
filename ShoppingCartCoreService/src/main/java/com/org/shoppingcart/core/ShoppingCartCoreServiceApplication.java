package com.org.shoppingcart.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
//@EnableHystrix
//@EnableHystrixDashboard
@EnableCaching
public class ShoppingCartCoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartCoreServiceApplication.class, args);
	}

}