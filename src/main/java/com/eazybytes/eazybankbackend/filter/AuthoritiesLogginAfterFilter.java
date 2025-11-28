package com.eazybytes.eazybankbackend.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Slf4j
public class AuthoritiesLogginAfterFilter implements Filter {

    // Filtro che logga le authorities dopo il BasicAuthenticationFilter
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Recupera l'Authentication dal SecurityContext dopo che il BasicAuthenticationFilter ha lavorato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (null != authentication) {
            // Logga utente autenticato e authorities assegnate
            log.info("User" + authentication.getName() + " is successfully authenticated and " + "has the authorities " + authentication.getAuthorities().toString());
        }
        // Prosegue la catena dei filtri
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
