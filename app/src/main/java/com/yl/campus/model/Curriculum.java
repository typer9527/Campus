package com.yl.campus.model;

import java.util.List;

/**
 * 课程实体类
 * Created by Luke on 2017/10/9.
 */

public class Curriculum {
    public int weekNum;
    public List<Course> courses;

    public class Course {
        public String bgColor;
        public String classroom;
        public String courseName;
        public int endLesson;
        public int startLesson;
        public String teacherName;
        public int weekday;
    }

    @Override
    public String toString() {
        return "Curriculum{" +
                "weekNum=" + weekNum +
                '}';
    }
}
