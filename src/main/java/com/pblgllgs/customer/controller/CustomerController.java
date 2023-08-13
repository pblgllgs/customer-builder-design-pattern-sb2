package com.pblgllgs.customer.controller;

import com.pblgllgs.customer.dto.CustomerDto;
import com.pblgllgs.customer.dto.response.ApiResponse;
import com.pblgllgs.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;


@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Find all customers
     *
     * @return ApiResponse object
     */
    @GetMapping()
    public ResponseEntity<ApiResponse> getEmployees() {
        return customerService.findAll();
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse> findCustomerById(@PathVariable("customerId") Long id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping("/email")
    public ResponseEntity<ApiResponse> findCustomerByEmail(@Email @RequestParam("customerEmail")String email) {
        return customerService.findCustomerByEmail(email);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<ApiResponse> updateCustomer(
            @Valid @RequestBody CustomerDto customerDto,
            @PathVariable("customerId") Long id){
        return customerService.updateCustomer(id,customerDto);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse> updateCustomer(
            @PathVariable("customerId") Long id){
        return customerService.deleteCustomer(id);
    }
}