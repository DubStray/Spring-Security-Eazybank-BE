package com.eazybytes.eazybankbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
@RequiredArgsConstructor
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    // UserDetailsService custom che recupera l'utente dal DB
    private final UserDetailsService userDetailsService;
    // Encoder password configurato (non usato qui, vedi nota sotto)
    private final PasswordEncoder passwordEncoder;

    // Autentica un utente con username/password (dev: non verifica la password)
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        // Carica i dettagli dell'utente
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // N.B. In questa variante dev la password NON viene verificata.
        // Best practice: usare passwordEncoder.matches(pwd, userDetails.getPassword())
        // come fa la classe prod per evitare accessi senza controllo.
        return new UsernamePasswordAuthenticationToken(username, pwd, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
