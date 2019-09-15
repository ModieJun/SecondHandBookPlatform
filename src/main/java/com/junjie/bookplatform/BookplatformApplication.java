package com.junjie.bookplatform;

import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Services.BookService;
import com.junjie.bookplatform.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookplatformApplication {
	@Autowired
	private UserService service;
	@Autowired
	private BookService bookService;

	public static void main(String[] args) {
		SpringApplication.run(BookplatformApplication.class, args);
	}

}
