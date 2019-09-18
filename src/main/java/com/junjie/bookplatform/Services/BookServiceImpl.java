package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.DB.BookRepository;
import com.junjie.bookplatform.DB.UserRepository;
import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.Name;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

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
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "java.persistence.lock.timout",value = "3000")})
    public boolean buyBook(Book b, User user) {
        Book book = this.getBookById(b.getId());
        logger.warn("DEBUG BOOK BUYER: " + book.getBuyer());
        if (book.getBuyer() != null) {
            return false;
        }
        //Object passed from the Controller so, if i set before checking the BUYER() the buyer will be set for the obj
        b.setBuyer(user);
        bookRepository.save(b);
        return true;
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
