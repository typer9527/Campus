package com.yl.campus.app.view;

/**
 * MainActivity的功能
 * Created by Luke on 2017/9/28.
 */

public interface MainView {
    String readNameFromPrefs();

    String readIdFromPrefs();

    void setNameText(String nameText);

    void setIdText(String idText);

    void setNotLoginText();

    void jumpToLogin();

    void jumpToPersonalInfo();

    void clearLoginPrefs();

    void toastNotLogin();

    void showExitDialog();

    void toastExitSucceed();
}