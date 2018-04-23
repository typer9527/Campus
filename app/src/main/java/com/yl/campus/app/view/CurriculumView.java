package com.yl.campus.app.view;

import com.yl.campus.app.model.Curriculum;
import com.yl.campus.common.base.BaseView;

/**
 * 课程表展示
 * Created by Luke on 2017/10/9.
 */

public interface CurriculumView extends BaseView {
    void showCourseContent(Curriculum curriculum);
}
