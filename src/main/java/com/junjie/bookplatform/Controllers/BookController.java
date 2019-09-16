package com.junjie.bookplatform.Controllers;

import com.junjie.bookplatform.DB.BookRepository;
import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Services.BookService;
import com.junjie.bookplatform.Services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String allBooks(Model model) {
        model.addAttribute("books", bookService.getBooks());
        return "books";
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("newBook", new Book());
        return "addBook";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("newBook") Book book) {
        User u = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        book.setBookOwner(u);
        bookService.addBook(book, u);
        return "redirect:/";
    }

    //consumes = {"application/x-www-form-urlencoded"}
    @PostMapping("/delete")
    public String removeBook( HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("book_id"));
        Book b = bookService.getBookById(id);
        LoggerFactory.getLogger(BookController.class).warn("Book deleted: " + b.getBookName());
        bookService.deleteBook(b, new User());
        return "redirect:/";
    }


}
