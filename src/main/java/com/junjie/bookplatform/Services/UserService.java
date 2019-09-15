package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.DB.UserRepository;
import com.junjie.bookplatform.Model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        User u = new User();
        u.setUsername("jj");
        u.setPassword(encoder.encode("123"));
        this.userRepository.save(u);
    }
}
