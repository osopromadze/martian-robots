package com.guidesmiths.martian_robot.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private HttpStatus status;

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
