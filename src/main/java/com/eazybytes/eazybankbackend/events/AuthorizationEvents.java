package com.eazybytes.eazybankbackend.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

/**
 * Listener custom per gli eventi di autorizzazione negata.
 * Quando un utente autenticato prova ad accedere a una risorsa senza permessi,
 * Spring Security pubblica un AuthorizationDeniedEvent che viene intercettato qui.
 * Usa Slf4j (fornito da Lombok) per loggare il dettaglio.
 */
@Component
@Slf4j
public class AuthorizationEvents {

    // Listener che intercetta autorizzazioni negate
    @EventListener
    public void onFailure(AuthorizationDeniedEvent deniedEvent) {
        // Logga le autorizzazioni negate; attenzione: getAuthentication() può essere vuoto in alcuni casi
        // (aggiungere un null-check se si vogliono evitare eccezioni quando l'evento non ha principal associato)
        log.error("Authorization failed for the user : {} due to : {}",
                deniedEvent.getAuthentication().get().getName(),
                deniedEvent.getAuthorizationDecision().toString());
    }
}

