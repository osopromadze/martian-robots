package com.guidesmiths.martian_robot.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RobotExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> resolveException(AppException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}
