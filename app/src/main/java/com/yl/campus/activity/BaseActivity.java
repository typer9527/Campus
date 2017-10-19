package com.yl.campus.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 对toolbar进行封装，实现标题居中和返回以及自定义菜单
 * Created by Luke on 2017/10/10.
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;
    @ViewById
    TextView titleText;
    ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    @AfterViews
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
