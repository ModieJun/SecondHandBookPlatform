package com.junjie.bookplatform.Model;

import io.micrometer.core.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookName;

    private String author;

    @ManyToOne()
    @NonNull
    @JoinTable(name = "book_User_Owner",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private User bookOwner;

    @ManyToOne()
    private User boughtBuy;

    @NonNull
    private Double price;

    public Book() {

    }

    public Long getId() {
        return id;
    }

    public Book(String name, String author ,User bookOwner, Double price) {
        this.bookOwner=bookOwner;
        this.bookName = name;
        this.author = author;
        this.price=price;
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setBookOwner(User bookOwner) {
        this.bookOwner = bookOwner;
    }

    public void setBoughtBuy(User boughtBuy) {
        this.boughtBuy = boughtBuy;
    }

    public User getBookOwner() {
        return bookOwner;
    }

    public User getBoughtBuy() {
        return boughtBuy;
    }
}
