package com.junjie.bookplatform.Controllers;

import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.Type;
import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Services.BookService;
import com.junjie.bookplatform.Services.FilterService;
import com.junjie.bookplatform.Services.UserService;
import com.junjie.bookplatform.Validators.BookValidator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.processing.Filer;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private FilterService filterService;
    @Autowired
    @Qualifier("bookValidator")
    private Validator validator;

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @GetMapping("/")
    public String allBooks(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("books", filterService.getAll());
        } else {
            User u = userService.getUser(principal.getName());
            model.addAttribute("books", filterService.getAllLoggedIn(u));
        }
        model.addAttribute("start", 0);
        return "books";
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("newBook", new Book());
        return "addBook";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("newBook") Book book, @RequestParam("imgFile") MultipartFile file, Principal principal, HttpServletRequest request, BindingResult result) {
        validator.validate(book,result);
        if (result.hasErrors()) {
            return "addBook";
        }
        User u = userService.getUser(principal.getName());
//        book.setYearNeed(request.getParameter("year_needed"));
        logger.warn("Type: " + Type.valueOf(request.getParameter("type")));
//        book.setType(Type.valueOf(request.getParameter("type")));
        bookService.addBook(book, u, file);
        return "success";
    }


    @PostMapping("/buy")
    public String buyBook(HttpServletRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userService.getUser(username);
        Book book = bookService.getBookById(Long.valueOf(request.getParameter("book_id")));
        logger.warn("Book id Buy: " + book.getId());
        if (bookService.buyBook(book, u)) {
            return "success";
        }
        return "failure";
    }

    @PostMapping("/buy/confirm")
    public String buyBookConfirm(HttpServletRequest request, Model model) {
        Long id = Long.valueOf(request.getParameter("book_id"));
        Book b = bookService.getBookById(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userService.getUser(username);
        if (u == null) {
            return "redirect:/login";
        }
        logger.info("Buying Book : " + b.getBookName() + " User id: " + u.getUser_Id());
        if (b.getBookOwner().getUsername().equals(u.getUsername())) {
            return "redirect:/books/";
        }
        model.addAttribute("confirmBook", b);
        model.addAttribute("buyBook", true);
        return "confirm";
    }

    /**
     * @param id       Id of the Image in DB
     * @param response ByteStream will be returned to the Front end
     * @throws IOException
     */
    @GetMapping("/image/{id}")
    public void showBookImg(@PathVariable String id, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        Book book = bookService.getBookById(Long.valueOf(id));
        if (book.getImage() != null) {
            InputStream is = new ByteArrayInputStream(book.getImage().getImgFile());
            IOUtils.copy(is, response.getOutputStream());
        }

    }

    /*
      -------------Filter Mappings-------------
     */
    @GetMapping("/search")
    public String filter(Model model, HttpServletRequest req) {
        Map<String, String> values = new HashMap<>();
        values.put("book_name", req.getParameter("book_name"));
        values.put("author", req.getParameter("author"));
        values.put("start", req.getParameter("start"));
        values.put("lim", req.getParameter("lim"));
        values.put("type",req.getParameter("type"));
        values.put("recent", req.getParameter("recent"));
        values.put("year_needed", req.getParameter("year_needed"));

        logger.warn("query: " + values.get("book_name") + " ," + values.get("start") + " ," + values.get("lim") + ",y_n:" + values.get("year_needed"));

        //Query
        if (Boolean.valueOf(req.getParameter("recent"))) {
            model.addAttribute("books", filterService.getAllRecent());
        } else {
            model.addAttribute("books", filterService.getAllFiltered(values.get("book_name"),
                    values.get("year_needed"), values.get("author"),values.get("type"),
                    Long.valueOf(values.get("start")), Long.valueOf(values.get("lim"))));

        }
        model.addAttribute("start", Long.valueOf(values.get("start")));
        return "books";
    }

}
