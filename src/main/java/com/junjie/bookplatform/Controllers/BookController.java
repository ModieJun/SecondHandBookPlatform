package com.junjie.bookplatform.Controllers;

import com.junjie.bookplatform.Components.ImageResizer;
import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Services.BookService;
import com.junjie.bookplatform.Services.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


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

    @PostMapping("/add" )
    public String addBook(@ModelAttribute("newBook") Book book, @RequestParam("imgFile") MultipartFile file) {
        User u = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        bookService.addBook(book, u,file);
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

    @GetMapping("/image/{id}")
    public void showBookImg(@PathVariable String id, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        Book book = bookService.getBookById(Long.valueOf(id));
        if (book.getImage()!=null) {
            InputStream is = new ByteArrayInputStream(book.getImage());
            IOUtils.copy(is,response.getOutputStream());
        }

    }
}
