package com.junjie.bookplatform.Model;

import io.micrometer.core.lang.NonNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookName;

    private String author;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "book_owner_id",referencedColumnName = "user_id")
//    @OnDelete --- very important for what happens when parent is deleted
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User bookOwner;


    @ManyToOne( targetEntity = User.class)
    @JoinColumn(name = "buyer_id",referencedColumnName = "user_id",nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private User buyer;

    @NonNull
    @Column(length = 10,precision = 2)
    private Double price;

    @Lob
    private byte[] image;

    public Book() {

    }

    public Long getId() {
        return id;
    }

    public Book(String name, String author , Double price) {
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

    public Double getPrice() {
        return this.price;
    }

    public void setBookOwner(User bookOwner) {
        this.bookOwner = bookOwner;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getBookOwner() {
        return bookOwner;
    }

    public User getBuyer() {
        return buyer;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
