/*
 * Copyright (c)-- Created By JUNJIE Lin -> On 2019/9/23
 */

package com.junjie.bookplatform.Validators;

import com.junjie.bookplatform.Model.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BookValidator  implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //Explicit Convert
        Book b=(Book)o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bookName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"author","NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "yearNeed", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"type","NotEmpty");

        if (noSymbol(b.getBookName())){
            errors.rejectValue("bookName","Book.regex.ContainsSpecialSymb");
        }
        if (noSymbol(b.getAuthor())) {
            errors.rejectValue("author","Book.regex.ContainsSpecialSymb");
        }
    }

    /**
     *
     * @param inp String
     * @return iF does contain will return --> TRUE
     *  else if none found --> FASLE
     */
    private boolean noSymbol(String inp) {
        Pattern p = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        Matcher m = p.matcher(inp);
        return m.find();
    }
}
