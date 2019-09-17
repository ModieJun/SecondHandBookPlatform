package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.DB.BookRepository;
import com.junjie.bookplatform.DB.UserRepository;
import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService{
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final BookRepository bookRepository;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.bookRepository = bookRepository;
    }

    /**
     *
     * @param user
     *      Username [@NotNUll]
     *      Password should be In Plain text
     * @return
     *      {True, False}
     * if the User has been saved Succesfully
     */
    @Override
    public boolean addUser(User user) {
        User u= this.getUser(user.getUsername());
        if (u != null) {
            return false;
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public void removeUser(User user) {
        //!!!!!!!!!!before removing user need to remove the books first . Hard code
        userRepository.delete(user);
    }

    @Override
    public User updateUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
