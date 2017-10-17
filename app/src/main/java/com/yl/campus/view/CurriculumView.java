package com.yl.campus.view;

import com.yl.campus.model.Curriculum;

/**
 * 课程表展示
 * Created by Luke on 2017/10/9.
 */

public interface CurriculumView {
    void showProgressBar();

    void hideProgressBar();

    void initCurriculum();

    void showCourseContent(Curriculum curriculum);

    void onLoadFailed();
}
