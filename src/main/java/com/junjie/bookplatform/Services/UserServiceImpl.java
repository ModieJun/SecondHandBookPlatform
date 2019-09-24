package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.DB.BookRepository;
import com.junjie.bookplatform.DB.UserRepository;
import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Model.UserContact;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService{
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final BookRepository bookRepository;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
     * if the User has been saved Successfully
     */
    @Override
    public boolean addUser(User user) {
        String pw = encoder.encode(user.getPassword());
        user.setPassword(pw).setPasswordConfirm(null);
        logger.warn("PW" + user.getPassword());
        userRepository.save(user);
        return true;
    }

    @Override
    public void removeUser(User user) {
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

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User updateUserContact(Long  user_id, UserContact userContact) {
        User u = this.getUserById(user_id);
        u.setContact(userContact);
        userRepository.save(u);
        return u;
    }
}
