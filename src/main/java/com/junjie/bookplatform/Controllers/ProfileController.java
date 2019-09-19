package com.junjie.bookplatform.Controllers;

import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Services.BookService;
import com.junjie.bookplatform.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final BookService bookService;

    private final UserService userService;

    private static Logger logger = LoggerFactory.getLogger(ProfileController.class);


    public ProfileController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    /*
        View ALl the Books the user has Listed
     */
    @GetMapping("/myBooks")
    public String myBooks(Model model) {
        User u = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title", "My Books");
        model.addAttribute("myBooks",bookService.getAllBooksUserLoggedIn(u));
        return  "profileBooks";
    }

    @RequestMapping("/myBooks/edit")
    public String editMyBook(HttpServletRequest request) {
        return "";
    }

    @PostMapping("/myBooks/edit/save")
    public String saveEdit(@ModelAttribute("editBook") Book book) {
        return "";
    }

    //consumes = {"application/x-www-form-urlencoded"}
    @GetMapping("/myBooks/delete")
    public String removeBookConfirmed( HttpServletRequest request) {

        return "";
    }

    @PostMapping("/myBooks/delete/confirm")
    public String removeBook(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("book_id"));
        Book b = bookService.getBookById(id);
        User u = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        //preventative measure
        if (b.getBookOwner().getUser_Id() != u.getUser_Id()) {
            return "/";
        }
        logger.warn("Deleting Book: " + b.getBookName() + " , Book Id: " + b.getId());
        bookService.deleteBook(b);
        return "";
    }
    /*
        View all Bought Books
     */
    @GetMapping("/myBought")
    public String viewBought(Model model) {
        User u = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title","Bought Books");
        model.addAttribute("boughtBooks",bookService.getAllBoughtBooks(u));
        return "profileBooks";
    }

    /*
        View Selected Bought Book
     */
    @GetMapping("/myBought/viewBook")
    public String viewBoughtBook(HttpServletRequest request) {
        return "";
    }




}
