package com.restful.todos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handleException(ResponseStatusException responseStatusException){
        return buildResponseEntity(responseStatusException, responseStatusException.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handlerException(Exception ex){
        return buildResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionResponse> buildResponseEntity(Exception exception, HttpStatusCode httpStatusCode) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(httpStatusCode.value(), exception.getMessage()
                                , System.currentTimeMillis());
        return new ResponseEntity<>(exceptionResponse, httpStatusCode);
    }
}
