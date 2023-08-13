package com.pblgllgs.customer.service;

import com.pblgllgs.customer.dto.CustomerDto;
import com.pblgllgs.customer.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

    ResponseEntity<ApiResponse> findAll();
    ResponseEntity<ApiResponse> createCustomer(CustomerDto customerDto);
    ResponseEntity<ApiResponse> findCustomerById(Long id);
    ResponseEntity<ApiResponse> updateCustomer(Long id, CustomerDto customerDto);
    ResponseEntity<ApiResponse> findCustomerByEmail(String email);
    ResponseEntity<ApiResponse> deleteCustomer(Long id);
}
