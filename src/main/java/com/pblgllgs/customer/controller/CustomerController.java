package com.pblgllgs.customer.controller;

import com.pblgllgs.customer.dto.CustomerDto;
import com.pblgllgs.customer.dto.response.ApiResponse;
import com.pblgllgs.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    /**
     * Find all customers
     * @return ApiResponse object
     */
    @GetMapping()
    public ResponseEntity<ApiResponse> getEmployees(){
        return customerService.findAll();
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createCustomer(@RequestBody CustomerDto customerDto){
        return customerService.createCustomer(customerDto);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse> findCustomerById(@PathVariable("customerId")Long id){
        return customerService.findCustomerById(id);
    }
}