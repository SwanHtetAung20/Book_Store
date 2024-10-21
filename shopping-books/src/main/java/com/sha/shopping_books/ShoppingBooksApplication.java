package com.sha.shopping_books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ShoppingBooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingBooksApplication.class, args);
	}

}
