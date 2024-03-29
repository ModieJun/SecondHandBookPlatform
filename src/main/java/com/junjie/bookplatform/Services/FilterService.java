package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;

import java.util.List;

public interface FilterService {
//    List<Book> findBooksByOwner(User user);
    List<Book> getAll();

    List<Book> getAllLoggedIn(User u);

    List<Book> getAllRecent();

    List<Book> getAllFiltered(String book_name, String year_needed,String author,String type, Long start, Long lim);
    /*
        Query = "value"
         this value compared to all attributes;
     */
    List<Book> getByQueryVal(String query,User u);


}
