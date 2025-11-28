package com.eazybytes.eazybankbackend.controller;

import com.eazybytes.eazybankbackend.model.Cards;
import com.eazybytes.eazybankbackend.repository.CardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardsController {

    // Repository per le carte collegate al cliente
    private final CardsRepository cardsRepository;

    // Rest endpoint che restituisce tutte le carte associate a un cliente
    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam long id) {
        // Recupera tutte le carte del cliente indicato
        List<Cards> cards = cardsRepository.findByCustomerId(id);
        if (cards != null ) {
            // Ritorna la lista (può essere vuota)
            return cards;
        }else {
            // Se non trovate, ritorna null (risposta vuota)
            return null;
        }
    }

}
