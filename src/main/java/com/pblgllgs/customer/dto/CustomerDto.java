package com.pblgllgs.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Long id;
    @NotBlank(message = "Can´t be blank")
    private String firstName;
    @NotBlank(message = "Can´t be blank")
    private String lastName;
    @Email
    @NotBlank
    private String email;
    private LocalDate createdAt;

    public CustomerDto(String firstName, String lastName, String email, LocalDate createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
    }
}
