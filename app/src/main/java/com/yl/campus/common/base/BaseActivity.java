package com.yl.campus.common.base;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yl.campus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 对toolbar进行封装，实现标题居中和返回以及自定义菜单
 * Created by Luke on 2017/10/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.titleText)
    public TextView titleText;
    public ActionBar actionBar;
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initBasePresenter();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initToolbarLayout();
        initView();
        initData();
        initCreate(savedInstanceState);
    }

    protected void initCreate(Bundle savedInstanceState) {
    }

    protected void initBasePresenter() {

    }

    protected void initData() {

    }

    protected void initView() {

    }

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            if (TextUtils.isEmpty(msg)) {
                mProgressDialog = ProgressDialog.show(this, "", "加载中...", true, true);
                return;
            }
            mProgressDialog = ProgressDialog.show(this, "", msg, true, true);
        } else {
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void initToolbarLayout() {
        setSupportActionBar(toolbar);
        // 设置默认标题
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getDefaultTitle());
        }
        // 设置居中标题
        titleText.setText(getToolbarTitle());
        if (!isHideHomeButton()) {
            setAndShowHomeButton();
        }
    }

    // 设置主页按钮，默认返回
    private void setAndShowHomeButton() {
        int resId = getHomeAsUpIndicator();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (resId != -1) {
                actionBar.setHomeAsUpIndicator(resId);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuId() == -1) {
            return super.onCreateOptionsMenu(menu);
        } else {
            getMenuInflater().inflate(getMenuId(), menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return true;
    }

    // 获取布局文件的资源Id
    protected abstract int getLayoutId();

    // 设置标题文字
    protected String getToolbarTitle() {
        return "";
    }

    // 设置默认默认标题
    protected String getDefaultTitle() {
        return "";
    }

    // 设置是否隐藏主页按钮，默认显示
    protected boolean isHideHomeButton() {
        return false;
    }

    // 设置菜单文件的资源Id，默认-1
    protected int getMenuId() {
        return -1;
    }

    // 自定义主页按钮图标，默认为-1
    protected int getHomeAsUpIndicator() {
        return -1;
    }

}
