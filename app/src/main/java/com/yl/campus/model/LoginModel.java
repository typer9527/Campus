package com.yl.campus.model;

import android.text.TextUtils;

/**
 * 登陆的学号和密码
 * Created by Luke on 2017/9/29.
 */

public class LoginModel {
    private String studentId;
    private String password;
    private String loginResult;

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoginInfoNull() {
        return (TextUtils.isEmpty(studentId) || TextUtils.isEmpty(password));
    }
}
