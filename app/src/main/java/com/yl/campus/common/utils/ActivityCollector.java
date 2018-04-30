package com.yl.campus.common.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity收集器
 * Created by Luke on 2018/4/30.
 */

public class ActivityCollector {
    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
        activities.clear();
    }
}
