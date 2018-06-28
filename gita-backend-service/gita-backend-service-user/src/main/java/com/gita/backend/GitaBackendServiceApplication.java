package com.gita.backend;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class GitaBackendServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitaBackendServiceApplication.class, args);
	}
}
