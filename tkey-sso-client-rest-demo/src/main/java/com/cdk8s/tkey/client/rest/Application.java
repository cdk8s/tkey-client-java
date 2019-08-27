package com.cdk8s.tkey.client.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@EnableTkeySso
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Value("${server.port:8080}")
	private String serverPort;

	@Value("${server.servlet.context-path:/demo}")
	private String serverContextPath;


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) {
		log.info("=================================Application Startup Success=================================");
		log.info("User Info >> http://test1.cdk8s.com:{}{}/user?id=123456&name=cdk8s", serverPort, serverContextPath);
		log.info("Logout >> http://test1.cdk8s.com:{}{}/logout", serverPort, serverContextPath);
		log.info("=================================Application Startup Success=================================");
	}

}
