package com.nju.bff_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableHystrix
@SpringBootApplication
public class BffServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BffServiceApplication.class, args);
	}
}
