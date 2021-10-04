package com.mytest.student.model;

import java.util.List;

public class StudentAcademicRecords {

    public long studentRollNumber;
    public List<Marks> marks;

    public StudentAcademicRecords(long studentRollNumber, List<Marks> marks) {
        this.studentRollNumber = studentRollNumber;
        this.marks = marks;
    }

    public List<Marks> getMarks() {
        return marks;
    }

    public long getStudentRollNumber() {
        return studentRollNumber;
    }

    @Override
    public String toString() {
        return "StudentMarks{" +
                "studentRollNumber=" + studentRollNumber +
                ", marks=" + marks +
                '}';
    }
}
