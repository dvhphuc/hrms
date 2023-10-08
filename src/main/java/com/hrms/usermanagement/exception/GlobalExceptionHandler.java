package com.hrms.usermanagement.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(UserNotFoundException ex, WebRequest request)
    {
        var jsonException = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity(jsonException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(WrongPasswordException ex, WebRequest request)
    {
        var jsonException = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity(jsonException, HttpStatus.UNAUTHORIZED);
    }

    @Getter
    @Setter
    static class ExceptionResponse {
        private String message;
        private LocalDateTime timestamp = LocalDateTime.now();
        public ExceptionResponse(String message) {
            this.message = message;
        }
    }
}
