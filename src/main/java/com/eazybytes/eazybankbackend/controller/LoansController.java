package com.eazybytes.eazybankbackend.controller;

import com.eazybytes.eazybankbackend.model.Customer;
import com.eazybytes.eazybankbackend.model.Loans;
import com.eazybytes.eazybankbackend.repository.CustomerRepository;
import com.eazybytes.eazybankbackend.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
// Controller REST che espone le API sui prestiti
public class LoansController {

    // Repository per leggere i prestiti
    private final LoanRepository loanRepository;
    // Repository per recuperare i dati del cliente
    private final CustomerRepository customerRepository;

    // Endpoint che restituisce la lista dei prestiti del cliente
    @GetMapping("/myLoans")
    @PostAuthorize("hasRole('USER')")
    public List<Loans> getLoanDetails(@RequestParam String email) {
        // Cerca il cliente tramite email
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if (optionalCustomer.isPresent()) {
            // Recupera i prestiti ordinati per data di inizio
            List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(optionalCustomer.get().getId());
            if (loans != null) {
                return loans;
            } else {
                return null;
            }
        } else {
            // Cliente non trovato: nessun prestito da restituire
            return null;
        }
    }

}
