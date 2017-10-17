package com.yl.campus.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.adapter.GridViewAdapter;
import com.yl.campus.presenter.MainPresenter;
import com.yl.campus.util.PrefsUtil;
import com.yl.campus.util.ToastUtil;
import com.yl.campus.view.MainView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class MainActivity extends BaseActivity implements
        MainView, AdapterView.OnItemClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    @ViewById
    public DrawerLayout drawerLayout;
    @ViewById
    public NavigationView navView;
    @ViewById
    public GridView gridView;
    private final int LOGIN = 1;
    private TextView nameText;
    private TextView idText;
    private MainPresenter presenter = new MainPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void init() {
        initNavigationView();
        initGridView();
        presenter.setNameAndId();
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
        return "安科小助手";
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
        nameText = (TextView) navHeader.findViewById(R.id.nameText);
        idText = (TextView) navHeader.findViewById(R.id.idText);
        nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLoginClicked();
            }
        });
        navView.setCheckedItem(R.id.item_home);
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(MainActivity.this, NewsActivity_.class));
                break;
            case 1:
                if (presenter.isLogon()) {
                    startActivity(new Intent(MainActivity.this,
                            CurriculumActivity_.class));
                } else {
                    toastNotLogin();
                }
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, BookSearchActivity_.class));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home:
                drawerLayout.closeDrawers();
                break;
            case R.id.item_info:
                presenter.showPersonalInfo();
                break;
            case R.id.item_exit:
                presenter.exitLogin();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOGIN:
                if (resultCode == RESULT_OK) {
                    presenter.setNameAndId();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public String readNameFromPrefs() {
        return PrefsUtil.getStringByKey(this, "name");
    }

    @Override
    public String readIdFromPrefs() {
        return PrefsUtil.getStringByKey(this, "id");
    }

    @Override
    public void setNameText(String nameText) {
        this.nameText.setText(nameText);
    }

    @Override
    public void setIdText(String idText) {
        this.idText.setText(idText);
    }

    @Override
    public void setNotLoginText() {
        nameText.setText("未登陆");
        idText.setText("");
    }

    @Override
    public void jumpToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
        startActivityForResult(intent, LOGIN);
    }

    @Override
    public void jumpToPersonalInfo() {
        Intent intent = new Intent(MainActivity.this, PersonalInfoActivity_.class);
        startActivity(intent);
    }

    @Override
    public void clearLoginPrefs() {
        PrefsUtil.setNullByKey(this, "name");
        PrefsUtil.setNullByKey(this, "id");
        PrefsUtil.setNullByKey(this, "info");
    }

    @Override
    public void toastNotLogin() {
        ToastUtil.showToast(this, "您还未登录", 0);
    }

    @Override
    public void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("退出")
                .setMessage("确认退出当前用户?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.clearUserData();
                    }
                }).setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    public void toastExitSucceed() {
        ToastUtil.showToast(this, "退出成功", 0);
    }
}
