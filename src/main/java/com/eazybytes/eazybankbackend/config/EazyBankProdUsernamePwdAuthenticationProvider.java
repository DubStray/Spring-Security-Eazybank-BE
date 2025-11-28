package com.eazybytes.eazybankbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class EazyBankProdUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    // UserDetailsService custom per caricare l'utente dal DB
    private final UserDetailsService userDetailsService;
    // Encoder per confrontare password in ingresso con quella hash salvata
    private final PasswordEncoder passwordEncoder;

    // Autentica un utente confrontando la password con l'hash salvato
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        // Carica i dati dell'utente
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Verifica la password con l'encoder configurato
        if(passwordEncoder.matches(pwd, userDetails.getPassword())) {
            // Ritorna il token autenticato con authorities
            return new UsernamePasswordAuthenticationToken(username, pwd, userDetails.getAuthorities());
        } else {
            // Password errata -> eccezione gestita da Spring Security
            throw new BadCredentialsException("Invalid password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
