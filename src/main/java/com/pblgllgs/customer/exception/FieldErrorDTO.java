package com.pblgllgs.customer.exception;

public record FieldErrorDTO(String objectName, String field, String annotation, String message) {
}