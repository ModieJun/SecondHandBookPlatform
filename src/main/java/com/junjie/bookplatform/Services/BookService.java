package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;

import java.util.List;

public interface BookService {
    boolean addBook(Book book, User u);

    boolean deleteBook(Book book);

    Book updateBook(Book book);

    Book getBook(String book_name);

    Book getBookById(Long id);

    Book buyBook(Book b, User user);

    List<Book> getBooks();
}
