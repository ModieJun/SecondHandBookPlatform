package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.Model.Book;

import java.util.List;

public interface BookService {
    boolean addBook(Book book);

    boolean deleteBook(Book book);

    Book updateBook(Book book);

    Book getBook(Long book_Id);

    List<Book> getBooks();
}
