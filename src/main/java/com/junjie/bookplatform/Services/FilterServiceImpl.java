package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.DB.BookRepository;
import com.junjie.bookplatform.DB.UserRepository;
import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterServiceImpl implements FilterService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll(PageRequest.of(0,10)).getContent();
    }

    @Override
    public List<Book> getAllLoggedIn(User u) {
        return bookRepository.findBooksByBookOwnerNot(u);
    }

    @Override
    public List<Book> getAllRecent() {
        return bookRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0,10));
    }

    @Override
    public List<Book> getAllFiltered(String book_name, String year_needed, String author
            ,Long start, Long lim) {
        return bookRepository.getAll(book_name, year_needed,author, PageRequest.of(start.intValue(), lim.intValue()));
    }
}
