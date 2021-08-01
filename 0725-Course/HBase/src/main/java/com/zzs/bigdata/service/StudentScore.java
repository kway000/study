package com.zzs.bigdata.service;

public class StudentScore {
    private String understanding;

    private String programming;

    public StudentScore(String understanding, String programming) {
        this.understanding = understanding;
        this.programming = programming;
    }

    public String getUnderstanding() {
        return understanding;
    }

    public void setUnderstanding(String understanding) {
        this.understanding = understanding;
    }

    public String getProgramming() {
        return programming;
    }

    public void setProgramming(String programming) {
        this.programming = programming;
    }
}
