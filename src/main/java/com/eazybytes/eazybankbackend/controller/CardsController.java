package com.eazybytes.eazybankbackend.controller;

import com.eazybytes.eazybankbackend.model.Cards;
import com.eazybytes.eazybankbackend.model.Customer;
import com.eazybytes.eazybankbackend.repository.CardsRepository;
import com.eazybytes.eazybankbackend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
// Controller REST che espone le API sulle carte
public class CardsController {

    // Repository per accedere ai dati delle carte
    private final CardsRepository cardsRepository;
    // Repository per recuperare i dati del cliente
    private final CustomerRepository customerRepository;

    // Endpoint che restituisce le carte del cliente
    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {
        // Cerca il cliente tramite email
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if (optionalCustomer.isPresent()) {
            // Recupera tutte le carte associate al cliente
            List<Cards> cards = cardsRepository.findByCustomerId(optionalCustomer.get().getId());
            if (cards != null) {
                return cards;
            } else {
                return null;
            }
        } else {
            // Cliente non trovato: nessuna carta da restituire
            return null;
        }
    }

}
