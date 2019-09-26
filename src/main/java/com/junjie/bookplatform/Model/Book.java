package com.junjie.bookplatform.Model;

import io.micrometer.core.lang.NonNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Arrays;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String bookName;

    @Column(nullable = false,length = 50)
    private String author;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_owner_id", referencedColumnName = "user_id")
//    @OnDelete --- very important for what happens when parent is deleted
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User bookOwner;


    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "buyer_id", referencedColumnName = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private User buyer;

    @NonNull
    @Column(length = 10, precision = 2)
    private Double price;

    @Column(length = 20,nullable = false)
    private String yearNeed;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Img.class)
    @JoinColumn(name = "img_id",referencedColumnName = "id")
    private Img image;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date createdAt;

    private Type type;


    /*
    ---------------Constructors------------------
     */

    public Book() {

    }

    public Book(String name, String author, Double price,Type type) {
        this.bookName = name;
        this.author = author;
        this.price = price;
        this.type= type;
    }


    /*
            -------------Getter + Setter-----------------
     */
    public Long getId() {
        return id;
    }


    public String getYearNeed() {
        return yearNeed;
    }

    public Book setYearNeed(String yearNeed) {
        this.yearNeed = yearNeed;
        return this;
    }

    public String getBookName() {
        return this.bookName;
    }

    public Book setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Book setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Double getPrice() {
        return this.price;
    }

    public Book setBookOwner(User bookOwner) {
        this.bookOwner = bookOwner;
        return this;
    }

    public Book setBuyer(User buyer) {
        this.buyer = buyer;
        return this;
    }

    public User getBookOwner() {
        return bookOwner;
    }

    public User getBuyer() {
        return buyer;
    }

    public Img getImage() {
        return image;
    }

    public Book setImage(Img image) {
        this.image = image;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Type getType() {
        return type;
    }

    public Book setType(Type type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", bookOwner=" + bookOwner +
                ", buyer=" + buyer +
                ", price=" + price +
                ", image=" + image +
                '}';
    }
}
