package com.pblgllgs.customer;

import com.github.javafaker.Faker;
import com.pblgllgs.customer.dto.CustomerDto;
import com.pblgllgs.customer.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static void generate(int quantity, CustomerService customerService) {
        for (int i = 0; i < quantity; i++) {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            CustomerDto customerDto = new CustomerDto(
                    firstName,
                    lastName,
                    firstName + "." + lastName + "@gmail.com",
                    LocalDate.now()

            );
            customerService.createCustomer(customerDto);
        }
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerService customerService) {
        return args -> generate(5, customerService);
    }

}
