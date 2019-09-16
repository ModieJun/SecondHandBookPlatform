package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.DB.BookRepository;
import com.junjie.bookplatform.DB.UserRepository;
import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FilterServiceImpl implements FilterService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private  UserRepository userRepository;

//    @Override
//    public List<Book> findBooksByOwner(User user) {
//        return bookRepository.findBooksByBookOwner(user);
//    }
}
