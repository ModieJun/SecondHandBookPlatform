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
        book.setBookOwner(u);
        bookRepository.save(book);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteBook(Book book) {
        bookRepository.delete(book);
        return true;
    }

    @Override
    public Book updateBook(Book book) {
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
    public Book buyBook(Book b, User user) {
        b.setBuyer(user);
        return bookRepository.save(b);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
