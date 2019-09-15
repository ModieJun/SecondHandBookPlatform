package com.junjie.bookplatform.Model;

import io.micrometer.core.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_Id;

    private String bookName;

    private String author;

    @ManyToOne()
    @NonNull
    private User bookOwner;

    @ManyToOne()
    private User boughtBuy;

    public Long getBook_Id() {
        return book_Id;
    }

    public Book(String name, String author ,User bookOwner) {
        this.bookOwner=bookOwner;
        this.bookName = name;
        this.author = author;
    }

    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
