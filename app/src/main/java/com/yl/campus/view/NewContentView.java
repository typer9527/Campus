package com.yl.campus.view;

/**
 * 新闻内容的功能
 * Created by Luke on 2017/9/28.
 */

public interface NewContentView {
    void showProgressBar();

    void hideProgressBar();

    void showNewsContent();

    void onLoadFailed();
}
