package com.zzs.bigdata.service;

public class Student {
    private String name;

    private StudentInfo info;

    private StudentScore score;

    public Student(String name) {
        this.name = name;
    }

    public static Student createStudent(String name, String studentId, String studentClass, String understanding, String programming) {
        Student student = new Student(name);
        StudentInfo info = new StudentInfo(studentId, studentClass);
        StudentScore score = new StudentScore(understanding, programming);
        student.setInfo(info);
        student.setScore(score);
        return student;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudentInfo getInfo() {
        return info;
    }

    public void setInfo(StudentInfo info) {
        this.info = info;
    }

    public StudentScore getScore() {
        return score;
    }

    public void setScore(StudentScore score) {
        this.score = score;
    }

}
