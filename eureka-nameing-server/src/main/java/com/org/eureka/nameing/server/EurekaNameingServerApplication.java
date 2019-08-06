package com.org.eureka.nameing.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaNameingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaNameingServerApplication.class, args);
	}

}
