package com.eazybytes.eazybankbackend.filter;

import com.eazybytes.eazybankbackend.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    // Filtro che valida il JWT presente nell'header Authorization e ricostruisce l'Authentication
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Recupera il token JWT dal header Authorization se presente
        String jwt = request.getHeader(ApplicationConstants.JWT_HEADER);
        if(null != jwt) {
            try {
                // Ottiene la chiave segreta dall'env o usa il default per verificare la firma
                Environment env = getEnvironment();
                if (null != env) {
                    String secret = env
                            .getProperty(
                                    ApplicationConstants.JWT_SECRET_KEY,
                                    ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);

                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                    if(null !=secretKey) {
                        // Decodifica e valida il token firmato
                        Claims claims = Jwts.parser()
                                .verifyWith(secretKey)
                                .build().parseSignedClaims(jwt)
                                .getPayload();

                        // Estrae username e authorities dai claims per ricreare l'oggetto Authentication
                        String username = String.valueOf(claims.get("username"));
                        String authorities = String.valueOf(claims.get("authorities"));
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                        // Salva l'Authentication nel SecurityContext per i filtri successivi
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception exception) {
                throw new BadCredentialsException("Invalid Token received!");
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Evita di validare il token durante la chiamata /user che produce il token stesso
        return request.getServletPath().equals("/user");
    }
}
