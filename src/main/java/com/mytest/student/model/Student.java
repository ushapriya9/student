package com.mytest.student.model;

import java.io.Serializable;

public final class Student implements Serializable {

    private final Long rollNumber;
    private final String name;
    private final String studentClass;

    public Student(long rollNo, String name, String studentClass) {
        this.rollNumber = rollNo;
        this.name = name;
        this.studentClass = studentClass;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public String getName() {
        return name;
    }

    public Long getRollNumber() {
        return rollNumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNumber=" + rollNumber +
                ", name='" + name + '\'' +
                ", studentClass='" + studentClass + '\'' +
                '}';
    }

}


