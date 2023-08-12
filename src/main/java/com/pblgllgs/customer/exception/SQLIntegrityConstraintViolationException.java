package com.pblgllgs.customer.exception;

import lombok.Data;

@Data
public class SQLIntegrityConstraintViolationException extends RuntimeException{

    private final String message;

    public SQLIntegrityConstraintViolationException(String message) {
        super(message);
        this.message = message;
    }
}