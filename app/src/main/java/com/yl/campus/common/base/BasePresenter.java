package com.yl.campus.common.base;

/**
 * BasePresenter
 * Created by Luke on 2018/4/23.
 */

public abstract class BasePresenter<V> {
    public V mView;

    void attach(V view) {
        this.mView = view;
    }

    void detach() {
        mView = null;
    }
}
