package com.yl.campus.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.common.base.BaseActivity;
import com.yl.campus.common.utils.ActivityCollector;
import com.yl.campus.common.utils.AppUtils;
import com.yl.campus.common.utils.CacheUtils;
import com.yl.campus.common.utils.DialogUtils;
import com.yl.campus.common.utils.PrefsUtils;
import com.yl.campus.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.rl_modify_psw)
    RelativeLayout rlModifyPsw;
    @BindView(R.id.rl_exit)
    RelativeLayout rlExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected String getDefaultTitle() {
        return "系统设置";
    }

    @Override
    protected void initData() {
        if (!LoginActivity.isLogon(this)) {
            rlModifyPsw.setVisibility(View.GONE);
            rlExit.setVisibility(View.GONE);
        }
        tvVersionName.setText(getString(R.string.version_mark,
                AppUtils.getVersionName(this)));
        tvCacheSize.setText(CacheUtils.getTotalCacheSize(this));
    }

    @OnClick({R.id.rl_modify_psw, R.id.ll_clear_cache, R.id.tv_about_us,
            R.id.ll_version_introduce, R.id.rl_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_modify_psw:
                break;
            case R.id.ll_clear_cache:
                DialogUtils.showTipDialog(this, "清理缓存", "确定要清理吗?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CacheUtils.clearAllCache(SettingActivity.this);
                        ToastUtils.showToast(SettingActivity.this, "缓存已清理", 0);
                        tvCacheSize.setText(CacheUtils.getTotalCacheSize(SettingActivity.this));
                    }
                });
                break;
            case R.id.tv_about_us:
                break;
            case R.id.ll_version_introduce:
                break;
            case R.id.rl_exit:
                DialogUtils.showTipDialog(this, "退出登录", "确认退出当前账号?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showProgressDialog("正在退出");
                        onExitDone();
                    }
                });
                break;
        }
    }

    private void onExitDone() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgressDialog();
                PrefsUtils.putString(SettingActivity.this, "login_psw", null);
                PrefsUtils.putBoolean(SettingActivity.this, "is_logon", false);
                ToastUtils.showToast(SettingActivity.this, "已退出当前账号", 0);
                ActivityCollector.finishAll();
                startActivity(new Intent(SettingActivity.this, MainActivity.class));
            }
        }, 1500);
    }
}
