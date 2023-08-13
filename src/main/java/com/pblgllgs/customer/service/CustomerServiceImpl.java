package com.pblgllgs.customer.service;

import com.pblgllgs.customer.dto.CustomerDto;
import com.pblgllgs.customer.dto.response.ApiResponse;
import com.pblgllgs.customer.dto.response.ResponseBuilder;
import com.pblgllgs.customer.entity.Customer;
import com.pblgllgs.customer.exception.EmailInUseException;
import com.pblgllgs.customer.exception.ResourceNotFoundException;
import com.pblgllgs.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ResponseBuilder responseBuilder;
    private final ModelMapper modelMapper;

    public ResponseEntity<ApiResponse> findAll() {
        List<CustomerDto> customersDto =
                customerRepository
                        .findAll()
                        .stream()
                        .map(
                                customer -> modelMapper.map(customer, CustomerDto.class))
                        .toList();
        return responseBuilder.buildResponse(
                HttpStatus.OK.value(),
                "Customer details",
                customersDto
        );
    }

    @Override
    public ResponseEntity<ApiResponse> createCustomer(CustomerDto customerDto) {
        Customer customerSaved = customerRepository.save(modelMapper.map(customerDto, Customer.class));
        return responseBuilder.buildResponse(
                HttpStatus.CREATED.value(),
                "Customer created",
                modelMapper.map(customerSaved, CustomerDto.class)
        );
    }

    @Override
    public ResponseEntity<ApiResponse> findCustomerById(Long id) {
        Customer customerDb =
                customerRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));
        return responseBuilder.buildResponse(
                HttpStatus.OK.value(),
                "Customer id: " + id + " details",
                modelMapper.map(customerDb, CustomerDto.class)
        );
    }

    @Override
    public ResponseEntity<ApiResponse> updateCustomer(Long id, CustomerDto customerDto) {
        if (customerRepository.existsCustomerByEmail(customerDto.getEmail())) {
            throw new EmailInUseException("Email " + customerDto.getEmail() + " ya esta en uso");
        }
        Customer customerDb =
                customerRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));
        customerDb.setFirstName(customerDto.getFirstName());
        customerDb.setLastName(customerDto.getLastName());
        customerDb.setEmail(customerDto.getEmail());
        Customer customerUpdated = customerRepository.save(customerDb);
        return responseBuilder.buildResponse(
                HttpStatus.OK.value(),
                "Customer id: " + id + " details",
                modelMapper.map(customerUpdated, CustomerDto.class)
        );

    }

    @Override
    public ResponseEntity<ApiResponse> findCustomerByEmail(String email) {
        if (Objects.equals(email, "")) {
            return responseBuilder.buildResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Debe entregar un email para hacer la búsqueda", "No existen coincidencias"
            );
        }
        if (!customerRepository.existsCustomerByEmail(email)) {
            return responseBuilder.buildResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Customer email: " + email + " details", "No existen coincidencias"
            );
        }
        Customer customerDb =
                customerRepository
                        .findCustomerByEmail(email).orElseThrow();
        return responseBuilder.buildResponse(
                HttpStatus.OK.value(),
                "Customer email: " + email + " details",
                modelMapper.map(customerDb, CustomerDto.class));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteCustomer(Long id) {
        Customer customerDb =
                customerRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));
        customerRepository.deleteById(customerDb.getId());
        return responseBuilder.buildResponse(
                HttpStatus.OK.value(),
                "Customer id: " + customerDb.getId() , "Se ha eliminado exitosamente!"
        );
    }
}
