package com.yl.campus.app.view;

import com.yl.campus.app.model.Curriculum;

/**
 * 课程表展示
 * Created by Luke on 2017/10/9.
 */

public interface CurriculumView {
    void showProgressBar();

    void hideProgressBar();

    void showCourseContent(Curriculum curriculum);

    void onLoadFailed();
}
