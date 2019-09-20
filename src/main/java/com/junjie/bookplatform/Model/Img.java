package com.junjie.bookplatform.Model;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Img {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String fileName;

    @OneToOne(mappedBy = "image")
    private Book book;

    @Lob
    private byte[] imgFile;

    public Img() {
    }

    public Img(String fileName, Book book, byte[] imgFile) {
        this.fileName = fileName;
        this.book = book;
        this.imgFile = imgFile;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public Img setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public Img setBook(Book book) {
        this.book = book;
        return this;
    }

    public byte[] getImgFile() {
        return imgFile;
    }

    public Img setImgFile(byte[] imgFile) {
        this.imgFile = imgFile;
        return this;
    }
}
