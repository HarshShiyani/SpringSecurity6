package com.harsh.securebank.controller;

import com.harsh.securebank.entity.Customer;
import com.harsh.securebank.entity.Loans;
import com.harsh.securebank.repository.CustomerRepository;
import com.harsh.securebank.repository.LoanRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myLoans")
    public List<Loans> getLoanDetails(@RequestParam String email) {

        Customer customer = customerRepository.findByEmail(email);

        if(customer == null)
            return null;

        return loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getId());
    }

}