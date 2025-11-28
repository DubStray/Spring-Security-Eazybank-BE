package com.eazybytes.eazybankbackend.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // Gestisce le risposte 403 Forbidden generando un JSON custom
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // Timestamp dell'errore
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        // Messaggio leggibile: se presente, usa quello dell'eccezione, altrimenti default
        String message = (accessDeniedException != null && accessDeniedException.getMessage() != null) ? accessDeniedException.getMessage() : "Authorization failed";
        // URI che ha generato l'accesso negato
        String path = request.getRequestURI();

        // Header custom di risposta
        response.setHeader("eazybank-denied=reason", "Authentication failed");
        // Status HTTP 403
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // Custom JSON response body (output di postman ad esempio, in caso di errore)
        // Nota: qui è presente un refuso ":" al posto di ";" nel content-type
        response.setContentType("application/json:charset=UTF-8");
        String jsonResponse = String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
                // Valorizzo il formato sopra
                currentTimeStamp, // Timestamp creato sopra, inizio metodo
                HttpStatus.FORBIDDEN.value(), // Valore HTTP (400, 401, 403)
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                message, // Messaggio creato sopra, inizio metodo
                path); // Path API, creato sopra, inizio metodo

        // Scrive il JSON nel body della risposta
        response.getWriter().write(jsonResponse);
    }
}
