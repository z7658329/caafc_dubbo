package com.micro;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class SparkApplication {

	public static void main(String[] args) {
		System.out.println("-----------------------");
		log.info("SparkApplication main=============");
		SpringApplication.run(SparkApplication.class, args);
		log.info("SparkApplication main=============");
	}
}
