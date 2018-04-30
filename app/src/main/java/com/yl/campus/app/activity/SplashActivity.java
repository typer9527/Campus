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
        // 初始化数据，包括登录状态，密码
        if (PrefsUtils.getString(this, "login_psw") == null) {
            PrefsUtils.setBoolean(this, "is_login", false);
            PrefsUtils.setString(this, "login_psw", "123456");
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
