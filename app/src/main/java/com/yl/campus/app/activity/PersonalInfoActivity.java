package com.yl.campus.app.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.app.model.StudentInfo;
import com.yl.campus.app.presenter.PersonalInfoPresenter;
import com.yl.campus.app.view.PersonalInfoView;
import com.yl.campus.common.base.BaseActivity;
import com.yl.campus.common.utils.PrefsUtils;
import com.yl.campus.common.utils.ToastUtils;

import butterknife.BindView;

public class PersonalInfoActivity extends BaseActivity
        implements PersonalInfoView {

    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.nameText)
    TextView nameText;
    @BindView(R.id.sexText)
    TextView sexText;
    @BindView(R.id.birthdayText)
    TextView birthdayText;
    @BindView(R.id.idText)
    TextView idText;
    @BindView(R.id.gradeText)
    TextView gradeText;
    @BindView(R.id.facultyText)
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
    protected String getDefaultTitle() {
        return getString(R.string.info);
    }

    @Override
    protected void initData() {
        presenter.showInfoData();
    }

    @Override
    public String getInfoPrefs() {
        return PrefsUtils.getStringByKey(this, "info");
    }

    @Override
    public String getIdPrefs() {
        return PrefsUtils.getStringByKey(this, "id");
    }

    @Override
    public void savePrefsInfo(String infoJson) {
        SharedPreferences.Editor editor = PrefsUtils.getEditor(this);
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
    public void showProgressBar() {
        linearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkError() {
        ToastUtils.showToast(this, "网络错误", 0);
        finish();
    }
}
