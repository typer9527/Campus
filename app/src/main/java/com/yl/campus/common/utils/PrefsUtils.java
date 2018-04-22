package com.yl.campus.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences工具类
 * Created by Luke on 2017/9/30.
 */

public class PrefsUtils {
    public static String getStringByKey(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, null);
    }

    public static void setNullByKey(Context context, String key) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, null);
        editor.apply();
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context).edit();
    }
}
