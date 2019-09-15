package com.junjie.bookplatform.Controllers;

import com.junjie.bookplatform.DB.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("")
    public String allBooks() {
        return "books";
    }

}
