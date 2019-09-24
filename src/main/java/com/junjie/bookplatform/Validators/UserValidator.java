/*
 * Copyright (c)-- Created By JUNJIE Lin -> On 2019/9/23
 */

package com.junjie.bookplatform.Validators;

import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator  implements Validator {

    @Autowired
    private UserService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ///Explicit convert OBJ
        User u = (User)o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","NotEmpty");
        if (u.getUsername().length() < 3 || u.getUsername().length() > 15) {
            errors.rejectValue("username","Size.newUser.username");
        }
        if (service.getUser(u.getUsername()) != null) {
            errors.rejectValue("username","Duplicate.newUser.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","NotEmpty");
        if (u.getPassword().length() < 6 || u.getPassword().length() > 32) {
            errors.rejectValue("password","Size.newUser.password");
        }

        if (!u.getPassword().equals(u.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm","Diff.newUser.passwordConfirm");
        }
    }
}
