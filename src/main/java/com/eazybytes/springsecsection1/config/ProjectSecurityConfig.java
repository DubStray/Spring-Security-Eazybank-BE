package com.eazybytes.springsecsection1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
}
