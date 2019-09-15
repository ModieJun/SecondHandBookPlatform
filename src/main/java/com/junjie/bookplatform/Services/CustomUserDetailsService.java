package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.DB.UserRepository;
import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Model.UserPrinciple;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    /*
        Dependancy Injection of the Repository
     */
    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    /*
        Load the userPrinciple for Authentication
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u = repository.findByUsername(s);
        if (u == null) {
            throw new UsernameNotFoundException("Not found" + s);
        }
        return new UserPrinciple(u);
    }
}
