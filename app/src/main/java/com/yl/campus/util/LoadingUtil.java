package com.yl.campus.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 加载进度工具类
 * Created by Luke on 2017/9/28.
 */

public class LoadingUtil {
    private static ProgressDialog progressDialog;

    public static void startLoad(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void endLoad() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
