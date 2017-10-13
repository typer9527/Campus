package com.yl.campus.view;

import com.yl.campus.model.News;
import com.yl.campus.model.TopNews;

import java.util.List;

/**
 * 新闻展示主要功能
 * Created by Luke on 2017/9/28.
 */

public interface NewsView {
    void showProgressDialog();

    void hideProgressDialog();

    void showTopNewses(List<TopNews> topNewses);

    void showNewsList(List<News> newsList);

    void onLoadFailed();

    void jumpToNewsContent(String url);

    void saveTopImagePrefs(String imageUrl);

    String getTopImagePrefs();

    void saveNewsListPrefs(String newsList);

    String getNewsListPrefs();
}
