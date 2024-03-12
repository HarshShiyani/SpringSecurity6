package com.harsh.securebank.controller;

import com.harsh.securebank.dto.CustomerDTO;
import com.harsh.securebank.entity.Authority;
import com.harsh.securebank.entity.Customer;
import com.harsh.securebank.repository.CustomerRepository;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginController(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        Customer savedCustomer;
        ResponseEntity<String> response = null;
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(LocalDateTime.now().toString());
            savedCustomer = customerRepository.save(customer);
            if (savedCustomer.getId() > 0) {
                response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Given user details are successfully registered");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An exception occurred due to " + ex.getMessage());
        }
        return response;
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
