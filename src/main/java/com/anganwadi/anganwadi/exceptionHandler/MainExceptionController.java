package com.anganwadi.anganwadi.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class MainExceptionController implements ErrorController {

    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<?> handleNullPointerException(NullPointerException nullError) {
        log.error(nullError.getMessage());
        Map<String, Object> setNullError = new HashMap<>();
        setNullError.put("status", HttpStatus.BAD_REQUEST);
        setNullError.put("message", nullError.getMessage());
//        setNullError.put("message", "Error Occurred, Please Try Again Later & Contact Support Team");
        return new ResponseEntity<>(setNullError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(CustomException.class)
    private ResponseEntity<?> handleNullPointerException(CustomException nullError) {
        log.error(nullError.getMessage());
        Map<String, Object> setNullError = new HashMap<>();
        setNullError.put("message", nullError.getMessage());
        return new ResponseEntity<>(setNullError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<?> handleBadRequestException(BadRequestException error) {
        log.error(error.getMessage());
        Map<String, Object> setNullError = new HashMap<>();
        setNullError.put("message", error.getMessage());
        return new ResponseEntity<>(setNullError, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    private ResponseEntity<?> notFoundError(String error) {
        log.error(error);
        Map<String, Object> setNullError = new HashMap<>();
        setNullError.put("status", HttpStatus.BAD_REQUEST);
        setNullError.put("message", "Please Check Details Properly");
        return new ResponseEntity<>(setNullError, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<?> handleMethodError(HttpRequestMethodNotSupportedException error) {
        log.error(error.getMessage());
        Map<String, Object> setNullError = new HashMap<>();
        setNullError.put("status", HttpStatus.METHOD_NOT_ALLOWED);
        setNullError.put("message", "Please Check The API Method");
        return new ResponseEntity<>(setNullError, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<?> handleMethodError(HttpMessageNotReadableException error) {
        log.error(error.getMessage());
        Map<String, Object> setBodyError = new HashMap<>();
        setBodyError.put("status", HttpStatus.PARTIAL_CONTENT);
        setBodyError.put("message", "Parameters "+error.getHttpInputMessage()+"Are Missing, Please Check");
        return new ResponseEntity<>(setBodyError, HttpStatus.PARTIAL_CONTENT);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    private ResponseEntity<?> handleHeadersError(MissingRequestHeaderException error) {
        log.error(error.getMessage());
        Map<String, Object> setBodyError = new HashMap<>();
        setBodyError.put("status", HttpStatus.BAD_REQUEST);
        setBodyError.put("message", error.getHeaderName() + " Is Missing, Please Check !!!");
        return new ResponseEntity<>(setBodyError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<?> handleHeadersError(NoSuchElementException error) {
        log.error(error.getMessage());
        Map<String, Object> setBodyError = new HashMap<>();
        setBodyError.put("status", HttpStatus.BAD_REQUEST);
        setBodyError.put("message","Some Data is Missing or Deleted,Please Check With Team!!!");
        return new ResponseEntity<>(setBodyError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    private ResponseEntity<?> handleOutOfBondsException(IndexOutOfBoundsException error) {
        log.error(error.getMessage());
        Map<String, Object> setBodyError = new HashMap<>();
        setBodyError.put("status", HttpStatus.BAD_REQUEST);
        setBodyError.put("message",error.getMessage());
        return new ResponseEntity<>(setBodyError, HttpStatus.BAD_REQUEST);
    }

}
