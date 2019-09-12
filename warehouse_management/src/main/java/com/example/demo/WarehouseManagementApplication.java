package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.constants.Constants;

@SpringBootApplication
public class WarehouseManagementApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext ctx = SpringApplication.run(WarehouseManagementApplication.class, args);
		String serverPort = ctx.getEnvironment().getProperty("server.port");
		Constants.url = "http://localhost:" + serverPort + "/";
	}

}
