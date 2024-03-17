package com.harsh.securebank.controller;

import com.harsh.securebank.entity.Accounts;
import com.harsh.securebank.entity.Customer;
import com.harsh.securebank.repository.AccountsRepository;
import com.harsh.securebank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {

        Customer customer = customerRepository.findByEmail(email);

        if(customer == null)
            return null;

        return accountsRepository.findByCustomerId(customer.getId());
    }

}