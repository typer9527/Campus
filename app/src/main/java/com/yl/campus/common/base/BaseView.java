package com.yl.campus.common.base;

/**
 * BaseView
 * Created by Luke on 2018/4/23.
 */

public interface BaseView {
    /**
     * loading对话框
     */
    void showLoadView(String msg);

    void hideLoadView();

    /**
     * 显示网络异常页面
     */
    void onNetworkError();
}
