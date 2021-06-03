package com.guidesmiths.martian_robot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RobotExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> resolveException(AppException exception) {

        HttpStatus status = exception.getStatus() == null ? HttpStatus.BAD_REQUEST : exception.getStatus();

        return ResponseEntity.status(status).body(exception.getMessage());
    }

}
