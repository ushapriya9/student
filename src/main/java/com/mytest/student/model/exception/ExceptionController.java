package com.mytest.student.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionController{
    @ExceptionHandler
    public ResponseEntity<Object> exception(SQLException exception) {
        return new ResponseEntity<>("Please ", HttpStatus.BAD_REQUEST);
    }
}
