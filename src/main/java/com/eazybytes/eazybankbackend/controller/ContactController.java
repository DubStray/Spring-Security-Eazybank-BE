package com.eazybytes.eazybankbackend.controller;

import com.eazybytes.eazybankbackend.model.Contact;
import com.eazybytes.eazybankbackend.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class ContactController {

    // Repository per salvare i messaggi di contatto
    private final ContactRepository contactRepository;

    // Endpoint POST pubblico che salva una richiesta di contatto
    @PostMapping("/contact")
    public Contact saveContactInquiryDetails(@RequestBody Contact contact) {
        // Genera un identificativo univoco-like per la richiesta
        contact.setContactId(getServiceReqNumber());
        // Timestamp di creazione
        contact.setCreateDt(new Date(System.currentTimeMillis()));
        // Salva su database e restituisce l'entità salvata
        return contactRepository.save(contact);
    }

    // Funzione helper per generare il codice SR casuale
    public String getServiceReqNumber() {
        // Genera un numero casuale; non garantisce unicità, usato solo a scopo demo
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR" + ranNum;
    }
}
