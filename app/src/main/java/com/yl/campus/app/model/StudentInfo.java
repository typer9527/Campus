package com.yl.campus.app.model;

public class StudentInfo {
	public String studentId;
	private String password;
	public String name;
	public String sex;
	public String birthday;
	public String grade;
	public String faculty;

	public StudentInfo(String studentId, String password, String name, 
			String sex, String birthday, String grade, String faculty) {
		this.studentId = studentId;
		this.password = password;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.grade = grade;
		this.faculty = faculty;
	}

	@Override
	public String toString() {
		return "StudentInfo [studentId=" + studentId + ", password=" + password + 
				", name=" + name + ", sex=" + sex + ", birthday=" + birthday + 
				", grade=" + grade + ", faculty=" + faculty + "]";
	}
	
}
