package com.pblgllgs.customer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {

    private List<FieldErrorDTO> errors;

    public void add(String objectName, String field, String annotation, String message) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(new FieldErrorDTO(objectName, field, annotation, message));
    }

}