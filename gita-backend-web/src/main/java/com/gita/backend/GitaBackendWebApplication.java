package com.gita.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication(scanBasePackages = "com.gita.backend")
public class GitaBackendWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitaBackendWebApplication.class, args);
		log.info("GitaBackendServiceApplication start success!!!");
	}
}
