package com.yl.campus.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.yl.campus.R;
import com.yl.campus.presenter.LoginPresenter;
import com.yl.campus.util.LoadingUtil;
import com.yl.campus.util.ToastUtil;
import com.yl.campus.view.LoginView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements LoginView {

    @ViewById
    public EditText idText;
    @ViewById
    public EditText passwordText;

    private LoginPresenter presenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Click
    public void loginButton(View view) {
        onLogin();
    }

    @Override
    public String getStudentId() {
        return idText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordText.getText().toString();
    }

    @Override
    public void onLogin() {
        presenter.login();
    }

    @Override
    public void onLoginInfoNull() {
        ToastUtil.showToast(this, "学号和密码不能为空", 0);
    }

    @Override
    public void onLoginSucceed() {
        ToastUtil.showToast(this, "登陆成功", 0);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void saveStudentNameAndId(String name, String id) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(this).edit();
        editor.putString("name", name);
        editor.putString("id", id);
        editor.apply();
    }

    @Override
    public void onIdError() {
        ToastUtil.showToast(this, "学号错误", 0);
    }

    @Override
    public void onPasswordError() {
        ToastUtil.showToast(this, "密码错误", 0);
    }

    @Override
    public void onNetworkError() {
        ToastUtil.showToast(this, "网络错误", 0);
    }

    @Override
    public void showProgressDialog() {
        LoadingUtil.onLoad(this, "正在登陆...");
    }

    @Override
    public void hideProgressDialog() {
        LoadingUtil.endLoad();
    }
}
