package com.gita.backend;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@EnableDubbo
@SpringBootApplication(scanBasePackages = "com.gita.backend")
public class GitaBackendWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitaBackendWebApplication.class, args);
	}
}
