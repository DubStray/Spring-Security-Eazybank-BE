package com.eazybytes.eazybankbackend.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

/*
 Custom class per gestire le eccezioni di tipo EntryPoint
 */
public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Gestisce risposte 401 per richieste non autenticate generando un JSON custom
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        // Data e ora attuale per tracciare l'errore
        LocalDateTime currentTimeStamp = LocalDateTime.now();

        // Recupero un messaggio leggibile: se l'eccezione esiste, uso il suo messaggio,
        // altrimenti predefinisco "Unauthorized"
        String message = (authException != null && authException.getMessage() != null)
                ? authException.getMessage()
                : "Unauthorized";

        // Path dell'endpoint che ha generato il problema
        String path = request.getRequestURI();

        // -------------------------------
        //  COSTRUZIONE DELLA RESPONSE
        // -------------------------------

        // Imposto lo status HTTP 401 (richiesta non autenticata)
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // Indico che il corpo della risposta sarà JSON, in UTF-8
        // NB: la forma corretta usa il punto e virgola, NON i due punti
        response.setContentType("application/json;charset=UTF-8");

        // Imposto esplicitamente l'encoding dei caratteri
        response.setCharacterEncoding("UTF-8");

        // Creo il JSON di risposta manualmente.
        // Puoi farlo anche con ObjectMapper per più pulizia, ma questo funziona perfettamente.
        String jsonResponse = String.format("""
                {
                  "timestamp": "%s",
                  "status": %d,
                  "error": "%s",
                  "message": "%s",
                  "path": "%s"
                }
                """,
                currentTimeStamp,                  // timestamp in cui è avvenuto l’errore
                HttpStatus.UNAUTHORIZED.value(),   // codice 401
                HttpStatus.UNAUTHORIZED.getReasonPhrase(), // errore standard HTTP ("Unauthorized")
                message,                           // messaggio dell'eccezione
                path                               // endpoint che ha causato l’errore
        );

        // Scrive il JSON nella response: questo è ciò che Postman (o un qualsiasi client)
        // riceverà come corpo della risposta in caso di errore di autenticazione.
        response.getWriter().write(jsonResponse);
    }
}
