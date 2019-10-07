package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.DB.BookRepository;
import com.junjie.bookplatform.DB.UserRepository;
import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.Type;
import com.junjie.bookplatform.Model.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterServiceImpl implements FilterService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll(PageRequest.of(0, 10)).getContent();
    }

    @Override
    public List<Book> getAllLoggedIn(User u) {
        return bookRepository.findBooksByBookOwnerNotAndBuyerNull(u);
    }

    @Override
    public List<Book> getAllRecent() {
        return bookRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, 10));
    }

    @Override
    public List<Book> getAllFiltered(String book_name, String year_needed, String author, String type
            , Long start, Long lim) {
        Type t = null;
        String bn=null;
        String auth= null;
        if (type != null && !type.equals("")) {
            t = Type.valueOf(type);
        }
        if (!book_name.equals("")) {
            bn=book_name;
        }
        if (!author.equals("")) {
            auth=author;
        }

        return bookRepository.getAll(bn, year_needed, auth, t, PageRequest.of(start.intValue(), lim.intValue()));
    }

    @Override
    public List<Book> getByQueryVal(String query,@Nullable User u) {
        return bookRepository.getAllByBookNameContainingOrAuthorContainingAndBookOwnerNot(query,query,u);
    }
}
