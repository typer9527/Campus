package com.yl.campus.app.model;

import android.text.TextUtils;

/**
 * MainActivity的数据：姓名、学号
 * Created by Luke on 2017/9/29.
 */

public class MainModel {
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isModelNull() {
        return (TextUtils.isEmpty(name) || TextUtils.isEmpty(id));
    }
}
