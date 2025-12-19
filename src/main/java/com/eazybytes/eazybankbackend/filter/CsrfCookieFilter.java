package com.eazybytes.eazybankbackend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Filtro che espone il token CSRF tramite cookie
public class CsrfCookieFilter extends OncePerRequestFilter {

    // Filtro che forza la materializzazione del token CSRF nel cookie
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Recupera il CsrfToken salvato come attributo della request
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        // Forza la materializzazione del token e quindi la creazione del cookie sul client
        csrfToken.getToken();
        // Prosegue la catena
        filterChain.doFilter(request, response);
    }
}
