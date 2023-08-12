package com.pblgllgs.customer.service;

import com.pblgllgs.customer.dto.CustomerDto;
import com.pblgllgs.customer.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

    ResponseEntity<ApiResponse> findAll();
    ResponseEntity<ApiResponse> createCustomer(CustomerDto customerDto);
    ResponseEntity<ApiResponse> findCustomerById(Long id);
}
