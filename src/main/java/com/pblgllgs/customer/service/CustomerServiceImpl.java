package com.pblgllgs.customer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pblgllgs.customer.dto.CustomerDto;
import com.pblgllgs.customer.dto.response.ApiResponse;
import com.pblgllgs.customer.dto.response.ResponseBuilder;
import com.pblgllgs.customer.entity.Customer;
import com.pblgllgs.customer.exception.ResourceNotFoundException;
import com.pblgllgs.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ResponseBuilder responseBuilder;

    public ResponseEntity<ApiResponse> findAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CustomerDto> customers =
                customerRepository
                        .findAll()
                        .stream()
                        .map(
                                customer -> objectMapper.convertValue(customer,CustomerDto.class))
                        .toList();
        return responseBuilder.buildResponse(
                HttpStatus.OK.value(),
                "Customer details",
                customers
        );
    }

    @Override
    public ResponseEntity<ApiResponse> createCustomer(CustomerDto customerDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer customerSaved = customerRepository.save(objectMapper.convertValue(customerDto,Customer.class));
        return responseBuilder.buildResponse(
                HttpStatus.CREATED.value(),
                "Customer created",
                customerSaved
        );
    }

    @Override
    public ResponseEntity<ApiResponse> findCustomerById(Long id) {
        Customer customer =
                customerRepository
                        .findById(id)
                        .orElseThrow( ()-> new ResourceNotFoundException("Customer with id "+id+" not found"));
        return responseBuilder.buildResponse(
                HttpStatus.OK.value(),
                "Customer id: "+id+" details",
                customer
        );
    }
}
