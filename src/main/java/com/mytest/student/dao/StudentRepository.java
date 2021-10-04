package com.mytest.student.dao;

import com.mytest.student.model.Student;
import com.mytest.student.model.Subject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface StudentRepository {

    int add(Student student);

    Student findById(long id);

    int editStudent(long prevRollNo, Student newStudentDetails);

    Map<Subject, List<Student>> findMaxScorers();

    List<Student> findScorersAbove(BigDecimal aggregate);
}
