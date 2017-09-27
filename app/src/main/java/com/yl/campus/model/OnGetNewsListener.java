package com.yl.campus.model;

import java.util.List;

/**
 * 爬取新闻的监听接口
 * Created by Luke on 2017/9/27.
 */

public interface OnGetNewsListener {
    void onSucceed(List<News> newses);

    void onFailed();
}
