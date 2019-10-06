package com.junjie.bookplatform.Controllers;

import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Model.UserContact;
import com.junjie.bookplatform.Services.BookService;
import com.junjie.bookplatform.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

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

    private boolean isRememberMeAuthenticated() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }
    /*
        Add Contact Information
     */

    @GetMapping("/addcontact")
    public String addContactPage(Model model,Principal principal) {
        User u = userService.getUser(principal.getName());
        model.addAttribute("user",u);
        return "addContact";
    }

    @PostMapping("/addcontact")
    public String addContact(HttpServletRequest request, Principal principal) {
        String contact = request.getParameter("contact");
        Long id = Long.valueOf(request.getParameter("user_id"));
        userService.updateUserContact(id,new UserContact(contact));
        return "redirect:/";
    }

    /*
        View ALl the Books the user has Listed
     */
    @RequestMapping("/myBooks")
    public String myBooks(Model model, Principal principal) {
        User u = userService.getUser(principal.getName());
        model.addAttribute("title", "My Books");
        model.addAttribute("myBooks", bookService.getAllBooksOwner(u));
        return "profileBooks";
    }

    @RequestMapping("/myBooks/edit")
    public String editMyBook(HttpServletRequest request, Model model) {
        Long book_id = Long.valueOf(request.getParameter("book_id"));
        model.addAttribute("editBook", bookService.getBookById(book_id));
        return "editBook";
    }

    @PostMapping("/myBooks/edit/save")
    public String saveEdit(HttpServletRequest request) {
        Map<String,String> values = new HashMap<>();
        values.put("bookName", request.getParameter("bookName"));
        values.put("author", request.getParameter("author"));
        values.put("type",request.getParameter("type"));
        values.put("year_needed", request.getParameter("year_needed"));
        values.put("price",request.getParameter("price"));
        Long id = Long.valueOf(request.getParameter("book_id"));

        logger.warn("Year_needed: " + values.get("year_needed"));
        logger.warn("Book Name:  "+ values.get("book_name"));
        Book b = bookService.getBookById(id);
        bookService.updateBook(b,values);
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
