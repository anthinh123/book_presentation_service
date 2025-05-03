package com.thinh.bookpresentationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BookPresentationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookPresentationServiceApplication.class, args);
	}

}
