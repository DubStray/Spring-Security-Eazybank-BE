package com.eazybytes.eazybankbackend.controller;

import com.eazybytes.eazybankbackend.model.AccountTransactions;
import com.eazybytes.eazybankbackend.model.Customer;
import com.eazybytes.eazybankbackend.repository.AccountTransactionsRepository;
import com.eazybytes.eazybankbackend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
// Controller REST che espone saldo e movimenti del conto
public class BalanceController {

    // Repository per leggere le transazioni del conto
    private final AccountTransactionsRepository accountTransactionsRepository;
    // Repository per recuperare i dati del cliente
    private final CustomerRepository customerRepository;

    // Endpoint che restituisce le transazioni ordinate per data (saldo/movimenti)
    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {
        // Cerca il cliente tramite email
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if (optionalCustomer.isPresent()) {
            // Recupera le transazioni del cliente ordinate dalla piu' recente
            List<AccountTransactions> accountTransactions = accountTransactionsRepository.
                    findByCustomerIdOrderByTransactionDtDesc(optionalCustomer.get().getId());
            if (accountTransactions != null) {
                return accountTransactions;
            } else {
                return null;
            }
        } else {
            // Cliente non trovato: nessun movimento da restituire
            return null;
        }
    }
}
