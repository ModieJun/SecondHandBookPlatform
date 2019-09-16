package com.junjie.bookplatform.Model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
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


    public User() {
    }

    public User(@NotNull String username, @NotNull String password, String wechat_id) {
        this.username = username;
        this.password = password;
        this.wechat_id = wechat_id;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

}
