package com.yl.campus.app.activity;

import android.content.Intent;
import android.os.Handler;

import com.yl.campus.R;
import com.yl.campus.common.base.BaseActivity;
import com.yl.campus.common.utils.PrefsUtils;

/**
 * 启动页面
 * Created by Luke on 2018/3/6.
 **/
public class SplashActivity extends BaseActivity {
    private static final long VALUE_DELAYED_TIME = 1500;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        if (!PrefsUtils.getBoolean(this, "is_logon")) {
            // 未登录状态下初始化登陆密码，默认昵称
            PrefsUtils.putBoolean(this, "is_logon", false);
            PrefsUtils.putString(this, "login_psw", "123456");
            PrefsUtils.putString(this, "nickname", "未设置昵称");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, VALUE_DELAYED_TIME);
    }
}
