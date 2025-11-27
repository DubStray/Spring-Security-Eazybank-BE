package com.eazybytes.eazybankbackend.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
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
public class AuthorizationEvents {

    @EventListener
    public void onFailure(AuthorizationDeniedEvent deniedEvent) {
        log.error("Authorization failed for the user : {} due to : {}", deniedEvent.getAuthentication().get().getName(),
                deniedEvent.getAuthorizationDecision().toString());
    }
}
