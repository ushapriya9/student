package com.mytest.student;

import com.mytest.student.dao.AcademicRecordRepository;
import com.mytest.student.dao.StudentRepository;
import com.mytest.student.model.Student;
import com.mytest.student.model.StudentAcademicRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@RestController
public class ApplicationController {

    @Autowired
    StudentRepository studentRepo;
    @Autowired
    AcademicRecordRepository markRepo;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationController.class, args);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return buildOKResponseEntity("Hello! Welcome to student database ");
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        studentRepo.add(student);
        return buildOKResponseEntity(String.format("created student with credentials : %s!",
                student));
    }

    @RequestMapping(value = "/editStudent/{prevRollNo}", method = RequestMethod.POST)
    public ResponseEntity<String> updateStudent(@PathVariable int prevRollNo, @RequestBody Student student) {
        studentRepo.editStudent(prevRollNo, student);
        return buildOKResponseEntity(String.format("updated student details to  %s ", student));
    }

    @GetMapping("/findRollNo/{rollNo}")
    public ResponseEntity<String> searchByRollNumber(@PathVariable long rollNo) {
        return buildOKResponseEntity(String.format("student with id  %s -> %s",
                rollNo, studentRepo.findById(rollNo)));
    }

    @RequestMapping(value = "/addMarks", method = RequestMethod.POST)
    public ResponseEntity<String> addMarks(@RequestBody List<StudentAcademicRecords> studentMarks) {
        markRepo.saveMarks(studentMarks);
        return buildOKResponseEntity(String.format("Added the following marks to db %s!", studentMarks));
    }

    @RequestMapping(value = "/updateMarks", method = RequestMethod.POST)
    public ResponseEntity<String> updateMarks(@RequestBody StudentAcademicRecords studentAcademicRecords) {
        markRepo.updateStudentMarks(studentAcademicRecords);
        return buildOKResponseEntity(String.format("Updated the following marks in db %s!", studentAcademicRecords));
    }

    @RequestMapping(value = "/deleteMarks", method = RequestMethod.POST)
    public ResponseEntity<String> deleteMarks(@RequestBody StudentAcademicRecords studentAcademicRecords) {
        markRepo.deleteStudentMarks(studentAcademicRecords);
        return buildOKResponseEntity(String.format("Deleted StudentMarks with name %s!", studentAcademicRecords));
    }

    @GetMapping("/getMarks/{rollNo}")
    public ResponseEntity<String> getMarksForRollNumber(@PathVariable long rollNo) {
        return buildOKResponseEntity("Student marks for thr given id -> "
                + markRepo.findMarksByRollNo(rollNo));
    }

    @GetMapping("/getTopScorers")
    public ResponseEntity<String> getTopScorers() {
        return buildOKResponseEntity("Top Scorers -> " + studentRepo.findMaxScorers());
    }

    @GetMapping("/getScorersAbove/{aggregate}")
    public ResponseEntity<String> getAggregateScorers(@PathVariable BigDecimal aggregate) {
        return buildOKResponseEntity(String.format("Students with aggregates above %s : %s  -> ",
                aggregate, studentRepo.findScorersAbove(aggregate)));
    }

	private ResponseEntity<String> buildOKResponseEntity(final String message){
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
