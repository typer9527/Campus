package com.yl.campus.view;

import com.yl.campus.model.StudentInfo;

/**
 * 个人信息的查询展示
 * Created by Luke on 2017/9/30.
 */

public interface PersonalInfoView {
    String getInfoPrefs();

    String getIdPrefs();

    void savePrefsInfo(String infoJson);

    void setInfoData(StudentInfo info);

    void showProgressDialog();

    void hideProgressDialog();

    void onNetworkError();
}
