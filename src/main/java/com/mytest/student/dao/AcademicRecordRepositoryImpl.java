package com.mytest.student.dao;

import com.mytest.student.model.Marks;
import com.mytest.student.model.StudentAcademicRecords;
import com.mytest.student.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AcademicRecordRepositoryImpl implements AcademicRecordRepository {

    private static final String ADD_MARKS =
            "insert into Student_Marks (roll_Number, subject, obtained_marks, out_of_marks) " +
            "values(?,?, ?, ?)";

    private static final String FIND_MARKS_BY_ROLL_NO =
            "select subject, obtained_marks, out_of_marks" +
            "  from student_marks where roll_number=?";

    private static final String UPDATE_MARKS =
            "UPDATE Student_Marks set obtained_marks = ? , out_of_marks = ? " +
            " where roll_Number = ? and  subject= ? ";

    private static final String DELETE_MARKS =
            "delete from  Student_Marks where roll_Number = ? and  subject= ? ";

    @Autowired
    public JdbcTemplate jdbctemplate;

    public void saveMarks(List<StudentAcademicRecords> studentMarks) {

        studentMarks.forEach( studentMark -> {

            jdbctemplate.batchUpdate(ADD_MARKS,
                    studentMark.getMarks(),
                    studentMark.getMarks().size(),
                    (ps, mark) -> {
                        ps.setLong(1, studentMark.getStudentRollNumber());
                        ps.setString(2, mark.getSubject().getName());
                        ps.setBigDecimal(3, mark.getObtained());
                        ps.setBigDecimal(4, mark.getOutOf());
                    }
            );
        });
    }

    public void updateStudentMarks(StudentAcademicRecords studentMark) {

        jdbctemplate.batchUpdate(UPDATE_MARKS,
                studentMark.getMarks(),
                studentMark.getMarks().size(),
                (ParameterizedPreparedStatementSetter<Marks>) (ps, mark) -> {
                    ps.setBigDecimal(1, mark.getObtained());
                    ps.setBigDecimal(2, mark.getOutOf());

                    ps.setLong(3, studentMark.getStudentRollNumber());
                    ps.setString(4, mark.getSubject().getName());

                }
        );
    }

    public void deleteStudentMarks(StudentAcademicRecords studentMark) {

        jdbctemplate.batchUpdate(DELETE_MARKS
                ,studentMark.getMarks(),
                studentMark.getMarks().size(),
                (ps, argument) -> {
                    ps.setLong(1, studentMark.getStudentRollNumber());
                    ps.setString(2, argument.getSubject().getName());
                }
        );
    }

    public StudentAcademicRecords findMarksByRollNo(long rollNo) {

        return new StudentAcademicRecords( rollNo, jdbctemplate.query(FIND_MARKS_BY_ROLL_NO, new Object[]{rollNo },
                (rs, rowNum) -> buildMarks(rs)));
    }

    private final Marks buildMarks( ResultSet rs) throws SQLException {
        return new Marks( new Subject(rs.getString("subject")), rs.getBigDecimal("obtained_marks"), rs.getBigDecimal("out_of_marks"));
    }

}
