package com.harsh.securebank.controller;

import com.harsh.securebank.entity.Cards;
import com.harsh.securebank.entity.Customer;
import com.harsh.securebank.repository.CardsRepository;
import com.harsh.securebank.repository.CustomerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {

        Customer customer = customerRepository.findByEmail(email);

        if(customer == null)
            return null;

        return cardsRepository.findByCustomerId(customer.getId());
    }

}