package com.eazybytes.eazybankbackend.controller;

import com.eazybytes.eazybankbackend.model.Customer;
import com.eazybytes.eazybankbackend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    // Repository per operare sulla tabella customer
    private final CustomerRepository customerRepository;
    // Encoder password iniettato dal contesto security
    private final PasswordEncoder passwordEncoder;

    // Endpoint pubblico di registrazione utenti
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            // Codifica la password in ingresso usando l'encoder configurato
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            // Imposta data di creazione
            customer.setCreateDt(new Date(System.currentTimeMillis()));
            // Persiste il nuovo utente
            Customer savedCustomer = customerRepository.save(customer);

            if (savedCustomer.getId() > 0) {
                // Inserimento riuscito -> 201 Created
                return ResponseEntity.status(HttpStatus.CREATED).
                        body("Given user details are successfully registered");
            } else {
                // Inserimento fallito -> 400 Bad Request
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                        body("User registration failed");
            }
        } catch (Exception ex) {
            // Gestione generica di errori imprevisti -> 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("An exception occurred: " + ex.getMessage());
        }
    }

    // Endpoint protetto che restituisce il profilo dell'utente autenticato
    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        // Recupera i dettagli dell'utente loggato tramite principal (email)
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        // Ritorna l'entità se presente, altrimenti null
        return optionalCustomer.orElse(null);
    }

}
