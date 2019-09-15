package com.junjie.bookplatform.Controllers;

import com.junjie.bookplatform.DB.BookRepository;
import com.junjie.bookplatform.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public String allBooks(Model model) {
        model.addAttribute("books",bookService.getBooks());
        return "books";
    }

}
