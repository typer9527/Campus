package com.yl.campus.model;

/**
 * 图书实体类
 * Created by Luke on 2017/10/10.
 */

public class Book {
    public String title;
    public String image;
    public String author[];
    public String summary;

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author[0] + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
