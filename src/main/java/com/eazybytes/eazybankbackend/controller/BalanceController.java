package com.eazybytes.eazybankbackend.controller;

import com.eazybytes.eazybankbackend.model.AccountTransactions;
import com.eazybytes.eazybankbackend.repository.AccountTransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BalanceController {

    // Repository per le transazioni di conto corrente
    private final AccountTransactionsRepository accountTransactionsRepository;

    // Rest endpoint che restituisce la lista di movimenti di un cliente
    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam long id) {
        // Carica le transazioni ordinate per data decrescente per il cliente indicato
        List<AccountTransactions> accountTransactions = accountTransactionsRepository.
                findByCustomerIdOrderByTransactionDtDesc(id);
        if (accountTransactions != null) {
            // Ritorna la lista (può essere vuota)
            return accountTransactions;
        } else {
            // Se il repository restituisce null, la risposta sarà vuota
            return null;
        }
    }
}
