package com.yl.campus.model;

import java.util.List;

/**
 * 爬取图片的监听接口
 * Created by Luke on 2017/9/27.
 */

public interface OnGetImageListener {
    void onSucceed(List<String> images);

    void onFailed();
}
