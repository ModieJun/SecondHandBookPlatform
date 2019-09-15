package com.junjie.bookplatform.Model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_Id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @Column(unique = true)
    private String wechat_id;

    @OneToMany(mappedBy = "book_id")
    private Set<Book> listedBooks;

    @OneToMany(mappedBy = "book_id")
    private Set<Book> boughtBooks;

    public User() {
    }

    public User(@NotNull String username, @NotNull String password, String wechat_id, Set<Book> listedBooks, Set<Book> boughtBooks) {
        this.username = username;
        this.password = password;
        this.wechat_id = wechat_id;
        this.listedBooks = listedBooks;
        this.boughtBooks = boughtBooks;
    }


    public User(String username, String password, String wechat_id) {
        this.username = username;
        this.password = password;
        this.wechat_id = wechat_id;
    }

    public Long getUser_Id() {
        return user_Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWechat_id() {
        return wechat_id;
    }

    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id;
    }

    public Set<Book> getListedBooks() {
        return listedBooks;
    }

    public void setListedBooks(Set<Book> listedBooks) {
        this.listedBooks = listedBooks;
    }

    public Set<Book> getBoughtBooks() {
        return boughtBooks;
    }

    public void setBoughtBooks(Set<Book> boughtBooks) {
        this.boughtBooks = boughtBooks;
    }
}
