package com.yl.campus.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences工具类
 * Created by Luke on 2017/9/30.
 */

public class PrefsUtils {
    public static String getString(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, null);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value);
        editor.apply();
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context).edit();
    }
}
