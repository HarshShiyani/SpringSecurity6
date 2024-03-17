package com.harsh.securebank.controller;

import com.harsh.securebank.dto.CustomerDTO;
import com.harsh.securebank.entity.Authority;
import com.harsh.securebank.entity.Customer;
import com.harsh.securebank.repository.CustomerRepository;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final CustomerRepository customerRepository;

    public LoginController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/user")
    public CustomerDTO getUserDetailsAfterLogin(Authentication authentication) {

        if (!authentication.isAuthenticated()) {
            return null;
        }

        Customer customerEntity = customerRepository.findByEmail(authentication.getName());

        return CustomerDTO.builder()
            .name(customerEntity.getName())
            .email(customerEntity.getEmail())
            .mobileNumber(customerEntity.getMobileNumber())
            .authorities(customerEntity.getAuthorities().stream().map(Authority::getName).collect(
                Collectors.toList()))
            .role(customerEntity.getRole())
            .build();
    }
}
