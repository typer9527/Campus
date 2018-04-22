package com.yl.campus.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 控制Toast的弹出
 * Created by Luke on 2017/9/15.
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToast(Context context, String content, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, content, duration);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
