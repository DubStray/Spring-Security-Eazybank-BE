package com.eazybytes.eazybankbackend.config;

import com.eazybytes.eazybankbackend.model.Customer;
import com.eazybytes.eazybankbackend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EazyBankUserDetailsService implements UserDetailsService {

    // Repository per cercare l'utente (Customer) su DB
    private final CustomerRepository customerRepository;

    // Carica un utente tramite username (email) per l'autenticazione
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Cerca il customer per email, altrimenti lancia eccezione gestita da Spring Security
        Customer customer = customerRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User details not found for the user: " + username));

        // Converte le Authority persistite in GrantedAuthority per Spring Security
        List<GrantedAuthority> authorities = customer
                .getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        // Restituisce un User Spring Security con username, password hash e authorities
        return new User(customer.getEmail(), customer.getPwd(), authorities);
    }
}
