package com.junjie.bookplatform.Controllers;

import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Services.BookService;
import com.junjie.bookplatform.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final BookService bookService;

    private final UserService userService;


    public ProfileController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    /*
        View ALl the Books the user has Listed
     */
    @GetMapping("/myBooks")
    public String myBooks() {
        return  " ";
    }

    @RequestMapping("/myBooks/edit")
    public String editMyBook(HttpServletRequest request) {
        return "";
    }

    @PostMapping("/myBooks/edit/save")
    public String saveEdit(@ModelAttribute("editBook") Book book) {
        return "";
    }

    /*
        View all Bought Books
     */
    @GetMapping("/myBought")
    public String viewBought() {
        return "";
    }

    /*
        View Selected Bought Book
     */
    @GetMapping("/myBought/viewBook")
    public String viewBoughtBook(HttpServletRequest request) {
        return "";
    }




}
