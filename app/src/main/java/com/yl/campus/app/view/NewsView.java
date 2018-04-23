package com.yl.campus.app.view;

import com.yl.campus.app.model.News;
import com.yl.campus.app.model.TopNews;
import com.yl.campus.common.base.BaseView;

import java.util.List;

/**
 * 新闻展示主要功能
 * Created by Luke on 2017/9/28.
 */

public interface NewsView extends BaseView {

    void refreshNews(List<TopNews> topNewses, List<News> newsList);

    void onRefreshing();

    void endRefresh();

    void onRefreshFailed();

    void showMoreNews(List<News> newsList);

    void onLoadMore();

    void endLoadMore();

    void jumpToNewsContent(String url);

    void saveTopImagePrefs(String imageUrl);

    String getTopImagePrefs();

    void saveNewsListPrefs(String newsList);

    String getNewsListPrefs();
}
