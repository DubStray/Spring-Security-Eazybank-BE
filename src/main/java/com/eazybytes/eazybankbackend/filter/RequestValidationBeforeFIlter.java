package com.eazybytes.eazybankbackend.filter;


import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.LogRecord;

public class RequestValidationBeforeFIlter implements Filter {

    // Filtro che valida l'header Basic Auth prima dell'autenticazione
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Obiettivo: controllare l'Authorization header "Basic ..." PRIMA di attivare BasicAuthenticationFilter
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        // Recupera l'header Authorization se presente
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (null != header) {
            header = header.trim();

            if(StringUtils.startsWithIgnoreCase(header, "Basic")) {
                // Header "Basic" = base64(user:password)
                // Decodifica credenziali Basic (base64 di user:password)
                byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decoded;

                try {
                    decoded = Base64.getDecoder().decode(base64Token);
                    String token = new String(decoded, StandardCharsets.UTF_8);
                    int delim = token.indexOf(":");
                    if (delim == -1) {
                        // Token malformato
                        throw new BadCredentialsException("Invalid basic authentication token");
                    }
                    String email = token.substring(0, delim);

                    // Blocco demo: se l'email contiene "test" rifiuta la richiesta
                    // (simula una regola di validazione preliminare a qualsiasi tentativo di login)
                    if (email.toLowerCase().contains("test")) {
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                } catch (IllegalArgumentException exception) {
                    // Base64 non decodificabile
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }
            }
        }
        // Prosegue la catena se tutto ok o header assente
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
