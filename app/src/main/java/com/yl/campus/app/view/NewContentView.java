package com.yl.campus.app.view;

/**
 * 新闻内容的功能
 * Created by Luke on 2017/9/28.
 */

public interface NewContentView {
    void showProgressBar();

    void hideProgressBar();

    void onLoadFailed();
}
