package com.harsh.securebank.controller;

import com.harsh.securebank.entity.AccountTransactions;
import com.harsh.securebank.repository.AccountTransactionsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @Autowired
    private AccountTransactionsRepository accountTransactionsRepository;

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam int id) {
        return accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(id);
    }
}