package com.yl.campus.app.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.app.presenter.PersonalInfoPresenter;
import com.yl.campus.app.view.PersonalInfoView;
import com.yl.campus.common.base.BaseMvpActivity;

import butterknife.BindView;

public class PersonalInfoActivity extends BaseMvpActivity<PersonalInfoView, PersonalInfoPresenter> implements PersonalInfoView {

    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_actual_name)
    TextView tvActualName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.btn_save)
    TextView btnSave;

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
        //presenter.showInfoData();
    }

    @Override
    protected PersonalInfoPresenter initPresenter() {
        return new PersonalInfoPresenter();
    }
}
