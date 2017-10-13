package com.yl.campus.presenter;

import com.yl.campus.model.MainModel;
import com.yl.campus.view.MainView;

/**
 * 处理MainActivity的数据和事件
 * Created by Luke on 2017/9/29.
 */

public class MainPresenter {
    private MainView view;
    private MainModel model;

    public MainPresenter(MainView view) {
        this.view = view;
        model = new MainModel();
    }

    public void setNameAndId() {
        model.setName(view.readNameFromPrefs());
        model.setId(view.readIdFromPrefs());
        if (model.isModelNull()) {
            view.setNotLoginText();
        } else {
            view.setNameText(model.getName());
            view.setIdText(model.getId());
        }
    }

    public void onLoginClicked() {
        if (model.isModelNull()) {
            view.jumpToLogin();
        }
    }

    public void exitLogin() {
        if (model.isModelNull()) {
            view.toastNotLogin();
            return;
        }
        view.showExitDialog();
    }

    public void clearUserData() {
        view.clearLoginPrefs();
        model = new MainModel();
        view.setNotLoginText();
        view.toastExitSucceed();
    }

    public void showPersonalInfo() {
        if (model.isModelNull()) {
            view.toastNotLogin();
            return;
        }
        view.jumpToPersonalInfo();
    }

    public boolean isLogon() {
        return !model.isModelNull();
    }
}
