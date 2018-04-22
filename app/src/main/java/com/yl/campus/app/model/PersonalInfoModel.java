package com.yl.campus.app.model;

/**
 * 个人信息的实体类
 * Created by Luke on 2017/9/30.
 */

public class PersonalInfoModel {
    private StudentInfo studentInfo;
    private String infoJson;

    public String getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(String infoJson) {
        this.infoJson = infoJson;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }
}
