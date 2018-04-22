package com.yl.campus.common.utils;

import android.view.View;
import android.widget.SearchView;

import java.lang.reflect.Field;

/**
 * 修改SearchView默认样式
 * Created by Luke on 2017/10/11.
 */

public class SearchViewHelper {
    // 修改SearchView下划线颜色
    public static void setUnderLineColor(SearchView searchView, int color) {
        if (searchView != null) {
            try {
                Class<? extends SearchView> aClass = searchView.getClass();
                Field field = aClass.getDeclaredField("mSearchPlate");
                field.setAccessible(true);
                View underLine = (View) field.get(searchView);
                underLine.setBackgroundColor(color);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
