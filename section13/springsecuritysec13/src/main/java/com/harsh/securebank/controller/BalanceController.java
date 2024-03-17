package com.harsh.securebank.controller;

import com.harsh.securebank.entity.AccountTransactions;
import com.harsh.securebank.entity.Customer;
import com.harsh.securebank.repository.AccountTransactionsRepository;
import com.harsh.securebank.repository.CustomerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @Autowired
    private AccountTransactionsRepository accountTransactionsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {

        Customer customer = customerRepository.findByEmail(email);

        if(customer == null)
            return null;

        return accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(customer.getId());
    }
}