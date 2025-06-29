package com.example.disovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DisoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisoveryApplication.class, args);
	}

}
