package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.Model.User;

public interface UserService {
    boolean addUser(User user);

    void removeUser(User user);

    User updateUser(User user);

    User getUser(String username);
}
