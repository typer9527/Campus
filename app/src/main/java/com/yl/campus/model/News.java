package com.yl.campus.model;

/**
 * 新闻实体类
 * Created by Luke on 2017/9/27.
 */

public class News {
    public String title;
    public String date;
    public String url;

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
