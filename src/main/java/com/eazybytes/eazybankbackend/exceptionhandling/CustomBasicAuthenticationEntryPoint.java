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

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // Variabili
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message = (authException != null && authException.getMessage() != null) ? authException.getMessage() : "Unauthorized";
        String path = request.getRequestURI();

        response.setHeader("eazybank-error=reason", "Authentication failed");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // Custom JSON response body (output di postman ad esempio, in caso di errore)
        response.setContentType("application/json:charset=UTF-8");
        String jsonResponse = String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
                // Valorizzo il formato sopra
                currentTimeStamp, // Timestamp creato sopra, inizio metodo
                HttpStatus.UNAUTHORIZED.value(), // Valore HTTP (400, 401, 403)
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                message, // Messaggio creato sopra, inizio metodo
                path); // Path API, creato sopra, inizio metodo

        response.getWriter().write(jsonResponse);
    }
}
