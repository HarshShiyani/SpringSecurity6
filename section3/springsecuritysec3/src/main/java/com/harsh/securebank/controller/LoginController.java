package com.harsh.securebank.controller;

import com.harsh.securebank.entity.Customer;
import com.harsh.securebank.repository.CustomerRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final CustomerRepository customerRepository;

    public LoginController(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/register")
    public String createCustomer(@RequestBody Customer customer)
    {
        customerRepository.save(customer);
        return "Customer saved successfully";
    }
}
