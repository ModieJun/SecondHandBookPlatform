package com.junjie.bookplatform.DB;

import com.junjie.bookplatform.Model.Book;
import com.junjie.bookplatform.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select b from Book b where b.bookName like CONCAT('%',:bN,'%' )")
    List<Book> findBooksByBookName(@Param("bN") String bookName);

//    @Query(value = "select b from Book  b where  b.bookOwner <> uid ")
    List<Book> findBooksByBookOwnerNot( User user);

    List<Book> findBooksByBookOwner(User user);

    List<Book> findBooksByBuyer(User u);

    Optional<Book> findById(Long id);

    Book findByBookName(String bookName);

    void deleteAllByBookOwner(User u);

}
