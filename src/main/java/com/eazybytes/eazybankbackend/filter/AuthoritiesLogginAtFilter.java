package com.eazybytes.eazybankbackend.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class AuthoritiesLogginAtFilter implements Filter {

    // Filtro che logga prima della fase di autenticazione di base
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Log iniziale prima della fase di autenticazione di BasicAuthenticationFilter
        log.info("Authentication validation is in progress");

        // Continua la catena dei filtri
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
