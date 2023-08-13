package com.pblgllgs.customer.exception;

import com.pblgllgs.customer.dto.response.ApiResponse;
import com.pblgllgs.customer.dto.response.ResponseBuilder;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final ResponseBuilder responseBuilder;

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
    public ResponseEntity<ApiResponse> handleBadRequestException(
            BadRequestException e,
            HttpServletRequest request
    ) {
        return responseBuilder.buildResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Ha ocurrido un error",
                contentException(
                        request,
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                )
        );
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> handleBadRequestException(
            SQLIntegrityConstraintViolationException e,
            HttpServletRequest request
    ) {
        return responseBuilder.buildResponse(
                HttpStatus.CONFLICT.value(),
                "Error en la integridad de la DB",
                contentException(
                        request,
                        e.getMessage(),
                        HttpStatus.CONFLICT.value()
                )
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> handleBadRequestException(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {
        return responseBuilder.buildResponse(
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                contentException(
                        request,
                        e.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                )
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> handleBadRequestException(
            IllegalArgumentException e,
            HttpServletRequest request
    ) {
        return responseBuilder.buildResponse(
                HttpStatus.CONFLICT.value(),
                "Debe entregar un email para hacer la búsqueda",
                contentException(
                        request,
                        e.getMessage(),
                        HttpStatus.CONFLICT.value()
                )
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> handleBadRequestException(
            ConstraintViolationException e,
            HttpServletRequest request
    ) {
        return responseBuilder.buildResponse(
                HttpStatus.NOT_ACCEPTABLE.value(),
                "Error en la integridad de la DB",
                contentException(
                        request,
                        "Debe"+e.getMessage().split(": debe")[1],
                        HttpStatus.NOT_ACCEPTABLE.value()
                )
        );
    }

    @ExceptionHandler(EmailInUseException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> handleBadRequestException(
            EmailInUseException e,
            HttpServletRequest request
    ) {
        return responseBuilder.buildResponse(
                HttpStatus.CONFLICT.value(),
                "El email ya esta registrado",
                contentException(
                        request,
                        e.getMessage(),
                        HttpStatus.CONFLICT.value()
                )
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
    public ResponseEntity<ApiResponse> processValidationError(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        BindingResult result = ex.getBindingResult();
        return responseBuilder.buildResponse(
                HttpStatus.CONFLICT.value(),
                "Error en la validación de los datos",
                processFieldErrors(result.getFieldErrors())
        );
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
