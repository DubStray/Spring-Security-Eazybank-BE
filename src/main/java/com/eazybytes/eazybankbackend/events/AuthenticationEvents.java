package com.eazybytes.eazybankbackend.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Classe custom per ascoltare gli eventi dell'Authentication
 *
 * onSuccess se il login avviene
 * onFailure se il login fallisce
 *
 * Slf4j libreria di lombok per loggare
 */
@Component
@Slf4j
public class AuthenticationEvents {

    // Listener che intercetta eventi di login riuscito
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent) {
        // Logga i login andati a buon fine con l'username usato
        log.info("Login successful for the user: {}", successEvent.getAuthentication().getName());
    }

    // Listener che intercetta eventi di login fallito
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failureEvent) {
        // Logga i login falliti e il motivo dell'eccezione di autenticazione
        log.error("Login failed for the user : {} due to : {}", failureEvent.getAuthentication().getName(),
                failureEvent.getException().getMessage());
    }
}
