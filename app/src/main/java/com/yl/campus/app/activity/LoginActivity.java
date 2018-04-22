package com.yl.campus.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import com.yl.campus.R;
import com.yl.campus.app.presenter.LoginPresenter;
import com.yl.campus.app.view.LoginView;
import com.yl.campus.common.base.BaseActivity;
import com.yl.campus.common.utils.LoadingUtils;
import com.yl.campus.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

// TODO: 2018/4/15 改为模拟登录
public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.idText)
    public EditText idText;
    @BindView(R.id.passwordText)
    public EditText passwordText;

    private LoginPresenter presenter = new LoginPresenter(this);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getDefaultTitle() {
        return getString(R.string.login);
    }

    @OnClick(R.id.loginButton)
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
        ToastUtils.showToast(this, "学号和密码不能为空", 0);
    }

    @Override
    public void onLoginSucceed() {
        ToastUtils.showToast(this, "登陆成功", 0);
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
        ToastUtils.showToast(this, "学号错误", 0);
    }

    @Override
    public void onPasswordError() {
        ToastUtils.showToast(this, "密码错误", 0);
    }

    @Override
    public void onNetworkError() {
        ToastUtils.showToast(this, "网络错误", 0);
    }

    @Override
    public void showProgressDialog() {
        LoadingUtils.onLoad(this, "正在登陆...");
    }

    @Override
    public void hideProgressDialog() {
        LoadingUtils.endLoad();
    }
}
