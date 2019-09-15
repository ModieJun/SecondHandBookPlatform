package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.DB.UserRepository;
import com.junjie.bookplatform.Model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService{
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
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
    public boolean removeUser(String username) {
        User u = this.getUser(username);
        if (u != null) {
            return false;
        }
        userRepository.delete(u);
        return true;
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
