/*
 * Copyright (c)-- Created By JUNJIE Lin -> On 2019/9/23
 */

package com.junjie.bookplatform.Model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class UserContact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Column(length = 100)
    private String contact;

    @OneToOne( targetEntity = User.class,fetch = FetchType.LAZY,mappedBy = "contact")
    private User user;

    public UserContact() {
    }

    public UserContact(String contact) {
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public String getContact() {
        return contact;
    }

    public UserContact setContact(String contact) {
        this.contact = contact;
        return this;
    }
}
