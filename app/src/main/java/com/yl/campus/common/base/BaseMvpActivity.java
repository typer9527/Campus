package com.yl.campus.common.base;

/**
 * BaseMvpActivity
 * Created by Luke on 2018/4/23.
 */

public abstract class BaseMvpActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity implements BaseView {
    protected P mPresenter;

    @Override
    protected void initBasePresenter() {
        mPresenter = initPresenter();
        mPresenter.attach((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    protected abstract P initPresenter();

    @Override
    public void showLoadView(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideLoadView() {
        dismissProgressDialog();
    }

    @Override
    public void onNetworkError() {
        dismissProgressDialog();
    }
}
