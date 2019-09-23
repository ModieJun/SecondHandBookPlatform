package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Model.UserContact;

public interface UserService {
    boolean addUser(User user);

    void removeUser(User user);

    User updateUser(User user);

    User getUser(String username);

    User getUserById(Long id);

    User updateUserContact(Long user_id, UserContact userContact);
}
