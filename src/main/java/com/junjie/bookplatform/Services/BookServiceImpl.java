package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.DB.BookRepository;
import com.junjie.bookplatform.DB.UserRepository;
import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;

    }

    @Override
    public boolean addBook(Book book) {
        bookRepository.save(book);
        return true;
    }

    @Override
    public boolean deleteBook(Book book) {
        Book b = this.getBook(book.getId());
        if (b == null) {
            return false;
        }
        bookRepository.delete(b);
        return true;
    }

    @Override
    public Book updateBook(Book book) {
        Book b = this.getBook(book.getId());
        if (b == null) {
            return null;
        }
        bookRepository.save(book);
        return book;
    }

    @Override
    public Book getBook(Long book_Id) {
        Optional<Book> b = bookRepository.findById(book_Id);
        return b.get();
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
