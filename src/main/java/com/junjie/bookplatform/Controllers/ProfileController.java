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
import java.security.Principal;

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
    @RequestMapping("/myBooks")
    public String myBooks(Model model, Principal principal) {
        User u = userService.getUser(principal.getName());
        model.addAttribute("title", "My Books");
        model.addAttribute("myBooks", bookService.getAllBooksUserLoggedIn(u));
        return "profileBooks";
    }

    @RequestMapping("/myBooks/edit")
    public String editMyBook(HttpServletRequest request, Model model) {
        Long book_id = Long.valueOf(request.getParameter("book_id"));
        model.addAttribute("editBook", bookService.getBookById(book_id));
        return "editBook";
    }

    @PostMapping("/myBooks/edit/save")
    public String saveEdit(@RequestParam("author") String author, @RequestParam("year_needed") String year_needed, @RequestParam("price") Double price,@RequestParam("bookName") String bookName,@RequestParam("book_id")Long id) {
            logger.warn("Book Name:  "+ bookName );
        Book b = bookService.getBookById(id);
        b.setBookName(bookName).setAuthor(author).setPrice(price).setYearNeed(year_needed);
            logger.warn("ID: " + b.getId() +"Param: " + bookName + " Author :" + author +
                " year_needed: " + year_needed + " Price : " + price);
        bookService.updateBook(b);
        return "redirect:/profile/myBooks";
    }

    //consumes = {"application/x-www-form-urlencoded"}
    @GetMapping("/myBooks/delete")
    public String removeBookConfirmed(@RequestParam(name = "book_id") String book_id, HttpServletRequest request, Model model) {
//        Book b = bookService.getBookById(Long.valueOf(request.getParameter("book_id")));
        Book b = bookService.getBookById(Long.valueOf(book_id));

        model.addAttribute("confirmBook", b);
        model.addAttribute("deleteBook", true);
        return "confirm";
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
        return "success";
    }

    /*
        View all Bought Books
     */
    @GetMapping("/myBought")
    public String viewBought(Model model) {
        User u = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title", "Bought Books");
        model.addAttribute("boughtBooks", bookService.getAllBoughtBooks(u));
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
