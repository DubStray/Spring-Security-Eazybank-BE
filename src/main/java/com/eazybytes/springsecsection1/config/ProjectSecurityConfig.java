package com.eazybytes.springsecsection1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    /*
    lambda -> anyRequest().permiAll() -> Rende accessibile qualsiasi API.
    lambda -> anyRequest().denyAll() -> Rende inaccessibile tutte le API.
    lambda -> request.requestMatchers("/API/") -> rende accessibile o inaccessibile l'API definita nel request matcher
    request.requestMatchers("/API").authenticated -> rende accessibile l'API solo se si é autenticati
    request.requestMatchers("/API").permitAll -> rende accessibile l'API pubblicamente
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error").permitAll());
//        http.formLogin(formLoginConf -> formLoginConf.disable());
//        http.httpBasic(httpBasicConf -> httpBasicConf.disable());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    /*
    Metodo temporaneo (presumo) dove "creare" in memoria, e quindi senza un DB, degli user conb relativi ruoli.
    Si settano lo username, password, autorizazzioni e alla fine si crea con il .build()
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("user")
                        .password("{noop}EazyBytes@12345")
                        .authorities("read")
                        .build();
        UserDetails admin =
                User.withUsername("admin")
                        .password("{bcrypt}$2a$12$X8HQD1QlCYXIafrYSoDZveRhd7wJufKA.sdLS4beiP5em0fUP9odO")
                        .authorities("admin")
                        .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    /*
    Metodo di encoder per la password, di default si dovrebbe utilizzare l'oggetto BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt é di default il metodo migliore per criptare password
//      return new BCryptPasswordEncoder();

        // Tramite costruttore di PasswordEncoderFactories c'é la possibilitá di non seguire lo standard di encrypt per
        // le password (BCrypt). Tuttavia lo si deve specificare {noop} per il plain text, {bcrypt} per bcrypt, etc...
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
    API di IHaveBeenPwnd? per testare la durevolezza di una password e capire se é compromessa o meno
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
