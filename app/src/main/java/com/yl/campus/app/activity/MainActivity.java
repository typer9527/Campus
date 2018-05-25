package com.yl.campus.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.app.adapter.GridViewAdapter;
import com.yl.campus.app.presenter.MainPresenter;
import com.yl.campus.app.view.MainView;
import com.yl.campus.common.base.BaseMvpActivity;
import com.yl.campus.common.utils.ActivityCollector;
import com.yl.campus.common.utils.DialogUtils;
import com.yl.campus.common.utils.PrefsUtils;
import com.yl.campus.common.utils.ToastUtils;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements
        MainView, AdapterView.OnItemClickListener,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navView)
    NavigationView navView;
    @BindView(R.id.gridView)
    GridView gridView;
    TextView nameText, idText;
    private boolean isLogon;

    @Override
    protected void initView() {
        initNavigationView();
        initGridView();
    }

    @Override
    protected void initData() {
        isLogon = LoginActivity.isLogon(this);
        nameText.setText(isLogon ? PrefsUtils.getString(this, "nickname") : "点击登录");
        idText.setText(isLogon ? PrefsUtils.getString(this, "login_id") : "您还没有登陆");
        navView.setCheckedItem(R.id.item_home);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navView.setCheckedItem(R.id.item_home);
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getDefaultTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected String getToolbarTitle() {
        return super.getToolbarTitle();
    }

    @Override
    protected int getHomeAsUpIndicator() {
        return R.drawable.ic_menu;
    }

    private void initGridView() {
        final GridViewAdapter adapter = new GridViewAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    private void initNavigationView() {
        View navHeader = navView.getHeaderView(0);
        CircleImageView headImage = (CircleImageView)
                navHeader.findViewById(R.id.headImage);
        nameText = (TextView) navHeader.findViewById(R.id.nameText);
        idText = (TextView) navHeader.findViewById(R.id.idText);
        headImage.setOnClickListener(this);
        nameText.setOnClickListener(this);
        idText.setOnClickListener(this);
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(MainActivity.this, NewsActivity.class));
                break;
            case 1:
                if (isLogon) {
                    startActivity(new Intent(MainActivity.this, CurriculumActivity.class));
                } else {
                    ToastUtils.showToast(this, "请先登录", 0);
                }
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, BookSearchActivity.class));
                break;
            case 3:
                if (isLogon) {
                    startActivity(new Intent(MainActivity.this, BalanceActivity.class));
                } else {
                    ToastUtils.showToast(this, "请先登录", 0);
                }
                break;
            case 4:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() != R.id.item_home && !isLogon) {
            ToastUtils.showToast(this, "请先登录", 0);
            return true;
        }
        switch (item.getItemId()) {
            case R.id.item_home:
                drawerLayout.closeDrawers();
                break;
            case R.id.item_info:
                startActivity(new Intent(MainActivity.this, PersonalInfoActivity.class));
                break;
            case R.id.item_exit:
                showExitDialog();
                break;
        }
        return true;
    }

    public void showExitDialog() {
        DialogUtils.showTipDialog(this, "退出", "确认退出当前用户?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showLoadView("正在退出");
                onExitLogin();
            }
        });
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    private void onExitLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadView();
                PrefsUtils.putString(MainActivity.this, "login_psw", null);
                PrefsUtils.putBoolean(MainActivity.this, "is_logon", false);
                ToastUtils.showToast(MainActivity.this, "已退出当前账号", 0);
                ActivityCollector.finishAll();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        }, 1500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headImage:
            case R.id.idText:
            case R.id.nameText:
                if (isLogon) {
                    startActivity(new Intent(MainActivity.this, PersonalInfoActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }
}
