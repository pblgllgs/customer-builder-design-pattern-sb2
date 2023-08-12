package com.pblgllgs.customer;

import com.github.javafaker.Faker;
import com.pblgllgs.customer.dto.CustomerDto;
import com.pblgllgs.customer.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    private static void generate(int quantity, CustomerService customerService) {
        for (int i = 0; i < quantity; i++) {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            CustomerDto customerDto = new CustomerDto(
                    firstName,
                    lastName,
                    firstName + "." + lastName + "@gmail.com"
            );
            customerService.createCustomer(customerDto);
        }
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerService customerService) {
        return args -> generate(5, customerService);
    }

}