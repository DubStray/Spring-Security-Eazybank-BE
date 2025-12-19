package com.eazybytes.eazybankbackend.controller;

import com.eazybytes.eazybankbackend.model.Accounts;
import com.eazybytes.eazybankbackend.model.Customer;
import com.eazybytes.eazybankbackend.repository.AccountsRepository;
import com.eazybytes.eazybankbackend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
// Controller REST che espone le API relative ai conti
public class AccountController {

    // Repository JPA che accede alla tabella accounts
    private final AccountsRepository accountsRepository;

    // Repository JPA che accede alla tabella customer
    private final CustomerRepository customerRepository;

    // Rest endpoint che restituisce il conto di un cliente dato l'id
    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {

        // Cerca il cliente a partire dall'email passata come parametro
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if (optionalCustomer.isPresent()) {
            // Recupera l'account del cliente passato via query param ?id=...
            Accounts accounts = accountsRepository.findByCustomerId(optionalCustomer.get().getId());
            if (accounts != null) {
                // Se esiste, viene ritornato e serializzato in JSON
                return accounts;
            } else {
                // Se non esiste, ritorna null (il client riceverà una risposta vuota)
                return null;
            }
        } else {
            // Cliente non trovato: nessun conto da restituire
            return null;
        }



    }
}
