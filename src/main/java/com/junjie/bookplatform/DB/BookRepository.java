package com.junjie.bookplatform.DB;

import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.Type;
import com.junjie.bookplatform.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAll(Pageable pageable);

    //    @Query(value = "select b from Book  b where  b.bookOwner <> uid ")
    List<Book> findBooksByBookOwnerNot(User user);

    List<Book> findBooksByBookOwner(User user);

    List<Book> findBooksByBuyer(User u);

    Optional<Book> findById(Long id);

    Book findByBookName(String bookName);

    /*
     *  All Filtering Methods
     */
    @Query("select b from Book  b where (:bn is null or b.bookName like CONCAT('%',:bn,'%') ) and " +
            "(:yn is null or b.yearNeed=:yn) and (:auth is null or b.author=:auth) and (:t is null or b.type=:t)")
    List<Book> getAll(@Param("bn") String book_name, @Param("yn") String year_needed,
                      @Param("auth")String author, @Param("t") Type t, Pageable pageable);

    List<Book> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
