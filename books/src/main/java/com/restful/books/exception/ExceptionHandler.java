package com.restful.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<BookErrorResponse> exceptionHandler(BookNotFoundException bookNotFoundException){
        return new ResponseEntity<>(new BookErrorResponse(HttpStatus.NOT_FOUND.value(), bookNotFoundException.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<BookErrorResponse> exceptionHandler(Exception exc){
        return new ResponseEntity<>(new BookErrorResponse(HttpStatus.BAD_REQUEST.value(), "Exception occurred.", System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
}
