package com.mytest.student.dao;

import com.mytest.student.model.StudentAcademicRecords;

import java.util.List;

public interface AcademicRecordRepository {

    void saveMarks(List<StudentAcademicRecords> studentMarks);

    void updateStudentMarks(StudentAcademicRecords studentMark);

    void deleteStudentMarks(StudentAcademicRecords studentMark);

    StudentAcademicRecords findMarksByRollNo(long rollNo);
}
