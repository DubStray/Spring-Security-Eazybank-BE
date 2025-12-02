package com.eazybytes.eazybankbackend.controller;

import com.eazybytes.eazybankbackend.model.Loans;
import com.eazybytes.eazybankbackend.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoansController {

    // Repository JPA per i prestiti
    private final LoanRepository loanRepository;

    // Rest endpoint che ritorna i prestiti di un cliente
    @PostAuthorize("hasRole('USER')")
    @GetMapping("/myLoans")
    public List<Loans> getLoanDetails(@RequestParam long id) {
        // Recupera i prestiti del cliente, ordinati dalla data più recente
        List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(id);
        if (loans != null) {
            // Ritorna la lista (può essere vuota)
            return loans;
        } else {
            // Se non ci sono risultati, ritorna null (risposta vuota)
            return null;
        }
    }

}
