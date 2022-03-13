package com.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EmsTaskServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmsTaskServiceApplication.class, args);
	}

}
