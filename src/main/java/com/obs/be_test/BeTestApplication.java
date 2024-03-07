package com.obs.be_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BeTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeTestApplication.class, args);
	}

}
