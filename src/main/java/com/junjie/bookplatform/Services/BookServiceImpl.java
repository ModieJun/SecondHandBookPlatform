package com.junjie.bookplatform.Services;

import com.junjie.bookplatform.Components.ImageResizer;
import com.junjie.bookplatform.DB.BookRepository;
import com.junjie.bookplatform.DB.UserRepository;
import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.Img;
import com.junjie.bookplatform.Model.Type;
import com.junjie.bookplatform.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final ImageResizer imageResizer;

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository, ImageResizer imageResizer) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.imageResizer = imageResizer;
    }

    @Transactional
    @Override
    public boolean addBook(Book book, User u, MultipartFile f) {
        if (f != null) {
            String imgName = StringUtils.cleanPath(f.getOriginalFilename());
            try {
                if (imgName.contains("..")) {
                    return false;
                }
                //Resize Image
                Img img = new Img();
                img.setFileName(imgName).setImgFile(imageResizer.resize(f));
                book.setImage(img);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        book.setBookOwner(u);
        bookRepository.save(book);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteBook(Book book) {
        bookRepository.delete(book);
        return true;
    }

    @Override
    public Book updateBook(Book book, Map<String,String> values) {
        Type t;
        String type = values.get("type");
        if (type != null && !type.equals("")) {
            t = Type.valueOf(type);
            book.setType(t);
        }

        book.setBookName(values.get("bookName"))
                .setAuthor(values.get("author")).setPrice(Double.valueOf(values.get("price")))
                .setYearNeed(values.get("year_needed"));
        bookRepository.save(book);
        return book;
    }
    /*
        book repository Used
     */

    @Override
    public Book getBook(String book_name) {
        Book b = bookRepository.findByBookName(book_name);
        return b;
    }

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "java.persistence.lock.timout",value = "3000")})
    public boolean buyBook(Book b, User user) {
        Book book = this.getBookById(b.getId());
        logger.warn("DEBUG BOOK BUYER: " + book.getBuyer());
        if (book.getBuyer() != null) {
            return false;
        }
        //Object passed from the Controller so, if i set before checking the BUYER() the buyer will be set for the obj
        b.setBuyer(user);
        bookRepository.save(b);
        return true;
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).get();
    }
    

    @Override
    public List<Book> getAllBooksOwner(User user) {
        return bookRepository.findBooksByBookOwner(user);
    }

    @Override
    public List<Book> getAllBoughtBooks(User user) {
        return bookRepository.findBooksByBuyer(user);
    }
}
