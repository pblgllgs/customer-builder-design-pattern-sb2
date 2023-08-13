package com.pblgllgs.customer.exception;

public class EmailInUseException extends RuntimeException{
    private final String message;

    public EmailInUseException(String message) {
        super(message);
        this.message = message;
    }
}
