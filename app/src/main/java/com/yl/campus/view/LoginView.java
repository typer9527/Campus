package com.yl.campus.view;

/**
 * 登陆事件
 * Created by Luke on 2017/9/29.
 */

public interface LoginView {
    String getStudentId();

    String getPassword();

    void onLogin();

    void onLoginInfoNull();

    void onLoginSucceed();

    void saveStudentNameAndId(String name, String id);

    void onIdError();

    void onPasswordError();

    void onNetworkError();

    void showProgressDialog();

    void hideProgressDialog();
}
