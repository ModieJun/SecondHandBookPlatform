package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.DB.BookRepository;
import com.junjie.bookplatform.DB.UserRepository;
import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public boolean addBook(Book book, User u) {
//        book.setBookOwner(u);
        bookRepository.save(book);
//        u.addBook(book);
//        userRepository.save(u);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteBook(Book book, User u) {
//        book.setBookOwner(null);
        bookRepository.delete(book);
//        u.removeBook(book);
//        userRepository.save(u);
        return true;
    }

    @Override
    public Book updateBook(Book book) {
        Book b = bookRepository.findById(book.getId()).get();
        if (b == null) {
            return null;
        }
        bookRepository.save(book);
        return book;
    }
    /*
        book repository Used
     */

    @Override
    public Book getBook(String book_name) {
        Book b = bookRepository.findByBookName(book_name);
        return b;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
