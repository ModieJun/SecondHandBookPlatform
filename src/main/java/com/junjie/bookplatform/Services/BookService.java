package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BookService {
    boolean addBook(Book book, User u, MultipartFile f);

    boolean deleteBook(Book book);

    Book updateBook(Book book, Map<String,String> values);

    Book getBook(String book_name);

    Book getBookById(Long id);

    boolean buyBook(Book b, User user);


    List<Book> getAllBooksOwner(User user);

    List<Book> getAllBoughtBooks(User user);
}
