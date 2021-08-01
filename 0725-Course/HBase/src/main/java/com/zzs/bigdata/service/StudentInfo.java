package com.zzs.bigdata.service;

public class StudentInfo {
    private String studentId;
    private String studentClass;

    public StudentInfo(String studentId, String studentClass) {
        this.studentId = studentId;
        this.studentClass = studentClass;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
}
