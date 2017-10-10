package com.yl.campus.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.model.StudentInfo;
import com.yl.campus.presenter.PersonalInfoPresenter;
import com.yl.campus.util.LoadingUtil;
import com.yl.campus.util.PrefsUtil;
import com.yl.campus.util.ToastUtil;
import com.yl.campus.view.PersonalInfoView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class PersonalInfoActivity extends BaseActivity
        implements PersonalInfoView {

    @ViewById
    LinearLayout linearLayout;
    @ViewById
    TextView nameText;
    @ViewById
    TextView sexText;
    @ViewById
    TextView birthdayText;
    @ViewById
    TextView idText;
    @ViewById
    TextView gradeText;
    @ViewById
    TextView facultyText;
    PersonalInfoPresenter presenter = new PersonalInfoPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.info);
    }

    @AfterViews
    public void init() {
        presenter.showInfoData();
    }

    @Override
    public String getInfoPrefs() {
        return PrefsUtil.getStringByKey(this, "info");
    }

    @Override
    public String getIdPrefs() {
        return PrefsUtil.getStringByKey(this, "id");
    }

    @Override
    public void savePrefsInfo(String infoJson) {
        SharedPreferences.Editor editor = PrefsUtil.getEditor(this);
        editor.putString("info", infoJson);
        editor.apply();
    }

    @Override
    public void setInfoData(StudentInfo info) {
        nameText.setText(String.format(getString(R.string.name_text), info.name));
        sexText.setText(String.format(getString(R.string.sex_text), info.sex));
        birthdayText.setText(String.format(getString(R.string.birthday_text),
                info.birthday));
        idText.setText(info.studentId);
        gradeText.setText(info.grade);
        facultyText.setText(info.faculty);
    }

    @Override
    public void showProgressDialog() {
        linearLayout.setVisibility(View.GONE);
        LoadingUtil.onLoad(this, "正在加载...");
    }

    @Override
    public void hideProgressDialog() {
        LoadingUtil.endLoad();
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkError() {
        ToastUtil.showToast(this, "网络错误", 0);
        finish();
    }
}
