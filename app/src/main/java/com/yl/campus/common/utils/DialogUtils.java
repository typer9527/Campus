package com.yl.campus.common.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * AlertDialog工具类
 * Created by Luke on 2018/4/25.
 */

public class DialogUtils {
    public static void showTipDialog(Context context, String title, String tipText,
                                     DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(tipText)
                .setPositiveButton("确定", listener).setNegativeButton("取消", null);
        builder.show();
    }
}
