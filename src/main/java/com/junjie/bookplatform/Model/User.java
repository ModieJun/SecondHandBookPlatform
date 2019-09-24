package com.junjie.bookplatform.Model;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity(name = "User")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_Id;

    @NotNull
    @Column(unique = true, length = 15)
    private String username;

    @NotNull
    private String password;

//    PasswordConfirm only USed for validation and Not stored
    @Null
    private String passwordConfirm;

    @OneToOne(cascade = CascadeType.ALL)
    private UserContact contact;


    public User() {
    }

    public User(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public User setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
        return this;
    }

    public Long getUser_Id() {
        return user_Id;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;return this;
    }

    public UserContact getContact() {
        return contact;
    }

    public User setContact(UserContact contact) {
        this.contact = contact;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_Id=" + user_Id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", contact=" + contact +
                '}';
    }
}
