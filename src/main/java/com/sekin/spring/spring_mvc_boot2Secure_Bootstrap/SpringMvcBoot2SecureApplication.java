package com.sekin.spring.spring_mvc_boot2Secure_Bootstrap;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class SpringMvcBoot2SecureApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcBoot2SecureApplication.class, args);
	}
}
