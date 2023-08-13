package com.pblgllgs.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.ZonedDateTime;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {

    public static ApiErrorResponse contentException(
            HttpServletRequest request,
            String message,
            int httpStatus
    ) {
        ApiErrorResponse exception = new ApiErrorResponse();
        exception.setPath(request.getRequestURI());
        exception.setErrorMessage(message);
        exception.setStatusCode(httpStatus);
        exception.setZonedDateTime(ZonedDateTime.now());
        return exception;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
            BadRequestException e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                contentException(
                        request,
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
            SQLIntegrityConstraintViolationException e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                contentException(
                        request,
                        e.getMessage(),
                        HttpStatus.CONFLICT.value()
                ),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                contentException(
                        request,
                        e.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
            IllegalArgumentException e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                contentException(
                        request,
                        e.getMessage(),
                        HttpStatus.CONFLICT.value()
                ),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
            ConstraintViolationException e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                contentException(
                        request,
                        "Debe" +e.getMessage().split(": debe")[1],
                        HttpStatus.NOT_ACCEPTABLE.value()
                ),
                HttpStatus.NOT_ACCEPTABLE
        );
    }

    @ExceptionHandler(EmailInUseException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
            EmailInUseException e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                contentException(
                        request,
                        e.getMessage(),
                        HttpStatus.CONFLICT.value()
                ),
                HttpStatus.CONFLICT
        );
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseBody
//    public ResponseEntity<List<String>> processUnmergeException(final MethodArgumentNotValidException ex) {
//
//        List<String> list = ex.getBindingResult().getAllErrors().stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .collect(Collectors.toList());
//
//        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
//        List<String> errors = ex.getBindingResult().getFieldErrors()
//                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
//        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }
//
//    private Map<String, List<String>> getErrorsMap(List<String> errors) {
//        Map<String, List<String>> errorResponse = new HashMap<>();
//        errorResponse.put("errors", errors);
//        return errorResponse;
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return processFieldErrors(result.getFieldErrors());
    }

    private ErrorDTO processFieldErrors(List<FieldError> errors) {
        ErrorDTO dto = new ErrorDTO();
        errors.forEach(error -> dto.add(
                error.getObjectName(),
                error.getField(),
                error.getCode(),
                error.getDefaultMessage()
        ));
        return dto;
    }

}
