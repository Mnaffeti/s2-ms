package com.example.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		String token = dotenv.get("MY_SECRET_TOKEN");
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
