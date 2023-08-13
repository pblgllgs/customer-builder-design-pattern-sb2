package com.pblgllgs.customer.exception;

public class IllegalArgumentException extends RuntimeException {
    private final String message;

    public IllegalArgumentException(String message) {
        super(message);
        this.message = message;
    }
}
