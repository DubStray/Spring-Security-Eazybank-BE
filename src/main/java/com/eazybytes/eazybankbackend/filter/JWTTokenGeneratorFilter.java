package com.eazybytes.eazybankbackend.filter;

import com.eazybytes.eazybankbackend.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    // Filtro che genera un JWT per le richieste andate a buon fine e lo aggiunge alla response
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Recupera l'oggetto Authentication popolato dai filtri precedenti
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {

            // Legge la chiave segreta dalle proprietà di ambiente (o usa il default demo)
            Environment env = getEnvironment();
            if (null != env) {

                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);

                // Genera la SecretKey a partire dalla stringa
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                // Costruisce il token JWT con issuer, claims e scadenza
                String jwt = Jwts.builder()
                        .issuer("Eazy Bank")
                        .subject("JWT Token")
                        .claim("username", authentication.getName())
                        .claim("authorities", authentication
                                .getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date((new Date()).getTime() + 30000000))
                        .signWith(secretKey).compact();
                // Espone il token nel response header per il client
                response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Genera il token solo dopo una chiamata riuscita a /user
        return !request.getServletPath().equals("/user");
    }
}
