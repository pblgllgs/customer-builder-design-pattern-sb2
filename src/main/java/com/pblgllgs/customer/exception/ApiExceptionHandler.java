package com.pblgllgs.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
            BadRequestException e,
            HttpServletRequest request
    ){
        ApiErrorResponse exception = new ApiErrorResponse();
        exception.setPath(request.getRequestURI());
        exception.setErrorMessage(e.getMessage());
        exception.setStatusCode(HttpStatus.BAD_REQUEST.value());
        exception.setZonedDateTime(ZonedDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
            SQLIntegrityConstraintViolationException e,
            HttpServletRequest request
    ){
        ApiErrorResponse exception = new ApiErrorResponse();
        exception.setPath(request.getRequestURI());
        exception.setErrorMessage(e.getMessage());
        exception.setStatusCode(HttpStatus.CONFLICT.value());
        exception.setZonedDateTime(ZonedDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
            ResourceNotFoundException e,
            HttpServletRequest request
    ){
        ApiErrorResponse exception = new ApiErrorResponse();
        exception.setPath(request.getRequestURI());
        exception.setErrorMessage(e.getMessage());
        exception.setStatusCode(HttpStatus.NOT_FOUND.value());
        exception.setZonedDateTime(ZonedDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
}
