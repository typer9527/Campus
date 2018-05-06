package com.yl.campus.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yl.campus.R;
import com.yl.campus.common.base.BaseActivity;
import com.yl.campus.common.utils.ActivityCollector;
import com.yl.campus.common.utils.PrefsUtils;
import com.yl.campus.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.idText)
    public EditText idText;
    @BindView(R.id.passwordText)
    public EditText passwordText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getDefaultTitle() {
        return getString(R.string.login);
    }

    @OnClick(R.id.loginButton)
    public void onLoginClick(View view) {
        if (TextUtils.isEmpty(idText.getText())) {
            ToastUtils.showToast(this, "学号不能为空", 0);
        } else if (TextUtils.isEmpty(passwordText.getText())) {
            ToastUtils.showToast(this, "密码不能为空", 0);
        } else {
            showProgressDialog("正在登录");
            onLoginDone(this);
        }
    }

    // 模拟在线登录
    private void onLoginDone(final Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgressDialog();
                String defaultPsw = PrefsUtils.getString(context, "login_psw");
                if (defaultPsw.equals(passwordText.getText().toString())) {
                    ToastUtils.showToast(context, "登录成功", 0);
                    PrefsUtils.putString(context, "login_id", idText.getText().toString());
                    PrefsUtils.putBoolean(context, "is_logon", true);
                    ActivityCollector.finishAll();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    ToastUtils.showToast(context, "密码错误", 0);
                }
            }
        }, 1500);
    }

    public static boolean isLogon(Context context) {
        return PrefsUtils.getBoolean(context, "is_logon");
    }
}
