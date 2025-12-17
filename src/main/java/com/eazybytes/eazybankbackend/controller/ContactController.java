package com.eazybytes.eazybankbackend.controller;

import com.eazybytes.eazybankbackend.model.Contact;
import com.eazybytes.eazybankbackend.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class ContactController {

    // Repository per salvare i messaggi di contatto
    private final ContactRepository contactRepository;

    // Endpoint POST pubblico che salva una richiesta di contatto
    @PostMapping("/contact")
    // @PreFilter("filterObject.contactName != 'Test'")
    @PostFilter("filterObject.contactName != 'Test'")
    public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {
        // NB: per semplicità prendiamo solo il primo elemento della lista inviata dal client
        // 1) estraiamo il primo contatto
        // 2) generiamo l'ID "SR" casuale (non garantisce univocità, ma basta per la demo)
        // 3) impostiamo la data di creazione
        // 4) salviamo a DB e ritorniamo una lista contenente il record salvato
        List<Contact> returnContacts = new ArrayList<>();
        if(!contacts.isEmpty()) {
            Contact contact = contacts.getFirst();
            contact.setContactId(getServiceReqNumber());
            contact.setCreateDt(new Date(System.currentTimeMillis()));
            Contact savedContact = contactRepository.save(contact);
            returnContacts.add(savedContact);
        }
        return returnContacts;
    }

    // Funzione helper per generare il codice SR casuale
    public String getServiceReqNumber() {
        // Genera un numero casuale; non garantisce unicità, usato solo a scopo demo
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR" + ranNum;
    }
}
