package com.yl.campus.model;

import java.util.List;

/**
 * 新闻图片和列表
 * Created by Luke on 2017/9/27.
 */

public class NewsModel {
    private List<TopNews> topNewses = null;
    private List<News> newsList = null;

    public List<TopNews> getTopNewses() {
        return topNewses;
    }

    public void setTopNewses(List<TopNews> topNewses) {
        this.topNewses = topNewses;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public boolean isNull() {
        return (topNewses.size() == 0 || newsList.size() == 0);
    }
}
