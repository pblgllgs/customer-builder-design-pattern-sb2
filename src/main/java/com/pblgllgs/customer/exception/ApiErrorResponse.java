package com.pblgllgs.customer.exception;

import lombok.Data;
import java.time.ZonedDateTime;

@Data
public class ApiErrorResponse {
    private String path;
    private String errorMessage;
    private Integer statusCode;
    private ZonedDateTime zonedDateTime;
}