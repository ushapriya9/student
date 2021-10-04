package com.mytest.student.dao;

import com.mytest.student.model.Marks;
import com.mytest.student.model.Student;
import com.mytest.student.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepositoryImpl implements StudentRepository{

    private static final String ADD_STUDENT =
            "insert into student (roll_Number, name, student_class) " +
            "values(?,  ?, ?)";

    private static final String FETCH_MAX_SCORERS =
            "select Subject, Student.roll_number,Name, Student_class " +
            "  from student_Marks " +
            "  join Student on (student_Marks.roll_number = student.roll_number) " +
            " where (subject,obtained_marks/out_of_marks)              " +
            "    in (                                                  " +
            "        select subject , max(obtained_marks/out_of_marks) " +
            "          from student_marks   " +
            "         group by Subject )       ";

    private static final String FETCH_SCORERS_ABOVE =
            "select Student.roll_number , name, Student_class\n" +
            "  from student_marks \n" +
            "  join Student on (student_Marks.roll_number = student.roll_number)\n" +
            " group by Student.roll_number\n" +
            "having avg(( obtained_marks/out_of_marks) *100 )> ?" ;

    private static final String FIND_BY_ID =
            "select roll_number, name, student_class "  +
            "  from student " +
            " where roll_number=?";

    private static final String UPDATE_STUDENT_DETAILS =
            "Update Student set roll_Number= ?, name= ? , student_class = ? " +
            " where roll_number = ? " ;
    @Autowired
    public JdbcTemplate jdbctemplate;

    public int add(Student student) {
        return jdbctemplate.update(ADD_STUDENT,
                new Object[] {
                        student.getRollNumber(), student.getName(), student.getStudentClass()
                });
    }
    public Student findById(long id) {
        return jdbctemplate.queryForObject(FIND_BY_ID, new Object[]{id}, (rs, rowNum) -> buildStudent(rs));
    }
    public int editStudent(long oldRollNo, Student newStudentDetails) {
        return jdbctemplate.update(UPDATE_STUDENT_DETAILS,
                new Object[] {
                        newStudentDetails.getRollNumber(), newStudentDetails.getName(), newStudentDetails.getStudentClass(),
                        oldRollNo
                });
    }

    public Map<Subject, List<Student>> findMaxScorers() {

        Map<Subject, List<Student>> maxScorers = new HashMap<>();

        jdbctemplate.query(FETCH_MAX_SCORERS,
                (rs, i) -> {

                    final Subject s = new Subject(rs.getString("subject"));
                    List<Student> topScorers = maxScorers.get(s);

                    if( topScorers == null ){
                        topScorers = new ArrayList<>();
                        maxScorers.put(s, topScorers);
                    }
                    topScorers.add( buildStudent (rs));
                    return null;
                }) ;
        return maxScorers;
    }

    public List<Student>  findScorersAbove( BigDecimal aggregate) {

        return jdbctemplate.query(FETCH_SCORERS_ABOVE,
                new Object[]{aggregate},
                 (rs, i) -> buildStudent(rs));

    }

    private final Student buildStudent( ResultSet rs) throws SQLException {
        return new Student( rs.getLong("roll_number"), rs.getString("Name"), rs.getString("student_class"));
    }
}
