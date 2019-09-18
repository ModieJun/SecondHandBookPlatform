package com.junjie.bookplatform.Controllers;

import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Services.BookService;
import com.junjie.bookplatform.Services.UserService;
import org.slf4j.Logger;
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

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

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
        bookService.addBook(book, u);
        return "redirect:/";
    }

    //consumes = {"application/x-www-form-urlencoded"}
    @PostMapping("/delete")
    public String removeBook( HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("book_id"));
        Book b = bookService.getBookById(id);
        User u = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (b.getBookOwner().getUser_Id() != u.getUser_Id()) {
            return "/";
        }
        logger.warn("Book deleted: " + b.getBookName());
        bookService.deleteBook(b);
        return "redirect:/";
    }

    @PostMapping("/buy")
    public String buyBook(HttpServletRequest request) {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userService.getUser(username);
        Book book = bookService.getBookById(Long.valueOf(request.getParameter("book_id")));
        logger.warn("Book id Buy: " + book.getId());
        if (bookService.buyBook(book,u)) {
            return "success";
        }
        return "failure";
    }

    @PostMapping("/buy/confirm")
    public String buyBookConfirm(HttpServletRequest request,Model model) {
        Long id = Long.valueOf(request.getParameter("book_id"));
        Book b = bookService.getBookById(id);
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userService.getUser(username);
        if (u == null) {
            return "redirect:/login";
        }
        logger.info("Buying Book : " + b.getBookName() + " User id: "+u.getUser_Id());
        if (b.getBookOwner().getUsername().equals(u.getUsername())) {
            return "redirect:/books/";
        }
        model.addAttribute("confirmBook",b);
        return "confirm";
    }


}
