package com.bci.proyectobci.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorUserExist.class)
    public ResponseEntity<Errors> handleExistingEmailException(ErrorUserExist exception,
                                                               WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails();
        final Errors errors = new Errors();
        errorDetails.setCode(exception.getCode());
        errorDetails.setDetails(exception.getDetail());
        errorDetails.setTimestamp(exception.getTimestamp());
        errors.setErrors(Set.of(errorDetails));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorExistUsername.class)
    public ResponseEntity<Errors> handleExistingNameException(ErrorExistUsername exception, WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails();
        final Errors errors = new Errors();

        errorDetails.setCode(exception.getCode());
        errorDetails.setDetails(exception.getDetail());
        errorDetails.setTimestamp(exception.getTimestamp());
        errors.setErrors(Set.of(errorDetails));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        final Errors errors = new Errors();
        errorDetails.setCode(400);
        errorDetails.setDetails(ex.getBindingResult().getFieldError().getDefaultMessage());
        errorDetails.setTimestamp(new Date());
        errors.setErrors(Set.of(errorDetails));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
