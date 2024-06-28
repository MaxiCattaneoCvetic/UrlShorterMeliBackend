package com.example.MeliUrlShorter;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class MeliUrlShorterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeliUrlShorterApplication.class, args);
	}

}
